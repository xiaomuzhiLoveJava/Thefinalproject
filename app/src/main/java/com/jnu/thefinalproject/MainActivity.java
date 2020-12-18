package com.jnu.thefinalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.aboutDate.DataBank;
import com.jnu.aboutDate.Record;

import static com.jnu.thefinalproject.HomeFragment.date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button buttonGetive, buttonGet;
    private ImageButton imageButton;
    private FloatingActionButton floatButton;
    private Toolbar toolbar;
    private FragmentManager frgManager;
    private androidx.fragment.app.FragmentTransaction frgTran;
    private Button[] buttons;
    private CalendarView calendarView;
    public static DataBank dataBank;
    private HomeFragment homeFragment;
    private GetFragment getFragment;
    private GiveFragment giveFragment;
    private GetFragment.GetExpandableListviewAdapter adapterget;
    private GiveFragment.ExpandableListviewAdapter adaptergive;
    private HomeFragment.AllrecordAdapter adapterhome;

    public static int Year =0;
    public static int Month =0;
    public static int DayOfMonth =0;

    public MainActivity() {
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//隐藏标题栏
        inithomeFragment();
//        Toast.makeText(this,HomeFragment.date,Toast.LENGTH_SHORT).show();
        findviewbyid();
        AddRecord();
    }

    void AddRecord() {
        floatButton=findViewById(R.id.floating_Button);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    private void showDialog(){
        final String[] list_String={"随礼","收礼"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.tips);
        builder.setTitle("请选择添加类型");
        builder.setItems(list_String, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    Intent intent=new Intent(MainActivity.this, AddgiveRecord.class);
                    intent.putExtra("date",homeFragment.date);
                    startActivityForResult(intent,1);
                }
                if(which==1){
                    Intent intent=new Intent();
                    intent.setClass(MainActivity.this, AddgetRecord.class);
                    intent.putExtra("date",homeFragment.date);
                    startActivityForResult(intent,1);
                }
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    void findviewbyid() {
        buttonGet=findViewById(R.id.button_get);
        buttonGetive=findViewById(R.id.button_give);
        imageButton=findViewById(R.id.image_Button);
        buttons=new Button[]{buttonGet, buttonGetive};

        buttonGetive.setOnClickListener(this);
        buttonGet.setOnClickListener(this);
        imageButton.setOnClickListener(this);
    }

    private void inithomeFragment() {
        frgTran=getSupportFragmentManager().beginTransaction();
        if (homeFragment == null) {
            homeFragment=new HomeFragment();
            frgTran.add(R.id.fragment_content, homeFragment);
        }
        hideFragment(frgTran);
        frgTran.show(homeFragment);
        frgTran.commit();
    }

    private void initgetFragment() {
        androidx.fragment.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

        if (getFragment == null) {
            getFragment=new GetFragment();
            transaction.add(R.id.fragment_content, getFragment);
        }
        hideFragment(transaction);
        transaction.show(getFragment);
        transaction.commit();
    }

    private void initgiveFragment() {
        androidx.fragment.app.FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

        if (giveFragment == null) {
            giveFragment=new GiveFragment();
            transaction.add(R.id.fragment_content, giveFragment);
        }
        hideFragment(transaction);
        transaction.show(giveFragment);
        transaction.commit();
    }

    private void hideFragment(androidx.fragment.app.FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (giveFragment != null) {
            transaction.hide(giveFragment);
        }
        if (getFragment != null) {
            transaction.hide(getFragment);
        }
    }
    @Override
    public void onClick(View v) {
        findviewbyid();
        if(v == buttonGet){
            initgetFragment();
        }else if(v == buttonGetive){
                initgiveFragment();
        }else if(v == imageButton){
            inithomeFragment();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if(resultCode==RESULT_OK){
                Bundle bundle = data.getExtras();
                String name = bundle.getString("name");
                String date= bundle.getString("date");
                String reson = bundle.getString("reson");
                String account = bundle.getString("account");
                dataBank.getAllrecords().add(new Record(name.trim(),account.trim(),date.trim(),"随礼",reson.trim()));
                dataBank.Save();
                dataBank.Load();
            }
            if(resultCode==RESULT_CANCELED){
                Bundle bundle = data.getExtras();
                String name = bundle.getString("name");
                String date= bundle.getString("date");
                String reson = bundle.getString("reson");
                String account = bundle.getString("account");

                dataBank.getAllrecords().add(new Record(name.trim(),account.trim(),date.trim(),"收礼",reson.trim()));
                dataBank.Save();
                dataBank.Load();
            }
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
            super.onActivityResult(requestCode, resultCode, data);
        }

}