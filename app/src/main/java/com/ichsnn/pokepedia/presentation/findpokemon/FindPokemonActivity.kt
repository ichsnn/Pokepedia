package com.ichsnn.pokepedia.presentation.findpokemon

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ichsnn.core.data.Resource
import com.ichsnn.core.domain.model.Pokemon
import com.ichsnn.core.utils.Format
import com.ichsnn.pokepedia.R
import com.ichsnn.pokepedia.databinding.ActivityFindPokemonBinding
import com.ichsnn.pokepedia.presentation.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FindPokemonActivity : AppCompatActivity() {
    private val findPokemonViewModel: FindPokemonViewModel by viewModels()

    private var _binding: ActivityFindPokemonBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFindPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchInputLayout.setStartIconOnClickListener {
            finish()
        }

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    findPokemonViewModel.searchQuery.value = s.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.viewError.tvErrorMessage.text = getString(R.string.pokemon_not_found)

        findPokemonViewModel.search.observe(this) { resource ->
            when (resource) {
                is Resource.Error -> {
                    handleOnError(resource)
                }

                is Resource.Loading -> {
                    handleOnLoading()
                }

                is Resource.Success -> {
                    handleOnSuccess(resource)
                }
            }
        }
    }

    private fun handleOnSuccess(resource: Resource<Pokemon>) {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            viewError.root.visibility = View.GONE
            pokemonItem.root.visibility = View.VISIBLE
            pokemonItem.tvPokemonId.text =
                Format.pokemonIdToString(resource.data?.id ?: 0)
            pokemonItem.tvName.text =
                Format.sentenceCapital(resource.data?.name.toString())
            Glide.with(this@FindPokemonActivity).load(resource.data?.imageUrl)
                .placeholder(com.ichsnn.core.R.drawable.ic_pokeball_primary)
                .into(binding.pokemonItem.ivPokemonImage)

            pokemonItem.root.setOnClickListener {
                val intent = Intent(this@FindPokemonActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.POKEMON_NAME, resource.data?.name)
                startActivity(intent)
            }
        }
    }

    private fun handleOnLoading() {
        binding.apply {
            progressBar.visibility = View.VISIBLE
            viewError.root.visibility = View.GONE
            pokemonItem.root.visibility = View.GONE
        }
    }

    private fun handleOnError(resource: Resource<Pokemon>) {
        binding.apply {
            progressBar.visibility = View.GONE
            pokemonItem.root.visibility = View.GONE
            viewError.root.visibility = View.VISIBLE
            viewError.tvErrorMessage.text = resource.message
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}