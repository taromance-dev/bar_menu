package com.taromance.barmenu;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter_Guide extends BaseAdapter {

    String getLanguage_setting;



    public ArrayList<ListViewItem_Guide> listViewItemList_Guide = new ArrayList<ListViewItem_Guide>();

    @Override
    public int getCount() {
        return listViewItemList_Guide.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList_Guide.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_scroll_guide, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득


        final TextView tv_Guide_Main = (TextView) convertView.findViewById(R.id.tv_Guide_Main);
        final TextView tv_Guide_Sub = (TextView) convertView.findViewById(R.id.tv_Guide_Sub);


        LinearLayout layout_scroll_guide = (LinearLayout) convertView.findViewById(R.id.layout_scroll_guide);
        LinearLayout layout_listview_guide = (LinearLayout) convertView.findViewById(R.id.layout_listview_guide);

        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewItem_Guide listViewItem_Guide = listViewItemList_Guide.get(position);


        getLanguage_setting = listViewItem_Guide.getRowLanguage();

        switch (getLanguage_setting)
        {

            case "English" :
                tv_Guide_Main.setText(listViewItem_Guide.getRowGuide_Main_E());
                tv_Guide_Sub.setText(listViewItem_Guide.getRowGuide_Sub_E());
                break;

            case "Korean" :
                tv_Guide_Main.setText(listViewItem_Guide.getRowGuide_Main_K());
                tv_Guide_Sub.setText(listViewItem_Guide.getRowGuide_Sub_K());

                break;

            case "Japanese" :
                tv_Guide_Main.setText(listViewItem_Guide.getRowGuide_Main_J());
                tv_Guide_Sub.setText(listViewItem_Guide.getRowGuide_Sub_J());

                break;

            case "Chinese" :
                tv_Guide_Main.setText(listViewItem_Guide.getRowGuide_Main_C());
                tv_Guide_Sub.setText(listViewItem_Guide.getRowGuide_Sub_C());

                break;

            default:
                break;

        }


        //텍스트 입력 시작

        //tv_Guide_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tv_Guide_Main.setTextColor(Color.BLACK);
        tv_Guide_Sub.setTextColor(Color.BLACK);


        layout_scroll_guide.setBackgroundColor(listViewItem_Guide.getRowcolor());


        return convertView;
    }


    public void addItem(String Language,
                        String Guide_Main_E, String Guide_Main_K, String Guide_Main_J, String Guide_Main_C,
                        String Guide_Sub_E, String Guide_Sub_K, String Guide_Sub_J, String Guide_Sub_C)
    {
        ListViewItem_Guide item = new ListViewItem_Guide();

        item.setRowLanguage(Language);
        item.setRowGuide_Main_E(Guide_Main_E);
        item.setRowGuide_Main_K(Guide_Main_K);
        item.setRowGuide_Main_J(Guide_Main_J);
        item.setRowGuide_Main_C(Guide_Main_C);
        item.setRowGuide_Sub_E(Guide_Sub_E);
        item.setRowGuide_Sub_K(Guide_Sub_K);
        item.setRowGuide_Sub_J(Guide_Sub_J);
        item.setRowGuide_Sub_C(Guide_Sub_C);


        listViewItemList_Guide.add(item);
    }


    public void clearItem(){
        listViewItemList_Guide.clear();
    }

    public void remove(int position) {
        listViewItemList_Guide.remove(position);

    }
}
