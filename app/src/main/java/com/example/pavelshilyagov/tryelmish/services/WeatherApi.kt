package com.example.pavelshilyagov.tryelmish.services

import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.util.FuelRouting
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

object Json {
    data class Response(
            @SerializedName("location") val location:Location,
            @SerializedName("current")val current:Current)

    data class Location(
            @SerializedName("name") val name:String,
            @SerializedName("region") val region:String,
            @SerializedName("country") val country:String,
            @SerializedName("lat") val lat:Double,
            @SerializedName("lon") val lon:Double,
            @SerializedName("tz_id") val timeZone:String,
            @SerializedName("localtime_epoch") val localTimeEpoch:Int,
            @SerializedName("localtime") val localtime:String)

    data class Current(
            @SerializedName("last_updated") val lastUpdated: String,
            @SerializedName("temp_c") val temperature: Double,
            @SerializedName("feelslike_c") val feelsLikeTemp: Double,
            @SerializedName("wind_kph") val windsSpeed: Double,
            @SerializedName("wind_dir") val windsDirection: String,
            @SerializedName("pressure_mb") val pressure: Double,
            @SerializedName("humidity") val humidity: Int
    )

    class CurrentDeserializer : ResponseDeserializable<Response> {
        override fun deserialize(content: String) = Gson().fromJson(content, Response::class.java)!!
    }
}

sealed class WeatherApi : FuelRouting {

    private val appKey = "09b3aff8cbe24ec58d4130805172410"
    override val basePath = "http://api.apixu.com/v1"

    class currentFor(val location:String): WeatherApi()

    override val path: String
        get() {
            return when(this) {
                is currentFor -> "/current.json"
            }
        }

    override val headers: Map<String, String>?
        get() = null

    override val params: List<Pair<String, Any?>>?
        get() {
            val baseParams = listOf("key" to appKey)
            val child = when(this) {
                is currentFor -> listOf("q" to location)
            }
            return baseParams+child
        }

    override val method: Method
        get() = Method.GET
}