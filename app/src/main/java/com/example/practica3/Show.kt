package com.example.practica3

data class Show(
    val id: Int,
    val name: String,
    val language: String,
    val genres: List<String>,
    val premiered: String,
    val officialSite: String,
    val rating: Rating,
    val network: Network,
    val image: Image,
    val summary: String
)

data class Rating(
    val average: Double
)

data class Network(
    val country: Country
)

data class Country(
    val name: String,
    val code: String
)

data class Image(
    val original: String
)
