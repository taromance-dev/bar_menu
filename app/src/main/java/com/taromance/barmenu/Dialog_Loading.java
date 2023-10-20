package com.taromance.barmenu;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;

public class Dialog_Loading extends Dialog {
    public Dialog_Loading(@NonNull Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //title 없이
        setContentView(R.layout.dialog_loading);

        //라이브러리 로딩이미지 사용
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite pulseRing = new FadingCircle();
        progressBar.setIndeterminateDrawable(pulseRing);


    }

}