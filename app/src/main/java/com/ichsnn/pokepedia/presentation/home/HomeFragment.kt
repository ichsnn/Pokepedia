package com.ichsnn.pokepedia.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ichsnn.core.data.Resource
import com.ichsnn.pokepedia.R
import com.ichsnn.pokepedia.adapter.PokemonAdapter
import com.ichsnn.pokepedia.databinding.FragmentHomeBinding
import com.ichsnn.pokepedia.utils.GridSpacingItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val pokemonAdapter = PokemonAdapter()
            homeViewModel.pokemon.observe(viewLifecycleOwner) { listPokemon ->
                if (listPokemon != null) {
                    when (listPokemon) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvErrorMessage.text =
                                listPokemon.message ?: getString(
                                    R.string.error_something_wrong
                                )
                        }


                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            pokemonAdapter.setData(listPokemon.data)
                        }
                    }
                }
            }

            val recyclerView = binding.rvHome
            with(recyclerView) {
                layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                adapter = pokemonAdapter
            }
            recyclerView.addItemDecoration(GridSpacingItemDecoration(2, 24, true, 0, false))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}