package com.jnu.thefinalproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.aboutDate.DataBank;
import com.jnu.aboutDate.Record;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddgiveRecord extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewName,textViewType,textViewDate,textViewtipName,editTextdate,accountText,editTextTextPersonName;
    private Button buttonAdd;
    private ImageButton buttong_back_give;
    private String tempdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgive);
        getSupportActionBar().hide();//隐藏标题栏
        addEvent();
    }

    void addEvent(){
        textViewName=findViewById(R.id.editTextTextName);
        textViewType=findViewById(R.id.textViewType);
        textViewDate=findViewById(R.id.textViewDate);
        textViewtipName=findViewById(R.id.textViewtipName);
        buttonAdd=findViewById(R.id.buttonAdd);
        editTextdate=findViewById(R.id.editTextDate);
        Intent intent = getIntent();
        tempdate=intent.getStringExtra("date");
        editTextdate.setText(tempdate);
        accountText=findViewById(R.id.editTextNumber);
        editTextTextPersonName=findViewById(R.id.editTextTextName2);
        buttong_back_give=findViewById(R.id.addimageButton_addgive);
        buttong_back_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddgiveRecord.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonAdd.setOnClickListener(this);
    }

    @RequiresApi(api=Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.attention);
        builder.setTitle("输入数据有误");
        if(editTextdate.getText().toString().length()!=8||(Integer.parseInt(editTextdate.getText().toString().substring(4,6))>12)||(Integer.parseInt(editTextdate.getText().toString().substring(6,8))>31)){
            builder.setMessage("日期格式有误");
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else if(editTextTextPersonName.getText().toString().length()==0){
            builder.setMessage("请输入送礼原因");
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else if(textViewName.getText().toString().length()==0){
            builder.setMessage("请输入好友姓名");
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else if(accountText.getText().toString().length()==0){
            builder.setMessage("请输入礼金数量");
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else{
            String name =textViewName.getText().toString();
            String date=editTextdate.getText().toString();
            String reson =editTextTextPersonName.getText().toString();
            String account =accountText.getText().toString();
            Intent intent = new Intent(AddgiveRecord.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("date",date);
            bundle.putString("reson",reson);
            bundle.putString("account",account);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}