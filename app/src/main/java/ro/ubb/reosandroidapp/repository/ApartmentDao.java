package ro.ubb.reosandroidapp.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ro.ubb.reosandroidapp.model.Apartment;

/**
 * Created by CristianCosmin on 21.12.2017.
 */

@Dao
public interface ApartmentDao {

    @Query("SELECT * FROM apartment")
    List<Apartment> getAll();


    @Query("SELECT * FROM apartment where id = :id")
    Apartment getApartmentById(Long id);

    @Insert
    void insertAll(Apartment... apartment);


    @Delete
    void delete(Apartment apartment);


    @Update
    void update(Apartment apartment);
}
