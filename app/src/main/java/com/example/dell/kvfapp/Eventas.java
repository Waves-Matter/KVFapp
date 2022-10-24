package com.example.dell.kvfapp;

import java.util.ArrayList;

public class Eventas
{
    //private instance variables
    private String title;
    private String info;
    private String eventDate;
    private String eventTime;
    private String needVolunteers;
    private String areVolunteers;
    private String id;
    private ArrayList<String> VolID;

    public Eventas(String t, String i, String d, String m, String s, String Are, String id)
    {
        this.title = t;
        this.info = i;
        this.eventDate = d;
        this.eventTime = m;
        this.needVolunteers = s;
        this.areVolunteers = Are;
        this.id = id;
        this.VolID = null;
    }

    public Eventas()
    {
        this.title = null;
        this.info = null;
        this.eventDate = null;
        this.eventTime = null;
        this.needVolunteers = null;
        this.areVolunteers = null;
        this.id = null;
        this.VolID = null;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getAreVolunteers() {
        return areVolunteers;
    }

    public String getNeedVolunteers() {
        return needVolunteers;
    }

    public String getId() {return id;}

    public String getVOLid(int i) {return VolID.get(i);}


    public void setTitle(String t) {
        title = t;
    }

    public void setInfo(String i) {
        info = i;
    }

    public void setEventDate(String ed) {
        eventDate = ed;
    }

    public void setAreVolunteers(String av) {
        areVolunteers = av;
    }

    public void setEventTime(String et) {
        eventTime = et;
    }

    public void setNeedVolunteers(String nv) {
        needVolunteers = nv;
    }

    public void setId(String i){id = i;}

    public void AddAnotherVoL(String a) {VolID.add(a) ;}

    public void ChangeVol (String a, int i) {VolID.set(i, a);}

    public void DeleteVol(int i) {VolID.remove(i);}

    public ArrayList getVolArrayL(){
        if (VolID != null){
        return VolID;
        }else return null;
    }
}
