package com.taromance.barmenu;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    //날짜 받기
/*    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yy년 MM월 dd일");*/

    final FirebaseFirestore db = FirebaseFirestore.getInstance();    // Firestore 인스턴스 선언

    CollectionReference db_menu_Guide = db.collection("DB_Guide");

    CollectionReference db_menu_Spirits = db.collection("DB_Spirits");

    CollectionReference db_menu_Cocktail = db.collection("DB_Cocktail");

    CollectionReference db_menu_Setting = db.collection("DB_Setting");


    //파이어베이스 초기 세팅 값

    private String getFireStore_Setting_SetOnSignature;


    //설정 관련

    Space space_signature;


    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private String getProperty_Menu_Main;
    private String getProperty_Menu_Sub;

    //다이얼로그 생성
    private AlertDialog.Builder dialog_language;
    private String language_setting;
    private int language_value;

    //언어 관련

    final String[] tv_menu_title_main_Guide = new String[]{
            "Guide to using Taromance",
            "타로맨스 이용안내",
            "일본어",
            "중국어"
    };
    final String[] tv_menu_title_main_Notice = new String[]{
            "Guide to using Taromance",
            "타로맨스 이용안내",
            "일본어",
            "중국어"
    };
    final String[] tv_menu_title_main_Deliveryfood = new String[]{
            "Outside food may be brought in and delivered.",
            "외부 음식 반입 및 배달이 가능합니다.",
            "일본어",
            "중국어"
    };

    final String[] tv_menu_title_main_Scotch = new String[]{
            "Touch the menu name. You can check detailed information.",
            "각 메뉴를 터치하시면 상세 정보를 확인하실 수 있습니다.",
            "일본어",
            "중국어"
    };
    final String[] tv_menu_title_main_American = new String[]{
            "Touch the menu name. You can check detailed information.",
            "각 메뉴를 터치하시면 상세 정보를 확인하실 수 있습니다.",
            "일본어",
            "중국어"
    };
    final String[] tv_menu_title_main_Others = new String[]{
            "Touch the menu name. You can check detailed information.",
            "각 메뉴를 터치하시면 상세 정보를 확인하실 수 있습니다.",
            "일본어",
            "중국어"
    };

    final String[] tv_menu_title_main_Classic = new String[]{
            "For cocktails not on the menu, ask the bartender.",
            "메뉴에 없는 칵테일도 주문이 가능합니다.",
            "일본어",
            "중국어"
    };
    final String[] tv_menu_title_main_Signature = new String[]{
            "Taromance's Signature Cocktail.\n",
            "타로맨스의 시그니처 칵테일입니다.",
            "일본어",
            "중국어"
    };

    final String[] tv_menu_title_main_Highball = new String[]{
            "We used Soda as a basic base.\nIt is possible to change to another soft drink.\n\nIf you want a different base\nIt is 'base 30ml price + 3,000 won'.",
            "기본 탄산수로 제공됩니다. \n요청시 다른 탄산 음료로 변경 가능합니다. \n\n하이볼 베이스 변경시 '베이스 30ml 금액 + 3,000원' 입니다.",
            "일본어",
            "중국어"
    };

    final String[] tv_menu_title_main_FreeOrder = new String[]{
            "Depending on the liquor and ingredients\nPrices may fluctuate.",
            "프리오더 칵테일은 주류 및 재료에 따라\n가격이 변동될 수 있습니다.",
            "일본어",
            "중국어"
    };

    final String[] tv_menu_title_main_Wine = new String[]{
            "Wine corkage is 30,000 won per bottle.",
            "와인 콜키지는 병당 30,000원 입니다.",
            "일본어",
            "중국어"
    };
    final String[] tv_menu_title_main_Spirits = new String[]{
            "Touch the menu name. You can check detailed information.",
            "각 메뉴를 터치하시면 상세 정보를 확인하실 수 있습니다.",
            "일본어",
            "중국어"
    };
    final String[] tv_menu_title_main_Liqueur = new String[]{
            "Touch the menu name. You can check detailed information.",
            "각 메뉴를 터치하시면 상세 정보를 확인하실 수 있습니다.",
            "일본어",
            "중국어"
    };


    RadioGroup radioGroup_menu_main;
    RadioButton radioButton_menu_main_Guide;
    RadioButton radioButton_menu_main_Whisky;
    RadioButton radioButton_menu_main_Cocktail;
    RadioButton radioButton_menu_main_WineAndSpirits;


    LinearLayout layout_guide;
    RadioGroup radioGroup_menu_sub_Guide;
    RadioButton radioButton_menu_sub_Guide;
    RadioButton radioButton_menu_sub_Notice;
    RadioButton radioButton_menu_sub_Deliveryfood;



    LinearLayout layout_whisky;
    RadioGroup radioGroup_menu_sub_Whisky;
    RadioButton radioButton_menu_sub_Recommendation;
    RadioButton radioButton_menu_sub_Scotch;
    RadioButton radioButton_menu_sub_American;
    RadioButton radioButton_menu_sub_Others;



    LinearLayout layout_cocktail;
    RadioGroup radioGroup_menu_sub_Cocktail;
    RadioButton radioButton_menu_sub_Classic;
    RadioButton radioButton_menu_sub_Highball;
    RadioButton radioButton_menu_sub_Signature;
    RadioButton radioButton_menu_sub_FreeOrder;


    LinearLayout layout_wineandspirits;
    RadioGroup radioGroup_menu_sub_WineAndSpirits;
    RadioButton radioButton_menu_sub_Wine;
    RadioButton radioButton_menu_sub_Spirits;
    RadioButton radioButton_menu_sub_Liqueur;

    TextView tv_lv_alcoholbooze;
    RadioGroup radioGroup_lv_alcoholbooze;
    RadioButton radioButton_lv_alcoholbooze_0;
    RadioButton radioButton_lv_alcoholbooze_1;
    RadioButton radioButton_lv_alcoholbooze_2;
    RadioButton radioButton_lv_alcoholbooze_3;
    RadioButton radioButton_lv_alcoholbooze_4;

    TextView tv_lv_sparkling;
    RadioGroup radioGroup_lv_sparkling;
    RadioButton radioButton_lv_sparkling_0;
    RadioButton radioButton_lv_sparkling_1;
    RadioButton radioButton_lv_sparkling_2;
    RadioButton radioButton_lv_sparkling_3;
    RadioButton radioButton_lv_sparkling_4;

    TextView tv_lv_sweetness;
    RadioGroup radioGroup_lv_sweetness;
    RadioButton radioButton_lv_sweetness_0;
    RadioButton radioButton_lv_sweetness_1;
    RadioButton radioButton_lv_sweetness_2;
    RadioButton radioButton_lv_sweetness_3;
    RadioButton radioButton_lv_sweetness_4;

    TextView tv_lv_sour;
    RadioGroup radioGroup_lv_sour;
    RadioButton radioButton_lv_sour_0;
    RadioButton radioButton_lv_sour_1;
    RadioButton radioButton_lv_sour_2;
    RadioButton radioButton_lv_sour_3;
    RadioButton radioButton_lv_sour_4;

    TextView tv_tag_flavor;
    private int checkbox_finder_Cocktail_count;
    CheckBox checkbox_finder_Cocktail_tag_Lemon;
    CheckBox checkbox_finder_Cocktail_tag_Lime;
    CheckBox checkbox_finder_Cocktail_tag_Peach;
    CheckBox checkbox_finder_Cocktail_tag_Berry;
    CheckBox checkbox_finder_Cocktail_tag_Orange;
    CheckBox checkbox_finder_Cocktail_tag_Melon;
    CheckBox checkbox_finder_Cocktail_tag_Grapefruit;
    CheckBox checkbox_finder_Cocktail_tag_Cinnamon;
    CheckBox checkbox_finder_Cocktail_tag_Coconut;
    CheckBox checkbox_finder_Cocktail_tag_Herb;
    CheckBox checkbox_finder_Cocktail_tag_Milk;
    CheckBox checkbox_finder_Cocktail_tag_Pineapple;
    CheckBox checkbox_finder_Cocktail_tag_Apple;
    CheckBox checkbox_finder_Cocktail_tag_Coffee;
    CheckBox checkbox_finder_Cocktail_tag_Chocolate;
    CheckBox checkbox_finder_Cocktail_tag_Wood;
    CheckBox checkbox_finder_Cocktail_tag_Smoke;
    CheckBox checkbox_finder_Cocktail_tag_Honey;
    CheckBox checkbox_finder_Cocktail_tag_Ginger;
    CheckBox checkbox_finder_Cocktail_tag_Banana;

    Button btn_freeorder_reset;
    Button btn_freeorder_search;

    TextView tv_freeorder_notice;


    Button btn_setting;


    private int isChecked_Lv_AlcoholBooze;
    private int isChecked_Lv_Sparkling;
    private int isChecked_Lv_Sweetness;
    private int isChecked_Lv_Sour;


    //주류 검색
    CheckBox checkBox_finder;
    Button btn_finder_reset;
    Button btn_finder_search;


    CheckBox checkBox_finder_Whisky_finder_tag_Honey;
    CheckBox checkBox_finder_Whisky_finder_tag_Floral;
    CheckBox checkBox_finder_Whisky_finder_tag_Vanilla;
    CheckBox checkBox_finder_Whisky_finder_tag_Cinnamon;

    CheckBox checkBox_finder_Whisky_finder_tag_Fresh_Fruits;
    CheckBox checkBox_finder_Whisky_finder_tag_Dried_Fruits;
    CheckBox checkBox_finder_Whisky_finder_tag_Citrus_Fruits;
    CheckBox checkBox_finder_Whisky_finder_tag_Sweet_Fruits;

    CheckBox checkBox_finder_Whisky_finder_tag_Butter;
    CheckBox checkBox_finder_Whisky_finder_tag_Cream;
    CheckBox checkBox_finder_Whisky_finder_tag_Caramel;
    CheckBox checkBox_finder_Whisky_finder_tag_Chocolate;

    CheckBox checkBox_finder_Whisky_finder_tag_Nuts;
    CheckBox checkBox_finder_Whisky_finder_tag_Malt;
    CheckBox checkBox_finder_Whisky_finder_tag_Wood;
    CheckBox checkBox_finder_Whisky_finder_tag_Salt;

    CheckBox checkBox_finder_Whisky_finder_tag_Spicy;
    CheckBox checkBox_finder_Whisky_finder_tag_Smoky;
    CheckBox checkBox_finder_Whisky_finder_tag_Peat;
    CheckBox checkBox_finder_Whisky_finder_tag_Herb;

    CheckBox checkBox_finder_Whisky_finder_tag_Strength;
    CheckBox checkBox_finder_Whisky_finder_tag_Limited;
    CheckBox checkBox_finder_Whisky_finder_tag_Old;


    //클래식 칵테일 검색

    RadioGroup radioGroup_finder_cocktail_lv_alcoholbooze;
    RadioButton radiobutton_finder_cocktail_lv_alcoholbooze_0;
    RadioButton radiobutton_finder_cocktail_lv_alcoholbooze_1;
    RadioButton radiobutton_finder_cocktail_lv_alcoholbooze_2;
    RadioButton radiobutton_finder_cocktail_lv_alcoholbooze_3;

    RadioGroup radioGroup_finder_cocktail_tag_fruits;
    RadioButton radiobutton_finder_Classic_finder_tag_fruits_citrus;
    RadioButton radiobutton_finder_Classic_finder_tag_fruits_berry;
    RadioButton radiobutton_finder_Classic_finder_tag_fruits_soft;
    RadioButton radiobutton_finder_Classic_finder_tag_fruits_tropical;

    RadioGroup radioGroup_finder_cocktail_tag_etc;
    RadioButton radiobutton_finder_Classic_finder_tag_sparkling;
    RadioButton radiobutton_finder_Classic_finder_tag_milk_cream;
    RadioButton radiobutton_finder_Classic_finder_tag_chocolate_coffee;
    RadioButton radiobutton_finder_Classic_finder_tag_herb_spice;

    Button btn_finder_cocktail_reset;
    Button btn_finder_cocktail_search;


    //검색기능

    LinearLayout layout_finder_whisky;
    LinearLayout layout_finder_cocktail;

    LinearLayout layout_menu_title;
    LinearLayout layout_menu_title_sub;

    LinearLayout layout_FreeOrder;

    ConstraintLayout layout_title;
    Button btn_startMain;

    ConstraintLayout layout_main;



    //애니메이션 관련
    private ConstraintLayout layout_menu;
    private ImageView iv_arrow;

    TextView tv_touchpress;


    LinearLayout layout_listview;
    private Animation ani_listview;
    private Animation ani_item_layout;




    //리스트뷰

    private ListView listView_Guide;
    private ListViewAdapter_Guide listViewAdapter_Guide;

    private ListView listView;
    private ListViewAdapter listViewAdapter;

    private ListView listView_Cocktail;
    private ListViewAdapter_Cocktail listViewAdapter_Cocktail;

    //private boolean isLoading;


    //임시 다이얼로그

    private AlertDialog.Builder dialog_finder;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        layout_main = findViewById(R.id.layout_main);

        ImageView gif_title = (ImageView) findViewById(R.id.gif_title);
        Glide.with(this).load(R.raw.gif_homebg3).into(gif_title);

        // 언어 실행 (한국어)
        dialog_language = new AlertDialog.Builder(this);
        Clear_Language();

        //애니메이션 선언

        layout_menu = findViewById(R.id.layout_menu);
        //ani_scroll();

        ani_listview();

        layout_listview = findViewById(R.id.layout_listview);
        tv_touchpress = findViewById(R.id.tv_touchpress);
//        tv_touchpress.startAnimation();


        AndroidBug5497Workaround.assistActivity(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);


        // 전원버튼시 타이틀화면으로
        BroadcastReceiver screenOnOff = new BroadcastReceiver()
        {
            public static final String ScreenOff = "android.intent.action.SCREEN_OFF";
            public static final String ScreenOn = "android.intent.action.SCREEN_ON";

            public void onReceive(Context contex, Intent intent)
            {
                if (intent.getAction().equals(ScreenOff))
                {
                    Clear_Language();
                    layout_title.setVisibility(View.VISIBLE);
                    layout_main.setVisibility(View.INVISIBLE);
                    /*
                    invisibleview();

                    list_btv = 0;

                    ani_scroll_close();


                    bt_home.setVisibility(View.INVISIBLE);

                    tv_hometext.setVisibility(View.VISIBLE);


                    lo_title.setVisibility(View.VISIBLE);
                    iv_mainbg.setVisibility(View.INVISIBLE);


                    lo_upside.setVisibility(View.INVISIBLE);
                    lo_downside.setVisibility(View.INVISIBLE);

                    Log.e("MainActivity", "Screen off");
                    */

//                    Intent gototitle = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(gototitle);

                    //화면 초기화
                    init();


                }
                else if (intent.getAction().equals(ScreenOn))
                {

                    Log.e("MainActivity", "Screen On");
                }
            }
        };
        registerReceiver(screenOnOff, intentFilter);

        btn();

        init();

        // 권한ID를 가져옵니다
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int permission2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        // 권한이 열려있는지 확인
        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED) {
            // 마쉬멜로우 이상버전부터 권한을 물어본다
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        1000);
            }
            return;
        }



    }


    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            // 권한 체크에 동의를 하지 않으면 안드로이드 종료
            if (check_result == true) {

            } else {
                finish();
            }
        }
    }




    public void Clear_Language(){

        //언어 초기화
        language_value = 1;
        language_setting = "Korean";

        getProperty_Menu_Main = "Guide";
        getProperty_Menu_Sub = "Guide";
    }

    public void init(){

        //설정 관련

        //시그니처 옆 스페이스

        space_signature = findViewById(R.id.space_signature);


        //검색기

        layout_finder_whisky = findViewById(R.id.layout_finder_whisky);
        layout_finder_cocktail = findViewById(R.id.layout_finder_cocktail);
        checkBox_finder.setChecked(false);

        //애니메이션
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);

        layout_menu_title = findViewById(R.id.layout_menu_title);
        layout_menu_title.setVisibility(View.GONE);
        layout_menu_title_sub = findViewById(R.id.layout_menu_title_sub);
        layout_FreeOrder = findViewById(R.id.layout_freeorder);
        layout_FreeOrder.setVisibility(View.INVISIBLE);

        setLayoutVisibility();

        //초기화 메뉴 가이드 페이지
        radioButton_menu_main_Guide.setChecked(true);
        radioButton_menu_sub_Guide.setChecked(true);
        layout_guide.setVisibility(View.VISIBLE);

        //getDBFirebase("Guide","Guide");

        // 시작화면


        tv_lv_alcoholbooze = findViewById(R.id.tv_lv_alcoholbooze);
        tv_lv_sparkling = findViewById(R.id.tv_lv_sparkling);
        tv_lv_sweetness = findViewById(R.id.tv_lv_sweetness);
        tv_lv_sour = findViewById(R.id.tv_lv_sour);

        tv_tag_flavor = findViewById(R.id.tv_tag_flavor);

        tv_freeorder_notice = findViewById(R.id.tv_freeorder_notice);


        // 임시 다이얼로그

        dialog_finder = new AlertDialog.Builder(this);



        // 파이어베이스 세팅 값 가져오기


        db_menu_Setting.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d(TAG, "Log : get_FireStore Setting DB getFireStore_Setting_SetOnSignature" + document.get("SetOnSignature").toString());

                                getFireStore_Setting_SetOnSignature = document.get("SetOnSignature").toString();

                                Log.d(TAG, "Log : FireStore Setting DB getFireStore_Setting_SetOnSignature" + getFireStore_Setting_SetOnSignature);

                                Log.d(TAG, "Log : FireStore Setting DB Load Success!");


                            }

                            Log.d(TAG, "Log : Firebase DB Load Success!");

                        } else {
                            Log.d(TAG, "Error : getting documents: ", task.getException());
                        }

                    }

                });



    }

    public void setLayout_menu_title(String Property_Menu_Main, String Property_Menu_Sub){


        layout_menu_title_sub.setVisibility(View.VISIBLE);
        TextView tv_menu_title_main = findViewById(R.id.tv_menu_title_main);
        TextView tv_menu_title_glass = findViewById(R.id.tv_menu_title_glass);
        TextView tv_menu_title_bottle = findViewById(R.id.tv_menu_title_bottle);


        switch (Property_Menu_Main)
        {
            case "Guide" :{

                switch (Property_Menu_Sub){

                    case "Guide" : {
                        tv_menu_title_main.setText(tv_menu_title_main_Guide[language_value]);
                        tv_menu_title_glass.setText("");
                        tv_menu_title_bottle.setText("");

                    }
                    break;


                    case "Notice" : {
                        tv_menu_title_main.setText(tv_menu_title_main_Notice[language_value]);
                        tv_menu_title_glass.setText("");
                        tv_menu_title_bottle.setText("");

                    }
                    break;

                    case "Deliveryfood" : {
                        tv_menu_title_main.setText(tv_menu_title_main_Deliveryfood[language_value]);
                        tv_menu_title_glass.setText("");
                        tv_menu_title_bottle.setText("");

                    }
                    break;

                    default:
                        break;
                }

                layout_menu_title.setVisibility(View.VISIBLE);

            }
            break;

            case "Whisky" :{

                switch (Property_Menu_Sub){

                    case "Scotch" : {
                        tv_menu_title_main.setText(tv_menu_title_main_Scotch[language_value]);
                        tv_menu_title_glass.setText("30ml");
                        tv_menu_title_bottle.setText("Bottle");

                    }
                    break;


                    case "American" : {
                        tv_menu_title_main.setText(tv_menu_title_main_American[language_value]);
                        tv_menu_title_glass.setText("30ml");
                        tv_menu_title_bottle.setText("Bottle");

                    }
                    break;

                    case "Other Country" : {
                        tv_menu_title_main.setText(tv_menu_title_main_Others[language_value]);
                        tv_menu_title_glass.setText("30ml");
                        tv_menu_title_bottle.setText("Bottle");

                    }
                    break;

                    default:
                        break;
                }
                layout_menu_title.setVisibility(View.VISIBLE);


            }
            break;

            case "Cocktail" :{
                switch (Property_Menu_Sub){

                    case "Classic" : {
                        tv_menu_title_main.setText(tv_menu_title_main_Classic[language_value]);
                        tv_menu_title_glass.setText("");
                        tv_menu_title_bottle.setText("Glass");
                        layout_menu_title.setVisibility(View.VISIBLE);

                    }
                    break;

                    case "Highball" : {
                        tv_menu_title_main.setText(tv_menu_title_main_Highball[language_value]);
                        tv_menu_title_glass.setText("");
                        tv_menu_title_bottle.setText("Glass");
                        layout_menu_title.setVisibility(View.VISIBLE);

                    }
                    break;


                    case "Signature" : {
                        tv_menu_title_main.setText(tv_menu_title_main_Signature[language_value]);
                        tv_menu_title_glass.setText("");
                        tv_menu_title_bottle.setText("Glass");
                        layout_menu_title.setVisibility(View.VISIBLE);

                    }
                    break;

                    case "FreeOrder" : {
                        tv_menu_title_main.setText(tv_menu_title_main_FreeOrder[language_value]);
                        tv_menu_title_glass.setText("");
                        tv_menu_title_bottle.setText("");

                    }
                    break;

                    default:
                        break;
                }


            }
            break;

            case "WineAndSpirits" :{

                switch (Property_Menu_Sub){
                    case "Wine" : {
                        tv_menu_title_main.setText(tv_menu_title_main_Wine[language_value]);
                        tv_menu_title_glass.setText("");
                        tv_menu_title_bottle.setText("Bottle");

                    }
                    break;

                    case "Spirits": {
                        tv_menu_title_main.setText(tv_menu_title_main_Spirits[language_value]);
                        tv_menu_title_glass.setText("30ml");
                        tv_menu_title_bottle.setText("Bottle");

                    }
                    break;

                    case "Liqueur": {
                        tv_menu_title_main.setText(tv_menu_title_main_Liqueur[language_value]);
                        tv_menu_title_glass.setText("30ml");
                        tv_menu_title_bottle.setText("Bottle");

                    }
                    break;
                }
                layout_menu_title.setVisibility(View.VISIBLE);


            }
            break;

            default:

                break;

        }



    }


    public void setLoading(boolean setActivate){

        Dialog_Loading LoadingDialog = new Dialog_Loading(this); //다이얼로그 선언
        LoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //백그라운를 투명하게
        LoadingDialog.setCanceledOnTouchOutside(false);
        LoadingDialog.setCancelable(false); //다이얼로그 외부 클릭으로 종료되지 않게


        if(setActivate){

            LoadingDialog.show(); //로딩화면 보여주기

        }
        else{

            LoadingDialog.cancel(); //로딩화면 종료
        }



    }


    public void getDBFirebase(String Property_Menu_Main, String Property_Menu_Sub){

        Dialog_Loading LoadingDialog = new Dialog_Loading(this); //다이얼로그 선언
        LoadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); //주변 투명
        LoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //백그라운를 투명하게
        LoadingDialog.setCanceledOnTouchOutside(false);
        LoadingDialog.setCancelable(false); //다이얼로그 외부 클릭으로 종료되지 않게
        LoadingDialog.show(); //로딩화면 보여주기


        //ani_scroll();
        getProperty_Menu_Main = Property_Menu_Main;
        getProperty_Menu_Sub = Property_Menu_Sub;

        Log.d(TAG, "get 메인 값 : " + getProperty_Menu_Main + "get 서브 값 : " + getProperty_Menu_Sub);



        if(getProperty_Menu_Sub == "FreeOrder"){
            Log.d(TAG, "Complete : FreeOrder 실행 취소");
            setLayout_menu_title(Property_Menu_Main, Property_Menu_Sub);
/*            radioButton_lv_alcoholbooze_4.setChecked(true);
            radioButton_lv_sparkling_4.setChecked(true);
            radioButton_lv_sweetness_4.setChecked(true);
            radioButton_lv_sour_4.setChecked(true);*/
            LoadingDialog.cancel(); //로딩화면 종료


        }

        else {
            //화살표 안보임
            clear_ani();


            listView.setVisibility(View.VISIBLE);
            layout_FreeOrder.setVisibility(View.INVISIBLE);

            Log.d(TAG, "메인 값 : " + Property_Menu_Main + "서브 값 : " + Property_Menu_Sub);


            layout_listview.setVisibility(View.INVISIBLE);

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    //딜레이 후 시작할 코드 작성
                    switch(Property_Menu_Main)
                    {

                        case "Guide" : {
                            setLayout_menu_title(Property_Menu_Main, Property_Menu_Sub);

                            //리스트뷰, 리스트뷰 어뎁터 초기화
                            listView_Guide = (ListView)findViewById(R.id.listview_scroll);
                            listViewAdapter_Guide = new ListViewAdapter_Guide();


                            db_menu_Guide.orderBy("Number")
                                    .whereEqualTo("Category", Property_Menu_Sub)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {


                                                    final String list_Guide_Main_E = document.get("Main_E").toString();
                                                    final String list_Guide_Main_K = document.get("Main_K").toString();
                                                    final String list_Guide_Main_J = document.get("Main_J").toString();
                                                    final String list_Guide_Main_C = document.get("Main_C").toString();

                                                    final String list_Guide_Sub_E = document.get("Sub_E").toString();
                                                    final String list_Guide_Sub_K = document.get("Sub_K").toString();
                                                    final String list_Guide_Sub_J = document.get("Sub_J").toString();
                                                    final String list_Guide_Sub_C = document.get("Sub_C").toString();
                                                    listViewAdapter_Guide.addItem(language_setting,
                                                            list_Guide_Main_E, list_Guide_Main_K, list_Guide_Main_J, list_Guide_Main_C,
                                                            list_Guide_Sub_E, list_Guide_Sub_K, list_Guide_Sub_J, list_Guide_Sub_C);
                                                    listViewAdapter_Guide.notifyDataSetChanged();


                                                }

                                                Log.d(TAG, "Log : Firebase DB Load Success!");


                                            } else {
                                                Log.d(TAG, "Error : getting documents: ", task.getException());
                                            }

                                        }



                                    });

                            //리스트뷰에 어뎁터 set
                            listView_Guide.setAdapter(listViewAdapter_Guide);

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable()  {
                                public void run() {
                                    LoadingDialog.cancel(); //로딩화면 종료

                                    layout_listview.setVisibility(View.VISIBLE);

                                    layout_listview.startAnimation(ani_listview);
                                    ani_arrow();
                                }
                            }, 600);
                            Log.d(TAG, "Log : ListView Adapter Set Success!");



                        }
                        break;

                        case "Whisky" : {

                            setLayout_menu_title(Property_Menu_Main, Property_Menu_Sub);
                            //리스트뷰, 리스트뷰 어뎁터 초기화
                            listView = (ListView)findViewById(R.id.listview_scroll);
                            listViewAdapter = new ListViewAdapter();


                            if(Property_Menu_Sub.equals("Recommendation"))
                            {
                                db_menu_Spirits.orderBy("Number")
                                        .whereEqualTo("Tag", Property_Menu_Sub)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                                        final String list_Number = document.get("Number").toString();

                                                        final String list_Code = document.get("Code").toString();
                                                        final String list_Liquor_Company = document.get("Liquor_Company").toString();

                                                        final String list_Property_Menu_Main = document.get("Property_Menu_Main").toString();
                                                        final String list_Property_Menu_Sub = document.get("Property_Menu_Sub").toString();

                                                        final String list_Category = document.get("Category").toString();
                                                        final String list_Country = document.get("Country").toString();
                                                        final String list_Region = document.get("Region").toString();

                                                        final String list_Name_E = document.get("Name_E").toString();
                                                        final String list_Name_K = document.get("Name_K").toString();
                                                        final String list_Name_J = document.get("Name_J").toString();
                                                        final String list_Name_C = document.get("Name_C").toString();
                                                        final String list_Name_Sub = document.get("Name_Sub").toString();

                                                        final String list_Aging = document.get("Aging").toString();
                                                        final String list_Cask = document.get("Cask").toString();
                                                        final String list_ABV = document.get("ABV").toString();

                                                        final String list_Size_Bottle = document.get("Size_Bottle").toString();
                                                        final String list_Size_30ml = document.get("Size_30ml").toString();

                                                        final String list_Stock_Glass = document.get("Stock_Glass").toString();
                                                        final String list_Stock_Bottle = document.get("Stock_Bottle").toString();

                                                        final String list_Price_Glass = document.get("Price_Glass").toString();
                                                        final String list_Price_Glass_VAT_False = document.get("Price_Glass_VAT_False").toString();
                                                        final String list_Margin_Glass = document.get("Margin_Glass").toString();

                                                        final String list_Price_Bottle = document.get("Price_Bottle").toString();
                                                        final String list_Price_Bottle_VAT_False = document.get("Price_Bottle_VAT_False").toString();
                                                        final String list_Margin_Bottle = document.get("Margin_Bottle").toString();

                                                        final String list_Stock_Price_30ml_VAT = document.get("Stock_Price_30ml_VAT").toString();
                                                        final String list_Stock_Price_Bottle = document.get("Stock_Price_Bottle").toString();
                                                        final String list_Stock_Price_Bottle_VAT = document.get("Stock_Price_Bottle_VAT").toString();

                                                        //final String list_Multiply_Glass = document.get("Multiply_Glass").toString();
                                                        //final String list_Multiply_Bottle = document.get("Multiply_Bottle").toString();

                                                        final String list_Event_Sale_Glass = document.get("Event_Sale_Glass").toString();
                                                        final String list_Event_Sale_Price_Glass = document.get("Event_Sale_Price_Glass").toString();
                                                        final String list_Event_Sale_Bottle = document.get("Event_Sale_Bottle").toString();
                                                        final String list_Event_Sale_Price_Bottle = document.get("Event_Sale_Price_Bottle").toString();

                                                        final String list_Last_Update = document.get("Last_Update").toString();

                                                        final String list_Reference = document.get("Reference").toString();

                                                        final String list_Distillery = document.get("Distillery").toString();

                                                        final String list_Tag = document.get("Tag").toString();

                                                        final String list_Info_E = document.get("Info_E").toString();
                                                        final String list_Info_K = document.get("Info_K").toString();
                                                        final String list_Info_J = document.get("Info_J").toString();
                                                        final String list_Info_C = document.get("Info_C").toString();

                                                        final String list_Finder_Tag = document.get("Finder_Tag").toString();

                                                        final String list_Lv_Body = document.get("Lv_Body").toString();
                                                        final String list_Lv_Richness = document.get("Lv_Richness").toString();
                                                        final String list_Lv_Smoke = document.get("Lv_Smoke").toString();
                                                        final String list_Lv_Sweetness = document.get("Lv_Sweetness").toString();

                                                        final String list_Flavour_Character = document.get("Flavour_Character").toString();
                                                        final String list_Colouring = document.get("Colouring").toString();
                                                        final String list_Chill_Filtered = document.get("Chill_Filtered").toString();

                                                        final String list_Taste_Nose_E = document.get("Taste_Nose_E").toString();
                                                        final String list_Taste_Nose_K = document.get("Taste_Nose_K").toString();
                                                        final String list_Taste_Nose_J = document.get("Taste_Nose_J").toString();
                                                        final String list_Taste_Nose_C = document.get("Taste_Nose_C").toString();

                                                        final String list_Taste_Plate_E = document.get("Taste_Plate_E").toString();
                                                        final String list_Taste_Plate_K = document.get("Taste_Plate_K").toString();
                                                        final String list_Taste_Plate_J = document.get("Taste_Plate_J").toString();
                                                        final String list_Taste_Plate_C = document.get("Taste_Plate_C").toString();

                                                        final String list_Taste_Finish_E = document.get("Taste_Finish_E").toString();
                                                        final String list_Taste_Finish_K = document.get("Taste_Finish_K").toString();
                                                        final String list_Taste_Finish_J = document.get("Taste_Finish_J").toString();
                                                        final String list_Taste_Finish_C = document.get("Taste_Finish_C").toString();


                                                        listViewAdapter.addItem(language_setting, list_Number, list_Code, list_Property_Menu_Main, list_Property_Menu_Sub, list_Category,
                                                                list_Name_E, list_Name_K, list_Name_J, list_Name_C, list_Name_Sub,
                                                                list_Stock_Glass, list_Stock_Bottle,
                                                                list_Price_Glass, list_Price_Bottle);
                                                        listViewAdapter.notifyDataSetChanged();


                                                        Log.d(TAG, "Number : " + list_Number + "Code : " + list_Code + "Name_E : "+ list_Name_E + "Log : List Item Adding");


                                                    }

                                                    Log.d(TAG, "Log : Firebase DB Load Success!");


                                                } else {
                                                    Log.d(TAG, "Error : getting documents: ", task.getException());
                                                }

                                            }

                                        });

                            }

                            else{
                                db_menu_Spirits.orderBy("Number")
                                        .whereEqualTo("Property_Menu_Sub", Property_Menu_Sub)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                                        final String list_Number = document.get("Number").toString();

                                                        final String list_Code = document.get("Code").toString();
                                                        final String list_Liquor_Company = document.get("Liquor_Company").toString();

                                                        final String list_Property_Menu_Main = document.get("Property_Menu_Main").toString();
                                                        final String list_Property_Menu_Sub = document.get("Property_Menu_Sub").toString();

                                                        final String list_Category = document.get("Category").toString();
                                                        final String list_Country = document.get("Country").toString();
                                                        final String list_Region = document.get("Region").toString();

                                                        final String list_Name_E = document.get("Name_E").toString();
                                                        final String list_Name_K = document.get("Name_K").toString();
                                                        final String list_Name_J = document.get("Name_J").toString();
                                                        final String list_Name_C = document.get("Name_C").toString();
                                                        final String list_Name_Sub = document.get("Name_Sub").toString();

                                                        final String list_Aging = document.get("Aging").toString();
                                                        final String list_Cask = document.get("Cask").toString();
                                                        final String list_ABV = document.get("ABV").toString();

                                                        final String list_Size_Bottle = document.get("Size_Bottle").toString();
                                                        final String list_Size_30ml = document.get("Size_30ml").toString();

                                                        final String list_Stock_Glass = document.get("Stock_Glass").toString();
                                                        final String list_Stock_Bottle = document.get("Stock_Bottle").toString();

                                                        final String list_Price_Glass = document.get("Price_Glass").toString();
                                                        final String list_Price_Glass_VAT_False = document.get("Price_Glass_VAT_False").toString();
                                                        final String list_Margin_Glass = document.get("Margin_Glass").toString();

                                                        final String list_Price_Bottle = document.get("Price_Bottle").toString();
                                                        final String list_Price_Bottle_VAT_False = document.get("Price_Bottle_VAT_False").toString();
                                                        final String list_Margin_Bottle = document.get("Margin_Bottle").toString();

                                                        final String list_Stock_Price_30ml_VAT = document.get("Stock_Price_30ml_VAT").toString();
                                                        final String list_Stock_Price_Bottle = document.get("Stock_Price_Bottle").toString();
                                                        final String list_Stock_Price_Bottle_VAT = document.get("Stock_Price_Bottle_VAT").toString();

                                                        //final String list_Multiply_Glass = document.get("Multiply_Glass").toString();
                                                        //final String list_Multiply_Bottle = document.get("Multiply_Bottle").toString();

                                                        final String list_Event_Sale_Glass = document.get("Event_Sale_Glass").toString();
                                                        final String list_Event_Sale_Price_Glass = document.get("Event_Sale_Price_Glass").toString();
                                                        final String list_Event_Sale_Bottle = document.get("Event_Sale_Bottle").toString();
                                                        final String list_Event_Sale_Price_Bottle = document.get("Event_Sale_Price_Bottle").toString();

                                                        final String list_Last_Update = document.get("Last_Update").toString();

                                                        final String list_Reference = document.get("Reference").toString();

                                                        final String list_Distillery = document.get("Distillery").toString();

                                                        final String list_Tag = document.get("Tag").toString();

                                                        final String list_Info_E = document.get("Info_E").toString();
                                                        final String list_Info_K = document.get("Info_K").toString();
                                                        final String list_Info_J = document.get("Info_J").toString();
                                                        final String list_Info_C = document.get("Info_C").toString();

                                                        final String list_Finder_Tag = document.get("Finder_Tag").toString();

                                                        final String list_Lv_Body = document.get("Lv_Body").toString();
                                                        final String list_Lv_Richness = document.get("Lv_Richness").toString();
                                                        final String list_Lv_Smoke = document.get("Lv_Smoke").toString();
                                                        final String list_Lv_Sweetness = document.get("Lv_Sweetness").toString();

                                                        final String list_Flavour_Character = document.get("Flavour_Character").toString();
                                                        final String list_Colouring = document.get("Colouring").toString();
                                                        final String list_Chill_Filtered = document.get("Chill_Filtered").toString();

                                                        final String list_Taste_Nose_E = document.get("Taste_Nose_E").toString();
                                                        final String list_Taste_Nose_K = document.get("Taste_Nose_K").toString();
                                                        final String list_Taste_Nose_J = document.get("Taste_Nose_J").toString();
                                                        final String list_Taste_Nose_C = document.get("Taste_Nose_C").toString();

                                                        final String list_Taste_Plate_E = document.get("Taste_Plate_E").toString();
                                                        final String list_Taste_Plate_K = document.get("Taste_Plate_K").toString();
                                                        final String list_Taste_Plate_J = document.get("Taste_Plate_J").toString();
                                                        final String list_Taste_Plate_C = document.get("Taste_Plate_C").toString();

                                                        final String list_Taste_Finish_E = document.get("Taste_Finish_E").toString();
                                                        final String list_Taste_Finish_K = document.get("Taste_Finish_K").toString();
                                                        final String list_Taste_Finish_J = document.get("Taste_Finish_J").toString();
                                                        final String list_Taste_Finish_C = document.get("Taste_Finish_C").toString();


                                                        listViewAdapter.addItem(language_setting, list_Number, list_Code, list_Property_Menu_Main, list_Property_Menu_Sub, list_Category,
                                                                list_Name_E, list_Name_K, list_Name_J, list_Name_C, list_Name_Sub,
                                                                list_Stock_Glass, list_Stock_Bottle,
                                                                list_Price_Glass, list_Price_Bottle);
                                                        listViewAdapter.notifyDataSetChanged();


                                                        Log.d(TAG, "Number : " + list_Number + "Code : " + list_Code + "Name_E : "+ list_Name_E + "Log : List Item Adding");


                                                    }

                                                    Log.d(TAG, "Log : Firebase DB Load Success!");


                                                } else {
                                                    Log.d(TAG, "Error : getting documents: ", task.getException());
                                                }

                                            }

                                        });

                            }


                            //리스트뷰에 어뎁터 set
                            listView.setAdapter(listViewAdapter);

                            Log.d(TAG, "Log : ListView Adapter Set Success!");

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable()  {
                                public void run() {
                                    LoadingDialog.cancel(); //로딩화면 종료

                                    layout_listview.setVisibility(View.VISIBLE);

                                    layout_listview.startAnimation(ani_listview);
                                    ani_arrow();
                                }
                            }, 1000);



                        }
                        break;

                        case "Cocktail": {


                            setLayout_menu_title(Property_Menu_Main, Property_Menu_Sub);
                            //리스트뷰, 리스트뷰 어뎁터 초기화


                            listView_Cocktail = (ListView)findViewById(R.id.listview_scroll);
                            listViewAdapter_Cocktail = new ListViewAdapter_Cocktail();
                            db_menu_Cocktail.orderBy("Number")
                                    .whereEqualTo("Category", Property_Menu_Sub)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                    final String list_Number = document.get("Number").toString();

                                                    final String list_Code = document.get("Code").toString();

                                                    final String list_Property_Menu_Main = Property_Menu_Main;
                                                    final String list_Property_Menu_Sub = Property_Menu_Sub;

                                                    final String list_Category = document.get("Category").toString();
                                                    final String list_Type = document.get("Type").toString();

                                                    final String list_Name_E = document.get("Name_E").toString();
                                                    final String list_Name_K = document.get("Name_K").toString();
                                                    final String list_Name_J = document.get("Name_J").toString();
                                                    final String list_Name_C = document.get("Name_C").toString();

                                                    final String list_Base = document.get("Base").toString();
                                                    final String list_Simple_Recipe = document.get("Simple_Recipe").toString();
                                                    final String list_Glass = document.get("Glass").toString();
                                                    final String list_Price_Glass = document.get("Price_Glass").toString();

                                                    String iv_name = list_Glass.toLowerCase();
                                                    iv_name = iv_name.replace(" ", "");
                                                    iv_name = iv_name.replace("&", "");
                                                    iv_name = iv_name.replace("'", "");

                                                    final int resid = getResources().getIdentifier(iv_name, "drawable", getPackageName());


                                                    listViewAdapter_Cocktail.addItem(language_setting,
                                                            list_Number, list_Code, list_Property_Menu_Main, list_Property_Menu_Sub, list_Category, list_Type,
                                                            list_Name_E, list_Name_K, list_Name_J, list_Name_C,
                                                            list_Base, list_Simple_Recipe, list_Glass, list_Price_Glass, resid);
                                                    listViewAdapter_Cocktail.notifyDataSetChanged();


                                                }

                                                Log.d(TAG, "Log : Firebase DB Load Success!");


                                            } else {
                                                Log.d(TAG, "Error : getting documents: ", task.getException());
                                            }

                                        }



                                    });

                            //리스트뷰에 어뎁터 set
                            listView_Cocktail.setAdapter(listViewAdapter_Cocktail);

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable()  {
                                public void run() {
                                    LoadingDialog.cancel(); //로딩화면 종료

                                    layout_listview.setVisibility(View.VISIBLE);

                                    layout_listview.startAnimation(ani_listview);
                                    ani_arrow();
                                }
                            }, 600);
                            Log.d(TAG, "Log : ListView Adapter Set Success!");




                        }
                        break;

                        case "WineAndSpirits": {
                            setLayout_menu_title(Property_Menu_Main, Property_Menu_Sub);
                            //리스트뷰, 리스트뷰 어뎁터 초기화
                            listView = (ListView)findViewById(R.id.listview_scroll);
                            listViewAdapter = new ListViewAdapter();
                            db_menu_Spirits.orderBy("Number")
                                    .whereEqualTo("Property_Menu_Sub", Property_Menu_Sub)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                    final String list_Number = document.get("Number").toString();

                                                    final String list_Code = document.get("Code").toString();
                                                    final String list_Liquor_Company = document.get("Liquor_Company").toString();

                                                    final String list_Property_Menu_Main = document.get("Property_Menu_Main").toString();
                                                    final String list_Property_Menu_Sub = document.get("Property_Menu_Sub").toString();

                                                    final String list_Category = document.get("Category").toString();
                                                    final String list_Country = document.get("Country").toString();
                                                    final String list_Region = document.get("Region").toString();

                                                    final String list_Name_E = document.get("Name_E").toString();
                                                    final String list_Name_K = document.get("Name_K").toString();
                                                    final String list_Name_J = document.get("Name_J").toString();
                                                    final String list_Name_C = document.get("Name_C").toString();
                                                    final String list_Name_Sub = document.get("Name_Sub").toString();

                                                    final String list_Aging = document.get("Aging").toString();
                                                    final String list_Cask = document.get("Cask").toString();
                                                    final String list_ABV = document.get("ABV").toString();

                                                    final String list_Size_Bottle = document.get("Size_Bottle").toString();
                                                    final String list_Size_30ml = document.get("Size_30ml").toString();

                                                    final String list_Stock_Glass = document.get("Stock_Glass").toString();
                                                    final String list_Stock_Bottle = document.get("Stock_Bottle").toString();

                                                    final String list_Price_Glass = document.get("Price_Glass").toString();
                                                    final String list_Price_Glass_VAT_False = document.get("Price_Glass_VAT_False").toString();
                                                    final String list_Margin_Glass = document.get("Margin_Glass").toString();

                                                    final String list_Price_Bottle = document.get("Price_Bottle").toString();
                                                    final String list_Price_Bottle_VAT_False = document.get("Price_Bottle_VAT_False").toString();
                                                    final String list_Margin_Bottle = document.get("Margin_Bottle").toString();

                                                    final String list_Stock_Price_30ml_VAT = document.get("Stock_Price_30ml_VAT").toString();
                                                    final String list_Stock_Price_Bottle = document.get("Stock_Price_Bottle").toString();
                                                    final String list_Stock_Price_Bottle_VAT = document.get("Stock_Price_Bottle_VAT").toString();

                                                    //final String list_Multiply_Glass = document.get("Multiply_Glass").toString();
                                                    //final String list_Multiply_Bottle = document.get("Multiply_Bottle").toString();

                                                    final String list_Event_Sale_Glass = document.get("Event_Sale_Glass").toString();
                                                    final String list_Event_Sale_Price_Glass = document.get("Event_Sale_Price_Glass").toString();
                                                    final String list_Event_Sale_Bottle = document.get("Event_Sale_Bottle").toString();
                                                    final String list_Event_Sale_Price_Bottle = document.get("Event_Sale_Price_Bottle").toString();

                                                    final String list_Last_Update = document.get("Last_Update").toString();

                                                    final String list_Reference = document.get("Reference").toString();

                                                    final String list_Distillery = document.get("Distillery").toString();

                                                    final String list_Tag = document.get("Tag").toString();

                                                    final String list_Info_E = document.get("Info_E").toString();
                                                    final String list_Info_K = document.get("Info_K").toString();
                                                    final String list_Info_J = document.get("Info_J").toString();
                                                    final String list_Info_C = document.get("Info_C").toString();

                                                    final String list_Finder_Tag = document.get("Finder_Tag").toString();

                                                    final String list_Lv_Body = document.get("Lv_Body").toString();
                                                    final String list_Lv_Richness = document.get("Lv_Richness").toString();
                                                    final String list_Lv_Smoke = document.get("Lv_Smoke").toString();
                                                    final String list_Lv_Sweetness = document.get("Lv_Sweetness").toString();

                                                    final String list_Flavour_Character = document.get("Flavour_Character").toString();
                                                    final String list_Colouring = document.get("Colouring").toString();
                                                    final String list_Chill_Filtered = document.get("Chill_Filtered").toString();

                                                    final String list_Taste_Nose_E = document.get("Taste_Nose_E").toString();
                                                    final String list_Taste_Nose_K = document.get("Taste_Nose_K").toString();
                                                    final String list_Taste_Nose_J = document.get("Taste_Nose_J").toString();
                                                    final String list_Taste_Nose_C = document.get("Taste_Nose_C").toString();

                                                    final String list_Taste_Plate_E = document.get("Taste_Plate_E").toString();
                                                    final String list_Taste_Plate_K = document.get("Taste_Plate_K").toString();
                                                    final String list_Taste_Plate_J = document.get("Taste_Plate_J").toString();
                                                    final String list_Taste_Plate_C = document.get("Taste_Plate_C").toString();

                                                    final String list_Taste_Finish_E = document.get("Taste_Finish_E").toString();
                                                    final String list_Taste_Finish_K = document.get("Taste_Finish_K").toString();
                                                    final String list_Taste_Finish_J = document.get("Taste_Finish_J").toString();
                                                    final String list_Taste_Finish_C = document.get("Taste_Finish_C").toString();


                                                    listViewAdapter.addItem(language_setting, list_Number, list_Code, list_Property_Menu_Main, list_Property_Menu_Sub, list_Category,
                                                            list_Name_E, list_Name_K, list_Name_J, list_Name_C, list_Name_Sub,
                                                            list_Stock_Glass, list_Stock_Bottle,
                                                            list_Price_Glass, list_Price_Bottle);
                                                    listViewAdapter.notifyDataSetChanged();


                                                    Log.d(TAG, "Number : " + list_Number + "Code : " + list_Code + "Name_E : "+ list_Name_E + "Log : List Item Adding");


                                                }

                                                Log.d(TAG, "Log : Firebase DB Load Success!");


                                            } else {
                                                Log.d(TAG, "Error : getting documents: ", task.getException());
                                            }

                                        }

                                    });

                            //리스트뷰에 어뎁터 set
                            listView.setAdapter(listViewAdapter);

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable()  {
                                public void run() {
                                    LoadingDialog.cancel(); //로딩화면 종료

                                    layout_listview.setVisibility(View.VISIBLE);

                                    layout_listview.startAnimation(ani_listview);
                                    ani_arrow();
                                }
                            }, 1000);

                            Log.d(TAG, "Log : ListView Adapter Set Success!");


                        }
                        break;


                        default: {

                            LoadingDialog.cancel(); //로딩화면 종료

                        }
                        break;




                    }
                }
            }, 300);// 0.6초 정도 딜레이를 준 후 시작



        }







    }

    public void createListView(){




    }



    //검색 기능

    public void getDBFirebase_finder(String Property_Menu_Main, String Property_Menu_Sub){

        Dialog_Loading LoadingDialog = new Dialog_Loading(this); //다이얼로그 선언
        LoadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); //주변 투명
        LoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //백그라운를 투명하게
        LoadingDialog.setCanceledOnTouchOutside(false);
        LoadingDialog.setCancelable(false); //다이얼로그 외부 클릭으로 종료되지 않게
        LoadingDialog.show(); //로딩화면 보여주기


        //ani_scroll();
        getProperty_Menu_Main = Property_Menu_Main;
        getProperty_Menu_Sub = Property_Menu_Sub;

        Log.d(TAG, "get 메인 값 : " + getProperty_Menu_Main + "get 서브 값 : " + getProperty_Menu_Sub);

        //화살표 안보임
        clear_ani();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //딜레이 후 시작할 코드 작성
                switch(Property_Menu_Main)
                {

                    case "Whisky" : {
                        listView.setVisibility(View.VISIBLE);
                        layout_FreeOrder.setVisibility(View.INVISIBLE);

                        Log.d(TAG, "메인 값 : " + Property_Menu_Main + "서브 값 : " + Property_Menu_Sub);


                        layout_listview.setVisibility(View.INVISIBLE);


                        // 파인더 태그 리스트 생성
                        List<String> finder_Whisky_finder_tag = new ArrayList<>();

                        // 파인더 태그 체크 리스트 생성
                        List<Boolean> isChecked_finder_Whisky_finder_tag = new ArrayList<>();


                        //체크 박스 상태 확인 후 태그 리스트에 추가

                        if(checkBox_finder_Whisky_finder_tag_Honey.isChecked()){
                            finder_Whisky_finder_tag.add("Honey");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Floral.isChecked()){
                            finder_Whisky_finder_tag.add("Floral");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Vanilla.isChecked()){
                            finder_Whisky_finder_tag.add("Vanilla");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Cinnamon.isChecked()){
                            finder_Whisky_finder_tag.add("Cinnamon");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }


                        if(checkBox_finder_Whisky_finder_tag_Fresh_Fruits.isChecked()){
                            finder_Whisky_finder_tag.add("Fresh Fruits");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Dried_Fruits.isChecked()){
                            finder_Whisky_finder_tag.add("Dried Fruits");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Citrus_Fruits.isChecked()){
                            finder_Whisky_finder_tag.add("Citrus Fruits");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Sweet_Fruits.isChecked()){
                            finder_Whisky_finder_tag.add("Sweet_Fruits");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }


                        if(checkBox_finder_Whisky_finder_tag_Butter.isChecked()){
                            finder_Whisky_finder_tag.add("Butter");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Cream.isChecked()){
                            finder_Whisky_finder_tag.add("Cream");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Caramel.isChecked()){
                            finder_Whisky_finder_tag.add("Caramel");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Chocolate.isChecked()){
                            finder_Whisky_finder_tag.add("Chocolate");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }


                        if(checkBox_finder_Whisky_finder_tag_Nuts.isChecked()){
                            finder_Whisky_finder_tag.add("Nuts");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Malt.isChecked()){
                            finder_Whisky_finder_tag.add("Malt");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Wood.isChecked()){
                            finder_Whisky_finder_tag.add("Wood");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Salt.isChecked()){
                            finder_Whisky_finder_tag.add("Salt");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }



                        if(checkBox_finder_Whisky_finder_tag_Spicy.isChecked()){
                            finder_Whisky_finder_tag.add("Spicy");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }


                        if(checkBox_finder_Whisky_finder_tag_Smoky.isChecked()){
                            finder_Whisky_finder_tag.add("Smoky");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Peat.isChecked()){
                            finder_Whisky_finder_tag.add("Peat");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }


                        if(checkBox_finder_Whisky_finder_tag_Herb.isChecked()){
                            finder_Whisky_finder_tag.add("Herb");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }



                        if(checkBox_finder_Whisky_finder_tag_Strength.isChecked()){
                            finder_Whisky_finder_tag.add("Strength");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Limited.isChecked()){
                            finder_Whisky_finder_tag.add("Limited");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        if(checkBox_finder_Whisky_finder_tag_Old.isChecked()){
                            finder_Whisky_finder_tag.add("Old");
                            Log.d(TAG, " finder_Whisky_finder_tag: " + finder_Whisky_finder_tag);
                        }

                        //


                        //본격적인 시작

                        setLayout_menu_title(Property_Menu_Main, Property_Menu_Sub);
                        //리스트뷰, 리스트뷰 어뎁터 초기화
                        listView = (ListView)findViewById(R.id.listview_scroll);
                        listViewAdapter = new ListViewAdapter();
                        db_menu_Spirits.orderBy("Number")
                                .whereEqualTo("Property_Menu_Sub", Property_Menu_Sub)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {

                                                final String list_Number = document.get("Number").toString();

                                                final String list_Code = document.get("Code").toString();
                                                final String list_Liquor_Company = document.get("Liquor_Company").toString();

                                                final String list_Property_Menu_Main = document.get("Property_Menu_Main").toString();
                                                final String list_Property_Menu_Sub = document.get("Property_Menu_Sub").toString();

                                                final String list_Category = document.get("Category").toString();
                                                final String list_Country = document.get("Country").toString();
                                                final String list_Region = document.get("Region").toString();

                                                final String list_Name_E = document.get("Name_E").toString();
                                                final String list_Name_K = document.get("Name_K").toString();
                                                final String list_Name_J = document.get("Name_J").toString();
                                                final String list_Name_C = document.get("Name_C").toString();
                                                final String list_Name_Sub = document.get("Name_Sub").toString();

                                                final String list_Aging = document.get("Aging").toString();
                                                final String list_Cask = document.get("Cask").toString();
                                                final String list_ABV = document.get("ABV").toString();

                                                final String list_Size_Bottle = document.get("Size_Bottle").toString();
                                                final String list_Size_30ml = document.get("Size_30ml").toString();

                                                final String list_Stock_Glass = document.get("Stock_Glass").toString();
                                                final String list_Stock_Bottle = document.get("Stock_Bottle").toString();

                                                final String list_Price_Glass = document.get("Price_Glass").toString();
                                                final String list_Price_Glass_VAT_False = document.get("Price_Glass_VAT_False").toString();
                                                final String list_Margin_Glass = document.get("Margin_Glass").toString();

                                                final String list_Price_Bottle = document.get("Price_Bottle").toString();
                                                final String list_Price_Bottle_VAT_False = document.get("Price_Bottle_VAT_False").toString();
                                                final String list_Margin_Bottle = document.get("Margin_Bottle").toString();

                                                final String list_Stock_Price_30ml_VAT = document.get("Stock_Price_30ml_VAT").toString();
                                                final String list_Stock_Price_Bottle = document.get("Stock_Price_Bottle").toString();
                                                final String list_Stock_Price_Bottle_VAT = document.get("Stock_Price_Bottle_VAT").toString();

                                                //final String list_Multiply_Glass = document.get("Multiply_Glass").toString();
                                                //final String list_Multiply_Bottle = document.get("Multiply_Bottle").toString();

                                                final String list_Event_Sale_Glass = document.get("Event_Sale_Glass").toString();
                                                final String list_Event_Sale_Price_Glass = document.get("Event_Sale_Price_Glass").toString();
                                                final String list_Event_Sale_Bottle = document.get("Event_Sale_Bottle").toString();
                                                final String list_Event_Sale_Price_Bottle = document.get("Event_Sale_Price_Bottle").toString();

                                                final String list_Last_Update = document.get("Last_Update").toString();

                                                final String list_Reference = document.get("Reference").toString();

                                                final String list_Distillery = document.get("Distillery").toString();

                                                final String list_Tag = document.get("Tag").toString();

                                                final String list_Info_E = document.get("Info_E").toString();
                                                final String list_Info_K = document.get("Info_K").toString();
                                                final String list_Info_J = document.get("Info_J").toString();
                                                final String list_Info_C = document.get("Info_C").toString();

                                                final String list_Finder_Tag = document.get("Finder_Tag").toString();

                                                final String list_Lv_Body = document.get("Lv_Body").toString();
                                                final String list_Lv_Richness = document.get("Lv_Richness").toString();
                                                final String list_Lv_Smoke = document.get("Lv_Smoke").toString();
                                                final String list_Lv_Sweetness = document.get("Lv_Sweetness").toString();

                                                final String list_Flavour_Character = document.get("Flavour_Character").toString();
                                                final String list_Colouring = document.get("Colouring").toString();
                                                final String list_Chill_Filtered = document.get("Chill_Filtered").toString();

                                                final String list_Taste_Nose_E = document.get("Taste_Nose_E").toString();
                                                final String list_Taste_Nose_K = document.get("Taste_Nose_K").toString();
                                                final String list_Taste_Nose_J = document.get("Taste_Nose_J").toString();
                                                final String list_Taste_Nose_C = document.get("Taste_Nose_C").toString();

                                                final String list_Taste_Plate_E = document.get("Taste_Plate_E").toString();
                                                final String list_Taste_Plate_K = document.get("Taste_Plate_K").toString();
                                                final String list_Taste_Plate_J = document.get("Taste_Plate_J").toString();
                                                final String list_Taste_Plate_C = document.get("Taste_Plate_C").toString();

                                                final String list_Taste_Finish_E = document.get("Taste_Finish_E").toString();
                                                final String list_Taste_Finish_K = document.get("Taste_Finish_K").toString();
                                                final String list_Taste_Finish_J = document.get("Taste_Finish_J").toString();
                                                final String list_Taste_Finish_C = document.get("Taste_Finish_C").toString();


/*

                                                    // 조건 반복문. 체크박스에 해당되는것 찾기

                                                    for(int i = 0; i < finder_Whisky_finder_tag.size(); i++){

                                                        Log.d(TAG, "Tag Check : " + i);

                                                        if(list_Tag.contains(finder_Whisky_finder_tag.get(i))){
                                                            isChecked_finder_Whisky_finder_tag.add(true);
                                                        }
                                                        else{
                                                            isChecked_finder_Whisky_finder_tag.add(false);

                                                        }


                                                    }

*/

                                                // 태그 리스트 문자열 > 리스트 변경
                                                List<String> isChanged_list_Finder_Tag = Arrays.asList(list_Finder_Tag.split(","));

                                                // 조건에 맞는 위스키 체크
                                                if(isChanged_list_Finder_Tag.containsAll(finder_Whisky_finder_tag)) {


                                                    listViewAdapter.addItem(language_setting, list_Number, list_Code, list_Property_Menu_Main, list_Property_Menu_Sub, list_Category,
                                                            list_Name_E, list_Name_K, list_Name_J, list_Name_C, list_Name_Sub,
                                                            list_Stock_Glass, list_Stock_Bottle,
                                                            list_Price_Glass, list_Price_Bottle);
                                                    listViewAdapter.notifyDataSetChanged();


                                                    Log.d(TAG, "Number : " + list_Number + "Code : " + list_Code + "Name_E : "+ list_Name_E + "Log : List Item Adding");


                                                }

                                                else{

                                                    Log.d(TAG, "Error : No Tag data: ", task.getException());

                                                }



                                            }

                                            Log.d(TAG, "Log : Firebase DB Load Success!");


                                        } else {
                                            Log.d(TAG, "Error : getting documents: ", task.getException());
                                        }

                                    }

                                });


/*
                        Log.d(TAG, "Check : listView Child Count" + listView.getChildCount());
                        if(listView.getChildCount()==0){

                            listViewAdapter.addItem(language_setting, "00000000", "00000000", Property_Menu_Main, Property_Menu_Sub, "list_Category",
                                    "We Couldn't find", "체크하신 조건에 맞는 제품을 \n찾지 못했습니다.", "list_Name_J", "list_Name_C", "",
                                    "", "",
                                    "", "");
                            listViewAdapter.notifyDataSetChanged();

                        }
                        */

                        //리스트뷰에 어뎁터 set
                        listView.setAdapter(listViewAdapter);

                        Log.d(TAG, "Log : ListView Adapter Set Success!");

                        Handler mHandler = new Handler();
                        mHandler.postDelayed(new Runnable()  {
                            public void run() {
                                LoadingDialog.cancel(); //로딩화면 종료

                                layout_listview.setVisibility(View.VISIBLE);

                                layout_listview.startAnimation(ani_listview);
                                ani_arrow();
                            }
                        }, 1000);



                    }
                    break;

                    case "Cocktail": {


                        // 파인더 태그 리스트 생성
                        List<String> finder_Cocktail_Finder_Tag = new ArrayList<>();

                        //체크 박스 상태 확인 후 태그 리스트에 추가


                        if(getProperty_Menu_Sub == "Classic"){







                            setLayout_menu_title(Property_Menu_Main, Property_Menu_Sub);
                            //리스트뷰, 리스트뷰 어뎁터 초기화


                            listView_Cocktail = (ListView)findViewById(R.id.listview_scroll);
                            listViewAdapter_Cocktail = new ListViewAdapter_Cocktail();
                            db_menu_Cocktail.orderBy("Number")
/*
                                    .whereEqualTo("Category", Property_Menu_Sub)
*/
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {


                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                    final String list_Number = document.get("Number").toString();

                                                    final String list_Code = document.get("Code").toString();

                                                    final String list_Property_Menu_Main = Property_Menu_Main;
                                                    final String list_Property_Menu_Sub = Property_Menu_Sub;

                                                    final String list_Category = document.get("Category").toString();
                                                    final String list_Type = document.get("Type").toString();

                                                    final String list_Name_E = document.get("Name_E").toString();
                                                    final String list_Name_K = document.get("Name_K").toString();
                                                    final String list_Name_J = document.get("Name_J").toString();
                                                    final String list_Name_C = document.get("Name_C").toString();

                                                    final String list_Base = document.get("Base").toString();
                                                    final String list_Simple_Recipe = document.get("Simple_Recipe").toString();
                                                    final String list_Glass = document.get("Glass").toString();
                                                    final String list_Price_Glass = document.get("Price_Glass").toString();

                                                    Log.d(TAG, "Checked : getDB Part 1, ");

                                                    String iv_name = list_Glass;

                                                    if(list_Category.equals("Signature")){
                                                        iv_name = iv_name.replace(" ", "");
                                                        iv_name = iv_name.replace("&", "");
                                                        iv_name = iv_name.replace("'", "");
                                                    }

                                                    else{
                                                        iv_name = list_Glass.toLowerCase();
                                                        iv_name = iv_name.replace(" ", "");
                                                        iv_name = iv_name.replace("&", "");
                                                        iv_name = iv_name.replace("'", "");
                                                    }

                                                    Log.d(TAG, "Checked : getDB Part 2, ");



                                                    final String list_Finder_Tag = document.get("Finder_Tag").toString();

                                                    final String list_Lv_AlcoholBooze = document.get("Lv_AlcoholBooze").toString();
                                                    final String list_Lv_Sparkling = document.get("Lv_Sparkling").toString();

                                                    Log.d(TAG, "Checked : getDB Part 3, ");

                                                    final int getlist_Lv_AlcoholBooze = Integer.parseInt(list_Lv_AlcoholBooze);
                                                    final int getlist_list_Lv_Sparkling = Integer.parseInt(list_Lv_Sparkling);


                                                    Log.d(TAG, "Checked : getDB Part 4, ");

                                                    final int resid = getResources().getIdentifier(iv_name, "drawable", getPackageName());

                                                    Log.d(TAG, "Checked : getDB Part 5, ");

                                                    // 태그 리스트 문자열 > 리스트 변경
                                                    List<String> isChanged_list_Finder_Tag = Arrays.asList(list_Finder_Tag.split(","));

                                                    Log.d(TAG, "Checked : Finder_Tag Log, " + isChanged_list_Finder_Tag);

                                                    // 조건에 맞는 칵테일 체크


                                                    //타이틀일경우 검색 x
                                                    if(list_Type.equals("Title")){
                                                        Log.d(TAG, "Error : Don't DB. It's a Title");


                                                    }

                                                    else{
                                                        if(list_Category.equals("Classic")||list_Category.equals("FreeOrder")) {

                                                            Log.d(TAG, "Checked : Category Log, " + list_Category);

                                                            Log.d(TAG, "final finder Checked : finder_Cocktail_Finder_Tag, " + finder_Cocktail_Finder_Tag);
                                                            Log.d(TAG, "final finder Checked : isChanged_list_Finder_Tag, " + isChanged_list_Finder_Tag);

                                                            if(radiobutton_finder_Classic_finder_tag_fruits_citrus.isChecked()){
                                                                finder_Cocktail_Finder_Tag.add("Lemon");
                                                                finder_Cocktail_Finder_Tag.add("Lime");
                                                                finder_Cocktail_Finder_Tag.add("Orange");
                                                                finder_Cocktail_Finder_Tag.add("Grapefruit");
                                                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                                                            }

                                                            if(radiobutton_finder_Classic_finder_tag_fruits_berry.isChecked()){
                                                                finder_Cocktail_Finder_Tag.add("Berry");
                                                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                                                            }

                                                            if(radiobutton_finder_Classic_finder_tag_fruits_soft.isChecked()){
                                                                finder_Cocktail_Finder_Tag.add("Peach");
                                                                finder_Cocktail_Finder_Tag.add("Apple");
                                                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                                                            }

                                                            if(radiobutton_finder_Classic_finder_tag_fruits_tropical.isChecked()){
                                                                finder_Cocktail_Finder_Tag.add("Melon");
                                                                finder_Cocktail_Finder_Tag.add("Coconut");
                                                                finder_Cocktail_Finder_Tag.add("Pineapple");
                                                                finder_Cocktail_Finder_Tag.add("Banana");
                                                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                                                            }

                                                            if(radiobutton_finder_Classic_finder_tag_milk_cream.isChecked()){
                                                                finder_Cocktail_Finder_Tag.add("Milk");
                                                                finder_Cocktail_Finder_Tag.add("Cream");
                                                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                                                            }

                                                            if(radiobutton_finder_Classic_finder_tag_chocolate_coffee.isChecked()){
                                                                finder_Cocktail_Finder_Tag.add("Chocolate");
                                                                finder_Cocktail_Finder_Tag.add("Coffee");
                                                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                                                            }

                                                            if(radiobutton_finder_Classic_finder_tag_herb_spice.isChecked()){
                                                                finder_Cocktail_Finder_Tag.add("Cinnamon");
                                                                finder_Cocktail_Finder_Tag.add("Herb");
                                                                finder_Cocktail_Finder_Tag.add("Wood");
                                                                finder_Cocktail_Finder_Tag.add("Honey");
                                                                finder_Cocktail_Finder_Tag.add("Ginger");
                                                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                                                            }


/*
                                                            // 태그 리스트 카피
                                                            List<String> copy_finder_Cocktail_Finder_Tag = new ArrayList<>();
                                                            copy_finder_Cocktail_Finder_Tag = finder_Cocktail_Finder_Tag;

                                                            Log.d(TAG, "final finder Checked : copy_finder_Cocktail_Finder_Tag, " + copy_finder_Cocktail_Finder_Tag);
*/


                                                            if(finder_Cocktail_Finder_Tag.size()==0){
                                                                if(isChecked_Lv_AlcoholBooze == getlist_Lv_AlcoholBooze
                                                                        || isChecked_Lv_AlcoholBooze == 4) {
                                                                    Log.d(TAG, "Checked : isChecked_Lv_AlcoholBooze Log, " + getlist_Lv_AlcoholBooze);


                                                                    if(radiobutton_finder_Classic_finder_tag_sparkling.isChecked()){


                                                                        if(getlist_list_Lv_Sparkling > 0){
                                                                            final int resid_ = getResources().getIdentifier(iv_name, "drawable", getPackageName());

                                                                            listViewAdapter_Cocktail.addItem(language_setting,
                                                                                    list_Number, list_Code, list_Property_Menu_Main, list_Property_Menu_Sub, list_Category, list_Type,
                                                                                    list_Name_E, list_Name_K, list_Name_J, list_Name_C,
                                                                                    list_Base, list_Simple_Recipe, list_Glass, list_Price_Glass, resid_);
                                                                            listViewAdapter_Cocktail.notifyDataSetChanged();

                                                                            Log.d(TAG, "Checked Cocktail Log, Number : " + list_Number + "Code : " + list_Code + "Name_E : "+ list_Name_E + " Log : List Item Adding");


                                                                        }

                                                                    }

                                                                    else{
                                                                        final int resid_ = getResources().getIdentifier(iv_name, "drawable", getPackageName());

                                                                        listViewAdapter_Cocktail.addItem(language_setting,
                                                                                list_Number, list_Code, list_Property_Menu_Main, list_Property_Menu_Sub, list_Category, list_Type,
                                                                                list_Name_E, list_Name_K, list_Name_J, list_Name_C,
                                                                                list_Base, list_Simple_Recipe, list_Glass, list_Price_Glass, resid_);
                                                                        listViewAdapter_Cocktail.notifyDataSetChanged();

                                                                        Log.d(TAG, "Checked Cocktail Log, Number : " + list_Number + "Code : " + list_Code + "Name_E : "+ list_Name_E + " Log : List Item Adding");




                                                                    }


                                                                }

                                                                else{

                                                                    Log.d(TAG, "Error : No Tag data: ", task.getException());

                                                                }


                                                            }

                                                            else{
                                                                finder_Cocktail_Finder_Tag.retainAll(isChanged_list_Finder_Tag);

                                                                Log.d(TAG, "Checked : retainAll finder_Cocktail_Finder_Tag, " + finder_Cocktail_Finder_Tag);


                                                                if(finder_Cocktail_Finder_Tag.size() > 0) {

                                                                    if(isChecked_Lv_AlcoholBooze == getlist_Lv_AlcoholBooze
                                                                            || isChecked_Lv_AlcoholBooze == 4) {
                                                                        Log.d(TAG, "Checked : isChecked_Lv_AlcoholBooze Log, " + getlist_Lv_AlcoholBooze);


                                                                        if(radiobutton_finder_Classic_finder_tag_sparkling.isChecked()){


                                                                            if(getlist_list_Lv_Sparkling > 0){
                                                                                final int resid_ = getResources().getIdentifier(iv_name, "drawable", getPackageName());

                                                                                listViewAdapter_Cocktail.addItem(language_setting,
                                                                                        list_Number, list_Code, list_Property_Menu_Main, list_Property_Menu_Sub, list_Category, list_Type,
                                                                                        list_Name_E, list_Name_K, list_Name_J, list_Name_C,
                                                                                        list_Base, list_Simple_Recipe, list_Glass, list_Price_Glass, resid_);
                                                                                listViewAdapter_Cocktail.notifyDataSetChanged();

                                                                                Log.d(TAG, "Checked Cocktail Log, Number : " + list_Number + "Code : " + list_Code + "Name_E : "+ list_Name_E + " Log : List Item Adding");


                                                                            }

                                                                        }

                                                                        else{
                                                                            final int resid_ = getResources().getIdentifier(iv_name, "drawable", getPackageName());

                                                                            listViewAdapter_Cocktail.addItem(language_setting,
                                                                                    list_Number, list_Code, list_Property_Menu_Main, list_Property_Menu_Sub, list_Category, list_Type,
                                                                                    list_Name_E, list_Name_K, list_Name_J, list_Name_C,
                                                                                    list_Base, list_Simple_Recipe, list_Glass, list_Price_Glass, resid_);
                                                                            listViewAdapter_Cocktail.notifyDataSetChanged();

                                                                            Log.d(TAG, "Checked Cocktail Log, Number : " + list_Number + "Code : " + list_Code + "Name_E : "+ list_Name_E + " Log : List Item Adding");




                                                                        }


                                                                    }


                                                                }

                                                                else{

                                                                    Log.d(TAG, "Error : No Tag data: ", task.getException());

                                                                }

                                                            }



                                                        }
                                                        else{
                                                            Log.d(TAG, "Error : Category Error!");


                                                        }


                                                    }







                                                }

                                                Log.d(TAG, "Log : Firebase DB Load Success!");





                                            } else {
                                                Log.d(TAG, "Error : getting documents: ", task.getException());
                                            }

                                        }



                                    });

                            //리스트뷰에 어뎁터 set

                            listView_Cocktail.setAdapter(listViewAdapter_Cocktail);

                            Log.d(TAG, "Log : ListView Adapter Set Success!");

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable()  {
                                public void run() {
                                    LoadingDialog.cancel(); //로딩화면 종료
                                    /*
                                    layout_listview.setVisibility(View.VISIBLE);

                                    layout_listview.startAnimation(ani_listview);
                                    */
                                    ani_arrow();

                                }
                            }, 1000);

                        }

                        else {

                            /*
                            listView.setVisibility(View.VISIBLE);
                            layout_FreeOrder.setVisibility(View.INVISIBLE);

                            Log.d(TAG, "메인 값 : " + Property_Menu_Main + "서브 값 : " + Property_Menu_Sub);


                            layout_listview.setVisibility(View.INVISIBLE);
                            */

                            // 파인더 랜덤박스용 리스트 생성
                            List<String> finder_list_Number = new ArrayList<>();



                            if(checkbox_finder_Cocktail_tag_Lemon.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Lemon");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Lime.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Lime");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Peach.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Peach");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Berry.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Berry");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Orange.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Orange");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Melon.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Melon");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Grapefruit.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Grapefruit");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Cinnamon.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Cinnamon");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Coconut.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Coconut");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Herb.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Herb");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Milk.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Milk");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Pineapple.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Pineapple");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Apple.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Apple");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Coffee.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Coffee");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Chocolate.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Chocolate");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Wood.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Wood");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Smoke.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Smoke");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Honey.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Honey");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Ginger.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Ginger");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }

                            if(checkbox_finder_Cocktail_tag_Banana.isChecked()){
                                finder_Cocktail_Finder_Tag.add("Banana");
                                Log.d(TAG, " finder_Cocktail_tag: " + finder_Cocktail_Finder_Tag);
                            }
                            //


                            setLayout_menu_title(Property_Menu_Main, Property_Menu_Sub);
                            //리스트뷰, 리스트뷰 어뎁터 초기화


                            listView_Cocktail = (ListView)findViewById(R.id.listview_scroll);
                            listViewAdapter_Cocktail = new ListViewAdapter_Cocktail();
                            db_menu_Cocktail.orderBy("Number")
/*
                                    .whereEqualTo("Category", Property_Menu_Sub)
*/
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {


                                                for (QueryDocumentSnapshot document : task.getResult()) {

                                                    final String list_Number = document.get("Number").toString();

                                                    final String list_Code = document.get("Code").toString();

                                                    final String list_Property_Menu_Main = Property_Menu_Main;
                                                    final String list_Property_Menu_Sub = Property_Menu_Sub;

                                                    final String list_Category = document.get("Category").toString();
                                                    final String list_Type = document.get("Type").toString();

                                                    final String list_Name_E = document.get("Name_E").toString();
                                                    final String list_Name_K = document.get("Name_K").toString();
                                                    final String list_Name_J = document.get("Name_J").toString();
                                                    final String list_Name_C = document.get("Name_C").toString();

                                                    final String list_Base = document.get("Base").toString();
                                                    final String list_Simple_Recipe = document.get("Simple_Recipe").toString();
                                                    final String list_Glass = document.get("Glass").toString();
                                                    final String list_Price_Glass = document.get("Price_Glass").toString();

                                                    Log.d(TAG, "Checked : getDB Part 1, ");

                                                    String iv_name = list_Glass;

                                                    if(list_Category.equals("Signature")){
                                                        iv_name = iv_name.replace(" ", "");
                                                        iv_name = iv_name.replace("&", "");
                                                        iv_name = iv_name.replace("'", "");
                                                    }

                                                    else{
                                                        iv_name = list_Glass.toLowerCase();
                                                        iv_name = iv_name.replace(" ", "");
                                                        iv_name = iv_name.replace("&", "");
                                                        iv_name = iv_name.replace("'", "");
                                                    }

                                                    Log.d(TAG, "Checked : getDB Part 2, ");



                                                    final String list_Finder_Tag = document.get("Finder_Tag").toString();

                                                    final String list_Lv_AlcoholBooze = document.get("Lv_AlcoholBooze").toString();
                                                    final String list_Lv_Sparkling = document.get("Lv_Sparkling").toString();
                                                    final String list_Lv_Sweetness = document.get("Lv_Sweetness").toString();
                                                    final String list_Lv_Sour = document.get("Lv_Sour").toString();
                                                    Log.d(TAG, "Checked : getDB Part 3, ");

                                                    final int getlist_Lv_AlcoholBooze = Integer.parseInt(list_Lv_AlcoholBooze);
                                                    final int getlist_list_Lv_Sparkling = Integer.parseInt(list_Lv_Sparkling);
                                                    final int getlist_Lv_list_Lv_Sweetness = Integer.parseInt(list_Lv_Sweetness);
                                                    final int getlist_Lv_list_Lv_Sour = Integer.parseInt(list_Lv_Sour);

                                                    Log.d(TAG, "Checked : getDB Part 4, ");

                                                    final int resid = getResources().getIdentifier(iv_name, "drawable", getPackageName());

                                                    Log.d(TAG, "Checked : getDB Part 5, ");

                                                    // 태그 리스트 문자열 > 리스트 변경
                                                    List<String> isChanged_list_Finder_Tag = Arrays.asList(list_Finder_Tag.split(","));

                                                    Log.d(TAG, "Checked : Finder_Tag Log, " + isChanged_list_Finder_Tag);

                                                    // 조건에 맞는 칵테일 체크


                                                    //타이틀일경우 검색 x
                                                    if(list_Type.equals("Title")){
                                                        Log.d(TAG, "Error : Don't DB. It's a Title");


                                                    }

                                                    else{
                                                        if(list_Category.equals("Classic")||list_Category.equals("FreeOrder")) {

                                                            Log.d(TAG, "Checked : Category Log, " + list_Category);

                                                            if(isChanged_list_Finder_Tag.containsAll(finder_Cocktail_Finder_Tag)) {

                                                                if(isChecked_Lv_AlcoholBooze == getlist_Lv_AlcoholBooze
                                                                        || isChecked_Lv_AlcoholBooze == 4) {
                                                                    Log.d(TAG, "Checked : isChecked_Lv_AlcoholBooze Log, " + getlist_Lv_AlcoholBooze);

                                                                    if(isChecked_Lv_Sparkling == getlist_list_Lv_Sparkling
                                                                            || isChecked_Lv_Sparkling == 4){
                                                                        Log.d(TAG, "Checked : isChecked_Lv_Sparkling Log, " + getlist_list_Lv_Sparkling);

                                                                        if(isChecked_Lv_Sweetness == getlist_Lv_list_Lv_Sweetness
                                                                                || isChecked_Lv_Sweetness == 4){
                                                                            Log.d(TAG, "Checked : isChecked_Lv_Sweetness Log, " + getlist_Lv_list_Lv_Sweetness);

                                                                            if(isChecked_Lv_Sour == getlist_Lv_list_Lv_Sour
                                                                                    || isChecked_Lv_Sour == 4) {
                                                                                Log.d(TAG, "Checked : isChecked_Lv_Sour Log, " + getlist_Lv_list_Lv_Sour);


                /*
                                                                        listViewAdapter_Cocktail.addItem(language_setting,
                                                                                list_Number, list_Code, list_Property_Menu_Main, list_Property_Menu_Sub, list_Category,
                                                                                list_Name_E, list_Name_K, list_Name_J, list_Name_C,
                                                                                list_Base, list_Simple_Recipe, list_Glass, list_Price_Glass, resid);
                                                                        listViewAdapter_Cocktail.notifyDataSetChanged();
                */

                                                                                Log.d(TAG, "Checked Cocktail Log, Number : " + list_Number + "Code : " + list_Code + "Name_E : "+ list_Name_E + " Log : List Item Adding");

                                                                                finder_list_Number.add(list_Number);




                                                                            }

                                                                        }


                                                                    }

                                                                }


                                                            }

                                                            else{

                                                                Log.d(TAG, "Error : No Tag data: ", task.getException());

                                                            }


                                                        }
                                                        else{
                                                            Log.d(TAG, "Error : Category Error!");


                                                        }


                                                    }







                                                }

                                                Log.d(TAG, "Log : Firebase DB Load Success!");





                                            } else {
                                                Log.d(TAG, "Error : getting documents: ", task.getException());
                                            }

                                        }



                                    });

                            //리스트뷰에 어뎁터 set

//                            listView_Cocktail.setAdapter(listViewAdapter_Cocktail);

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable()  {
                                public void run() {

                                    Random random = new Random();

                                    Log.d(TAG, "Checked : Finished GetDB(1). finder_list_Number : " + finder_list_Number);
                                    Log.d(TAG, "Checked : Finished GetDB(2). Property_Menu_Main : " + Property_Menu_Main);
                                    Log.d(TAG, "Checked : Finished GetDB(3). Property_Menu_Sub : " + Property_Menu_Sub);

/*
                                    if(finder_list_Number.size()>5){
                                        LoadingDialog.cancel(); //로딩화면 종료
                                        dialog_finder
                                                .setTitle("칵테일 검색")
                                                .setMessage("\n 조건이 너무 많습니다. \n\n 5개 이하의 조건으로 다시 검색하거나, \n\n 체크하신 내용을 바텐더에게 보여주세요.")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                });
                                        AlertDialog alertDialog = dialog_finder.create();

                                        alertDialog.show();

                                    }

                                    else

                                    */
                                    if(finder_list_Number.size()>0){

                                        int i = random.nextInt(finder_list_Number.size());

                                        Intent intent = new Intent(MainActivity.this, PopupActivityInfo.class);
                                        intent.putExtra("Number",finder_list_Number.get(i));
                                        intent.putExtra("Property_Menu_Main",Property_Menu_Main);
                                        intent.putExtra("Property_Menu_Sub",Property_Menu_Sub);
                                        LoadingDialog.cancel(); //로딩화면 종료
                                        startActivity(intent);
                                    }

                                    else{
                                        LoadingDialog.cancel(); //로딩화면 종료
                                        dialog_finder
                                                .setTitle("칵테일 검색")
                                                .setMessage("\n 모든 조건에 해당하는 칵테일을 찾기 어렵습니다. \n\n 조건을 수정하여 다시 검색하거나, \n\n 체크하신 내용을 바텐더에게 보여주세요.")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {

                                                    }
                                                });
                                        AlertDialog alertDialog = dialog_finder.create();

                                        alertDialog.show();

                                    }
                                }
                            }, 1000);
                            Log.d(TAG, "Log : ListView Adapter Set Success!");



                        }




                    }
                    break;

                    default: {

                        LoadingDialog.cancel(); //로딩화면 종료

                    }
                    break;




                }
            }
        }, 300);// 0.6초 정도 딜레이를 준 후 시작






    }






    //버튼 레이아웃 초기화
    public void setLayoutVisibility(){
        //btn_setting.setVisibility(View.INVISIBLE);
        checkBox_finder.setVisibility(View.INVISIBLE);
        layout_guide.setVisibility(View.INVISIBLE);
        layout_whisky.setVisibility(View.INVISIBLE);
        layout_cocktail.setVisibility(View.INVISIBLE);
        radioButton_menu_sub_Signature.setVisibility(View.INVISIBLE);
        layout_wineandspirits.setVisibility(View.INVISIBLE);

        listView = (ListView)findViewById(R.id.listview_scroll);
        listViewAdapter = new ListViewAdapter();
        listView.setAdapter(listViewAdapter);

        radioGroup_menu_sub_Guide.clearCheck();
        radioGroup_menu_sub_Whisky.clearCheck();
        radioGroup_menu_sub_Cocktail.clearCheck();
        radioGroup_menu_sub_WineAndSpirits.clearCheck();



        //프리오더 체크박스 초기화
        reset_checkbox();


        layout_finder_whisky.setVisibility(View.GONE);
        layout_finder_cocktail.setVisibility(View.GONE);
        checkBox_finder.setChecked(false);


    }




    // 버튼 선언
    public void btn(){
        btn_setting = findViewById(R.id.btn_setting);
        checkBox_finder = findViewById(R.id.checkBox_finder);

        checkBox_finder_Whisky_finder_tag_Honey = findViewById(R.id.checkBox_finder_Whisky_finder_tag_honey);
        checkBox_finder_Whisky_finder_tag_Floral = findViewById(R.id.checkBox_finder_Whisky_finder_tag_floral);
        checkBox_finder_Whisky_finder_tag_Vanilla = findViewById(R.id.checkBox_finder_Whisky_finder_tag_vanilla);
        checkBox_finder_Whisky_finder_tag_Cinnamon = findViewById(R.id.checkBox_finder_Whisky_finder_tag_cinnamon);

        checkBox_finder_Whisky_finder_tag_Fresh_Fruits = findViewById(R.id.checkBox_finder_Whisky_finder_tag_fresh_fruits);
        checkBox_finder_Whisky_finder_tag_Dried_Fruits = findViewById(R.id.checkBox_finder_Whisky_finder_tag_dried_fruits);
        checkBox_finder_Whisky_finder_tag_Citrus_Fruits = findViewById(R.id.checkBox_finder_Whisky_finder_tag_citrus_fruits);
        checkBox_finder_Whisky_finder_tag_Sweet_Fruits = findViewById(R.id.checkBox_finder_Whisky_finder_tag_sweet_fruits);

        checkBox_finder_Whisky_finder_tag_Butter = findViewById(R.id.checkBox_finder_Whisky_finder_tag_butter);
        checkBox_finder_Whisky_finder_tag_Cream = findViewById(R.id.checkBox_finder_Whisky_finder_tag_cream);
        checkBox_finder_Whisky_finder_tag_Caramel = findViewById(R.id.checkBox_finder_Whisky_finder_tag_caramel);
        checkBox_finder_Whisky_finder_tag_Chocolate = findViewById(R.id.checkBox_finder_Whisky_finder_tag_chocolate);

        checkBox_finder_Whisky_finder_tag_Nuts = findViewById(R.id.checkBox_finder_Whisky_finder_tag_nuts);
        checkBox_finder_Whisky_finder_tag_Malt = findViewById(R.id.checkBox_finder_Whisky_finder_tag_malt);
        checkBox_finder_Whisky_finder_tag_Wood = findViewById(R.id.checkBox_finder_Whisky_finder_tag_wood);
        checkBox_finder_Whisky_finder_tag_Salt = findViewById(R.id.checkBox_finder_Whisky_finder_tag_salt);

        checkBox_finder_Whisky_finder_tag_Spicy = findViewById(R.id.checkBox_finder_Whisky_finder_tag_spicy);
        checkBox_finder_Whisky_finder_tag_Smoky = findViewById(R.id.checkBox_finder_Whisky_finder_tag_smoky);
        checkBox_finder_Whisky_finder_tag_Peat = findViewById(R.id.checkBox_finder_Whisky_finder_tag_peat);
        checkBox_finder_Whisky_finder_tag_Herb = findViewById(R.id.checkBox_finder_Whisky_finder_tag_herb);

        checkBox_finder_Whisky_finder_tag_Strength = findViewById(R.id.checkBox_finder_Whisky_finder_tag_strength);
        checkBox_finder_Whisky_finder_tag_Limited = findViewById(R.id.checkBox_finder_Whisky_finder_tag_limited);
        checkBox_finder_Whisky_finder_tag_Old = findViewById(R.id.checkBox_finder_Whisky_finder_tag_old);

        btn_finder_reset = findViewById(R.id.btn_finder_reset);
        btn_finder_search = findViewById(R.id.btn_finder_search);



        radioGroup_finder_cocktail_lv_alcoholbooze = findViewById(R.id.radioGroup_finder_cocktail_lv_alcoholbooze);
        radiobutton_finder_cocktail_lv_alcoholbooze_0 = findViewById(R.id.radiobutton_finder_cocktail_lv_alcoholbooze_0);
        radiobutton_finder_cocktail_lv_alcoholbooze_1 = findViewById(R.id.radiobutton_finder_cocktail_lv_alcoholbooze_1);
        radiobutton_finder_cocktail_lv_alcoholbooze_2 = findViewById(R.id.radiobutton_finder_cocktail_lv_alcoholbooze_2);
        radiobutton_finder_cocktail_lv_alcoholbooze_3 = findViewById(R.id.radiobutton_finder_cocktail_lv_alcoholbooze_3);

        radioGroup_finder_cocktail_tag_fruits = findViewById(R.id.radioGroup_finder_cocktail_tag_fruits);
        radiobutton_finder_Classic_finder_tag_fruits_citrus = findViewById(R.id.radiobutton_finder_Classic_finder_tag_fruits_citrus);
        radiobutton_finder_Classic_finder_tag_fruits_berry = findViewById(R.id.radiobutton_finder_Classic_finder_tag_fruits_berry);
        radiobutton_finder_Classic_finder_tag_fruits_soft = findViewById(R.id.radiobutton_finder_Classic_finder_tag_fruits_soft);
        radiobutton_finder_Classic_finder_tag_fruits_tropical = findViewById(R.id.radiobutton_finder_Classic_finder_tag_fruits_tropical);

        radioGroup_finder_cocktail_tag_etc = findViewById(R.id.radioGroup_finder_cocktail_tag_etc);
        radiobutton_finder_Classic_finder_tag_sparkling = findViewById(R.id.radiobutton_finder_Classic_finder_tag_sparkling);
        radiobutton_finder_Classic_finder_tag_milk_cream = findViewById(R.id.radiobutton_finder_Classic_finder_tag_fruits_milk_cream);
        radiobutton_finder_Classic_finder_tag_chocolate_coffee = findViewById(R.id.radiobutton_finder_Classic_finder_tag_fruits_chocolate_coffee);
        radiobutton_finder_Classic_finder_tag_herb_spice = findViewById(R.id.radiobutton_finder_Classic_finder_tag_herb_spice);

        btn_finder_cocktail_reset = findViewById(R.id.btn_finder_cocktail_reset);
        btn_finder_cocktail_search = findViewById(R.id.btn_finder_cocktail_search);



        layout_title = findViewById(R.id.layout_title);
        btn_startMain = findViewById(R.id.btn_startMain);

        layout_guide = findViewById(R.id.layout_guide);
        layout_whisky = findViewById(R.id.layout_whisky);
        layout_cocktail = findViewById(R.id.layout_cocktail);
        layout_wineandspirits = findViewById(R.id.layout_wineandspirits);

        radioGroup_menu_main = findViewById(R.id.radioGroup_menu_main);
        radioButton_menu_main_Guide = findViewById(R.id.radioButton_menu_main_guide);
        radioButton_menu_main_Whisky = findViewById(R.id.radioButton_menu_main_whisky);
        radioButton_menu_main_Cocktail = findViewById(R.id.radioButton_menu_main_cocktail);
        radioButton_menu_main_WineAndSpirits = findViewById(R.id.radioButton_menu_main_wineandspirits);

        radioGroup_menu_sub_Guide = findViewById(R.id.radioGroup_menu_sub_guide);
        radioButton_menu_sub_Guide = findViewById(R.id.radioButton_menu_sub_guide);
        radioButton_menu_sub_Notice = findViewById(R.id.radioButton_menu_sub_notice);
        radioButton_menu_sub_Deliveryfood = findViewById(R.id.radioButton_menu_sub_deliveryfood);

        radioGroup_menu_sub_Whisky = findViewById(R.id.radioGroup_menu_sub_whisky);
        radioButton_menu_sub_Recommendation = findViewById(R.id.radioButton_menu_sub_recommendation);
        radioButton_menu_sub_Scotch = findViewById(R.id.radioButton_menu_sub_scotch);
        radioButton_menu_sub_American = findViewById(R.id.radioButton_menu_sub_american);
        radioButton_menu_sub_Others = findViewById(R.id.radioButton_menu_sub_others);

        radioGroup_menu_sub_Cocktail = findViewById(R.id.radioGroup_menu_sub_cocktail);
        radioButton_menu_sub_Classic = findViewById(R.id.radioButton_menu_sub_classic);
        radioButton_menu_sub_Highball = findViewById(R.id.radioButton_menu_sub_highball);
        radioButton_menu_sub_Signature = findViewById(R.id.radioButton_menu_sub_signature);
        radioButton_menu_sub_FreeOrder = findViewById(R.id.radioButton_menu_sub_freeorder);

        radioGroup_menu_sub_WineAndSpirits = findViewById(R.id.radioGroup_menu_sub_wineandspirits);
        radioButton_menu_sub_Wine = findViewById(R.id.radioButton_menu_sub_wine);
        radioButton_menu_sub_Spirits = findViewById(R.id.radioButton_menu_sub_spirits);
        radioButton_menu_sub_Liqueur = findViewById(R.id.radioButton_menu_sub_liqueur);

        radioGroup_lv_alcoholbooze = findViewById(R.id.radioGroup_lv_alcoholbooze);
        radioButton_lv_alcoholbooze_0 = findViewById(R.id.radiobutton_lv_alcoholbooze_0);
        radioButton_lv_alcoholbooze_1 = findViewById(R.id.radiobutton_lv_alcoholbooze_1);
        radioButton_lv_alcoholbooze_2 = findViewById(R.id.radiobutton_lv_alcoholbooze_2);
        radioButton_lv_alcoholbooze_3 = findViewById(R.id.radiobutton_lv_alcoholbooze_3);
        radioButton_lv_alcoholbooze_4 = findViewById(R.id.radiobutton_lv_alcoholbooze_4);

        radioGroup_lv_sparkling = findViewById(R.id.radioGroup_lv_sparkling);
        radioButton_lv_sparkling_0 = findViewById(R.id.radiobutton_lv_sparkling_0);
        radioButton_lv_sparkling_1 = findViewById(R.id.radiobutton_lv_sparkling_1);
        radioButton_lv_sparkling_2 = findViewById(R.id.radiobutton_lv_sparkling_2);
        radioButton_lv_sparkling_3 = findViewById(R.id.radiobutton_lv_sparkling_3);
        radioButton_lv_sparkling_4 = findViewById(R.id.radiobutton_lv_sparkling_4);

        radioGroup_lv_sweetness = findViewById(R.id.radioGroup_lv_sweetness);
        radioButton_lv_sweetness_0 = findViewById(R.id.radiobutton_lv_sweetness_0);
        radioButton_lv_sweetness_1 = findViewById(R.id.radiobutton_lv_sweetness_1);
        radioButton_lv_sweetness_2 = findViewById(R.id.radiobutton_lv_sweetness_2);
        radioButton_lv_sweetness_3 = findViewById(R.id.radiobutton_lv_sweetness_3);
        radioButton_lv_sweetness_4 = findViewById(R.id.radiobutton_lv_sweetness_4);

        radioGroup_lv_sour = findViewById(R.id.radioGroup_lv_sour);
        radioButton_lv_sour_0 = findViewById(R.id.radiobutton_lv_sour_0);
        radioButton_lv_sour_1 = findViewById(R.id.radiobutton_lv_sour_1);
        radioButton_lv_sour_2 = findViewById(R.id.radiobutton_lv_sour_2);
        radioButton_lv_sour_3 = findViewById(R.id.radiobutton_lv_sour_3);
        radioButton_lv_sour_4 = findViewById(R.id.radiobutton_lv_sour_4);

        checkbox_finder_Cocktail_tag_Lemon = findViewById(R.id.checkbox_finder_Cocktail_tag_lemon);
        checkbox_finder_Cocktail_tag_Lime = findViewById(R.id.checkbox_finder_Cocktail_tag_lime);
        checkbox_finder_Cocktail_tag_Peach = findViewById(R.id.checkbox_finder_Cocktail_tag_peach);
        checkbox_finder_Cocktail_tag_Berry = findViewById(R.id.checkbox_finder_Cocktail_tag_berry);
        checkbox_finder_Cocktail_tag_Orange = findViewById(R.id.checkbox_finder_Cocktail_tag_orange);
        checkbox_finder_Cocktail_tag_Melon = findViewById(R.id.checkbox_finder_Cocktail_tag_melon);
        checkbox_finder_Cocktail_tag_Grapefruit = findViewById(R.id.checkbox_finder_Cocktail_tag_grapefruit);
        checkbox_finder_Cocktail_tag_Cinnamon = findViewById(R.id.checkbox_finder_Cocktail_tag_cinnamon);
        checkbox_finder_Cocktail_tag_Coconut = findViewById(R.id.checkbox_finder_Cocktail_tag_coconut);
        checkbox_finder_Cocktail_tag_Banana = findViewById(R.id.checkbox_finder_Cocktail_tag_banana);
        checkbox_finder_Cocktail_tag_Milk = findViewById(R.id.checkbox_finder_Cocktail_tag_milk);
        checkbox_finder_Cocktail_tag_Pineapple = findViewById(R.id.checkbox_finder_Cocktail_tag_pineapple);
        checkbox_finder_Cocktail_tag_Apple = findViewById(R.id.checkbox_finder_Cocktail_tag_apple);
        checkbox_finder_Cocktail_tag_Coffee = findViewById(R.id.checkbox_finder_Cocktail_tag_coffee);
        checkbox_finder_Cocktail_tag_Chocolate = findViewById(R.id.checkbox_finder_Cocktail_tag_chocolate);
        checkbox_finder_Cocktail_tag_Wood = findViewById(R.id.checkbox_finder_Cocktail_tag_wood);
        checkbox_finder_Cocktail_tag_Smoke = findViewById(R.id.checkbox_finder_Cocktail_tag_smoke);
        checkbox_finder_Cocktail_tag_Honey = findViewById(R.id.checkbox_finder_Cocktail_tag_honey);
        checkbox_finder_Cocktail_tag_Ginger = findViewById(R.id.checkbox_finder_Cocktail_tag_ginger);
        checkbox_finder_Cocktail_tag_Herb = findViewById(R.id.checkbox_finder_Cocktail_tag_herb);

        btn_freeorder_reset = findViewById(R.id.btn_freeorder_reset);
        btn_freeorder_search = findViewById(R.id.btn_freeorder_search);


        btn_startMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_title.setVisibility(View.GONE);
                layout_main.setVisibility(View.VISIBLE);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setChoice_Language();
            }
        });

        checkBox_finder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked){
                    switch (getProperty_Menu_Main)
                    {
                        case "Whisky" :
                            layout_finder_cocktail.setVisibility(View.GONE);
                            layout_finder_whisky.setVisibility(View.VISIBLE);

                            break;

                        case "Cocktail" :
                            if(getProperty_Menu_Sub.equals("Classic"))
                            {
                                layout_finder_whisky.setVisibility(View.GONE);
                                layout_finder_cocktail.setVisibility(View.VISIBLE);

                            }
                            else{
                                layout_finder_cocktail.setVisibility(View.GONE);

                            }

                            break;

                        default :

                            break;

                    }

                }
                else{
                    layout_finder_whisky.setVisibility(View.GONE);
                    layout_finder_cocktail.setVisibility(View.GONE);


                }



            }
        });

        btn_finder_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파인더 체크박스 초기화

                reset_checkbox();


            }
        });


        btn_finder_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //파인더 체크박스 검색

                getDBFirebase_finder("Whisky", "Scotch");

                /*
                dialog_finder
                        .setTitle("주류 검색")
                        .setMessage("\n 검색 기능 준비중입니다. \n\n 체크하신 내용을 바텐더에게 보여주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });


                AlertDialog alertDialog = dialog_finder.create();

                alertDialog.show();
*/
            }
        });


        btn_freeorder_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //프리오더 체크박스 초기화

                reset_checkbox();

            }
        });

        btn_freeorder_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //프리오더 체크박스 검색


                getDBFirebase_finder("Cocktail", "FreeOrder");

/*



                dialog_finder
                        .setTitle("칵테일 검색")
                        .setMessage("\n 버그로 인해 검색기능을 막아두었습니다. \n\n 체크하신 내용을 바텐더에게 보여주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alertDialog = dialog_finder.create();

                alertDialog.show();

*/

            }
        });


        btn_finder_cocktail_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //칵테일 검색 체크박스 초기화

                reset_checkbox();

            }
        });

        btn_finder_cocktail_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //칵테일 체크박스 검색


                getDBFirebase_finder("Cocktail", "Classic");

/*



                dialog_finder
                        .setTitle("칵테일 검색")
                        .setMessage("\n 버그로 인해 검색기능을 막아두었습니다. \n\n 체크하신 내용을 바텐더에게 보여주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog alertDialog = dialog_finder.create();

                alertDialog.show();

*/

            }
        });


        checkbox_finder_Cocktail_tag_Lemon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Lime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Peach.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Berry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Orange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Melon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Grapefruit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Cinnamon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Coconut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Herb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });

        checkbox_finder_Cocktail_tag_Milk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                    else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });

        checkbox_finder_Cocktail_tag_Pineapple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Apple.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Coffee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Chocolate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Wood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Smoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Honey.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });


        checkbox_finder_Cocktail_tag_Ginger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                                        else {
                        compoundButton.setChecked(false);
                    }

                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });



        checkbox_finder_Cocktail_tag_Banana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                if(checked){

                    if(checkbox_finder_Cocktail_count<3){
                        checkbox_finder_Cocktail_count++;
                    }
                    
                                        else {
                        compoundButton.setChecked(false);
                    }
                    
                }
                else{
                    if(checkbox_finder_Cocktail_count>1)
                    {
                        checkbox_finder_Cocktail_count--;
                    }
                    else{

                        checkbox_finder_Cocktail_count = 0;
                    }

                }

            }
        });

        radioGroup_finder_cocktail_lv_alcoholbooze.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radiobutton_finder_cocktail_lv_alcoholbooze_0:

                        isChecked_Lv_AlcoholBooze = 0;

                        break;

                    case R.id.radiobutton_finder_cocktail_lv_alcoholbooze_1:

                        isChecked_Lv_AlcoholBooze = 1;

                        break;

                    case R.id.radiobutton_finder_cocktail_lv_alcoholbooze_2:

                        isChecked_Lv_AlcoholBooze = 2;

                        break;

                    case R.id.radiobutton_finder_cocktail_lv_alcoholbooze_3:

                        isChecked_Lv_AlcoholBooze = 3;

                        break;



                    default:

                        break;


                }

            }
        });



        radioGroup_menu_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                layout_guide.setVisibility(View.INVISIBLE);
                layout_whisky.setVisibility(View.INVISIBLE);
                layout_cocktail.setVisibility(View.INVISIBLE);
                layout_wineandspirits.setVisibility(View.INVISIBLE);

                switch (checkedId){

                    case R.id.radioButton_menu_main_guide:

                        if(radioButton_menu_main_Guide.isChecked()){
                            Log.d(TAG, "Menu Guide setVisible");
                            setLayoutVisibility();
                            layout_guide.setVisibility(View.VISIBLE);
                            if(radioButton_menu_sub_Guide.isChecked()==false){
                                radioButton_menu_sub_Guide.setChecked(true);
                            }

                        }

                        break;

                    case R.id.radioButton_menu_main_whisky:
                        if(radioButton_menu_main_Whisky.isChecked()){
                            Log.d(TAG, "Menu Whisky setVisible");
                            setLayoutVisibility();
                            //getDBFirebase("Whisky","Scotch");
                            layout_whisky.setVisibility(View.VISIBLE);
                            if(radioButton_menu_sub_Scotch.isChecked()==false){
                                radioButton_menu_sub_Scotch.setChecked(true);

                            }

                        }


                        break;

                    case R.id.radioButton_menu_main_cocktail:
                        if(radioButton_menu_main_Cocktail.isChecked()){
                            Log.d(TAG, "Menu Cocktail setVisible");
                            setLayoutVisibility();
                            //getDBFirebase("Cocktail","Classic");

                            layout_cocktail.setVisibility(View.VISIBLE);

                            //파이어스토어 시그니처 세팅 온

                            Log.d(TAG, "getFireStore_Setting_SetOnSignature : " + getFireStore_Setting_SetOnSignature);

                            if(getFireStore_Setting_SetOnSignature.equals("On"))
                            {
                                space_signature.setVisibility(View.VISIBLE);
                                radioButton_menu_sub_Signature.setVisibility(View.VISIBLE);
                            }
                            else{
                                space_signature.setVisibility(View.GONE);
                                radioButton_menu_sub_Signature.setVisibility(View.GONE);
                            }


                            if(radioButton_menu_sub_Classic.isChecked()==false){
                                radioButton_menu_sub_Classic.setChecked(true);

                            }

/*
                            if(radioButton_menu_sub_Signature.isChecked()==false){
                                radioButton_menu_sub_Signature.setChecked(true);

                            }
*/


                        }

                        break;



                    case R.id.radioButton_menu_main_wineandspirits:
                        if(radioButton_menu_main_WineAndSpirits.isChecked()){
                            Log.d(TAG, "Menu WineAndSpirits setVisible");
                            setLayoutVisibility();
                            layout_wineandspirits.setVisibility(View.VISIBLE);
                            if(radioButton_menu_sub_Wine.isChecked()==false){
                                radioButton_menu_sub_Wine.setChecked(true);

                            }


                        }

                        break;

                    default:

                        break;

                }

            }
        });


        radioGroup_menu_sub_Guide.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                switch (checkedId){

                    case R.id.radioButton_menu_sub_guide:
                        if(radioButton_menu_sub_Guide.isChecked())
                        {
                            checkBox_finder.setVisibility(View.INVISIBLE);
                            Log.d(TAG, "Menu Guide setVisible");
                            getDBFirebase("Guide","Guide");

                        }

                        break;

                    case R.id.radioButton_menu_sub_notice:
                        if(radioButton_menu_sub_Notice.isChecked()){
                            checkBox_finder.setVisibility(View.INVISIBLE);
                            getDBFirebase("Guide","Notice");


                        }

                        break;

                    case R.id.radioButton_menu_sub_deliveryfood:
                        if(radioButton_menu_sub_Deliveryfood.isChecked()){
                            checkBox_finder.setVisibility(View.INVISIBLE);
                            getDBFirebase("Guide","Deliveryfood");


                        }

                        break;


                    default:

                        break;

                }

            }
        });

        radioGroup_menu_sub_Whisky.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (checkedId){

                            case R.id.radioButton_menu_sub_recommendation:
                                if(radioButton_menu_sub_Recommendation.isChecked()){
                                    checkBox_finder.setVisibility(View.INVISIBLE);
                                    reset_checkbox();
                                    getDBFirebase("Whisky","Recommendation");


                                }


                                break;


                            case R.id.radioButton_menu_sub_scotch:
                                if(radioButton_menu_sub_Scotch.isChecked()){
                                    checkBox_finder.setVisibility(View.VISIBLE);
                                    reset_checkbox();
                                    getDBFirebase("Whisky","Scotch");


                                }


                                break;

                            case R.id.radioButton_menu_sub_american:
                                if(radioButton_menu_sub_American.isChecked()){
                                    checkBox_finder.setVisibility(View.VISIBLE);
                                    reset_checkbox();
                                    getDBFirebase("Whisky","American");


                                }

                                break;

                            case R.id.radioButton_menu_sub_others:
                                if(radioButton_menu_sub_Others.isChecked()){
                                    checkBox_finder.setVisibility(View.VISIBLE);
                                    reset_checkbox();
                                    getDBFirebase("Whisky","Other Country");


                                }

                                break;


                            default:

                                break;

                        }

                    }
                }, 0);

            }
        });



        radioGroup_menu_sub_Cocktail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {


                switch (checkedId){

                    case R.id.radioButton_menu_sub_classic:
                        if(radioButton_menu_sub_Classic.isChecked()){
//                            radioButton_menu_sub_Classic.setAnimation();

                            getDBFirebase("Cocktail","Classic");
                            checkBox_finder.setChecked(false);
                            checkBox_finder.setVisibility(View.VISIBLE);
                            //layout_finder_cocktail.setVisibility(View.VISIBLE);
                            reset_checkbox();

                        }
                        break;

                    case R.id.radioButton_menu_sub_highball:
                        if(radioButton_menu_sub_Highball.isChecked()){
                            checkBox_finder.setVisibility(View.INVISIBLE);
                            layout_finder_cocktail.setVisibility(View.GONE);
                            getDBFirebase("Cocktail","Highball");

                        }

                        break;

                    case R.id.radioButton_menu_sub_signature:
                        if(radioButton_menu_sub_Signature.isChecked()){
                            checkBox_finder.setVisibility(View.INVISIBLE);
                            layout_finder_cocktail.setVisibility(View.GONE);
                            getDBFirebase("Cocktail","Signature");

                        }

                        break;

                    case R.id.radioButton_menu_sub_freeorder:
                        if(radioButton_menu_sub_FreeOrder.isChecked()){
                            checkBox_finder.setVisibility(View.INVISIBLE);
                            clear_ani();

                            layout_finder_whisky.setVisibility(View.GONE);
                            layout_finder_cocktail.setVisibility(View.GONE);
                            checkBox_finder.setChecked(false);

                            layout_menu_title_sub.setVisibility(View.GONE);
                            setLayout_menu_title("Cocktail","FreeOrder");
                            listView.setVisibility(View.INVISIBLE);
                            layout_FreeOrder.setVisibility(View.VISIBLE);
                            getDBFirebase("Cocktail","FreeOrder");

                        }

                        else{
                            layout_menu_title_sub.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.VISIBLE);
                            layout_FreeOrder.setVisibility(View.INVISIBLE);

                        }

                        break;


                    default:

                        break;

                }

            }
        });



        radioGroup_lv_alcoholbooze.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radiobutton_lv_alcoholbooze_0:

                        isChecked_Lv_AlcoholBooze = 0;

                        break;

                    case R.id.radiobutton_lv_alcoholbooze_1:

                        isChecked_Lv_AlcoholBooze = 1;

                        break;

                    case R.id.radiobutton_lv_alcoholbooze_2:

                        isChecked_Lv_AlcoholBooze = 2;

                        break;

                    case R.id.radiobutton_lv_alcoholbooze_3:

                        isChecked_Lv_AlcoholBooze = 3;

                        break;

                    case R.id.radiobutton_lv_alcoholbooze_4:

                        isChecked_Lv_AlcoholBooze = 4;

                        break;


                    default:

                        break;


                }

            }
        });


        radioGroup_lv_sparkling.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radiobutton_lv_sparkling_0:

                        isChecked_Lv_Sparkling = 0;

                        break;

                    case R.id.radiobutton_lv_sparkling_1:

                        isChecked_Lv_Sparkling = 1;

                        break;

                    case R.id.radiobutton_lv_sparkling_2:

                        isChecked_Lv_Sparkling = 2;

                        break;

                    case R.id.radiobutton_lv_sparkling_3:

                        isChecked_Lv_Sparkling = 3;

                        break;

                    case R.id.radiobutton_lv_sparkling_4:

                        isChecked_Lv_Sparkling = 4;

                        break;


                    default:

                        break;


                }

            }
        });


        radioGroup_lv_sweetness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radiobutton_lv_sweetness_0:

                        isChecked_Lv_Sweetness = 0;

                        break;

                    case R.id.radiobutton_lv_sweetness_1:

                        isChecked_Lv_Sweetness = 1;

                        break;

                    case R.id.radiobutton_lv_sweetness_2:

                        isChecked_Lv_Sweetness = 2;

                        break;

                    case R.id.radiobutton_lv_sweetness_3:

                        isChecked_Lv_Sweetness = 3;

                        break;

                    case R.id.radiobutton_lv_sweetness_4:

                        isChecked_Lv_Sweetness = 4;

                        break;


                    default:

                        break;


                }

            }
        });


        radioGroup_lv_sour.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId)
                {
                    case R.id.radiobutton_lv_sour_0:

                        isChecked_Lv_Sour = 0;

                        break;

                    case R.id.radiobutton_lv_sour_1:

                        isChecked_Lv_Sour = 1;

                        break;

                    case R.id.radiobutton_lv_sour_2:

                        isChecked_Lv_Sour = 2;

                        break;

                    case R.id.radiobutton_lv_sour_3:

                        isChecked_Lv_Sour = 3;

                        break;

                    case R.id.radiobutton_lv_sour_4:

                        isChecked_Lv_Sour = 4;

                        break;


                    default:

                        break;


                }

            }
        });




        radioGroup_menu_sub_WineAndSpirits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (checkedId){

                            case R.id.radioButton_menu_sub_wine:
                                if(radioButton_menu_sub_Wine.isChecked()){
                                    checkBox_finder.setVisibility(View.INVISIBLE);
                                    getDBFirebase("WineAndSpirits","Wine");

                                }
                                break;

                            case R.id.radioButton_menu_sub_spirits:
                                if(radioButton_menu_sub_Spirits.isChecked()){
                                    checkBox_finder.setVisibility(View.INVISIBLE);
                                    getDBFirebase("WineAndSpirits","Spirits");

                                }

                                break;

                            case R.id.radioButton_menu_sub_liqueur:
                                if(radioButton_menu_sub_Liqueur.isChecked()){
                                    checkBox_finder.setVisibility(View.INVISIBLE);
                                    getDBFirebase("WineAndSpirits","Liqueur");

                                }

                                break;


                            default:

                                break;

                        }

                    }
                }, 100);

            }
        });




    }


    // 언어선택 다이얼로그
    public void setChoice_Language(){

        final String[] items = new String[]{"English", "한국어"};
/*
        final String[] btn_menu_Setting = new String[]{"Language", "언 어", "일본어", "중국어"};
        final String[] btn_menu_Finder = new String[]{"Language", "언 어", "일본어", "중국어"};
*/

        final String[] btn_menu_sub_Guide = new String[]{"Guide", "이용안내", "일본어", "중국어"};
        final String[] btn_menu_sub_Notice = new String[]{"Notice", "공지사항", "일본어", "중국어"};
        final String[] btn_menu_sub_Deliveryfood = new String[]{"Delivery", "배달추천", "일본어", "중국어"};

        final String[] btn_menu_sub_Recommendation = new String[]{"Recommend", "추천", "일본어", "중국어"};
        final String[] btn_menu_sub_Scotch = new String[]{"Scotch", "스카치", "일본어", "중국어"};
        final String[] btn_menu_sub_American = new String[]{"American", "아메리칸", "일본어", "중국어"};
        final String[] btn_menu_sub_Others = new String[]{"Others", "기타 국가", "일본어", "중국어"};

        final String[] btn_menu_sub_Classic = new String[]{"Classic", "클래식", "일본어", "중국어"};
        final String[] btn_menu_sub_Highball = new String[]{"Highball", "하이볼", "일본어", "중국어"};
        final String[] btn_menu_sub_Signature = new String[]{"Signature", "시그니처", "일본어", "중국어"};
        final String[] btn_menu_sub_FreeOrder = new String[]{"FreeOrder", "프리 오더", "일본어", "중국어"};

        final String[] btn_menu_sub_Wine = new String[]{"Wine", "와인", "일본어", "중국어"};
        final String[] btn_menu_sub_Spirits = new String[]{"Spirits", "스피릿", "일본어", "중국어"};
        final String[] btn_menu_sub_Liqueur = new String[]{"Liqueur", "리큐르", "일본어", "중국어"};

        final String[] tv_lv_Alcoholbooze = new String[]{"Lv_Alcohol Booze", "알콜 도수", "일본어", "중국어"};
        final String[] tv_lv_Sparkling = new String[]{"Lv_Sparkling", "탄산량", "일본어", "중국어"};
        final String[] tv_lv_Sweetness = new String[]{"Lv_Sweetness", "단맛", "일본어", "중국어"};
        final String[] tv_lv_Sour = new String[]{"Lv_Sour", "신맛", "일본어", "중국어"};

        final String[] radioButton_FreeOrder_Lv0 = new String[]{"None", "없음", "일본어", "중국어"};
        final String[] radioButton_FreeOrder_Lv1 = new String[]{"Weakly", "약하게", "일본어", "중국어"};
        final String[] radioButton_FreeOrder_Lv2 = new String[]{"Normal", "적당히", "일본어", "중국어"};
        final String[] radioButton_FreeOrder_Lv3 = new String[]{"Strong", "강하게", "일본어", "중국어"};
        final String[] radioButton_FreeOrder_Lv4 = new String[]{"Anything", "상관 없음", "일본어", "중국어"};

        final String[] tv_tag_Flavor = new String[]{"Favorite Flavor (Max 3 Tag)", "선호하는 맛 / 향 (3개 이하)", "일본어", "중국어"};

        final String[] setCheckbox_finder_Cocktail_tag_Lemon = new String[]{"Lemon", "레몬", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Lime = new String[]{"Lime", "라임", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Peach = new String[]{"Peach", "복숭아", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Berry = new String[]{"Berry", "베리류", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Orange = new String[]{"Orange", "오렌지", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Melon = new String[]{"Melon", "멜론", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Grapefruit = new String[]{"Grapefruit", "자몽", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Cinnamon = new String[]{"Cinnamon", "시나몬", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Coconut = new String[]{"Coconut", "코코넛", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Herb = new String[]{"Herb", "허브", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Milk = new String[]{"Milk", "우유", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Pineapple = new String[]{"Pineapple", "파인애플", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Apple = new String[]{"Apple", "사과", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Coffee = new String[]{"Coffee", "커피", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Chocolate = new String[]{"Chocolate", "초콜릿", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Wood = new String[]{"Wood", "목재", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Smoke = new String[]{"Smoke", "훈연", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Honey = new String[]{"Honey", "꿀", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Ginger = new String[]{"Ginger", "생강", "일본어", "중국어"};
        final String[] setCheckbox_finder_Cocktail_tag_Banana = new String[]{"Banana", "바나나", "일본어", "중국어"};

        final String[] setTextview_freeorder_notice = new String[]{"- After checking, show it to the bartender. -", "- 체크 후 바텐더에게 보여주세요. -", "일본어", "중국어"};



        final int[] selectedIndex = {0};


        dialog_language = new AlertDialog.Builder(this);

        dialog_language.setTitle("Language").setCancelable(true)
                .setSingleChoiceItems(items, language_value, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex[0] = which;
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        switch (items[selectedIndex[0]]){

                            case "English" :
                            {
                                language_value = 0;
                                language_setting = "English";
                                Log.d(TAG,"언어 설정 : " + language_setting);

/*                                radioButton_menu_main_Guide.setBackgroundResource();
                                radioButton_menu_main_Whisky.setBackgroundResource();
                                radioButton_menu_main_Cocktail.setBackgroundResource();
                                radioButton_menu_main_WineAndSpirits.setBackgroundResource();*/


                                break;
                            }

                            case "한국어" :
                            {
                                language_value = 1;
                                language_setting = "Korean";
                                Log.d(TAG,"언어 설정 : " + language_setting);

                                break;
                            }

                            case "日本語" :
                            {
                                language_value = 2;
                                language_setting = "Japanese";
                                Log.d(TAG,"언어 설정 : " + language_setting);

                                break;
                            }

                            case "中国人" :
                            {
                                language_value = 3;
                                language_setting = "Chinese";
                                Log.d(TAG,"언어 설정 : " + language_setting);
                                break;
                            }


                        }


                        //언어 변경 적용
                        radioButton_menu_sub_Guide.setText(btn_menu_sub_Guide[language_value]);
                        radioButton_menu_sub_Notice.setText(btn_menu_sub_Notice[language_value]);
                        radioButton_menu_sub_Deliveryfood.setText(btn_menu_sub_Deliveryfood[language_value]);

                        radioButton_menu_sub_Recommendation.setText(btn_menu_sub_Recommendation[language_value]);
                        radioButton_menu_sub_Scotch.setText(btn_menu_sub_Scotch[language_value]);
                        radioButton_menu_sub_American.setText(btn_menu_sub_American[language_value]);
                        radioButton_menu_sub_Others.setText(btn_menu_sub_Others[language_value]);

                        radioButton_menu_sub_Classic.setText(btn_menu_sub_Classic[language_value]);
                        radioButton_menu_sub_Signature.setText(btn_menu_sub_Signature[language_value]);
                        radioButton_menu_sub_Highball.setText(btn_menu_sub_Highball[language_value]);
                        radioButton_menu_sub_FreeOrder.setText(btn_menu_sub_FreeOrder[language_value]);

                        radioButton_menu_sub_Wine.setText(btn_menu_sub_Wine[language_value]);
                        radioButton_menu_sub_Spirits.setText(btn_menu_sub_Spirits[language_value]);
                        radioButton_menu_sub_Liqueur.setText(btn_menu_sub_Liqueur[language_value]);

                        tv_lv_alcoholbooze.setText(tv_lv_Alcoholbooze[language_value]);
                        radioButton_lv_alcoholbooze_0.setText(radioButton_FreeOrder_Lv0[language_value]);
                        radioButton_lv_alcoholbooze_1.setText(radioButton_FreeOrder_Lv1[language_value]);
                        radioButton_lv_alcoholbooze_2.setText(radioButton_FreeOrder_Lv2[language_value]);
                        radioButton_lv_alcoholbooze_3.setText(radioButton_FreeOrder_Lv3[language_value]);
                        radioButton_lv_alcoholbooze_4.setText(radioButton_FreeOrder_Lv4[language_value]);

                        tv_lv_sparkling.setText(tv_lv_Sparkling[language_value]);
                        radioButton_lv_sparkling_0.setText(radioButton_FreeOrder_Lv0[language_value]);
                        radioButton_lv_sparkling_1.setText(radioButton_FreeOrder_Lv1[language_value]);
                        radioButton_lv_sparkling_2.setText(radioButton_FreeOrder_Lv2[language_value]);
                        radioButton_lv_sparkling_3.setText(radioButton_FreeOrder_Lv3[language_value]);
                        radioButton_lv_sparkling_4.setText(radioButton_FreeOrder_Lv4[language_value]);

                        tv_lv_sweetness.setText(tv_lv_Sweetness[language_value]);
                        radioButton_lv_sweetness_0.setText(radioButton_FreeOrder_Lv0[language_value]);
                        radioButton_lv_sweetness_1.setText(radioButton_FreeOrder_Lv1[language_value]);
                        radioButton_lv_sweetness_2.setText(radioButton_FreeOrder_Lv2[language_value]);
                        radioButton_lv_sweetness_3.setText(radioButton_FreeOrder_Lv3[language_value]);
                        radioButton_lv_sweetness_4.setText(radioButton_FreeOrder_Lv4[language_value]);

                        tv_lv_sour.setText(tv_lv_Sour[language_value]);
                        radioButton_lv_sour_0.setText(radioButton_FreeOrder_Lv0[language_value]);
                        radioButton_lv_sour_1.setText(radioButton_FreeOrder_Lv1[language_value]);
                        radioButton_lv_sour_2.setText(radioButton_FreeOrder_Lv2[language_value]);
                        radioButton_lv_sour_3.setText(radioButton_FreeOrder_Lv3[language_value]);
                        radioButton_lv_sour_4.setText(radioButton_FreeOrder_Lv4[language_value]);

                        tv_tag_flavor.setText(tv_tag_Flavor[language_value]);
                        checkbox_finder_Cocktail_tag_Lemon.setText(setCheckbox_finder_Cocktail_tag_Lemon[language_value]);
                        checkbox_finder_Cocktail_tag_Lime.setText(setCheckbox_finder_Cocktail_tag_Lime[language_value]);
                        checkbox_finder_Cocktail_tag_Peach.setText(setCheckbox_finder_Cocktail_tag_Peach[language_value]);
                        checkbox_finder_Cocktail_tag_Berry.setText(setCheckbox_finder_Cocktail_tag_Berry[language_value]);
                        checkbox_finder_Cocktail_tag_Orange.setText(setCheckbox_finder_Cocktail_tag_Orange[language_value]);
                        checkbox_finder_Cocktail_tag_Melon.setText(setCheckbox_finder_Cocktail_tag_Melon[language_value]);
                        checkbox_finder_Cocktail_tag_Grapefruit.setText(setCheckbox_finder_Cocktail_tag_Grapefruit[language_value]);
                        checkbox_finder_Cocktail_tag_Cinnamon.setText(setCheckbox_finder_Cocktail_tag_Cinnamon[language_value]);
                        checkbox_finder_Cocktail_tag_Coconut.setText(setCheckbox_finder_Cocktail_tag_Coconut[language_value]);
                        checkbox_finder_Cocktail_tag_Herb.setText(setCheckbox_finder_Cocktail_tag_Herb[language_value]);
                        checkbox_finder_Cocktail_tag_Milk.setText(setCheckbox_finder_Cocktail_tag_Milk[language_value]);
                        checkbox_finder_Cocktail_tag_Pineapple.setText(setCheckbox_finder_Cocktail_tag_Pineapple[language_value]);
                        checkbox_finder_Cocktail_tag_Apple.setText(setCheckbox_finder_Cocktail_tag_Apple[language_value]);
                        checkbox_finder_Cocktail_tag_Coffee.setText(setCheckbox_finder_Cocktail_tag_Coffee[language_value]);
                        checkbox_finder_Cocktail_tag_Chocolate.setText(setCheckbox_finder_Cocktail_tag_Chocolate[language_value]);
                        checkbox_finder_Cocktail_tag_Wood.setText(setCheckbox_finder_Cocktail_tag_Wood[language_value]);
                        checkbox_finder_Cocktail_tag_Smoke.setText(setCheckbox_finder_Cocktail_tag_Smoke[language_value]);
                        checkbox_finder_Cocktail_tag_Honey.setText(setCheckbox_finder_Cocktail_tag_Honey[language_value]);
                        checkbox_finder_Cocktail_tag_Ginger.setText(setCheckbox_finder_Cocktail_tag_Ginger[language_value]);
                        checkbox_finder_Cocktail_tag_Banana.setText(setCheckbox_finder_Cocktail_tag_Banana[language_value]);

                        tv_freeorder_notice.setText(setTextview_freeorder_notice[language_value]);

                        getDBFirebase(getProperty_Menu_Main, getProperty_Menu_Sub);


                        SetLanguage_Size(language_value);




                    }
                }).create().show();





    }

    public void SetLanguage_Size(int getLanguage_value){

        int setLanguage_value = getLanguage_value;

        switch (setLanguage_value){

            case 0:


                //영어 언어 변경 적용

                radioButton_menu_main_Guide.setBackgroundResource(R.drawable.iv_btn_menu_main_guide_e);
                radioButton_menu_main_Whisky.setBackgroundResource(R.drawable.iv_btn_menu_main_whisky_e);
                radioButton_menu_main_Cocktail.setBackgroundResource(R.drawable.iv_btn_menu_main_cocktail_e);
                radioButton_menu_main_WineAndSpirits.setBackgroundResource(R.drawable.iv_btn_menu_main_wineandspirits_e);


                radioButton_lv_alcoholbooze_0.setTextSize(Dimension.SP, 15);
                radioButton_lv_alcoholbooze_1.setTextSize(Dimension.SP, 15);
                radioButton_lv_alcoholbooze_2.setTextSize(Dimension.SP, 15);
                radioButton_lv_alcoholbooze_3.setTextSize(Dimension.SP, 15);
                radioButton_lv_alcoholbooze_4.setTextSize(Dimension.SP, 15);

                radioButton_lv_sparkling_0.setTextSize(Dimension.SP, 15);
                radioButton_lv_sparkling_1.setTextSize(Dimension.SP, 15);
                radioButton_lv_sparkling_2.setTextSize(Dimension.SP, 15);
                radioButton_lv_sparkling_3.setTextSize(Dimension.SP, 15);
                radioButton_lv_sparkling_4.setTextSize(Dimension.SP, 15);

                radioButton_lv_sweetness_0.setTextSize(Dimension.SP, 15);
                radioButton_lv_sweetness_1.setTextSize(Dimension.SP, 15);
                radioButton_lv_sweetness_2.setTextSize(Dimension.SP, 15);
                radioButton_lv_sweetness_3.setTextSize(Dimension.SP, 15);
                radioButton_lv_sweetness_4.setTextSize(Dimension.SP, 15);

                radioButton_lv_sour_0.setTextSize(Dimension.SP, 15);
                radioButton_lv_sour_1.setTextSize(Dimension.SP, 15);
                radioButton_lv_sour_2.setTextSize(Dimension.SP, 15);
                radioButton_lv_sour_3.setTextSize(Dimension.SP, 15);
                radioButton_lv_sour_4.setTextSize(Dimension.SP, 15);

                checkbox_finder_Cocktail_tag_Lemon.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Lime.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Peach.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Berry.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Orange.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Melon.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Grapefruit.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Cinnamon.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Coconut.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Herb.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Milk.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Pineapple.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Apple.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Coffee.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Chocolate.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Wood.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Smoke.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Honey.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Ginger.setTextSize(Dimension.SP, 16);
                checkbox_finder_Cocktail_tag_Banana.setTextSize(Dimension.SP, 16);

                tv_freeorder_notice.setTextSize(Dimension.SP, 16);



                break;



            case 1:

                //한국어 언어 변경 적용

                radioButton_menu_main_Guide.setBackgroundResource(R.drawable.iv_btn_menu_main_guide_k);
                radioButton_menu_main_Whisky.setBackgroundResource(R.drawable.iv_btn_menu_main_whisky_k);
                radioButton_menu_main_Cocktail.setBackgroundResource(R.drawable.iv_btn_menu_main_cocktail_k);
                radioButton_menu_main_WineAndSpirits.setBackgroundResource(R.drawable.iv_btn_menu_main_wineandspirits_k);

                radioButton_lv_alcoholbooze_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sparkling_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sweetness_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sour_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_4.setTextSize(Dimension.SP, 18);

                checkbox_finder_Cocktail_tag_Lemon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Lime.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Peach.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Berry.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Orange.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Melon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Grapefruit.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Cinnamon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Coconut.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Herb.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Milk.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Pineapple.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Apple.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Coffee.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Chocolate.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Wood.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Smoke.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Honey.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Ginger.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Banana.setTextSize(Dimension.SP, 18);

                tv_freeorder_notice.setTextSize(Dimension.SP, 25);


                break;

            case 2:


                //한국어 언어 변경 적용


                radioButton_menu_main_Guide.setBackgroundResource(R.drawable.iv_btn_menu_main_guide_k);
                radioButton_menu_main_Whisky.setBackgroundResource(R.drawable.iv_btn_menu_main_whisky_k);
                radioButton_menu_main_Cocktail.setBackgroundResource(R.drawable.iv_btn_menu_main_cocktail_k);
                radioButton_menu_main_WineAndSpirits.setBackgroundResource(R.drawable.iv_btn_menu_main_wineandspirits_k);


                radioButton_lv_alcoholbooze_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sparkling_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sweetness_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sour_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_4.setTextSize(Dimension.SP, 18);

                checkbox_finder_Cocktail_tag_Lemon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Lime.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Peach.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Berry.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Orange.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Melon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Grapefruit.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Cinnamon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Coconut.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Herb.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Milk.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Pineapple.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Apple.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Coffee.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Chocolate.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Wood.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Smoke.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Honey.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Ginger.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Banana.setTextSize(Dimension.SP, 18);

                tv_freeorder_notice.setTextSize(Dimension.SP, 25);


                break;

            case 3:


                //한국어 언어 변경 적용


                radioButton_menu_main_Guide.setBackgroundResource(R.drawable.iv_btn_menu_main_guide_k);
                radioButton_menu_main_Whisky.setBackgroundResource(R.drawable.iv_btn_menu_main_whisky_k);
                radioButton_menu_main_Cocktail.setBackgroundResource(R.drawable.iv_btn_menu_main_cocktail_k);
                radioButton_menu_main_WineAndSpirits.setBackgroundResource(R.drawable.iv_btn_menu_main_wineandspirits_k);


                radioButton_lv_alcoholbooze_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sparkling_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sweetness_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sour_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_4.setTextSize(Dimension.SP, 18);

                checkbox_finder_Cocktail_tag_Lemon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Lime.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Peach.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Berry.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Orange.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Melon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Grapefruit.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Cinnamon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Coconut.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Herb.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Milk.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Pineapple.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Apple.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Coffee.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Chocolate.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Wood.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Smoke.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Honey.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Ginger.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Banana.setTextSize(Dimension.SP, 18);

                tv_freeorder_notice.setTextSize(Dimension.SP, 25);


                break;

            default:


                //한국어 언어 변경 적용


                radioButton_lv_alcoholbooze_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_alcoholbooze_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sparkling_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sparkling_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sweetness_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sweetness_4.setTextSize(Dimension.SP, 18);

                radioButton_lv_sour_0.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_1.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_2.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_3.setTextSize(Dimension.SP, 18);
                radioButton_lv_sour_4.setTextSize(Dimension.SP, 18);

                checkbox_finder_Cocktail_tag_Lemon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Lime.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Peach.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Berry.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Orange.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Melon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Grapefruit.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Cinnamon.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Coconut.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Herb.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Milk.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Pineapple.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Apple.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Coffee.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Chocolate.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Wood.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Smoke.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Honey.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Ginger.setTextSize(Dimension.SP, 18);
                checkbox_finder_Cocktail_tag_Banana.setTextSize(Dimension.SP, 18);

                tv_freeorder_notice.setTextSize(Dimension.SP, 25);


                break;


        }


    }


    // 리스트뷰 애니메이션 나타내기
    public void ani_listview(){
        ani_listview = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ani_listview);
        ani_listview.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    // 리스트뷰 아이템 애니메이션 나타내기
    public void ani_item_layout(){
        ani_item_layout = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.ani_item_layout);
        ani_item_layout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    //체크박스 전체 리셋
    public void reset_checkbox() {
        checkBox_finder_Whisky_finder_tag_Honey.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Floral.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Vanilla.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Cinnamon.setChecked(false);

        checkBox_finder_Whisky_finder_tag_Fresh_Fruits.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Dried_Fruits.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Citrus_Fruits.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Sweet_Fruits.setChecked(false);

        checkBox_finder_Whisky_finder_tag_Butter.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Cream.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Caramel.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Chocolate.setChecked(false);

        checkBox_finder_Whisky_finder_tag_Nuts.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Malt.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Wood.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Salt.setChecked(false);

        checkBox_finder_Whisky_finder_tag_Spicy.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Smoky.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Peat.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Herb.setChecked(false);

        checkBox_finder_Whisky_finder_tag_Strength.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Limited.setChecked(false);
        checkBox_finder_Whisky_finder_tag_Old.setChecked(false);
/*

        radioGroup_lv_alcoholbooze.clearCheck();
        radioGroup_lv_sparkling.clearCheck();
        radioGroup_lv_sweetness.clearCheck();
        radioGroup_lv_sour.clearCheck();

*/
        radioButton_lv_alcoholbooze_4.setChecked(true);
        radioButton_lv_sparkling_4.setChecked(true);
        radioButton_lv_sweetness_4.setChecked(true);
        radioButton_lv_sour_4.setChecked(true);

        checkbox_finder_Cocktail_count = 0;
        checkbox_finder_Cocktail_tag_Lemon.setChecked(false);
        checkbox_finder_Cocktail_tag_Lime.setChecked(false);
        checkbox_finder_Cocktail_tag_Peach.setChecked(false);
        checkbox_finder_Cocktail_tag_Berry.setChecked(false);
        checkbox_finder_Cocktail_tag_Orange.setChecked(false);
        checkbox_finder_Cocktail_tag_Melon.setChecked(false);
        checkbox_finder_Cocktail_tag_Grapefruit.setChecked(false);
        checkbox_finder_Cocktail_tag_Cinnamon.setChecked(false);
        checkbox_finder_Cocktail_tag_Coconut.setChecked(false);
        checkbox_finder_Cocktail_tag_Banana.setChecked(false);
        checkbox_finder_Cocktail_tag_Milk.setChecked(false);
        checkbox_finder_Cocktail_tag_Pineapple.setChecked(false);
        checkbox_finder_Cocktail_tag_Apple.setChecked(false);
        checkbox_finder_Cocktail_tag_Coffee.setChecked(false);
        checkbox_finder_Cocktail_tag_Chocolate.setChecked(false);
        checkbox_finder_Cocktail_tag_Wood.setChecked(false);
        checkbox_finder_Cocktail_tag_Smoke.setChecked(false);
        checkbox_finder_Cocktail_tag_Honey.setChecked(false);
        checkbox_finder_Cocktail_tag_Ginger.setChecked(false);
        checkbox_finder_Cocktail_tag_Herb.setChecked(false);

        radioGroup_finder_cocktail_lv_alcoholbooze.clearCheck();
        radiobutton_finder_cocktail_lv_alcoholbooze_0.setChecked(true);

        radioGroup_finder_cocktail_tag_fruits.clearCheck();
        /*
        radiobutton_finder_Classic_finder_tag_fruits_citrus.setChecked(false);
        radiobutton_finder_Classic_finder_tag_fruits_berry.setChecked(false);
        radiobutton_finder_Classic_finder_tag_fruits_soft.setChecked(false);
        radiobutton_finder_Classic_finder_tag_fruits_tropical.setChecked(false);
*/
        radioGroup_finder_cocktail_tag_etc.clearCheck();
        /*
        radiobutton_finder_Classic_finder_tag_sparkling.setChecked(false);
        radiobutton_finder_Classic_finder_tag_milk_cream.setChecked(false);
        radiobutton_finder_Classic_finder_tag_chocolate_coffee.setChecked(false);
        radiobutton_finder_Classic_finder_tag_herb_spice.setChecked(false);
*/
    }


    public void ani_scroll(){

        layout_menu.setVisibility(View.VISIBLE);
        Animation ani_scroll = new AlphaAnimation(0.0f, 1.0f);
        ani_scroll.setDuration(1000);
        //ani_scroll.setStartOffset(20);
        //ani_scroll.setRepeatMode(Animation.REVERSE);
        //ani_scroll.setRepeatCount(1);
        layout_menu.startAnimation(ani_scroll);


    }

    //애니 화살표 깜빡임
    public void ani_arrow(){

        iv_arrow.setVisibility(View.VISIBLE);
        Animation ani_arrow = new AlphaAnimation(0.0f, 0.8f);
        ani_arrow.setDuration(1000);
        ani_arrow.setStartOffset(20);
        ani_arrow.setRepeatMode(Animation.REVERSE);
        ani_arrow.setRepeatCount(Animation.INFINITE);
        iv_arrow.startAnimation(ani_arrow);


    }

    public void clear_ani(){

        iv_arrow.clearAnimation();

        iv_arrow.setVisibility(View.INVISIBLE);

    }

}
