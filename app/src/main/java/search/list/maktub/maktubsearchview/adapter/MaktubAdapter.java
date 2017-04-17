package search.list.maktub.maktubsearchview.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import search.list.maktub.maktublistsearchview.R;
import search.list.maktub.maktublistsearchview.adapter.MaktubViewHolder;
import search.list.maktub.maktubsearchview.utils.Constans;
import search.list.maktub.maktubsearchview.utils.ObjectMain;

/**
 * Created by Maktub on 7/19/2016.
 */
public class MaktubAdapter<T> extends ArrayAdapter<T> {
    private Activity activity;
    private List<T> mList;
    DisplayMetrics displayMetrics;
    private onActionSearch mActionSearch;
    private String mKeyClearSearch = "Clear";

    public MaktubAdapter(Activity activity, List<T> list) {
        super(activity, 0, list);
        this.activity = activity;
        mList = list;
        displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        MaktubHolder holder;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                convertView = View.inflate(activity, R.layout.view_search, null);
            } else {
                convertView = View.inflate(activity, R.layout.item_main_list, null);
            }
            holder = new MaktubHolder(convertView, position);
        } else {
            holder = (MaktubHolder) convertView.getTag();
        }
        if (position > 0)
            holder.bindData((ObjectMain) mList.get(position));
        else {
            if (mList.get(0) != null && ((ObjectMain) mList.get(0)).getName() != null && ((ObjectMain) mList.get(0)).getName().equalsIgnoreCase(mKeyClearSearch) && holder.mEdtSearch != null) {
                holder.mEdtSearch.setText("");
                holder.mTvClear.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    private class MaktubHolder extends MaktubViewHolder<ObjectMain> {
        TextView tvGroupName;
//        RelativeLayout layoutContainer;
        EditText mEdtSearch;
        TextView mTvClear;

        public MaktubHolder(View root, int position) {
            super(root);
            if (position > 0) {
                tvGroupName = findViewById(R.id.tl_title);
//                int width = displayMetrics.widthPixels;
//                AbsListView.LayoutParams params = new AbsListView.LayoutParams(
//                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//                params.width = width;
//                params.height = (width * 8) / 15;
//                layoutContainer.setLayoutParams(params);
            } else {
                mEdtSearch = findViewById(R.id.edt_search);
                mEdtSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
                mEdtSearch.setSingleLine(true);
                mTvClear = findViewById(R.id.tv_clear);
                mEdtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if (editable.toString().length() > 0) {
                            mTvClear.setVisibility(editable.toString().trim().length() > 0 ? View.VISIBLE : View.GONE);
                            if (editable.toString().trim().length() > 0)
                                if (mActionSearch != null)
                                    try {
                                        mActionSearch.onValuesSearch(URLEncoder.encode(editable.toString().trim(), "utf-8"));
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                        }
                    }
                });
                mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            if (mEdtSearch.getText().toString().trim().length() > 0)
                                if (mActionSearch != null)
                                    try {
                                        mActionSearch.onValuesSearch(URLEncoder.encode(mEdtSearch.toString().trim(), "utf-8"));
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                            return true;
                        }
                        return false;
                    }
                });
                mTvClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mEdtSearch.setText("");
                        mTvClear.setVisibility(View.GONE);
                        if (mActionSearch != null)
                            mActionSearch.onCancelSearch();
                    }
                });
            }
        }

        @Override
        public void bindData(final ObjectMain item) {
            if (item.getTypeItem() == Constans.STATUS_ITEM_SEARCH.TYPE_ITEM_NORMAL) {
                tvGroupName.setText(item.getName());
            }

        }
    }

    public void setActionSearch(onActionSearch actionSearch) {
        mActionSearch = actionSearch;
    }

    public interface onActionSearch {
        void onValuesSearch(String valuesSearch);

        void onCancelSearch();

    }

    // Clear text search when need.
    public void clearSearchContent() {
        ((ObjectMain) mList.get(0)).setName(mKeyClearSearch);
        notifyDataSetChanged();
    }
}
