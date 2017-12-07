package com.example.pavelshilyagov.tryelmish.details

import com.example.pavelshilyagov.tryelmish.search.CurrentWeatherModel

data class DetailsModel(val weather: CurrentWeatherModel)

sealed class DetailsMsg