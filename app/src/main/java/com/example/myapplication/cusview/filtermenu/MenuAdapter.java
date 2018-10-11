package com.example.myapplication.cusview.filtermenu;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    private ArrayList<String> mNameList = new ArrayList<String>();
    private LayoutInflater mInflater;
    private Context mContext;


    public MenuAdapter(Context context, ArrayList<String> nameList) {
        mNameList = nameList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mNameList.size();
    }

    public Object getItem(int position) {
        return mNameList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gridview_item, null);
            TextView textView= (TextView) convertView.findViewById(R.id.text);
            viewTag=new ItemViewTag(textView);
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }

        // set name
        viewTag.mName.setText(mNameList.get(position));

        return convertView;
    }


    class ItemViewTag
    {
        protected TextView mName;

        /**
         * The constructor to construct a navigation view tag
         *
         * @param name
         *            the name view of the item

         */
        public ItemViewTag(TextView name)
        {
            this.mName = name;
        }
    }
}