package com.example.myproyectomascotas.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;



@Dao
public interface EventDao{

    @Insert
    void insertAll(EntityClass entityClass);

    @Query("SELECT * FROM myTable WHERE eventidmascota == :id")
    List<EntityClass> getAllData(String id);


}
