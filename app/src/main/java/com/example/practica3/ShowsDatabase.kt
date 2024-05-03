package com.example.practica3

import android.content.Context
//import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream

class ShowsDatabase(context : Context) {

    val inputStream: InputStream = context.resources.openRawResource(R.raw.shows)
    val jsonFileString = inputStream.bufferedReader().use { it.readText() }

    val showListType = object: TypeToken<List<Show>>() {}.type
    val shows: List<Show> = Gson().fromJson(jsonFileString, showListType)

    /*
    fun printShows() {
        for (q in shows) {
            Log.d("TESTEO", "Titulo: ${q.name}")
            Log.d("TESTEO", "Generos: ${q.genres.joinToString()}")
            Log.d("TESTEO", "ID: ${q.id} \n")
        }
    }
    */

}