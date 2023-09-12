package com.ichsnn.pokepedia

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ichsnn.core.data.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.pokemon.observe(this) { listPokemon ->
            if (listPokemon != null) {
                when (listPokemon) {
                    is Resource.Error -> {
                        Toast.makeText(this, listPokemon.message.toString(), Toast.LENGTH_SHORT)
                    }

                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                        Log.d("Success", "${listPokemon.data}")
                    }
                }
            }
        }
    }
}