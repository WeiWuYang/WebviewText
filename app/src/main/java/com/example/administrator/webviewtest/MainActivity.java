package com.example.administrator.webviewtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private WebView webview;
    private Button button;
    private TextView textview;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webview);
        button = (Button) findViewById(R.id.my_btn);
        textview = (TextView) findViewById(R.id.my_text);
        //1 设置webview，允许运行JAVAscript代码运行
        webview.getSettings().setJavaScriptEnabled(true);
        // 2 为webview添加一个JavaScriptinterface的接口类对象，这个方法接受2个参数
        //第一个参数自定义接口类对象，第二个参数为字符串，用于JS中调用android
        webview.addJavascriptInterface(new AndroidBridge(this), "android");
        //4 加载写好的html文件
        webview.loadUrl("file:///android_asset/index.html");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 5 安卓调用网页上（js）的代码，使用的是loadUrl方法，格式为javascrip（）；方法名
                webview.loadUrl("javascript:useJs()");
            }
        });

    }
}

//JS和java通信
//     js调用安卓代码
//     1 在接口中声明方法(安卓的) 加上@JavascriptInterface这样一个声明
//     2  在js里 使用指定字符串，  android.changetext();
//     3  声明一个自己的接口类
class AndroidBridge {
    private TextView tv;
  private Context context;
    public AndroidBridge(TextView tv) {
        this.tv = tv;
    }
    public AndroidBridge(Context context){
        this.context=context;
    }
    @JavascriptInterface
    public void login() {
        Intent intent=new Intent(context,Main2Activity.class);
         context.startActivity(intent);
    }
    @JavascriptInterface
    public void changetext() {
        tv.setText("收到了来自网页的消息");
    }
}