package com.example.reminders;

//this is a normal POJO: plain old java object
//Data model

public class Reminder {

    private int mId;
    private int mImportant;
    private String mContent;

    public Reminder (int id, String content, int important) {
        mId = id;
        mContent = content;
        mImportant = important;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getImportant() {
        return mImportant;
    }

    public void setImportant(int important){
        mImportant = important;
    }

    public String getContent () {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
