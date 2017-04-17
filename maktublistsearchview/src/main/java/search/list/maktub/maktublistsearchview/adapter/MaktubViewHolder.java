package search.list.maktub.maktublistsearchview.adapter;

import android.view.View;

public abstract class MaktubViewHolder<T> {
	protected View root;

	public MaktubViewHolder(View root) {
		this.root = root;
		root.setTag(this);
	}
	/**
	 * Bind data of object into view
	 * @param item
	 */
	public abstract void bindData(T item);

	public <T>T findViewById(int resId) {
		return (T) root.findViewById(resId);
	}
}
