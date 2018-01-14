package ro.ubb.reosandroidapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by CristianCosmin on 08.11.2017.
 */
//@Entity(tableName = "apartment")
public class Apartment {
    //    @PrimaryKey(autoGenerate = true)
    private String userId;
    private String id;
    private String title;
//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private String image;
    private int cost;

    public Apartment(String userId, String title, String image, int cost) {
        this.userId = userId;
        this.title = title;
        this.image = image;
        this.cost = cost;
    }

    public Apartment() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}