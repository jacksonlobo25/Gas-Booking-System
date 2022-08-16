package com.example.hospital_ms;

public class Feedbacks{
    public String uid,Rating,datetime,feedback;
    public Feedbacks() {
    }
    public Feedbacks(String uid,String datetime,String Rating,String feedback){
        this.uid=uid;
        this.datetime=datetime;
        this.Rating=Rating;
        this.feedback=feedback;
    }
}
