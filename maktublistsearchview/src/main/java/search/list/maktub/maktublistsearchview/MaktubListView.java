package search.list.maktub.maktublistsearchview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by Maktub on 3/22/2017.
 */

public class MaktubListView extends android.widget.ListView implements View.OnTouchListener {

    private OnScrollListener onScrollListener;
    private int mHeightViewSearch = 0;
    private int mStatusList = Constants.STATUS_SEARCH.DEFAULT;
    private View mViewSearch;
    private boolean clickDetected = false;
    private boolean mIsScrollList = false;

    public MaktubListView(Context context) {
        super(context);
        onCreate(context, null, null);
    }

    public MaktubListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate(context, attrs, null);
    }

    public MaktubListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        onCreate(context, attrs, defStyle);
    }

    @SuppressWarnings("UnusedParameters")
    private void onCreate(Context context, AttributeSet attrs, Integer defStyle) {
        setListeners();
        this.setOnTouchListener(this);
        if (mHeightViewSearch > 0)
            hiddenHeaderView(mHeightViewSearch);
    }

    private void setListeners() {
        super.setOnScrollListener(new OnScrollListener() {

            private int oldTop;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                    if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                        mIsScrollList = false;
                    } else if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        mIsScrollList = true;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
                onDetectedListScroll(view, firstVisibleItem);
            }

            private void onDetectedListScroll(AbsListView absListView, int firstVisibleItem) {
                mViewSearch = absListView.getChildAt(0);
                int top = (mViewSearch == null) ? 0 : mViewSearch.getTop();
                if (mViewSearch == null)
                    return;
                mHeightViewSearch = mViewSearch.getMeasuredHeight();
                // Only check when search view show.
                if (firstVisibleItem == 0) {
                    if (top > oldTop) {
                        if (Math.abs(top) >= mViewSearch.getMeasuredHeight() / 3) {
                            mStatusList = Constants.STATUS_SEARCH.SHOW;
                        }

                    } else if (top < oldTop) {
                        if (Math.abs(top) >= mViewSearch.getMeasuredHeight() / 3) {
                            mStatusList = Constants.STATUS_SEARCH.HIDDEN;
                        }
                    }
                } else {
                    mStatusList = Constants.STATUS_SEARCH.DEFAULT;
                }
                oldTop = top;
            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int action = motionEvent.getAction();
        final int x = (int) motionEvent.getX();
        final int y = (int) motionEvent.getY();
        if (action == MotionEvent.ACTION_DOWN) {
            clickDetected = false;
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (mIsScrollList) {
                if (mStatusList == Constants.STATUS_SEARCH.SHOW) {
                    clickDetected = false;
                    showHeaderView();
                } else if (mStatusList == Constants.STATUS_SEARCH.HIDDEN) {
                    clickDetected = false;
                    hiddenHeaderView(mHeightViewSearch);
                } else {
                    clickDetected = true;
                }
            } else {
                if (clickDetected) {
                    int position = pointToPosition(x, y) - getFirstVisiblePosition();
                    View viewClick = getChildAt(position);
                    if (viewClick != null) {
                        long id = viewClick.getId();
                        this.performItemClick(viewClick, position, id);//pass control back to fragment/activity
                    }
                }
                clickDetected = true;
            }
        }
        return false;
    }

    private void hiddenHeaderView(final int values) {
        this.smoothScrollBy(values - Math.abs(mViewSearch.getTop()), 1000);
        this.setFocusable(true);
    }

    private void showHeaderView() {
        this.smoothScrollToPosition(0);
        this.setFocusable(true);
    }

    public int heightOfViewSearch() {
        return mViewSearch == null ? 0 : mViewSearch.getMeasuredHeight();
    }
}