package com.example.dmrf.filemanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by DMRF on 2017/7/15.
 */

public class EditTxtActivity extends Activity implements View.OnClickListener {

    //显示打开文本的内容
    private EditText txtEditText;
    //显示打开的文件名
    private TextView txtTextView;
    //“保存”按钮
    private Button txtSaveButton;
    //"取消"按钮
    private Button txtCancleButton;
    private String txtTitle;
    private String txtPath;
    private String txtData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_text);
        //初始化界面
        initConvertView();
        //获得文件路径
        txtPath = getIntent().getStringExtra("path");
        //获得文本内容
        txtData = getIntent().getStringExtra("data");
        //获得文本标题
        txtTitle = getIntent().getStringExtra("title");

        //转码
        try {
            txtData = new String(txtData.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        txtTextView.setText(txtTitle);
        txtEditText.setText(txtData);


    }

    private void initConvertView() {
        txtEditText = findViewById(R.id.EditTextDetail);
        txtTextView = findViewById(R.id.TextViewTitle);
        txtCancleButton = findViewById(R.id.ButtonCancle);
        txtSaveButton = findViewById(R.id.ButtonSave);

        //绑定监听器
        txtSaveButton.setOnClickListener(this);
        txtCancleButton.setOnClickListener(this);
    }

    //事件监听器
    @Override
    public void onClick(View view) {
        if (view.getId() == txtCancleButton.getId()) {
//保存
            try {
                saveTxt();
            } catch (IOException e) {
                Toast.makeText(EditTxtActivity.this, "保存文件的时候遇到了未知异常！", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (view.getId() == txtSaveButton.getId()) {
            EditTxtActivity.this.finish();
        }
    }

    //保存编辑后的文本信息
    private void saveTxt() throws IOException {
        String newData = txtEditText.getText().toString();
        BufferedWriter mBW = new BufferedWriter(new FileWriter(new File(txtPath)));
        mBW.write(newData, 0, newData.length());
        mBW.newLine();
        mBW.close();
        //提示
        Toast.makeText(EditTxtActivity.this, "文件保存成功！", Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
