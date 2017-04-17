package search.list.maktub.maktubsearchview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import search.list.maktub.maktublistsearchview.MaktubListView;
import search.list.maktub.maktubsearchview.adapter.MaktubAdapter;
import search.list.maktub.maktubsearchview.utils.Constans;
import search.list.maktub.maktubsearchview.utils.ObjectMain;

public class MainActivity extends AppCompatActivity {
    private MaktubListView mListView;
    private MaktubAdapter<ObjectMain> adapterMain;
    private List<ObjectMain> mListData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (MaktubListView) findViewById(R.id.list_content);
        mListData = new ArrayList<>();
        // Add object using for view search.
        mListData.add(new ObjectMain(""));
        // Add normal object.
        /**
         * TODO : Noted. If total height item on list < height screen. Add more item to using animation hidden search form.
         */
        for (int i = 0; i < 20; i++) {
            mListData.add(new ObjectMain("Data 1", Constans.STATUS_ITEM_SEARCH.TYPE_ITEM_NORMAL));
        }
        adapterMain = new MaktubAdapter<>(MainActivity.this, mListData);
        mListView.setAdapter(adapterMain);
        adapterMain.setActionSearch(new MaktubAdapter.onActionSearch() {
            @Override
            public void onValuesSearch(String valuesSearch) {
                Toast.makeText(MainActivity.this, "Search : " + valuesSearch, Toast.LENGTH_SHORT).show();
                /**
                 * TODO : Here.
                 * You can request to your server get data.
                 */
            }

            @Override
            public void onCancelSearch() {
                Log.e("Values", "Search Cancel");
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
        hiddenHeaderView(mListView.heightOfViewSearch());
    }

    private void hiddenHeaderView(final int values) {
        mListView.smoothScrollBy(values, 1000);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hiddenHeaderView(mListView.heightOfViewSearch());
    }
}
