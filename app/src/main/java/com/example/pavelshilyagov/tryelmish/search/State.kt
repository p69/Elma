package com.example.pavelshilyagov.tryelmish.search

import com.example.pavelshilyagov.tryelmish.main.Msg
import com.example.pavelshilyagov.tryelmish.services.Json
import com.example.pavelshilyagov.tryelmish.services.WeatherApi
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.p69.elma.core.*
import io.michaelrocks.optional.Optional
import kotlinx.coroutines.experimental.*

object Search {
    fun init(): SearchModel {
        return SearchModel("", false, Optional.None, error = "")
    }

    fun update(msg: SearchMsg, model: SearchModel): UpdateResultWithParentsEffects<SearchModel, SearchMsg, Msg> {
        return when (msg) {
            is SearchMsg.OnTextChanged -> UpdateResultWithParentsEffects(model.copy(searchValue = msg.text))
            is SearchMsg.SearchByCity -> {
                val requestCmd = CmdF.ofAsyncFunc(
                        {q->requestByQuery(q)},
                        model.searchValue,
                        SearchMsg::OnSearchSuccess,
                        SearchMsg::OnSearchError)
                UpdateResultWithParentsEffects(model.copy(isLoading = true), requestCmd)
            }
            is SearchMsg.OnSearchSuccess ->
                UpdateResultWithParentsEffects(model.copy(isLoading = false, current = Optional.Some(createCurrentWeatherModel(msg.result))))
            is SearchMsg.OnSearchError ->
                UpdateResultWithParentsEffects(model.copy(isLoading = false, current = Optional.None, error = msg.exc.toString()))
            is SearchMsg.ShowDetails ->
                UpdateResultWithParentsEffects(
                    model,
                    parentEffects =  CmdF.batch(CmdF.ofMsg(Msg.GoToDetails(msg.details)), CmdF.ofMsg(Msg.HideVirtualKeyboard)))
        }
    }
}

private suspend fun requestByQuery(query:String): Json.Response = withContext(CommonPool) {
    delay(300) // to make sure that ripple animation is finished :D
    val (_,_,res) = Fuel.request(WeatherApi.currentFor(query)).responseObject(deserializer = Json.CurrentDeserializer())
    return@withContext when (res) {
        is Result.Success -> res.value
        is Result.Failure -> throw res.error
    }
}

private fun createCurrentWeatherModel(response: Json.Response): CurrentWeatherModel {
    return CurrentWeatherModel(
            Location(response.location.name, response.location.country),
            response.current.temperature,
            response.current.feelsLikeTemp,
            WindCondition(response.current.windsSpeed, response.current.windsDirection),
            response.current.pressure,
            response.current.humidity)
}