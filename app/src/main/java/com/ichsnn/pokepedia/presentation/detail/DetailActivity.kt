package com.ichsnn.pokepedia.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ichsnn.core.data.Resource
import com.ichsnn.core.utils.Format
import com.ichsnn.pokepedia.R
import com.ichsnn.pokepedia.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val detailViewModel: DetailViewModel by viewModels()

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pokemonName = intent.getStringExtra(POKEMON_NAME) ?: ""

        showPokemonDetail(pokemonName)
    }

    private fun showPokemonDetail(pokemonName: String) {
        detailViewModel.getPokemonDetailByName(pokemonName).observe(this) { resource ->
            when (resource) {
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.viewErrorContainer.visibility = View.VISIBLE
                    binding.viewError.tvErrorMessage.text =
                        resource.message ?: getString(R.string.error_something_wrong)
                }

                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    resource.data?.let { pokemon ->
                        binding.favoriteAction.setImageDrawable(setFavoriteIcon(pokemon.isFavorite))
                        binding.progressBar.visibility = View.GONE
                        Glide.with(this).load(pokemon.imageUrl).into(binding.ivPokemonImage)
                        binding.tvDescription.text = pokemon.description
                        binding.tvName.text = Format.sentenceCapital(pokemon.name)
                        binding.tvPokemonId.text = Format.pokemonIdToString(pokemon.id)

                        binding.backAction.setOnClickListener {
                            finish()
                        }

                        binding.favoriteAction.setOnClickListener {
                            detailViewModel.setFavoritePokemon(pokemon, !pokemon.isFavorite)
                            binding.favoriteAction.setImageDrawable(setFavoriteIcon(pokemon.isFavorite))
                        }
                    }
                }
            }
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) = ContextCompat.getDrawable(
        this,
        if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
    )

    companion object {
        const val POKEMON_NAME = "pokemon_name"
    }
}