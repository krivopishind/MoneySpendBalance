package com.example.kryvopyshyn.mytestapplication.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dmytro_Kryvopyshyn on 15-Nov-17.
 */

public class SmsDto {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat("MM.yy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    private long id;
    private String sender;
    private String sumString;
    private double balance;
    private double sum;
    private String place;
    private long timestamp;
    private String subject;
    private boolean addAction;
    //for testing
    private String date;

    public SmsDto(String sender) {
        this.sender = sender;
    }

    public SmsDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSumString() {
        return sumString;
    }

    public void setSumString(String sumString) {
        this.sumString = sumString;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        Date date = new Date(this.timestamp);
        setDate(DATE_FORMAT.format(date));
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isAddAction() {
        return addAction;
    }

    public void setAddAction(boolean addAction) {
        this.addAction = addAction;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getMonthYear(){
        Date date = new Date(this.timestamp);
        return YEAR_MONTH_FORMAT.format(date);
    }

    public String getTime(){
        Date date = new Date(this.timestamp);
        return TIME_FORMAT.format(date);
    }

}
