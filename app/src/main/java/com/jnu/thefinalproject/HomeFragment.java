package com.jnu.thefinalproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.aboutDate.DataBank;
import com.jnu.aboutDate.Record;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";
    public static AllrecordAdapter adapterhome;
    private ImageButton imageButtonOut;

    public static String date;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static ArrayList<Record> homeData;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment=new HomeFragment();
        Bundle args=new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1=getArguments().getString(ARG_PARAM1);
            mParam2=getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        imageButtonOut = view.findViewById(R.id.imageButton_give_out);
        imageButtonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        return view;
    }

    private void initView(View view) {
        MainActivity.dataBank = new DataBank(this.getContext());
        MainActivity.dataBank.Load();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String[] str = new String[3];
        str[0] =String.valueOf(year);
        if(month<10){
            str[1]="0"+String.valueOf(month);
        }
        else{
            str[1]=String.valueOf(month);
        }
        if(day<10){
            str[2]="0"+String.valueOf(day);
        }
        else{
            str[2]=String.valueOf(day);
        }
        date = str[0]+str[1]+str[2];
        homeData = MainActivity.dataBank.getHomeRecords(str[0]+str[1]+str[2]);

        if(0==homeData.size()) {
            homeData=homeData;
        }
        adapterhome= new AllrecordAdapter(this.getContext(), R.layout.listview_grandson_give,  homeData);

        ListView listViewRecord=view.findViewById(R.id.listview_all);
        listViewRecord.setAdapter(adapterhome);
        adapterhome.notifyDataSetChanged();
        this.registerForContextMenu(listViewRecord);
        CalendarView calendarView = view.findViewById(R.id.calendarView);          //日历监听事件，获取日历当前选中的年月日
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView View, int year, int month, int dayOfMonth) {
                String[] str=new String[3];
                str[0] =String.valueOf(year);
                if(month<10){
                    str[1]="0"+String.valueOf(month+1);
                }
                else{
                    str[1]=String.valueOf(month+1);
                }
                if(dayOfMonth<10){
                    str[2]="0"+String.valueOf(dayOfMonth);
                }
                else{
                    str[2]=String.valueOf(dayOfMonth);
                }
                date = str[0]+str[1]+str[2];
                ArrayList<Record> mid = MainActivity.dataBank.getHomeRecords(date);
                homeData=mid;
                adapterhome.notifyDataSetChanged();
            }
        });
    }
    //home适配器adapter
    public static class AllrecordAdapter extends ArrayAdapter<Record> {
        private int resourceId;

        public AllrecordAdapter(@NonNull Context context, int resource, @NonNull List<Record> objects) {
            super(context, resource,objects);
            this.resourceId=resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Record records = getItem(position);//获取当前项的实例
            View view;
            if(null==convertView)
                view = LayoutInflater.from(getContext()).inflate(this.resourceId, parent, false);
            else
                view=convertView;
            ((TextView) view.findViewById(R.id.textView_give_date)).setText(records.getDate());
            ((TextView) view.findViewById(R.id.textView_give_name)).setText(records.getName());
            ((TextView) view.findViewById(R.id.textView_give_account)).setText("￥"+String.valueOf(records.getAccount())+"元");
            ((TextView) view.findViewById(R.id.textView_give_type)).setText(records.getType());
            return view;
        }
    }
    public static void delet(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String[] str = new String[3];
        str[0] =String.valueOf(year);
        if(month<10){
            str[1]="0"+String.valueOf(month);
        }
        else{
            str[1]=String.valueOf(month);
        }
        if(day<10){
            str[2]="0"+String.valueOf(day);
        }
        else{
            str[2]=String.valueOf(day);
        }
        homeData.clear();
        homeData=MainActivity.dataBank.getHomeRecords(str[0]+str[1]+str[2]);
        adapterhome.notifyDataSetInvalidated();
    }
}