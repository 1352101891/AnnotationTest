package com.example.myapplication.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.intent_apt.annotations.StaticIntentKey;
import com.example.myapplication.R;
import com.example.viewfactory.ViewBinderFactory;

import java.util.ArrayList;


public class AnnotationActivity extends Activity {

    @StaticIntentKey(R.id.text)
    public TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        DynamicIntentProcessor.Init(this);
        ViewBinderFactory.binderViews(this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnnotationActivity.this,"我被绑定成功!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<String> getList(){
        ArrayList<String> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            list.add("我是测试字符串---"+i);
        }
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
