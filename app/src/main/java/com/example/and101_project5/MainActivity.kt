package com.example.and101_project5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.and101_project5.databinding.ActivityMainBinding
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var pokemonImage = ""
    var pokemonName = ""
    var pokemonWeight = ""
    var pokemonType = ""

    private lateinit var binding: ActivityMainBinding
    private val button get() = binding.btnGetRandomPokemon
    private val image get() = binding.ivLogo
    private val textName get() = binding.tvName
    private val textWeight get() = binding.tvWeight
    private val textType get() = binding.tvType




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPokemonInfo()
        getNextPokemon(button, image, textName, textWeight, textType)


    }

    private fun getPokemonInfo(){

        val client = AsyncHttpClient()
        var clientString = getRandomClient()

        client[clientString, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {

                pokemonImage = json.jsonObject.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork").getString("front_default")
                pokemonName = json.jsonObject.getString("name")
                pokemonWeight = json.jsonObject.getString("weight")
                pokemonType = json.jsonObject.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Pokemon Error", errorResponse)
            }
        }]

    }

    fun getRandomClient():String {
        var clientString = "https://pokeapi.co/api/v2/pokemon/"

        val random =  Random.Default
        val min = 1
        val max = 1017

        var randomInt = random.nextInt(min, max + 1).toString()

        clientString += randomInt

        return clientString

    }

    fun getNextPokemon(button: Button, image: ImageView, textName: TextView, textWeight: TextView, textType: TextView){
        button.setOnClickListener{
            getPokemonInfo()
            Glide.with(this)
                .load(pokemonImage)
                .fitCenter()
                .into(image)

            textName.setText(pokemonName)
            textWeight.setText(pokemonWeight)
            textType.setText(pokemonType)




        }


    }





}