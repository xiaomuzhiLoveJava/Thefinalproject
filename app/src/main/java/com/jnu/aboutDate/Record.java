package com.jnu.aboutDate;

import java.io.Serializable;

public class Record implements Serializable,Comparable<Record> {
    String name;
    String account;
    String date;
    String type;
    String reson;
    String year;
    public Record(String name, String account, String date, String type, String reson){
        this.name=name;
        this.account=account;
        this.date=date;
        this.type=type;
        this.reson=reson;
        this.year=date.substring(0,4);
    }

    public String getYear() { return year; }
    public String getName(){
        return name;
    }
    public String getAccount(){
        return account;
    }
    public String getDate(){
        return date;
    }
    public String getType(){return type;}
    public String getReson(){ return reson;}

    public void setName(String name){ this.name=name; }
    public void setAccount(String account){ this.account=account; }
    public void setDate(String date){ this.date=date; }
    public void setType(String type){ this.type=type; }
    public void setReson(String reson){ this.reson=reson;}

    @Override
    public int compareTo(Record o) {
        return Integer.parseInt(date);
    }
}
