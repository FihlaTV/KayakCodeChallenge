package com.shaznee.kayak.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Airline implements Parcelable {

    public static final String BASE_URL = "https://www.kayak.com";

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("logoURL")
    @Expose
    private String logoURL;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("site")
    @Expose
    private String webSite;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public String getCompleteLogoURL() {
        StringBuilder logoURLBuilder = new StringBuilder();
        logoURLBuilder.append(BASE_URL);
        logoURLBuilder.append(logoURL);
        return logoURLBuilder.toString();
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Airline(){}

    protected Airline(Parcel in) {
        code = in.readString();
        logoURL = in.readString();
        name = in.readString();
        phone = in.readString();
        webSite = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(logoURL);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(webSite);
    }

    public static final Parcelable.Creator<Airline> CREATOR = new Parcelable.Creator<Airline>() {
        @Override
        public Airline createFromParcel(Parcel in) {
            return new Airline(in);
        }

        @Override
        public Airline[] newArray(int size) {
            return new Airline[size];
        }
    };

}