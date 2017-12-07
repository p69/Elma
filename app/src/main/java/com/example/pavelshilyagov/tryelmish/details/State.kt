package com.example.pavelshilyagov.tryelmish.details

import com.example.pavelshilyagov.tryelmish.search.CurrentWeatherModel

object Details {
    fun init(weather: CurrentWeatherModel): DetailsModel = DetailsModel(weather)
}