package com.udacity.asteroidProject3.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidProject3.PictureOfDay
import com.udacity.asteroidProject3.api.Network
import com.udacity.asteroidProject3.api.toDomainModel
import com.udacity.asteroidProject3.database.AsteroidDatabase
import com.udacity.asteroidProject3.domain.toDatabaseModel
import com.udacity.asteroidProject3.domain.toDomainModel
import com.udacity.asteroidradar.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PictureRepository(private val database: AsteroidDatabase) {

    val pictureOfTheDay: LiveData<PictureOfDay> =
        Transformations.map(database.pictureDao.getPictureOfTheDay()) {
            it?.toDomainModel()
        }

    suspend fun refreshPictureOfTheDay() {
        withContext(Dispatchers.IO) {
            try {
                val picture = Network.pictureOfTheDayService.getPictureOfTheDay(BuildConfig
                    .API_KEY)
                val domainPicture = picture.toDomainModel()

                Log.e("PictureRepository","picture  = $domainPicture")

                if (domainPicture.mediaType == "image") {
                    database.pictureDao.clear()
                    database.pictureDao.insertAll(domainPicture.toDatabaseModel())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("PictureRepository","Refresh failed ${e.message}")
                }
                e.printStackTrace()
            }
        }
    }
}