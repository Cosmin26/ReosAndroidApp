package ro.ubb.reosandroidapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by CristianCosmin on 08.11.2017.
 */
@Entity(tableName = "apartment")
public class Apartment {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String title;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;
    private int cost;

    public Apartment(String title, byte[] image, int cost) {
        this.title = title;
        this.image = image;
        this.cost = cost;
    }

    public Apartment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
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