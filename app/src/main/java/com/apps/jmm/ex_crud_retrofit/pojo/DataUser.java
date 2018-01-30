package com.apps.jmm.ex_crud_retrofit.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class DataUser implements Parcelable {
    public static final Creator<DataUser> CREATOR = new Creator<DataUser>() {
        @Override
        public DataUser createFromParcel(Parcel in) {
            return new DataUser(in);
        }

        @Override
        public DataUser[] newArray(int size) {
            return new DataUser[size];
        }
    };

    private int id = 0;
    private String name = "";
    private String birthdate = "";

    public DataUser() {
    }

    public DataUser(int id, String name, String birthDate) {
        this.id = id;
        this.name = name;
        this.birthdate = birthDate;
    }

    protected DataUser(Parcel in) {
        id = in.readInt();
        name = in.readString();
        birthdate = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthdate;
    }

    public void setBirthDate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(birthdate);
    }
}
