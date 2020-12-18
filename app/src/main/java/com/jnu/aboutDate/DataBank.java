package com.jnu.aboutDate;

import android.content.Context;

import com.jnu.thefinalproject.HomeFragment;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class DataBank {
    public ArrayList<Record> listRecord = new ArrayList<Record>();
    ArrayList<Record> GetRecords = new ArrayList<>();
    ArrayList<Record> GiveRecords = new ArrayList<>();
    ArrayList<Record> HomeRecords = new ArrayList<>();
    Context context;

    private final String CAT_FILE_NAME="Cats.txt";
    public DataBank(Context context)
    {
        this.context=context;
    }
    public ArrayList<Record> getAllrecords() {
        Collections.sort(listRecord);
        return listRecord;
    }

    public ArrayList<Record> getGetRecords(){
        for(int i=0;i<listRecord.size();i++){
            if(listRecord.get(i).getType().equals("收礼")){
                GiveRecords.add(listRecord.get(i));
            }
        }
        Collections.sort(GiveRecords);
        return GiveRecords;
    }
    public ArrayList<Record> getGiveRecords(){
        for(int i=0;i<listRecord.size();i++){
            if(listRecord.get(i).getType().equals("随礼")){
                GetRecords.add(listRecord.get(i));
            }
        }
        Collections.sort(GetRecords);
        return GetRecords;
    }
    public ArrayList<Record> getHomeRecords(String date){
        HomeRecords.clear();
        for(int i=0;i<listRecord.size();i++){
            if(listRecord.get(i).getDate().equals(date))
                HomeRecords.add(listRecord.get(i));
        }
        Collections.sort(HomeRecords);
        return HomeRecords;
    }

    public void Save()
    {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(context.openFileOutput(CAT_FILE_NAME,Context.MODE_PRIVATE));
            oos.writeObject(listRecord);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Load()
    {
        ObjectInputStream ois = null;
        listRecord=new ArrayList<>();
        try {
            ois = new ObjectInputStream(context.openFileInput(CAT_FILE_NAME));
            listRecord = (ArrayList<Record>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
