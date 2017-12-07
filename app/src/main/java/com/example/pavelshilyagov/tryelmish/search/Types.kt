package com.example.pavelshilyagov.tryelmish.search

import com.example.pavelshilyagov.tryelmish.services.Json
import io.michaelrocks.optional.Optional


data class SearchModel (val searchValue:String, val isLoading:Boolean, val current:Optional<CurrentWeatherModel>, val error:String)

data class WindCondition(val speed:Double, val direction:String)
data class Location(val name:String, val country:String)

data class CurrentWeatherModel(val location: Location, val temperature: Double, val feelsLike: Double, val wind: WindCondition, val pressure: Double, val humidity: Int)

sealed class SearchMsg {
    data class OnTextChanged(val text: String) : SearchMsg()
    object SearchByCity : SearchMsg()
    data class OnSearchSuccess(val result: Json.Response) : SearchMsg()
    data class OnSearchError(val exc: Exception) : SearchMsg()
    data class ShowDetails(val details:CurrentWeatherModel) : SearchMsg()
}
