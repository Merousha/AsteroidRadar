package com.udacity.asteroidProject3.api

import com.udacity.asteroidProject3.PictureOfDay
import com.udacity.asteroidProject3.domain.Asteroid
import com.udacity.asteroidProject3.network.NetworkAsteroid
import com.udacity.asteroidProject3.network.NetworkPicture

fun List<Asteroid>.toNetworkModel():List<NetworkAsteroid>{

    return map{
        NetworkAsteroid(
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

fun List<NetworkAsteroid>.toDomainModel():List<Asteroid>{
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

fun NetworkPicture.toDomainModel(): PictureOfDay {
    return PictureOfDay(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}

fun PictureOfDay.toDomainModel(): NetworkPicture {
    return NetworkPicture(
        mediaType = this.mediaType,
        title = this.title,
        url = this.url
    )
}