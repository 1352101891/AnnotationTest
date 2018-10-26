package com.lvqiu.myapplication.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lvqiu.intent_apt.tool.BinderFactory;
import com.lvqiu.intent_apt.annotations.DynamicBindKey;
import com.lvqiu.intent_apt.annotations.StaticBindKey;
import com.lvqiu.myapplication.R;

import java.util.ArrayList;


public class AnnotationActivity extends Activity {
    @DynamicBindKey(R.id.auto_text)
    @StaticBindKey(R.id.text)
    public TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         DynamicIntentProcessor.Init(this);


        BinderFactory.init(this);

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
