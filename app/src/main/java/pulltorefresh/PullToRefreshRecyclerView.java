
package pulltorefresh;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import pulltorefresh.internal.DividerItemDecoration;


/**
 * Created by Vincent on 15/4/6.
 */
public class PullToRefreshRecyclerView extends PullToRefreshBase<RecyclerView> {

	private boolean mPullDownEnable = true;

	private boolean mPullUpEnable = true;

	public PullToRefreshRecyclerView(Context context) {
		super(context);
	}

	public PullToRefreshRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToRefreshRecyclerView(Context context, int mode) {
		super(context, mode);
	}

	@Override
	protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
		RecyclerView recyclerView = new RecyclerView(context, attrs);
		return recyclerView;
	}

	@SuppressWarnings("rawtypes")
	public void setAdapter(RecyclerView.Adapter adapter) {
		refreshableView.setAdapter(adapter);
	}

	public void setItemAnimator(DefaultItemAnimator defaultItemAnimator) {
		refreshableView.setItemAnimator(defaultItemAnimator);

	}

	private LinearLayoutManager mLayoutManager;

	public void setLayoutManager(LinearLayoutManager layoutManager) {
		this.mLayoutManager = layoutManager;
		refreshableView.setLayoutManager(mLayoutManager);

	}



	public void addItemDecoration(DividerItemDecoration dividerItemDecoration) {
		refreshableView.addItemDecoration(dividerItemDecoration);
	}

	/**
	 * 下拉是否可用
	 * 
	 * @param flag
	 *            true可用，false禁用
	 */
	public void setPullDownEnable(boolean flag) {
		mPullDownEnable = flag;
	}

	/**
	 * 上拉是否可用
	 * 
	 * @param flag
	 *            true可用，false禁用
	 */
	public void setPullUpEnable(boolean flag) {
		mPullUpEnable = flag;
	}

	protected boolean isReadyForPullDown() {
		if (!mPullDownEnable)
			return false;
		return isFirstItemVisible();
	}

	protected boolean isReadyForPullUp() {
		if (!mPullUpEnable)
			return false;
		return isLastItemVisible();
	}

	private boolean isFirstItemVisible() {
		if (this.refreshableView.getAdapter().getItemCount() == 0) {
			return true;
		} else if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {

			final View firstVisibleChild = refreshableView.getChildAt(0);

			if (firstVisibleChild != null) {
				return firstVisibleChild.getTop() >= refreshableView.getTop();
			}
		}

		return false;
	}

	private boolean isLastItemVisible() {
		final int count = this.refreshableView.getAdapter().getItemCount();
		final int lastVisiblePosition = mLayoutManager.findLastCompletelyVisibleItemPosition();

		if (count == 0) {
			return true;
		} else if (lastVisiblePosition == count - 1) {

			final int childIndex = lastVisiblePosition - mLayoutManager.findLastVisibleItemPosition();
			final View lastVisibleChild = refreshableView.getChildAt(childIndex);

			if (lastVisibleChild != null) {
				return lastVisibleChild.getBottom() <= refreshableView.getBottom();
			}
		}
		return false;
	}
}
