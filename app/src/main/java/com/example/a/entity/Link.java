package com.example.a.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "link",
        indices = {@Index(value = {"imageLink"}, unique = true)})

public class Link {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "imageLink")
    private String imageLink;

    @ColumnInfo(name = "status")
    private int status;

    @ColumnInfo(name = "time")
    private String date;

    public Link(String imageLink, int status, String date) {
        this.imageLink = imageLink;
        this.status = status;
        this.date = date;
    }

    @Ignore
    public Link(long id, String imageLink, int status, String date) {
        this.id = id;
        this.imageLink = imageLink;
        this.status = status;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setString(String date) {
        this.date = date;
    }
}


/*
package com.example.a.model.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Link {

    @PrimaryKey
    public int id;

    public String imageLink;

    public int status;

    public String date;

    @Ignore
    public static int lastID;

    public Link(int id, String imageLink, int status, String date) {
        this.id = id;
        this.imageLink = imageLink;
        this.status = status;
        this.date = date;
        lastID = id;
    }

    @Override
    public String toString() {
        return imageLink;
    }
}
*/