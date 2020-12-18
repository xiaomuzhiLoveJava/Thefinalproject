package com.jnu.thefinalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class AddgetRecord extends AppCompatActivity implements View.OnClickListener{
    private EditText textName,textAccount,textDate,textReson;
    private Button buttonAdd;
    private ImageButton button_back;
    private String tempdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addget);
        getSupportActionBar().hide();//隐藏标题栏
        addEvent();
    }

    private void addEvent(){
        textName = findViewById(R.id.editTextTextName);
        textAccount = findViewById(R.id.editTextNumber);
        textDate = findViewById(R.id.editTextDate);

        Intent intent = getIntent();
        tempdate=intent.getStringExtra("date");
        textDate.setText(tempdate);
        Toast.makeText(this,tempdate+"安卓",Toast.LENGTH_SHORT).show();
        textReson = findViewById(R.id.editTextTextPersonName);
        buttonAdd = findViewById(R.id.buttonAdd);
        button_back = findViewById(R.id.imageButton_addget);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddgetRecord.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.attention);
        builder.setTitle("输入数据有误");
        if(textDate.getText().toString().length()!=8||(Integer.parseInt(textDate.getText().toString().substring(4,6))>12)||(Integer.parseInt(textDate.getText().toString().substring(6,8))>31)){

            builder.setMessage("日期格式有误");
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else if(textReson.getText().toString().length()==0){
            builder.setMessage("请输入送礼原因");
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else if(textName.getText().toString().length()==0){
            builder.setMessage("请输入好友姓名");
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else if(textAccount.getText().toString().length()==0){

            builder.setMessage("请输入礼金数量");
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        else{
            String name =textName.getText().toString();
            String date=textDate.getText().toString();
            String reson =textReson.getText().toString();
            String account =textAccount.getText().toString();
            Intent intent = new Intent(AddgetRecord.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("date",date);
            bundle.putString("reson",reson);
            bundle.putString("account",account);
            intent.putExtras(bundle);
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }
}