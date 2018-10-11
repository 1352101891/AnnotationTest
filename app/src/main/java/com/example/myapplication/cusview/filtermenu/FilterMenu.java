package com.example.myapplication.cusview.filtermenu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class FilterMenu extends LinearLayout {
    GridView gridView;

    public FilterMenu(Context context) {
        super(context);
    }

    public FilterMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FilterMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void InitViews(){
        this.setOrientation(HORIZONTAL);
        this.setWeightSum(2);

        TextView leftText=new TextView(getContext());
        leftText.setText("放款额度");
        leftText.setTextSize(15);
        LinearLayout.LayoutParams layoutParams1=new LayoutParams(0,0,1);
        leftText.setLayoutParams(layoutParams1);

        TextView rightText=new TextView(getContext());
        rightText.setText("放款利息");
        rightText.setTextSize(15);
        LinearLayout.LayoutParams layoutParams2=new LayoutParams(0,0,1);
        rightText.setLayoutParams(layoutParams2);


    }


    public ArrayList<String> getList(){
        ArrayList<String> strings=new ArrayList<>();
        for (int i=0;i<10;i++){
            strings.add("text_"+i);
        }
        return strings;
    }

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popwindow_layout, null);
        PopupWindow mPopWindow = new PopupWindow(contentView,
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        gridView= (GridView) contentView.findViewById(R.id.gridview);
        gridView.setNumColumns(3);
        gridView.setAdapter(new MenuAdapter(getContext(),getList()));
        //显示PopupWindow
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout., null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

}
