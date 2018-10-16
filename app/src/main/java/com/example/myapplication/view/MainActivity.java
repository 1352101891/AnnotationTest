package com.example.myapplication.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.intent_apt.annotations.StaticIntentKey;
import com.example.myapplication.R;
import com.example.viewfactory.ViewBinderFactory;

public class MainActivity extends AppCompatActivity {

    @StaticIntentKey(R.id.auto_text)
    public TextView auto_text;
    WebView webView;
    Handler handler=new Handler();
    String url="file:///" + Environment.getExternalStorageDirectory().getPath() + "/main.html";
    int REQUEST_WRITE_EXTERNAL_STORAGE=111;
    int REQUEST_READ_EXTERNAL_STORAGE=1113;


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewBinderFactory.binderViews(this);
        auto_text.setText("我被绑定成功！");

        Intent intent=getIntent();
        if (intent!=null){
            url=intent.getStringExtra("path")==null?"file:///android_asset/main.html":intent.getStringExtra("path");
        }
        webView= (WebView) findViewById(R.id.webview_content);
        Log.e("MainActivity",url);
        //启用javascript
        webView.getSettings().setJavaScriptEnabled(true);
        //启用url访问路径文件功能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webView.getSettings().setAllowFileAccessFromFileURLs(true);
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        }


        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //检查权限
                checkPermission();
                flag=true;
                Toast.makeText(MainActivity.this,"加载完波",Toast.LENGTH_SHORT).show();
            }
        });
        /**
         * 添加javascriptInterface
         * 第一个参数：这里需要一个与js映射的java对象
         * 第二个参数：该java对象被映射为js对象后在js里面的对象名，在js中要调用该对象的方法就是通过这个来调用
         */
        webView.addJavascriptInterface(new JSInterface(this), "jsi");
    }


    boolean flag=false;
    @SuppressWarnings("unused")
    public  class JSInterface{
        private Context mContext;

        public JSInterface(Context mContext) {
            this.mContext = mContext;
        }

        @JavascriptInterface
        public void showDialog(String str){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("提示");
            builder.setMessage(str);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    show("确定");
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    show("取消");
                }
            });
            builder.create().show();
        }

        @JavascriptInterface
        public void showToast(final String str){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:jsText('"+str+"')");
                }
            },1000);
            Toast.makeText(mContext,str,Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void getResult(final String str){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishcallback(str);
                }
            },3000);
            Toast.makeText(mContext,str,Toast.LENGTH_SHORT).show();
        }
    }


    public void show(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_WRITE_EXTERNAL_STORAGE||requestCode==REQUEST_READ_EXTERNAL_STORAGE){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                webView.loadUrl(url);
            }else {
                checkPermission();
            }
        }
    }

    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "checkPermission: 已经授权！");
            webView.loadUrl(url);
        }
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "checkPermission: 已经授权！");
            webView.loadUrl(url);
        }
    }


    public void finishcallback(String s){
        Intent intent = new Intent();//数据是使用Intent返回
        intent.putExtra("key", s);//把返回数据存入Intent
        this.setResult(RESULT_OK,intent);//设置返回数据
        this.finish();//关闭Activity
    }

}
