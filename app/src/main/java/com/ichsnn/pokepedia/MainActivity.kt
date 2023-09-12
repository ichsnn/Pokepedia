package com.ichsnn.pokepedia

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ichsnn.core.data.Resource
import com.ichsnn.pokepedia.adapter.PokemonAdapter
import com.ichsnn.pokepedia.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonAdapter = PokemonAdapter()

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
                        pokemonAdapter.setData(listPokemon.data)
                    }
                }
            }
        }

        with(binding.rvMain) {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = pokemonAdapter
        }
    }
}