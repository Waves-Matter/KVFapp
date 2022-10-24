package com.example.dell.kvfapp;

public class KVFUser {

    private String Name;
    private String Surname;
    private String Email;
    private String Phone;
    private Boolean IsMOD;
    private String id;
    private boolean Verified;
//appendix
    public KVFUser(String n, String s, String e, String p, String id)
    {
        this.Name = n;
        this.Surname = s;
        this.Email = e;
        this.Phone = p;
        this.IsMOD = false;
        this.id = id;
        Verified = false;
    }



    public KVFUser()
    {
        this.Name = null;
        this.Surname = null;
        this.Email = null;
        this.Phone = null;
        this.IsMOD = false;
        this.id = null;
        this.Verified = false;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public Boolean getIsMOD() { return IsMOD; }

    public Boolean getVerified() { return Verified; }

    public String getId() {return id;}


    public void setName(String t) {
        Name = t;
    }

    public void setSurname(String i) {
        Surname = i;
    }

    public void setEmail(String ed) {
        Email = ed;
    }

    public void setPhone(String av) {
        Phone = av;
    }

    public void setIsMOD(Boolean et) {IsMOD = et;}

    public void setVerified(Boolean v) { Verified = v; }

    public void PromoteToMod() {
        IsMOD = true;
    }
}
