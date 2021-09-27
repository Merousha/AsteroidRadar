package com.udacity.asteroidProject3.domain

import com.udacity.asteroidProject3.PictureOfDay
import com.udacity.asteroidProject3.database.entities.AsteroidEntity
import com.udacity.asteroidProject3.database.entities.PictureEntity

fun ArrayList<Asteroid>.toDatabaseModel():Array<AsteroidEntity>{

    return map{
        AsteroidEntity(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun List<AsteroidEntity>.toDomainModel():List<Asteroid>{
    return map{
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

fun PictureOfDay.toDatabaseModel():PictureEntity{
    return PictureEntity(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}

fun PictureEntity.toDomainModel():PictureOfDay{
    return PictureOfDay(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}

