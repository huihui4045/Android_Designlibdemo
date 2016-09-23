
package pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ExpandableListView;

import pulltorefresh.internal.EmptyViewMethodAccessor;


public class PullToRefreshExpandableListView extends
        PullToRefreshAdapterViewBase<ExpandableListView> {

    class InternalExpandableListView extends ExpandableListView implements EmptyViewMethodAccessor {

        public InternalExpandableListView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public void setEmptyView(View emptyView) {
            PullToRefreshExpandableListView.this.setEmptyView(emptyView);
        }

        @Override
        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }

        public ContextMenuInfo getContextMenuInfo() {
            return super.getContextMenuInfo();
        }

    }

    public PullToRefreshExpandableListView(Context context) {
        super(context);
    }

    public PullToRefreshExpandableListView(Context context, int mode) {
        super(context, mode);
    }

    public PullToRefreshExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableListView listView;

    @Override
    protected final ExpandableListView createRefreshableView(Context context, AttributeSet attrs) {
        final ExpandableListView lv = new InternalExpandableListView(context, attrs);

        // Set it to this so it can be used in ListActivity/ListFragment
        lv.setId(android.R.id.list);
        listView = lv;
        return lv;
    }

    @Override
    public ContextMenuInfo getContextMenuInfo() {
        return ((InternalExpandableListView)getRefreshableView()).getContextMenuInfo();
    }
}