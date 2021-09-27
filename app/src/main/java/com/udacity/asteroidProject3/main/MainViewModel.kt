package com.udacity.asteroidProject3.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidProject3.Event
import com.udacity.asteroidProject3.database.getDatabase
import com.udacity.asteroidProject3.domain.Asteroid
import com.udacity.asteroidProject3.repositories.AsteroidRepository
import com.udacity.asteroidProject3.repositories.PictureRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val pictureRepository = PictureRepository(database)
    private val asteroidRepository = AsteroidRepository(database)

    private val _navigateToDetails = MutableLiveData<Event<Asteroid>>()

    val navigateToDetails: LiveData<Event<Asteroid>>
        get() = _navigateToDetails

    init {
        viewModelScope.launch {
            pictureRepository.refreshPictureOfTheDay()
            asteroidRepository.refreshAsteroids()
        }
    }

    val pictureOfTheDay = pictureRepository.pictureOfTheDay
    val asteroids = asteroidRepository.asteroids

    fun onApplyFilter(filter : AsteroidRepository.FilterType){
        asteroidRepository.applyFilter(filter)
    }

    fun asteroidClicked(asteroid: Asteroid){
        _navigateToDetails.value = Event(asteroid)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}