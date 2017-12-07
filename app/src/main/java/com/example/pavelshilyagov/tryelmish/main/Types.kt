package com.example.pavelshilyagov.tryelmish.main

import com.example.pavelshilyagov.tryelmish.details.DetailsModel
import com.example.pavelshilyagov.tryelmish.details.DetailsMsg
import com.example.pavelshilyagov.tryelmish.search.CurrentWeatherModel
import com.example.pavelshilyagov.tryelmish.search.SearchModel
import com.example.pavelshilyagov.tryelmish.search.SearchMsg
import java.util.*

sealed class Screen {
    data class Search(val model:SearchModel) : Screen()
    data class Details(val model: DetailsModel) : Screen()
}

data class MainModel (val screen:Screen, val backStack: Stack<Screen>)

sealed class Msg {
    data class Search(val msg:SearchMsg) : Msg()
    data class Details(val msg:DetailsMsg) : Msg()
    data class GoToDetails(val details:CurrentWeatherModel) : Msg()
    object GoBack : Msg()
    object Exit : Msg()
    object HideVirtualKeyboard : Msg()
}

