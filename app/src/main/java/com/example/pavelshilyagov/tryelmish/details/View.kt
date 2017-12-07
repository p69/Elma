package com.example.pavelshilyagov.tryelmish.details


import com.example.pavelshilyagov.tryelmish.search.CurrentWeatherModel
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.widget.Text
import com.p69.elma.core.*
import com.p69.elma.litho.ElmaLithoView

object DetailsUI {
    fun view(model: DetailsModel, ctx: ComponentContext, dispatcher: Dispatch<DetailsMsg>): ElmaLithoView = ElmaLithoView.ComponentLayoutView(
        Column.create(ctx)
                .child(Text.create(ctx)
                        .isSingleLine(false)
                        .maxLines(10)
                        .textSizeDip(16f)
                        .text(createDescriptionText(model.weather)))
                .build()
    )

    private fun createDescriptionText(weather: CurrentWeatherModel): String =
            """Temperature: ${weather.temperature} ℃ / feels like ${weather.feelsLike} ℃
Wind: ${weather.wind.speed} km/h ${weather.wind.direction}
Pressure: ${weather.pressure} mb
Humidity: ${weather.humidity}%"""

}