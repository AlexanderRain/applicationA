package com.example.a.model.room.core;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Delete;

import com.example.a.entity.Link;

import java.util.List;

@Dao
public interface LinkDao {

    @Query("SELECT * FROM link")
    LiveData<List<Link>> getAllLinks();

    @Query("DELETE from link WHERE id = :id")
    void deleteById(long... id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Link link);

    @Update
    void update(Link link);

    @Delete
    void delete(Link link);
}
