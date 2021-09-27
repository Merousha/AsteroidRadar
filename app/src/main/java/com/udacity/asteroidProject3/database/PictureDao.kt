package com.udacity.asteroidProject3.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidProject3.database.entities.PictureEntity

@Dao
interface PictureDao {

    @Query("select * from picture_table")
    fun getPictureOfTheDay() : LiveData<PictureEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg pictureEntity: PictureEntity)

    @Query("Delete from picture_table")
    fun  clear()
}