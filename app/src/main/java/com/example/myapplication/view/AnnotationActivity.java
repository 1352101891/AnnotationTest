package com.example.myapplication.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intent_apt.annotations.DynamicIntentKey;
import com.example.intent_apt.annotations.StaticIntentKey;
import com.example.myapplication.R;
import com.example.myapplication.processor.DynamicIntentProcessor;

public class AnnotationActivity extends Activity {

    //注解赋予int类型的值
    @StaticIntentKey(R.id.text)
    @DynamicIntentKey(R.id.text)
    private TextView textView;

    //注解赋予int类型的值
    @DynamicIntentKey(R.id.content_linear)
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DynamicIntentProcessor.Init(this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnnotationActivity.this,"我被绑定成功!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
