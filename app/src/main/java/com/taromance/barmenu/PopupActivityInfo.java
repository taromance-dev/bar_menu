package com.taromance.barmenu;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Locale;


public class PopupActivityInfo extends Activity {


    //데이터 베이스 받기

    final FirebaseFirestore db = FirebaseFirestore.getInstance();    // Firestore 인스턴스 선언

    CollectionReference db_menu_Guide = db.collection("DB_Guide");

    CollectionReference db_menu_Spirits = db.collection("DB_Spirits");

    CollectionReference db_menu_Cocktail = db.collection("DB_Cocktail");


    //firebaseStorage 인스턴스 생성
    //하나의 Storage와 연동되어 있는 경우, getInstance()의 파라미터는 공백으로 두어도 됨
    //하나의 앱이 두개 이상의 Storage와 연동이 되어있 경우, 원하는 저장소의 스킴을 입력
    //getInstance()의 파라미터는 firebase console에서 확인 가능('gs:// ... ')
    final FirebaseStorage storage = FirebaseStorage.getInstance("gs://taromance-a6d1e.appspot.com");

    //생성된 FirebaseStorage를 참조하는 storage 생성
    StorageReference storageRef = storage.getReference();




    private String getNumber;


    private String getProperty_Menu_Main;
    private String getProperty_Menu_Sub;


    private TextView tv_Name_E;
    private TextView tv_Name_K;
    private TextView tv_Name_Sub;

    private TextView tv_Aging;
    private TextView tv_Cask;
    private TextView tv_ABV;

    private TextView tv_Country;
    private TextView tv_Region;

    private TextView tv_Distillery;

    private TextView tv_Tag;

    private TextView tv_Info;

    private TextView tv_Lv_Body;
    private TextView tv_Lv_Richness;
    private TextView tv_Lv_Smoke;
    private TextView tv_Lv_Sweetness;

    private TextView tv_Flavour_Character;
    private TextView tv_Colouring;
    private TextView tv_Chill_Filtered;

    private TextView tv_Taste_Nose;
    private TextView tv_Taste_Plate;
    private TextView tv_Taste_Finish;

    private ImageView iv_profile;







    private BroadcastReceiver screenOffReceiver;
    private IntentFilter screenFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_popup_info);

        // 전원 스크린 off


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);

        BroadcastReceiver screenOnOff = new BroadcastReceiver()
        {
            public static final String ScreenOff = "android.intent.action.SCREEN_OFF";
            public static final String ScreenOn = "android.intent.action.SCREEN_ON";

            public void onReceive(Context contex, Intent intent)
            {
                if (intent.getAction().equals(ScreenOff))
                {

                    finish();


                }
                else if (intent.getAction().equals(ScreenOn))
                {
    
                    Log.e("pop", "Screen On");
                }
            }
        };
        registerReceiver(screenOnOff, intentFilter);


        Dialog_Loading LoadingDialog = new Dialog_Loading(this); //다이얼로그 선언
        LoadingDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); //주변 투명
        LoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //백그라운를 투명하게
        LoadingDialog.setCanceledOnTouchOutside(false);
        LoadingDialog.setCancelable(false); //다이얼로그 외부 클릭으로 종료되지 않게
        LoadingDialog.show(); //로딩화면 보여주기


        tv_Name_E = (TextView) findViewById(R.id.tv_name_e);
        tv_Name_K = (TextView) findViewById(R.id.tv_name_k);
        tv_Name_Sub = (TextView) findViewById(R.id.tv_name_sub);

//        tv_Aging = (TextView) findViewById(R.id.tv_aging);
//        tv_Cask = (TextView) findViewById(R.id.tv_cask);
        tv_ABV = (TextView) findViewById(R.id.tv_abv);

//        tv_Country = (TextView) findViewById(R.id.tv_country);
        tv_Region = (TextView) findViewById(R.id.tv_region);

        tv_Distillery = (TextView) findViewById(R.id.tv_distillery);

//        tv_Tag = (TextView) findViewById(R.id.tv_tag);

        tv_Info = (TextView) findViewById(R.id.tv_info);

//        tv_Lv_Body = (TextView) findViewById(R.id.tv_lv_body);
//        tv_Lv_Richness = (TextView) findViewById(R.id.tv_lv_richness);
//        tv_Lv_Smoke = (TextView) findViewById(R.id.tv_lv_smoke);
//        tv_Lv_Sweetness = (TextView) findViewById(R.id.tv_lv_sweetness);

//        tv_Flavour_Character = (TextView) findViewById(R.id.tv_flavour_character);
//        tv_Colouring = (TextView) findViewById(R.id.tv_colouring);
//        tv_Chill_Filtered = (TextView) findViewById(R.id.cv_chill_filtered);

//        tv_Taste_Nose = (TextView) findViewById(R.id.tv_taste_nose);
//        tv_Taste_Plate = (TextView) findViewById(R.id.tv_taste_plate);
//        tv_Taste_Finish = (TextView) findViewById(R.id.tv_taste_finish);

        iv_profile = (ImageView) findViewById(R.id.iv_profile);

        //데이터 받기

        Intent intent = getIntent();

        getNumber = intent.getStringExtra("Number");
        getProperty_Menu_Main = intent.getStringExtra("Property_Menu_Main");
        getProperty_Menu_Sub = intent.getStringExtra("Property_Menu_Sub");

        Log.d(TAG, "Complete : Get Intent:"
                + " Property_Menu_Main : " + getProperty_Menu_Main
                + " Property_Menu_Sub : " + getProperty_Menu_Sub
                + " Number : " + getNumber);


        //데이터 베이스 연동

        switch (getProperty_Menu_Main)
        {
            case "Guide" :

                break;

            case "Whisky" :

                db_menu_Spirits.whereEqualTo("Property_Menu_Sub", getProperty_Menu_Sub)
                        .whereEqualTo("Number",getNumber)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        final String popup_Number = document.get("Number").toString();

                                        final String popup_Code = document.get("Code").toString();
                                        final String popup_Liquor_Company = document.get("Liquor_Company").toString();

                                        final String popup_Property_Menu_Main = document.get("Property_Menu_Main").toString();
                                        final String popup_Property_Menu_Sub = document.get("Property_Menu_Sub").toString();

                                        final String popup_Category = document.get("Category").toString();
                                        final String popup_Country = document.get("Country").toString();
                                        final String popup_Region = document.get("Region").toString();

                                        final String popup_Name_E = document.get("Name_E").toString();
                                        final String popup_Name_K = document.get("Name_K").toString();
                                        final String popup_Name_J = document.get("Name_J").toString();
                                        final String popup_Name_C = document.get("Name_C").toString();
                                        final String popup_Name_Sub = document.get("Name_Sub").toString();

                                        final String popup_Aging = document.get("Aging").toString();
                                        final String popup_Cask = document.get("Cask").toString();
                                        final String popup_ABV = document.get("ABV").toString();

                                        final String popup_Size_Bottle = document.get("Size_Bottle").toString();
                                        final String popup_Size_30ml = document.get("Size_30ml").toString();

                                        final String popup_Stock_Glass = document.get("Stock_Glass").toString();
                                        final String popup_Stock_Bottle = document.get("Stock_Bottle").toString();

                                        final String popup_Price_Glass = document.get("Price_Glass").toString();
                                        final String popup_Price_Glass_VAT_False = document.get("Price_Glass_VAT_False").toString();
                                        final String popup_Margin_Glass = document.get("Margin_Glass").toString();

                                        final String popup_Price_Bottle = document.get("Price_Bottle").toString();
                                        final String popup_Price_Bottle_VAT_False = document.get("Price_Bottle_VAT_False").toString();
                                        final String popup_Margin_Bottle = document.get("Margin_Bottle").toString();

//                                        final String popup_Stock_Price_30ml_VAT = document.get("Stock_Price_30ml_VAT").toString();
//                                        final String popup_Stock_Price_Bottle = document.get("Stock_Price_Bottle").toString();
//                                        final String popup_Stock_Price_Bottle_VAT = document.get("Stock_Price_Bottle_VAT").toString();

                                        //final String popup_Multiply_Glass = document.get("Multiply_Glass").toString();
                                        //final String popup_Multiply_Bottle = document.get("Multiply_Bottle").toString();

                                        final String popup_Event_Sale_Glass = document.get("Event_Sale_Glass").toString();
                                        final String popup_Event_Sale_Price_Glass = document.get("Event_Sale_Price_Glass").toString();
                                        final String popup_Event_Sale_Bottle = document.get("Event_Sale_Bottle").toString();
                                        final String popup_Event_Sale_Price_Bottle = document.get("Event_Sale_Price_Bottle").toString();

                                        final String popup_Last_Update = document.get("Last_Update").toString();

                                        final String popup_Reference = document.get("Reference").toString();

                                        final String popup_Distillery = document.get("Distillery").toString();

                                        final String popup_Tag = document.get("Tag").toString();

                                        final String popup_Info_E = document.get("Info_E").toString();
                                        final String popup_Info_K = document.get("Info_K").toString();
                                        final String popup_Info_J = document.get("Info_J").toString();
                                        final String popup_Info_C = document.get("Info_C").toString();

                                        final String popup_Lv_Body = document.get("Lv_Body").toString();
                                        final String popup_Lv_Richness = document.get("Lv_Richness").toString();
                                        final String popup_Lv_Smoke = document.get("Lv_Smoke").toString();
                                        final String opup_Lv_Sweetness = document.get("Lv_Sweetness").toString();

                                        final String popup_Flavour_Character = document.get("Flavour_Character").toString();
                                        final String popup_Colouring = document.get("Colouring").toString();
                                        final String popup_Chill_Filtered = document.get("Chill_Filtered").toString();

                                        final String popup_Taste_Nose_E = document.get("Taste_Nose_E").toString();
                                        final String popup_Taste_Nose_K = document.get("Taste_Nose_K").toString();
                                        final String popup_Taste_Nose_J = document.get("Taste_Nose_J").toString();
                                        final String popup_Taste_Nose_C = document.get("Taste_Nose_C").toString();

                                        final String popup_Taste_Plate_E = document.get("Taste_Plate_E").toString();
                                        final String popup_Taste_Plate_K = document.get("Taste_Plate_K").toString();
                                        final String popup_Taste_Plate_J = document.get("Taste_Plate_J").toString();
                                        final String popup_Taste_Plate_C = document.get("Taste_Plate_C").toString();

                                        final String popup_Taste_Finish_E = document.get("Taste_Finish_E").toString();
                                        final String popup_Taste_Finish_K = document.get("Taste_Finish_K").toString();
                                        final String popup_Taste_Finish_J = document.get("Taste_Finish_J").toString();
                                        final String popup_Taste_Finish_C = document.get("Taste_Finish_C").toString();

                                        Log.d(TAG, "Complete : getting documents: ", task.getException());

                                        tv_Name_E.setText(popup_Name_E);
                                        tv_Name_K.setText(popup_Name_K);
                                        tv_Name_Sub.setText(popup_Name_Sub);

                                //        tv_Aging = (TextView) findViewById(R.id.tv_aging);
                                //        tv_Cask = (TextView) findViewById(R.id.tv_cask);
                                        tv_ABV.setText(popup_ABV+"%");

                                //        tv_Country = (TextView) findViewById(R.id.tv_country);
                                        tv_Region.setText(popup_Region);

                                        tv_Distillery.setText(popup_Distillery);

                                //        tv_Tag = (TextView) findViewById(R.id.tv_tag);

                                        tv_Info.setText(popup_Info_E);

                                //        tv_Lv_Body = (TextView) findViewById(R.id.tv_lv_body);
                                //        tv_Lv_Richness = (TextView) findViewById(R.id.tv_lv_richness);
                                //        tv_Lv_Smoke = (TextView) findViewById(R.id.tv_lv_smoke);
                                //        tv_Lv_Sweetness = (TextView) findViewById(R.id.tv_lv_sweetness);

                                //        tv_Flavour_Character = (TextView) findViewById(R.id.tv_flavour_character);
                                //        tv_Colouring = (TextView) findViewById(R.id.tv_colouring);
                                //        tv_Chill_Filtered = (TextView) findViewById(R.id.cv_chill_filtered);

                                //        tv_Taste_Nose = (TextView) findViewById(R.id.tv_taste_nose);
                                //        tv_Taste_Plate = (TextView) findViewById(R.id.tv_taste_plate);
                                //        tv_Taste_Finish = (TextView) findViewById(R.id.tv_taste_finish);

                                        String iv_name = popup_Name_E.toLowerCase();
                                        iv_name = iv_name.replace(" ", "");
                                        iv_name = iv_name.replace("&", "");
                                        iv_name = iv_name.replace("'", "");

                                        Log.d(TAG, "Complete : Change iv_name : " + iv_name);

                                        //Storage 내부의 images 폴더 안의 image.jpg 파일명을 가리키는 참조 생성
                                        StorageReference pathReference = storageRef.child("Spirits").child(popup_Property_Menu_Main).child(popup_Property_Menu_Sub).child(popup_Name_E + ".png");
                                        Log.e("Checked DB","Checked : DB Image Load url : " + pathReference);

                                        try{
                                            File dir = new File(Environment.getExternalStorageDirectory() + "/photos");
                                            final File file = new File(dir, "image.jpg");
                                            if (!dir.exists()) {
                                                dir.mkdirs();
                                            }
                                            final FileDownloadTask fileDownloadTask = pathReference.getFile(file);
                                            fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                    iv_profile.setImageURI(Uri.fromFile(file));
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    //임시 이미지 차용

                                                    int resid = getResources().getIdentifier("noimage", "drawable", getPackageName());

                                                    // 이미지 적용
                                                    iv_profile.setImageResource(resid);
                                                    Log.e("fail", "Error : DB Image Load Error");
                                                }
                                            });
                                        } catch(Exception e){
                                            e.printStackTrace();
                                        }

/*

                                        int resid = getResources().getIdentifier(iv_name, "drawable", getPackageName());

                                        // 이미지 적용
                                        iv_profile.setImageResource(resid);
*/


                                    }

                                }


                                else{

                                    Log.d(TAG, "Error : getting documents: ", task.getException());

                                }

                            }
                        });


                break;

            case "Cocktail" :

                Log.d(TAG, "Loading : get DB ");

                db_menu_Cocktail
                        /*.whereEqualTo("Category", getProperty_Menu_Sub)*/
                        .whereEqualTo("Number",getNumber)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        final String popup_Number = document.get("Number").toString();

                                        final String popup_Code = document.get("Code").toString();

                                        final String popup_Base = document.get("Base").toString();
                                        final String popup_Category = document.get("Category").toString();
                                        final String popup_Type = document.get("Type").toString();

                                        final String popup_Name_E = document.get("Name_E").toString();
                                        final String popup_Name_K = document.get("Name_K").toString();
                                        final String popup_Name_J = document.get("Name_J").toString();
                                        final String popup_Name_C = document.get("Name_C").toString();
                                        
                                        Log.d(TAG, "Check : 이름까지 ", task.getException());

                                        final String popup_Simple_Recipe = document.get("Simple_Recipe").toString();

                                        // 리스트 오브젝트로 받아야함
                                        //final String popup_IBA_Recipe = document.get("IBA_Recipe").toString();
                                        //final String popup_Public_Recipe = document.get("Public_Recipe").toString();
                                        //final String popup_Taromance_Recipe = document.get("Taromance_Recipe").toString();

                                        Log.d(TAG, "Check : 레시피까지 ", task.getException());


                                        final String popup_Max_Volume = document.get("Max_Volume").toString();

                                        // 리스트 오브젝트로 받아야함
                                        //final String popup_Technical = document.get("Technical").toString();

                                        final String popup_Glass = document.get("Glass").toString();
                                        final String popup_Ice = document.get("Ice").toString();

                                        // 리스트 오브젝트로 받아야함
                                        //final String popup_Garnish = document.get("Garnish").toString();

                                        final String popup_Tag = document.get("Tag").toString();

                                        final String popup_ABV = document.get("ABV").toString();

                                        Log.d(TAG, "Check : 알콜까지 ", task.getException());
/*

                                        final String popup_Lv_AlcoholBooze = document.get("Lv_AlcoholBooze").toString();
                                        final String popup_Lv_Sparkling = document.get("Lv_Sparkling").toString();
                                        final String popup_Lv_Sweetness = document.get("Lv_Sweetness").toString();
                                        final String popup_Lv_Sour = document.get("Lv_Sour").toString();
*/

                                        final String popup_Story_E = document.get("Story_E").toString();
                                        final String popup_Story_K = document.get("Story_K").toString();
                                        final String popup_Story_J = document.get("Story_J").toString();
                                        final String popup_Story_C = document.get("Story_C").toString();

                                        final String popup_Cost = document.get("Cost").toString();
                                        final String popup_Margin = document.get("Margin").toString();
                                        final String popup_Price_Glass = document.get("Price_Glass").toString();

                                        Log.d(TAG, "Complete : getting documents: ", task.getException());

                                        tv_Name_E.setText(popup_Name_E);
                                        tv_Name_K.setText(popup_Name_K);

                                        tv_ABV.setText(popup_ABV+"%");

                                        //        tv_Country = (TextView) findViewById(R.id.tv_country);
                                        tv_Distillery.setText(popup_Base + " Base Cocktail");


                                        tv_Info.setText(popup_Simple_Recipe + "\n\n" + popup_Story_K);



                                        //Storage 내부의 images 폴더 안의 image.jpg 파일명을 가리키는 참조 생성
                                        StorageReference pathReference = storageRef.child("Cocktail").child(popup_Category).child(popup_Name_E + ".png");
                                        Log.e("Checked DB","Checked : DB Image Load url : " + pathReference);
                                        try{
                                            File dir = new File(Environment.getExternalStorageDirectory() + "/photos");
                                            final File file = new File(dir, "image.jpg");
                                            if (!dir.exists()) {
                                                dir.mkdirs();
                                            }
                                            final FileDownloadTask fileDownloadTask = pathReference.getFile(file);
                                            fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                    iv_profile.setImageURI(Uri.fromFile(file));

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    Log.e("fail", "Error : DB Image Load Error");

                                                    //임시 이미지 차용
                                                    String iv_name = popup_Glass.toLowerCase();
                                                    iv_name = iv_name.replace(" ", "");

                                                    Log.d(TAG, "Complete : Change iv_name : " + iv_name);


                                                    int resid = getResources().getIdentifier(iv_name, "drawable", getPackageName());

                                                    // 이미지 적용
                                                    iv_profile.setImageResource(resid);
                                                }
                                            });
                                        } catch(Exception e){
                                            e.printStackTrace();
                                        }



                                    }

                                }


                                else{

                                    Log.d(TAG, "Error : getting documents: ", task.getException());

                                }

                            }
                        });

                break;

            case "WineAndSpirits" :


                db_menu_Spirits.whereEqualTo("Property_Menu_Sub", getProperty_Menu_Sub)
                        .whereEqualTo("Number",getNumber)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                        final String popup_Number = document.get("Number").toString();

                                        final String popup_Code = document.get("Code").toString();
                                        final String popup_Liquor_Company = document.get("Liquor_Company").toString();

                                        final String popup_Property_Menu_Main = document.get("Property_Menu_Main").toString();
                                        final String popup_Property_Menu_Sub = document.get("Property_Menu_Sub").toString();

                                        final String popup_Category = document.get("Category").toString();
                                        final String popup_Country = document.get("Country").toString();
                                        final String popup_Region = document.get("Region").toString();

                                        final String popup_Name_E = document.get("Name_E").toString();
                                        final String popup_Name_K = document.get("Name_K").toString();
                                        final String popup_Name_J = document.get("Name_J").toString();
                                        final String popup_Name_C = document.get("Name_C").toString();
                                        final String popup_Name_Sub = document.get("Name_Sub").toString();

                                        final String popup_Aging = document.get("Aging").toString();
                                        final String popup_Cask = document.get("Cask").toString();
                                        final String popup_ABV = document.get("ABV").toString();

                                        final String popup_Size_Bottle = document.get("Size_Bottle").toString();
                                        final String popup_Size_30ml = document.get("Size_30ml").toString();

                                        final String popup_Stock_Glass = document.get("Stock_Glass").toString();
                                        final String popup_Stock_Bottle = document.get("Stock_Bottle").toString();

                                        final String popup_Price_Glass = document.get("Price_Glass").toString();
                                        final String popup_Price_Glass_VAT_False = document.get("Price_Glass_VAT_False").toString();
                                        final String popup_Margin_Glass = document.get("Margin_Glass").toString();

                                        final String popup_Price_Bottle = document.get("Price_Bottle").toString();
                                        final String popup_Price_Bottle_VAT_False = document.get("Price_Bottle_VAT_False").toString();
                                        final String popup_Margin_Bottle = document.get("Margin_Bottle").toString();

//                                        final String popup_Stock_Price_30ml_VAT = document.get("Stock_Price_30ml_VAT").toString();
//                                        final String popup_Stock_Price_Bottle = document.get("Stock_Price_Bottle").toString();
//                                        final String popup_Stock_Price_Bottle_VAT = document.get("Stock_Price_Bottle_VAT").toString();

                                        //final String popup_Multiply_Glass = document.get("Multiply_Glass").toString();
                                        //final String popup_Multiply_Bottle = document.get("Multiply_Bottle").toString();

                                        final String popup_Event_Sale_Glass = document.get("Event_Sale_Glass").toString();
                                        final String popup_Event_Sale_Price_Glass = document.get("Event_Sale_Price_Glass").toString();
                                        final String popup_Event_Sale_Bottle = document.get("Event_Sale_Bottle").toString();
                                        final String popup_Event_Sale_Price_Bottle = document.get("Event_Sale_Price_Bottle").toString();

                                        final String popup_Last_Update = document.get("Last_Update").toString();

                                        final String popup_Reference = document.get("Reference").toString();

                                        final String popup_Distillery = document.get("Distillery").toString();

                                        final String popup_Tag = document.get("Tag").toString();

                                        final String popup_Info_E = document.get("Info_E").toString();
                                        final String popup_Info_K = document.get("Info_K").toString();
                                        final String popup_Info_J = document.get("Info_J").toString();
                                        final String popup_Info_C = document.get("Info_C").toString();

                                        final String popup_Lv_Body = document.get("Lv_Body").toString();
                                        final String popup_Lv_Richness = document.get("Lv_Richness").toString();
                                        final String popup_Lv_Smoke = document.get("Lv_Smoke").toString();
                                        final String opup_Lv_Sweetness = document.get("Lv_Sweetness").toString();

                                        final String popup_Flavour_Character = document.get("Flavour_Character").toString();
                                        final String popup_Colouring = document.get("Colouring").toString();
                                        final String popup_Chill_Filtered = document.get("Chill_Filtered").toString();

                                        final String popup_Taste_Nose_E = document.get("Taste_Nose_E").toString();
                                        final String popup_Taste_Nose_K = document.get("Taste_Nose_K").toString();
                                        final String popup_Taste_Nose_J = document.get("Taste_Nose_J").toString();
                                        final String popup_Taste_Nose_C = document.get("Taste_Nose_C").toString();

                                        final String popup_Taste_Plate_E = document.get("Taste_Plate_E").toString();
                                        final String popup_Taste_Plate_K = document.get("Taste_Plate_K").toString();
                                        final String popup_Taste_Plate_J = document.get("Taste_Plate_J").toString();
                                        final String popup_Taste_Plate_C = document.get("Taste_Plate_C").toString();

                                        final String popup_Taste_Finish_E = document.get("Taste_Finish_E").toString();
                                        final String popup_Taste_Finish_K = document.get("Taste_Finish_K").toString();
                                        final String popup_Taste_Finish_J = document.get("Taste_Finish_J").toString();
                                        final String popup_Taste_Finish_C = document.get("Taste_Finish_C").toString();

                                        Log.d(TAG, "Complete : getting documents: ", task.getException());

                                        tv_Name_E.setText(popup_Name_E);
                                        tv_Name_K.setText(popup_Name_K);
                                        tv_Name_Sub.setText(popup_Name_Sub);

                                        //        tv_Aging = (TextView) findViewById(R.id.tv_aging);
                                        //        tv_Cask = (TextView) findViewById(R.id.tv_cask);
                                        tv_ABV.setText(popup_ABV);

                                        //        tv_Country = (TextView) findViewById(R.id.tv_country);
                                        tv_Region.setText(popup_Region);

                                        //        tv_Distillery = (TextView) findViewById(R.id.tv_distillery);

                                        //        tv_Tag = (TextView) findViewById(R.id.tv_tag);

                                        tv_Info.setText(popup_Info_E);

                                        //        tv_Lv_Body = (TextView) findViewById(R.id.tv_lv_body);
                                        //        tv_Lv_Richness = (TextView) findViewById(R.id.tv_lv_richness);
                                        //        tv_Lv_Smoke = (TextView) findViewById(R.id.tv_lv_smoke);
                                        //        tv_Lv_Sweetness = (TextView) findViewById(R.id.tv_lv_sweetness);

                                        //        tv_Flavour_Character = (TextView) findViewById(R.id.tv_flavour_character);
                                        //        tv_Colouring = (TextView) findViewById(R.id.tv_colouring);
                                        //        tv_Chill_Filtered = (TextView) findViewById(R.id.cv_chill_filtered);

                                        //        tv_Taste_Nose = (TextView) findViewById(R.id.tv_taste_nose);
                                        //        tv_Taste_Plate = (TextView) findViewById(R.id.tv_taste_plate);
                                        //        tv_Taste_Finish = (TextView) findViewById(R.id.tv_taste_finish);


                                        String iv_name = popup_Name_E.toLowerCase();
                                        iv_name = iv_name.replace(" ", "");

                                        Log.d(TAG, "Complete : Change iv_name : " + iv_name);


                                        //Storage 내부의 images 폴더 안의 image.jpg 파일명을 가리키는 참조 생성
                                        StorageReference pathReference = storageRef.child("Spirits").child(popup_Property_Menu_Main).child(popup_Property_Menu_Sub).child(popup_Name_E + ".png");
                                        Log.e("Checked DB","Checked : DB Image Load url : " + pathReference);
                                        try{
                                            File dir = new File(Environment.getExternalStorageDirectory() + "/photos");
                                            final File file = new File(dir, "image.jpg");
                                            if (!dir.exists()) {
                                                dir.mkdirs();
                                            }
                                            final FileDownloadTask fileDownloadTask = pathReference.getFile(file);
                                            fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                    iv_profile.setImageURI(Uri.fromFile(file));
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    //임시 이미지 차용

                                                    int resid = getResources().getIdentifier("noimage", "drawable", getPackageName());

                                                    // 이미지 적용
                                                    iv_profile.setImageResource(resid);
                                                    Log.e("fail", "Error : DB Image Load Error");
                                                }
                                            });
                                        } catch(Exception e){
                                            e.printStackTrace();
                                        }
/*

                                        int resid = getResources().getIdentifier(iv_name, "drawable", getPackageName());

                                        // 이미지 적용
                                        iv_profile.setImageResource(resid);
*/

                                    }

                                }


                                else{

                                    Log.d(TAG, "Error : getting documents: ", task.getException());

                                }

                            }
                        });

                break;

            default:
                break;

        }


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                LoadingDialog.cancel(); //로딩화면 종료

            }
        }, 1500);



    }






}
