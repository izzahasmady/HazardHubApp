package com.example.gmapactivity; ;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Hazard {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("hazardType")
    @Expose
    public String hazardType;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("time")
    @Expose
    public String time;
    @SerializedName("lng")
    @Expose
    public String lng;
    @SerializedName("lat")
    @Expose
    public String lat;
    @SerializedName("reporter")
    @Expose
    public String reporter;

}