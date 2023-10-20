package com.taromance.barmenu;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ListViewAdapter_Cocktail extends BaseAdapter {


    public ArrayList<ListViewItem_Cocktail> listViewItemList_Cocktail = new ArrayList<ListViewItem_Cocktail>();

    String getLanguage_setting;

    String[] splited_Price_Glass;

    String getNumber;
    String getProperty_Menu_Main;
    String getProperty_Menu_Sub;

    String iv_name;




    @Override
    public int getCount() {
        return listViewItemList_Cocktail.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList_Cocktail.get(position) ;
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
            convertView = inflater.inflate(R.layout.listview_scroll_cocktail, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득


        final TextView tv_Name_Main = (TextView) convertView.findViewById(R.id.tv_Name_Main);
        final TextView tv_Name_Sub = (TextView) convertView.findViewById(R.id.tv_Name_Sub);
        final TextView tv_Simple_Recipe = (TextView) convertView.findViewById(R.id.tv_Simple_Recipe);
        final TextView tv_Price_Glass = (TextView) convertView.findViewById(R.id.tv_Price_Glass);
        final ImageView iv_Glass = (ImageView) convertView.findViewById(R.id.iv_Glass);




        LinearLayout layout_scroll_Cocktail = (LinearLayout) convertView.findViewById(R.id.layout_scroll_cocktail);
        LinearLayout layout_listview_Cocktail = (LinearLayout) convertView.findViewById(R.id.layout_listview_cocktail);

        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        final ListViewItem_Cocktail listViewItem_Cocktail = listViewItemList_Cocktail.get(position);


        getLanguage_setting = listViewItem_Cocktail.getRowLanguage();

        switch (getLanguage_setting)
        {

            case "English" :
                tv_Name_Main.setText(listViewItem_Cocktail.getRowName_E());
                tv_Name_Sub.setText(null);

                break;

            case "Korean" :
                tv_Name_Main.setText(listViewItem_Cocktail.getRowName_K());
                tv_Name_Sub.setText(null);

                break;

            case "Japanese" :
                tv_Name_Main.setText(listViewItem_Cocktail.getRowName_J());
                tv_Name_Sub.setText(null);

                break;

            case "Chinese" :
                tv_Name_Main.setText(listViewItem_Cocktail.getRowName_C());
                tv_Name_Sub.setText(null);

                break;

            default:
                break;

        }


        //텍스트 입력 시작

        tv_Simple_Recipe.setText(listViewItem_Cocktail.getRowSimple_Recipe());
        //tv_Price_Glass.setText(listViewItem_Cocktail.getRowPrice_Glass());

        splited_Price_Glass = listViewItem_Cocktail.getRowPrice_Glass().split(",");
        tv_Price_Glass.setText(splited_Price_Glass[0]);



        //tv_Cocktail_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tv_Name_Main.setTextColor(Color.BLACK);
        tv_Price_Glass.setTextColor(Color.BLACK);

        String iv_name = listViewItem_Cocktail.getRowGlass().toLowerCase();
        iv_name = iv_name.replace(" ", "");
        iv_name = iv_name.replace("&", "");
        iv_name = iv_name.replace("'", "");

        Log.d(TAG, "Complete : Change iv_name : " + iv_name);





/*
        if(listViewItem_Cocktail.getRowCategory().equals("Blank")){
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 5);
            tv_Name_Sub.setTextSize(TypedValue.COMPLEX_UNIT_SP, 5);
            tv_Price_Glass.setTextSize(TypedValue.COMPLEX_UNIT_SP, 5);
        }
        else{
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            tv_Name_Sub.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tv_Price_Glass.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

        }
*/

        if(listViewItem_Cocktail.getRowType().equals("Title")){
            // 이미지 삭제
            iv_Glass.setVisibility(View.GONE);
            // 가격 삭제
            tv_Price_Glass.setVisibility(View.GONE);

            tv_Price_Glass.setText(listViewItem_Cocktail.getRowPrice_Glass());
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
            tv_Name_Main.setTextColor(Color.parseColor("#000033"));
            tv_Name_Sub.setTextColor(Color.parseColor("#000033"));



        }

        // 정상 출력
        else{
            // 이미지 적용
            //iv_Glass.setVisibility(View.VISIBLE);
            iv_Glass.setImageResource(listViewItem_Cocktail.getRowIv_Glass());
            // 가격 적용
            tv_Price_Glass.setVisibility(View.VISIBLE);


            tv_Price_Glass.setTextColor(Color.BLACK);

            tv_Name_Main.setTextColor(Color.BLACK);
            tv_Name_Sub.setTextColor(Color.BLACK);
            tv_Name_Main.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);



        }


        layout_scroll_Cocktail.setBackgroundColor(listViewItem_Cocktail.getRowcolor());


        //리스트 아이템 팝업 연결


        layout_listview_Cocktail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listViewItem_Cocktail.getRowType().equals("Blank")||listViewItem_Cocktail.getRowType().equals("Title"))
                {
                    return;
                }
                else
                {

                    //리스트 아이템 팝업 연결

                    getNumber = listViewItem_Cocktail.getRowNumber();
                    getProperty_Menu_Main = listViewItem_Cocktail.getRowProperty_Menu_Main();
                    getProperty_Menu_Sub = listViewItem_Cocktail.getRowProperty_Menu_Sub();


                    Intent intent = new Intent(context, PopupActivityInfo.class);
                    intent.putExtra("Number",getNumber);
                    intent.putExtra("Property_Menu_Main",getProperty_Menu_Main);
                    intent.putExtra("Property_Menu_Sub",getProperty_Menu_Sub);
                    context.startActivity(intent);


                }



            }
        });


        return convertView;
    }


    public void addItem(String Language,String Number, String Code, String Property_Menu_Main, String Property_Menu_Sub, String Category, String Type,
                        String Name_E, String Name_K, String Name_J, String Name_C,
                        String Base, String Simple_Recipe, String Glass, String Price_Glass, int Iv_Glass)
    {
        ListViewItem_Cocktail item = new ListViewItem_Cocktail();

        item.setRowLanguage(Language);
        item.setRowNumber(Number);
        item.setRowCode(Code);
        item.setRowProperty_Menu_Main(Property_Menu_Main);
        item.setRowProperty_Menu_Sub(Property_Menu_Sub);
        item.setRowCategory(Category);
        item.setRowType(Type);
        item.setRowName_E(Name_E);
        item.setRowName_K(Name_K);
        item.setRowName_J(Name_J);
        item.setRowName_C(Name_C);
        item.setRowBase(Base);
        item.setRowSimple_Recipe(Simple_Recipe);
        item.setRowGlass(Glass);
        item.setRowPrice_Glass(Price_Glass);
        item.setRowIv_Glass(Iv_Glass);

        listViewItemList_Cocktail.add(item);
    }


    public void clearItem(){
        listViewItemList_Cocktail.clear();
    }

    public void remove(int position) {
        listViewItemList_Cocktail.remove(position);

    }
}
