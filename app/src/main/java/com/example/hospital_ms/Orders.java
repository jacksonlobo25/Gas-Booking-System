package com.example.hospital_ms;

public class Orders {
    public  String uid,datetime,status,amount;
    public Orders(){

    }
    public Orders(String uid, String datetime, String amount, String status)
    {
        this.uid= uid;
        this.datetime=datetime;
        this.amount=amount;
        this.status=status;
    }
    public Orders(String datetime, String amount, String status)
    {
        this.datetime=datetime;
        this.amount=amount;
        this.status=status;
    }

    public String getUid() {
        return uid;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }
}
