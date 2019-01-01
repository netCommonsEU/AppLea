package com.example.commontask.view.mappers

import java.text.SimpleDateFormat
import java.util.*

object ForecastCommonMapper {

    fun getWeatherDescription(icon: String): String = when (icon) {


        "clear-day", "clear-night" -> "clear sky"
        "rain" -> "rain"
        "snow" -> "snow"
        "sleet" -> "sleet"
        "wind" -> "wind"
        "fog" -> "fog"
        "cloudy" -> "cloudy"
        "partly-cloudy-day", "partly-cloudy-night" -> "partly cloudy day"
        "hail" -> "hail"
        "thunderstorm" -> "thunderstorm"
        "tornado" -> "tornado"
        else -> "Μη-Αναγνωρίσιμο Φαινόμενο"



    }



    fun calculateWind(w: Double?): String {
        return if (w != null) {
            "%.2f".format(w) + " m/s"
        } else {
            "NA"
        }
    }

    fun calculateHumidity(h: Double?): String {
        return if (h != null) {
            val humidity = h.times(100)
            "%.0f".format(humidity) + " %"
        } else {
            "NA"
        }
    }

    // f1 = low, f2 = high
    fun fahrenheitToCelsius(f1: Double?, f2: Double? = null): String {
        return if (f2 == null) {
            if (f1 != null) {
                val temperature = (f1 - 32) * 0.5556
                if ("%.0f".format(temperature) == "-0") {
                    "0°C"
                } else {
                    "%.0f".format(temperature) + "°C"
                }
            } else {
                "NA"
            }
        } else {
            if (f1 != null) {
                val temperatureLow = (f1 - 32) * 0.5556
                val temperatureHigh = (f2 - 32) * 0.5556
                if ("%.0f".format(temperatureLow) == "-0") {
                    "0°" + " | " + "%.0f".format(temperatureHigh) + "°"
                } else if ("%.0f".format(temperatureHigh) == "-0") {
                    "%.0f".format(temperatureLow) + "°" + " | " + "0°"
                } else if ("%.0f".format(temperatureLow) == "-0" && "%.0f".format(temperatureHigh) == "-0") {
                    "0°" + " | " + "0°"
                } else {
                    "%.0f".format(temperatureLow) + "°" + " | " + "%.0f".format(temperatureHigh) + "°"
                }
            } else {
                "NA"
            }
        }
    }

    fun getListItemDay(unixTime: Long): String {


        val date = Date(unixTime * 1000L) // *1000 is to convert seconds to milliseconds
        val sdf = SimpleDateFormat("E", Locale.getDefault())
        return sdf.format(date)


    }

    fun getIcon(condition: String?, timeStamp: Long? = null): String {



        val cal = Calendar.getInstance()
        var hour = cal.get(Calendar.HOUR_OF_DAY)
        if (timeStamp != null) {
            val date = Date(timeStamp * 1000L)
            val sdf = SimpleDateFormat("H", Locale.getDefault())
            hour = sdf.format(date).toInt()
        }
        val isNight = hour < 6 || hour > 18
        return if (isNight) {
            ForecastCommonMapper.nightConditionToIcon(condition)
        } else {
            ForecastCommonMapper.dayConditionToIcon(condition)
        }



    }

    fun dayConditionToIcon(condition: String?): String {
        return when (condition) {
            "clear-day", "clear-night" -> "ic_weather_set_1_36"
            "rain" -> "ic_weather_set_1_40"
            "snow" -> "ic_weather_set_1_43"
            "sleet" -> "ic_weather_set_1_42"
            "wind" -> "ic_weather_set_1_24"
            "fog" -> "ic_weather_set_1_19"
            "cloudy" -> "ic_weather_set_1_26"
            "partly-cloudy-day", "partly-cloudy-night" -> "ic_weather_set_1_34"
            "hail" -> "ic_weather_set_1_18"
            "thunderstorm" -> "ic_weather_set_1_35"
            "tornado" -> "ic_weather_set_1_20"
            else -> "ic_weather_set_1_32"
        }
    }

    private fun nightConditionToIcon(condition: String?): String = when (condition) {


        "clear-day", "clear-night" -> "ic_weather_set_1_31"
        "rain" -> "ic_weather_set_1_45"
        "snow" -> "ic_weather_set_1_46"
        "sleet" -> "ic_weather_set_1_05"
        "wind" -> "ic_weather_set_1_24"
        "fog" -> "ic_weather_set_1_21"
        "cloudy" -> "ic_weather_set_1_27"
        "partly-cloudy-night", "partly-cloudy-day" -> "ic_weather_set_1_34"
        "hail" -> "ic_weather_set_1_18"
        "thunderstorm" -> "ic_weather_set_1_00"
        "tornado" -> "ic_weather_set_1_20"
        else -> "ic_weather_set_1_31"


    }

    fun timestampToDate(timeStamp: Long): String {
        val date = Date(timeStamp * 1000L) // *1000 is to convert seconds to milliseconds
        //val sdf = SimpleDateFormat("E, MMM dd, yyyy", Locale.getDefault())
        val sdf = SimpleDateFormat("E, MMM dd", Locale.getDefault())
        return sdf.format(date)
    }

    fun timestampToHour(timeStamp: Long): String {
        val date = Date(timeStamp * 1000L)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(date).toLowerCase()
    }

}