package com.example.dell.kvfapp;

import java.util.ArrayList;

public class UserEvent {
    String EventID;
    ArrayList UserID;

    public UserEvent(String eventID, ArrayList userID) {
        EventID = eventID;
        UserID = userID;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public ArrayList getUserID() {
        return UserID;
    }

    public void setUserID(ArrayList userID) {
        UserID = userID;
    }
}
