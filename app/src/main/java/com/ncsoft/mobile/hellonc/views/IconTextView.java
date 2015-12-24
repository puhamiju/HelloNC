package com.ncsoft.mobile.hellonc.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ncsoft.mobile.hellonc.R;

/**
 * Created by puhamiju on 2015-10-02.
 */
public class IconTextView extends LinearLayout{

    /**
     * Icon
     */
    private ImageView mIcon;

    /**
     * TextView 01
     */
    private TextView mText01;

    /**
     * TextView 02
     */
    private TextView mText02;

    /**
     * TextView 03
     */
    private TextView mText03;

    /**
     * TextView 04
     */
    private TextView mText04;

    private String url;

    public IconTextView(Context context, IconTextItem aItem) {
        super(context);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listitem, this, true);

        mIcon = (ImageView) findViewById(R.id.iconItem);
//        mIcon.setImageDrawable(aItem.getIcon());
//        mIcon.setImageURI(parse("http://static.plaync.co.kr/gaiaupload/BladeNSoul/main/5015/wing_119x18920151203113357.png"));


        mText01 = (TextView) findViewById(R.id.dataItem01);
        mText01.setText(aItem.getData(0));
        // Set Text 02
        mText02 = (TextView) findViewById(R.id.dataItem02);
        mText02.setText(aItem.getData(1));

        // Set Text 03
        mText03 = (TextView) findViewById(R.id.dataItem03);
        mText03.setText(aItem.getData(2));

        // Set Text 04
        mText04 = (TextView) findViewById(R.id.dataItem04);
        mText04.setText(aItem.getData(3));

    }

    public void setText(int index, String data) {
        if(index == 0) {
            mText01.setText(data);
        } else if (index == 1) {
            mText02.setText(data);
        } else if (index == 2) {
            mText03.setText(data);
        } else if (index == 3) {
            mText04.setText(data);
        } else if (index == 4) {
           url = data;
        } else {
            throw new IllegalArgumentException();
        }

    }

    public void setIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);

    }

    public ImageView getIcon() {

        return mIcon;

    }

}
