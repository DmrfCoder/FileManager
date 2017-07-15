package com.example.dmrf.filemanager;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.net.Proxy.Type.HTTP;

/**
 * Created by DMRF on 2017/7/14.
 */

class WebActivity extends Activity {
    //网页浏览器
    private WebView webView;
    //进度条布局和网页内容主体布局
    private RelativeLayout loadingLayout, webLayout;
    //放大、缩小控制器
    private ZoomControls zoomControls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_raeder);
        //初始化页面组件
        webView = findViewById(R.id.webkit);
        loadingLayout = findViewById(R.id.loadinglayout);
        webLayout = findViewById(R.id.weblayout);
        zoomControls = findViewById(R.id.zoomControls);

        WebSettings webSettings = webView.getSettings();
        //设置可以使用js脚本
        webSettings.setJavaScriptEnabled(true);
        //执行异步进程
        new MyAsyncTask().execute("");
    }

    //异步处理类
    private class MyAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingLayout.setVisibility(View.VISIBLE);
            webLayout.setVisibility(View.GONE);
        }

        //后台执行
        @Override
        protected String doInBackground(String... strings) {
            reading();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //隐藏进度条布局
            loadingLayout.setVisibility(View.GONE);
            //显示浏览器布局
            webLayout.setVisibility(View.VISIBLE);
            //放大按钮
            zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.zoomIn();
                }
            });

            //缩小按钮
            zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.zoomOut();
                }
            });
        }
    }

    private void reading() {
        String filePath = getIntent().getStringExtra("filepath");
        if (filePath != null) {
            webView.loadData(readWebDataToStringFromPath(filePath, new FileReadOverBack() {
                @Override
                public void fileReadOver() {

                }
            }), "text/html", "UTF-8");
        } else {
            new AlertDialog.Builder(WebActivity.this).setTitle("出错了").setMessage("获取文件路径出错").setPositiveButton("返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    WebActivity.this.finish();
                }
            });
        }
    }

    //将网页数据读取到一个字符串变量当中
    private String readWebDataToStringFromPath(String filePath, FileReadOverBack fileReadOverBack) {
        File file = new File(filePath);
        StringBuffer stringBuffer = new StringBuffer();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int readCount = 0;
            while ((readCount = inputStream.read(bytes)) > 0) {
                stringBuffer.append(new String(bytes, 0, readCount));
            }
            fileReadOverBack.fileReadOver();
        } catch (FileNotFoundException e) {
            return "文件不存在";
        } catch (IOException e) {
            return "文件读取错误！";
        }
        return stringBuffer.toString();
    }

    interface FileReadOverBack {
        void fileReadOver();
    }
}
