package com.example.listycity3

import androidx.compose.runtime.mutableStateListOf

class CityRepository {
    private val _cities = mutableStateListOf(
        City("Edmonton", "AB"),
        City("Vancouver", "BC"),
        City("Toronto", "ON")
    )

    val cities: List<City> //外部接口，调用cities的时候返回_cities
        get() = _cities

    fun addCity(city: City){
        _cities.add(city)
    }

    fun updateCity(oldCity: City, updatedCity: City) {
        val index = _cities.indexOf(oldCity)
        if (index != -1) {
            _cities[index] = updatedCity
        }
    }
}