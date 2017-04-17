package search.list.maktub.maktubsearchview.utils;

/**
 * Created by dinhdv on 4/12/2017.
 */

public class ObjectMain {
    String name;
    int typeItem = Constans.STATUS_ITEM_SEARCH.TYPE_ITEM_NORMAL;

    public ObjectMain(String name) {
        this.name = name;
        this.typeItem = Constans.STATUS_ITEM_SEARCH.TYPE_ITEM_SEARCH;
    }

    public ObjectMain(String name, int type) {
        this.name = name;
        this.typeItem = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeItem() {
        return typeItem;
    }

    public void setTypeItem(int typeItem) {
        this.typeItem = typeItem;
    }
}
