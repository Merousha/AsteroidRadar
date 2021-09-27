package com.udacity.asteroidProject3.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidProject3.database.getDatabase
import com.udacity.asteroidProject3.repositories.AsteroidRepository
import com.udacity.asteroidProject3.repositories.PictureRepository
import retrofit2.HttpException

class RefreshDataWorker (appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val asteroidRepository = AsteroidRepository(database)
        val pictureRepository = PictureRepository(database)

        return try {
            pictureRepository.refreshPictureOfTheDay()

            asteroidRepository.refreshAsteroids()
            asteroidRepository.removeOldAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}