/*
 * Copyright (C) 2013 Sergej Shafarenka, halfbit.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file kt in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.teplot.app.dybc.fragment.adress;

import com.trplot.app.dybs.BuildConfig;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

/**
 * 列表视图,能够销部分视图在顶部,其余的则仍在滚动。
 */
public class PinnedSectionListView extends ListView {

    //-- inner classes

	/** 列表适配器实现的用于PinnedSectionListView适配器 */
	public static interface PinnedSectionListAdapter extends ListAdapter {
		/** 这个方法将返回“true”如果给定类型的观点必须是固定的。 */
		boolean isItemViewTypePinned(int viewType);
	}

	/** 包装类固定部分视图和它的位置在列表中. */
	static class PinnedSection {
		public View view;
		public int position;
		public long id;
	}

	//-- class fields

    // 字段用于处理触摸事件
    private final Rect mTouchRect = new Rect();
    private final PointF mTouchPoint = new PointF();
    private int mTouchSlop;
    private View mTouchTarget;
    private MotionEvent mDownEvent;

    // 字段用于绘制阴影下固定部分
    private GradientDrawable mShadowDrawable;
    private int mSectionsDistanceY;
    private int mShadowHeight;

    /** 委托侦听器,可以为空。 */
    OnScrollListener mDelegateOnScrollListener;

    /** 影子被回收,可以为空。 */
    PinnedSection mRecycleSection;

    /** 影子与固定视图实例,可以为空。 */
    PinnedSection mPinnedSection;

    /*** Y-translation固定视图。我们用它来固定在下一节。 */
    int mTranslateY;

	/** 这魔法卷轴侦听器*/
	private final OnScrollListener mOnScrollListener = new OnScrollListener() {

		@Override public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (mDelegateOnScrollListener != null) { // delegate
				mDelegateOnScrollListener.onScrollStateChanged(view, scrollState);
			}
		}

		@Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            if (mDelegateOnScrollListener != null) { // delegate
                mDelegateOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            // 得到预期的适配器或快速失败
            ListAdapter adapter = getAdapter();
            if (adapter == null || visibleItemCount == 0) return; // nothing to do

            final boolean isFirstVisibleItemSection =
                    isItemViewTypePinned(adapter, adapter.getItemViewType(firstVisibleItem));

            if (isFirstVisibleItemSection) {
                View sectionView = getChildAt(0);
                if (sectionView.getTop() == getPaddingTop()) { // view sticks to the top, no need for pinned shadow
                    destroyPinnedShadow();
                } else { // section doesn't stick to the top, make sure we have a pinned shadow
                    ensureShadowForPosition(firstVisibleItem, firstVisibleItem, visibleItemCount);
                }
            } else { // section is not at the first visible position
                int sectionPosition = findCurrentSectionPosition(firstVisibleItem);
                if (sectionPosition > -1) { // we have section position
                    ensureShadowForPosition(sectionPosition, firstVisibleItem, visibleItemCount);
                } else { // there is no section for the first visible item, destroy shadow
                    destroyPinnedShadow();
                }
            }
		};

	};

	/** 默认改变观察者 */
    private final DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override public void onChanged() {
            recreatePinnedShadow();
        };
        @Override public void onInvalidated() {
            recreatePinnedShadow();
        }
    };

	//-- 构造函数

    public PinnedSectionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PinnedSectionListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        setOnScrollListener(mOnScrollListener);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        initShadow(true);
    }

    //-- 公共API方法

    public void setShadowVisible(boolean visible) {
        initShadow(visible);
        if (mPinnedSection != null) {
            View v = mPinnedSection.view;
            invalidate(v.getLeft(), v.getTop(), v.getRight(), v.getBottom() + mShadowHeight);
        }
    }

    //-- 固定剖面图的方法

    public void initShadow(boolean visible) {
        if (visible) {
            if (mShadowDrawable == null) {
                mShadowDrawable = new GradientDrawable(Orientation.TOP_BOTTOM,
                        new int[] { Color.parseColor("#ffa0a0a0"), Color.parseColor("#50a0a0a0"), Color.parseColor("#00a0a0a0")});
                mShadowHeight = (int) (8 * getResources().getDisplayMetrics().density);
            }
        } else {
            if (mShadowDrawable != null) {
                mShadowDrawable = null;
                mShadowHeight = 0;
            }
        }
    }

	/** 与固定视图的视图创建阴影包装在给定的位置 */
	void createPinnedShadow(int position) {

		// try to recycle shadow
		PinnedSection pinnedShadow = mRecycleSection;
		mRecycleSection = null;

		// create new shadow, if needed
		if (pinnedShadow == null) pinnedShadow = new PinnedSection();
		// request new view using recycled view, if such
		View pinnedView = getAdapter().getView(position, pinnedShadow.view, PinnedSectionListView.this);

		// read layout parameters
		LayoutParams layoutParams = (LayoutParams) pinnedView.getLayoutParams();
		if (layoutParams == null) {
//		    layoutParams = (LayoutParams) generateDefaultLayoutParams();
			// boredream-fix bug
			layoutParams = (AbsListView.LayoutParams) new AbsListView.LayoutParams(
	                AbsListView.LayoutParams.MATCH_PARENT,
	                AbsListView.LayoutParams.WRAP_CONTENT, 0); 
		    pinnedView.setLayoutParams(layoutParams);
		}

		int heightMode = MeasureSpec.getMode(layoutParams.height);
		int heightSize = MeasureSpec.getSize(layoutParams.height);

		if (heightMode == MeasureSpec.UNSPECIFIED) heightMode = MeasureSpec.EXACTLY;

		int maxHeight = getHeight() - getListPaddingTop() - getListPaddingBottom();
		if (heightSize > maxHeight) heightSize = maxHeight;

		// measure & layout
		int ws = MeasureSpec.makeMeasureSpec(getWidth() - getListPaddingLeft() - getListPaddingRight(), MeasureSpec.EXACTLY);
		int hs = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
		pinnedView.measure(ws, hs);
		pinnedView.layout(0, 0, pinnedView.getMeasuredWidth(), pinnedView.getMeasuredHeight());
		mTranslateY = 0;

		// 初始化固定的影子
		pinnedShadow.view = pinnedView;
		pinnedShadow.position = position;
		pinnedShadow.id = getAdapter().getItemId(position);

		// 存储固定的影子
		mPinnedSection = pinnedShadow;
	}

	/**毁灭阴影包装目前固定视图*/
	void destroyPinnedShadow() {
	    if (mPinnedSection != null) {
	        // 以后让影子被回收
	        mRecycleSection = mPinnedSection;
	        mPinnedSection = null;
	    }
	}

	/**确保我们有一个实际固定阴影位置。 */
    void ensureShadowForPosition(int sectionPosition, int firstVisibleItem, int visibleItemCount) {
        if (visibleItemCount < 2) { // no need for creating shadow at all, we have a single visible item
            destroyPinnedShadow();
            return;
        }

        if (mPinnedSection != null
                && mPinnedSection.position != sectionPosition) { // invalidate shadow, if required
            destroyPinnedShadow();
        }

        if (mPinnedSection == null) { // create shadow, if empty
            createPinnedShadow(sectionPosition);
        }

        // 根据下一节的位置对齐的影子,如果必要的
        int nextPosition = sectionPosition + 1;
        if (nextPosition < getCount()) {
            int nextSectionPosition = findFirstVisibleSectionPosition(nextPosition,
                    visibleItemCount - (nextPosition - firstVisibleItem));
            if (nextSectionPosition > -1) {
                View nextSectionView = getChildAt(nextSectionPosition - firstVisibleItem);
                final int bottom = mPinnedSection.view.getBottom() + getPaddingTop();
                mSectionsDistanceY = nextSectionView.getTop() - bottom;
                if (mSectionsDistanceY < 0) {
                    // next section overlaps pinned shadow, move it up
                    mTranslateY = mSectionsDistanceY;
                } else {
                    // next section does not overlap with pinned, stick to top
                    mTranslateY = 0;
                }
            } else {
                // no other sections are visible, stick to top
                mTranslateY = 0;
                mSectionsDistanceY = Integer.MAX_VALUE;
            }
        }

    }

	int findFirstVisibleSectionPosition(int firstVisibleItem, int visibleItemCount) {
		ListAdapter adapter = getAdapter();

        int adapterDataCount = adapter.getCount();
        if (getLastVisiblePosition() >= adapterDataCount) return -1; // dataset has changed, no candidate

        if (firstVisibleItem+visibleItemCount >= adapterDataCount){//added to prevent index Outofbound (in case)
            visibleItemCount = adapterDataCount-firstVisibleItem;
        }

		for (int childIndex = 0; childIndex < visibleItemCount; childIndex++) {
			int position = firstVisibleItem + childIndex;
			int viewType = adapter.getItemViewType(position);
			if (isItemViewTypePinned(adapter, viewType)) return position;
		}
		return -1;
	}

	int findCurrentSectionPosition(int fromPosition) {
		ListAdapter adapter = getAdapter();

		if (fromPosition >= adapter.getCount()) return -1; // dataset has changed, no candidate
		
		if (adapter instanceof SectionIndexer) {
			// try fast way by asking section indexer
			SectionIndexer indexer = (SectionIndexer) adapter;
			int sectionPosition = indexer.getSectionForPosition(fromPosition);
			int itemPosition = indexer.getPositionForSection(sectionPosition);
			int typeView = adapter.getItemViewType(itemPosition);
			if (isItemViewTypePinned(adapter, typeView)) {
				return itemPosition;
			} // else, no luck
		}

		// try slow way by looking through to the next section item above
		for (int position=fromPosition; position>=0; position--) {
			int viewType = adapter.getItemViewType(position);
			if (isItemViewTypePinned(adapter, viewType)) return position;
		}
		return -1; // no candidate found
	}

	void recreatePinnedShadow() {
	    destroyPinnedShadow();
        ListAdapter adapter = getAdapter();
        if (adapter != null && adapter.getCount() > 0) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int sectionPosition = findCurrentSectionPosition(firstVisiblePosition);
            if (sectionPosition == -1) return; // no views to pin, exit
            ensureShadowForPosition(sectionPosition,
                    firstVisiblePosition, getLastVisiblePosition() - firstVisiblePosition);
        }
	}

	@Override
	public void setOnScrollListener(OnScrollListener listener) {
		if (listener == mOnScrollListener) {
			super.setOnScrollListener(listener);
		} else {
			mDelegateOnScrollListener = listener;
		}
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		super.onRestoreInstanceState(state);
		post(new Runnable() {
			@Override public void run() { // restore pinned view after configuration change
			    recreatePinnedShadow();
			}
		});
	}

	@Override
	public void setAdapter(ListAdapter adapter) {

	    // assert adapter in debug mode
		if (BuildConfig.DEBUG && adapter != null) {
			if (!(adapter instanceof PinnedSectionListAdapter))
				throw new IllegalArgumentException("Does your adapter implement PinnedSectionListAdapter?");
			if (adapter.getViewTypeCount() < 2)
				throw new IllegalArgumentException("Does your adapter handle at least two types" +
						" of views in getViewTypeCount() method: items and sections?");
		}

		// unregister observer at old adapter and register on new one
		ListAdapter oldAdapter = getAdapter();
		if (oldAdapter != null) oldAdapter.unregisterDataSetObserver(mDataSetObserver);
		if (adapter != null) adapter.registerDataSetObserver(mDataSetObserver);

		// destroy pinned shadow, if new adapter is not same as old one
		if (oldAdapter != adapter) destroyPinnedShadow();

		super.setAdapter(adapter);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	    super.onLayout(changed, l, t, r, b);
        if (mPinnedSection != null) {
            int parentWidth = r - l - getPaddingLeft() - getPaddingRight();
            int shadowWidth = mPinnedSection.view.getWidth();
            if (parentWidth != shadowWidth) {
                recreatePinnedShadow();
            }
        }
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);

		if (mPinnedSection != null) {

			// prepare variables
			int pLeft = getListPaddingLeft();
			int pTop = getListPaddingTop();
			View view = mPinnedSection.view;

			// draw child
			canvas.save();

			int clipHeight = view.getHeight() +
			        (mShadowDrawable == null ? 0 : Math.min(mShadowHeight, mSectionsDistanceY));
			canvas.clipRect(pLeft, pTop, pLeft + view.getWidth(), pTop + clipHeight);

			canvas.translate(pLeft, pTop + mTranslateY);
			drawChild(canvas, mPinnedSection.view, getDrawingTime());

			if (mShadowDrawable != null && mSectionsDistanceY > 0) {
			    mShadowDrawable.setBounds(mPinnedSection.view.getLeft(),
			            mPinnedSection.view.getBottom(),
			            mPinnedSection.view.getRight(),
			            mPinnedSection.view.getBottom() + mShadowHeight);
			    mShadowDrawable.draw(canvas);
			}

			canvas.restore();
		}
	}

	//-- touch handling methods

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        final float x = ev.getX();
        final float y = ev.getY();
        final int action = ev.getAction();

        if (action == MotionEvent.ACTION_DOWN
                && mTouchTarget == null
                && mPinnedSection != null
                && isPinnedViewTouched(mPinnedSection.view, x, y)) { // create touch target

            // user touched pinned view
            mTouchTarget = mPinnedSection.view;
            mTouchPoint.x = x;
            mTouchPoint.y = y;

            // copy down event for eventually be used later
            mDownEvent = MotionEvent.obtain(ev);
        }

        if (mTouchTarget != null) {
            if (isPinnedViewTouched(mTouchTarget, x, y)) { // forward event to pinned view
                mTouchTarget.dispatchTouchEvent(ev);
            }

            if (action == MotionEvent.ACTION_UP) { // perform onClick on pinned view
                super.dispatchTouchEvent(ev);
                performPinnedItemClick();
                clearTouchTarget();

            } else if (action == MotionEvent.ACTION_CANCEL) { // cancel
                clearTouchTarget();

            } else if (action == MotionEvent.ACTION_MOVE) {
                if (Math.abs(y - mTouchPoint.y) > mTouchSlop) {

                    // cancel sequence on touch target
                    MotionEvent event = MotionEvent.obtain(ev);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    mTouchTarget.dispatchTouchEvent(event);
                    event.recycle();

                    // provide correct sequence to super class for further handling
                    super.dispatchTouchEvent(mDownEvent);
                    super.dispatchTouchEvent(ev);
                    clearTouchTarget();

                }
            }

            return true;
        }

        // call super if this was not our pinned view
        return super.dispatchTouchEvent(ev);
    }

    private boolean isPinnedViewTouched(View view, float x, float y) {
        view.getHitRect(mTouchRect);

        // by taping top or bottom padding, the list performs on click on a border item.
        // we don't add top padding here to keep behavior consistent.
        mTouchRect.top += mTranslateY;

        mTouchRect.bottom += mTranslateY + getPaddingTop();
        mTouchRect.left += getPaddingLeft();
        mTouchRect.right -= getPaddingRight();
        return mTouchRect.contains((int)x, (int)y);
    }

    private void clearTouchTarget() {
        mTouchTarget = null;
        if (mDownEvent != null) {
            mDownEvent.recycle();
            mDownEvent = null;
        }
    }

    private boolean performPinnedItemClick() {
        if (mPinnedSection == null) return false;

        OnItemClickListener listener = getOnItemClickListener();
        if (listener != null && getAdapter().isEnabled(mPinnedSection.position)) {
            View view =  mPinnedSection.view;
            playSoundEffect(SoundEffectConstants.CLICK);
            if (view != null) {
                view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
            }
            listener.onItemClick(this, view, mPinnedSection.position, mPinnedSection.id);
            return true;
        }
        return false;
    }

    public static boolean isItemViewTypePinned(ListAdapter adapter, int viewType) {
        if (adapter instanceof HeaderViewListAdapter) {
            adapter = ((HeaderViewListAdapter)adapter).getWrappedAdapter();
        }
        return ((PinnedSectionListAdapter) adapter).isItemViewTypePinned(viewType);
    }

}