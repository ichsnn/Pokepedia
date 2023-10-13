package com.ichsnn.pokepedia.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ichsnn.core.adapter.PokemonAdapter
import com.ichsnn.core.utils.GridSpacingItemDecoration
import com.ichsnn.pokepedia.di.FavoriteModuleDependencies
import com.ichsnn.pokepedia.favorite.databinding.FragmentFavoriteBinding
import com.ichsnn.pokepedia.presentation.detail.DetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        initDagger()
        super.onCreate(savedInstanceState)
    }

    private fun initDagger() {
        val coreDependencies = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            FavoriteModuleDependencies::class.java
        )
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(coreDependencies)
            .build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val pokemonAdapter = PokemonAdapter()
            pokemonAdapter.onItemClick = { selectedPokemon ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.POKEMON_NAME, selectedPokemon.name)
                startActivity(intent)
            }

            favoriteViewModel.favoritePokemon.observe(viewLifecycleOwner) { listPokemon ->
                binding.viewEmpty.root.visibility =
                    if (listPokemon.isEmpty()) View.VISIBLE else View.GONE
                pokemonAdapter.setData(listPokemon)
            }

            with(binding.rvFavorite) {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = pokemonAdapter
                addItemDecoration(GridSpacingItemDecoration(2, 24, true, 0, false))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}