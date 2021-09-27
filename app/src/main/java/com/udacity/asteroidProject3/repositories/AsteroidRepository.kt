package com.udacity.asteroidProject3.repositories

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidProject3.api.Network
import com.udacity.asteroidProject3.domain.toDatabaseModel
import com.udacity.asteroidProject3.database.AsteroidDatabase
import com.udacity.asteroidProject3.domain.Asteroid
import com.udacity.asteroidProject3.domain.toDomainModel
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidProject3.api.getOneWeekAheadDateFormatted
import com.udacity.asteroidProject3.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDate
import com.udacity.asteroidProject3.api.getTodayDateFormatted as getTodayDateFormatted1

class AsteroidRepository(private val database: AsteroidDatabase) {

    enum class FilterType {
        WEEKLY,
        TODAY,
        SAVED
    }

    private val _filterType =
        MutableLiveData(FilterType.WEEKLY)
    val filterType : LiveData<FilterType>
        get() = _filterType

    @RequiresApi(Build.VERSION_CODES.O)
    private val _startDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private val _endDate = _startDate.plusDays(7)

    @RequiresApi(Build.VERSION_CODES.O)
    val asteroids : LiveData<List<Asteroid>> = Transformations.switchMap(filterType) { it ->
        when(it){
            FilterType.WEEKLY ->
                Transformations.map(database.asteroidDao.getWeeklyAsteroids(_startDate.toString(),
                    _endDate.toString())){
                    it.toDomainModel()
                }

            FilterType.TODAY ->
                Transformations.map(database.asteroidDao.getTodaysAsteroids(_startDate.toString())){
                    it.toDomainModel()
                }

            FilterType.SAVED ->
                Transformations.map(database.asteroidDao.getSavedAsteroids()){
                    it.toDomainModel()
                }

            else -> throw IllegalArgumentException("")
        }
    }

    fun applyFilter(filterType: FilterType){
        _filterType.value = filterType
    }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            try{
                val startDate = getTodayDateFormatted1()
                val endDate = getOneWeekAheadDateFormatted()

                val asteroidsResult  = Network.asteroidService.getAsteroids(
                    startDate , endDate, BuildConfig.API_KEY)

                val jsonAsteroid = JSONObject(asteroidsResult)

                val parsedAsteroids = parseAsteroidsJsonResult(jsonAsteroid)

                database.asteroidDao.insertAll(*parsedAsteroids.toDatabaseModel())
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    Log.e("Refresh failed" ,  "Message = $e")
                }
                e.printStackTrace()

            }

        }
    }

    suspend fun removeOldAsteroids(){
        withContext(Dispatchers.IO){
            database.asteroidDao.removeOldAsteroids(getTodayDateFormatted1())
        }
    }

}