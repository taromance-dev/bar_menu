package com.taromance.barmenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {


    public ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    String getLanguage_setting;

    String[] splited_Price_Glass;
    String[] splited_Price_Bottle;

    String getNumber;
    String getCode;
    String getProperty_Menu_Main;
    String getProperty_Menu_Sub;


    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
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
            convertView = inflater.inflate(R.layout.listview_scroll, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득


        final TextView tv_Name_Main = (TextView) convertView.findViewById(R.id.tv_Name_Main);
        final TextView tv_Name_Sub = (TextView) convertView.findViewById(R.id.tv_Name_Sub);
        final TextView tv_Price_Glass = (TextView) convertView.findViewById(R.id.tv_Price_Glass);
        final TextView tv_Price_Bottle = (TextView) convertView.findViewById(R.id.tv_Price_Bottle);


        LinearLayout layout_scroll = (LinearLayout) convertView.findViewById(R.id.layout_scroll);
        LinearLayout layout_listview = (LinearLayout) convertView.findViewById(R.id.layout_listview);

        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewItem listViewItem = listViewItemList.get(position);


        getLanguage_setting = listViewItem.getRowLanguage();

        switch (getLanguage_setting)
        {

            case "English" :
                tv_Name_Main.setText(listViewItem.getRowName_E());

                break;

            case "Korean" :
                tv_Name_Main.setText(listViewItem.getRowName_K());

                break;

            case "Japanese" :
                tv_Name_Main.setText(listViewItem.getRowName_J());

                break;

            case "Chinese" :
                tv_Name_Main.setText(listViewItem.getRowName_C());

                break;

            default:
                break;

        }
        
        
        //텍스트 입력 시작
        
        

        tv_Name_Sub.setText(listViewItem.getRowName_Sub());

        //tv_Price_Glass.setText(listViewItem.getRowPrice_Glass());
        //tv_Price_Bottle.setText(listViewItem.getRowPrice_Bottle());

        splited_Price_Glass = listViewItem.getRowPrice_Glass().split(",");
        splited_Price_Bottle = listViewItem.getRowPrice_Bottle().split(",");



        if(listViewItem.getRowCategory().equals("Blank")){
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 5);
            tv_Name_Sub.setTextSize(TypedValue.COMPLEX_UNIT_SP, 5);
            tv_Price_Glass.setTextSize(TypedValue.COMPLEX_UNIT_SP, 5);
            tv_Price_Bottle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 5);
        }
        else{
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            tv_Name_Sub.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tv_Price_Glass.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tv_Price_Bottle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        }

        if(listViewItem.getRowStock_Glass().equals("0")&&listViewItem.getRowStock_Bottle().equals("0")){
            tv_Price_Glass.setText("Sold");
            tv_Price_Bottle.setText("Out");
            tv_Price_Glass.setTextColor(Color.parseColor("#440000"));
            tv_Price_Bottle.setTextColor(Color.parseColor("#440000"));
            //tv_nameE.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //tv_nameK.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //tv_subname.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //tv_nameE.setTextColor(Color.GRAY);
            //tv_nameK.setTextColor(Color.GRAY);
            //tv_subname.setTextColor(Color.GRAY);

            tv_Name_Main.setTextColor(Color.BLACK);
            //tv_nameK.setTextColor(Color.BLACK);
            tv_Name_Sub.setTextColor(Color.BLACK);
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        }
        else if(listViewItem.getRowStock_Bottle().equals("0")){
            splited_Price_Glass = listViewItem.getRowPrice_Glass().split(",");
            tv_Price_Glass.setText(splited_Price_Glass[0]);
            tv_Price_Bottle.setText("-   ");
            tv_Price_Glass.setTextColor(Color.BLACK);
            tv_Price_Bottle.setTextColor(Color.parseColor("#440000"));
            //tv_nameE.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //tv_nameK.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //tv_subname.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //tv_nameE.setTextColor(Color.GRAY);
            //tv_nameK.setTextColor(Color.GRAY);
            //tv_subname.setTextColor(Color.GRAY);

            tv_Name_Main.setTextColor(Color.BLACK);
            //tv_nameK.setTextColor(Color.BLACK);
            tv_Name_Sub.setTextColor(Color.BLACK);
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);



        }

        else if(listViewItem.getRowStock_Glass().equals("0")){
            splited_Price_Bottle = listViewItem.getRowPrice_Bottle().split(",");
            tv_Price_Bottle.setText(splited_Price_Bottle[0]);
            tv_Price_Glass.setText("");
            tv_Price_Bottle.setTextColor(Color.BLACK);
            tv_Price_Glass.setTextColor(Color.parseColor("#440000"));
            //tv_nameE.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //tv_nameK.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //tv_subname.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //tv_nameE.setTextColor(Color.GRAY);
            //tv_nameK.setTextColor(Color.GRAY);
            //tv_subname.setTextColor(Color.GRAY);

            tv_Name_Main.setTextColor(Color.BLACK);
            //tv_nameK.setTextColor(Color.BLACK);
            tv_Name_Sub.setTextColor(Color.BLACK);
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);



        }
        else if(listViewItem.getRowCategory().equals("Title")){

            tv_Price_Glass.setText(listViewItem.getRowPrice_Glass());
            tv_Price_Bottle.setText(listViewItem.getRowPrice_Bottle());
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
            tv_Name_Main.setTextColor(Color.parseColor("#000033"));
            tv_Name_Sub.setTextColor(Color.parseColor("#000033"));



        }

        // 정상 출력
        else{

            // 가격 ,000 문자열 제거
/*            if(listViewItem.getRowPrice_Glass() != "" || listViewItem.getRowPrice_Bottle() != "")
            {

            }

            else{
                tv_Price_Glass.setText(listViewItem.getRowPrice_Glass());
                tv_Price_Bottle.setText(listViewItem.getRowPrice_Bottle());
            }*/

            splited_Price_Glass = listViewItem.getRowPrice_Glass().split(",");
            splited_Price_Bottle = listViewItem.getRowPrice_Bottle().split(",");

            tv_Price_Glass.setText(splited_Price_Glass[0]);
            tv_Price_Bottle.setText(splited_Price_Bottle[0]);

            tv_Price_Glass.setTextColor(Color.BLACK);
            tv_Price_Bottle.setTextColor(Color.BLACK);
            //tv_nameE.setPaintFlags(0);
            //tv_nameK.setPaintFlags(0);
            //tv_subname.setPaintFlags(0);
            tv_Name_Main.setTextColor(Color.BLACK);
            //tv_nameK.setTextColor(Color.BLACK);
            tv_Name_Sub.setTextColor(Color.BLACK);
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);


        }




        layout_scroll.setBackgroundColor(listViewItem.getRowcolor());



        //리스트 아이템 팝업 연결


        layout_listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(listViewItem.getRowCategory().equals("Blank")||listViewItem.getRowCategory().equals("Title"))
            {
                return;
            }
            else
            {

                //리스트 아이템 팝업 연결


                getNumber = listViewItem.getRowNumber();
                getCode = listViewItem.getRowCode();
                getProperty_Menu_Main = listViewItem.getRowProperty_Menu_Main();
                getProperty_Menu_Sub = listViewItem.getRowProperty_Menu_Sub();


                Intent intent = new Intent(context, PopupActivityInfo.class);
                intent.putExtra("Number",getNumber);
                intent.putExtra("Code",getCode);
                intent.putExtra("Property_Menu_Main",getProperty_Menu_Main);
                intent.putExtra("Property_Menu_Sub",getProperty_Menu_Sub);
                context.startActivity(intent);


            }



            }
        });

        return convertView;
    }


    public void addItem(String Language, String Number, String Code, String Property_Menu_Main, String Property_Menu_Sub, String Category,
                        String Name_E, String Name_K, String Name_J, String Name_C, String Name_Sub,
                        String Stock_Glass, String Stock_Bottle,
                        String Price_Glass, String Price_Bottle)
        {
        ListViewItem item = new ListViewItem();

        item.setRowLanguage(Language);
        item.setRowNumber(Number);
        item.setRowCode(Code);
        item.setRowProperty_Menu_Main(Property_Menu_Main);
        item.setRowProperty_Menu_Sub(Property_Menu_Sub);
        item.setRowCategory(Category);
        item.setRowName_E(Name_E);
        item.setRowName_K(Name_K);
        item.setRowName_J(Name_J);
        item.setRowName_C(Name_C);
        item.setRowName_Sub(Name_Sub);
        item.setRowStock_Glass(Stock_Glass);
        item.setRowStock_Bottle(Stock_Bottle);
        item.setRowPrice_Glass(Price_Glass);
        item.setRowPrice_Bottle(Price_Bottle);

        listViewItemList.add(item);
    }


/*

    public void addcolor(int rowcolor, int rowbg1, String text0, String text1, String text2, String text3, String text4, String text5, String text6, String text7, String text8){
        ListViewItem item = new ListViewItem();

        item.setRowcolor(rowcolor);


        item.setRowbg1(rowbg1);


        item.setRowtext0(text0);
        item.setRowtext1(text1);
        item.setRowtext2(text2);
        item.setRowtext3(text3);
        item.setRowtext4(text4);
        item.setRowtext5(text5);
        item.setRowtext6(text6);
        item.setRowtext7(text7);
        item.setRowtext7(text8);


        listViewItemList.add(item);

    }
*/

    public void clearItem(){
        listViewItemList.clear();
    }

    public void remove(int position) {
        listViewItemList.remove(position);

    }

}
