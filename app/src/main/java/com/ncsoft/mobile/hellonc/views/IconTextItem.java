package com.ncsoft.mobile.hellonc.views;

/**
 * Created by puhamiju on 2015-10-06.
 */
public class IconTextItem {

    private String mIcon;
    private String[] mData;

    /**
     * True if this item is selectable
     */
    private boolean mSelectable = true;

    public IconTextItem(String icon, String[] obj) {
        mIcon = icon;
        mData = obj;
    }

    public IconTextItem (String icon, String obj01, String obj02, String obj03, String obj04, String obj05) {
        mIcon = icon;

        mData = new String[5];
        mData[0] = obj01;
        mData[1] = obj02;
        mData[2] = obj03;
        mData[3] = obj04;
        mData[4] = obj05;

    }

    /**
     * True if this item is selectable
     */
    public boolean isSelectable() {
        return mSelectable;
    }
    /**
     * Set selectable flag
     */
    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    public String[] getData() {
        return mData;
    }


    public String getData(int index) {
        if(mData == null || index >= mData.length) {
            return null;
        }

        return mData[index];

    }

    public void setData(String[] obj) {
        mData = obj;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getIcon() {
        return mIcon;
    }
    /**
     * Compare with the input object
     *
     * @param other
     * @return
     */
    public int compareTo(IconTextItem other) {
        if (mData != null) {
            String[] otherData = other.getData();
            if (mData.length == otherData.length) {
                for (int i = 0; i < mData.length; i++) {
                    if (!mData[i].equals(otherData[i])) {
                        return -1;
                    }
                }
            } else {
                return -1;
            }
        } else {
            throw new IllegalArgumentException();
        }

        return 0;
    }
}
