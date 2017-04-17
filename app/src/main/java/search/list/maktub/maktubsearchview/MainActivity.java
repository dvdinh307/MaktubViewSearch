package search.list.maktub.maktubsearchview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import search.list.maktub.maktublistsearchview.MaktubListView;
import search.list.maktub.maktubsearchview.adapter.MaktubAdapter;
import search.list.maktub.maktubsearchview.utils.Constans;
import search.list.maktub.maktubsearchview.utils.ObjectMain;

public class MainActivity extends AppCompatActivity {
    private MaktubListView mListView;
    private MaktubAdapter adapterMain;
    private List<ObjectMain> mListData;
    // Time delay search
    private int DELAY_WAIT_SEARCH = 2000;
    private Timer mTimer = new Timer();

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
            mListData.add(new ObjectMain("Data " + i, Constans.STATUS_ITEM_SEARCH.TYPE_ITEM_NORMAL));
        }
        adapterMain = new MaktubAdapter(MainActivity.this, mListData);
        mListView.setAdapter(adapterMain);
        adapterMain.setActionSearch(new MaktubAdapter.onActionSearch() {
            @Override
            public void onValuesSearch(final String valuesSearch) {

                Toast.makeText(MainActivity.this, "Search : " + valuesSearch, Toast.LENGTH_SHORT).show();
                /**
                 * TODO : Here.
                 * You can request to your server get data.
                 */
                mTimer.cancel();
                mTimer = new Timer();
                mTimer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterMain.getFilter().filter(valuesSearch);
                                        if (valuesSearch.length() == 0)
                                            adapterMain.resetDataSearch();
                                    }
                                });
                            }
                        }, DELAY_WAIT_SEARCH);
            }

            @Override
            public void onCancelSearch() {
                Log.e("Values", "Search Cancel");
                if (mTimer != null)
                    mTimer.cancel();
                adapterMain.clearSearchContent();
                adapterMain.resetDataSearch();
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
