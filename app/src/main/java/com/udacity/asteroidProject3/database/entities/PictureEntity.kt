package com.udacity.asteroidProject3.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "picture_table")
@Parcelize
data class PictureEntity(
    val mediaType: String,
    val title: String,
    @PrimaryKey
    val url: String
):Parcelable