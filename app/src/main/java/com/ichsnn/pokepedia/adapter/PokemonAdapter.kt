package com.ichsnn.pokepedia.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.disk.DiskCache
import coil.load
import coil.memory.MemoryCache
import com.ichsnn.core.domain.model.Pokemon
import com.ichsnn.pokepedia.R
import com.ichsnn.pokepedia.databinding.PokemonListItemBinding
import com.ichsnn.pokepedia.utils.Format

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.ListViewHolder>() {
    private val listData = mutableListOf<Pokemon>()
    var onItemClick: ((Pokemon) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<Pokemon>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
        )

    override fun getItemCount(): Int = listData.count()

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PokemonListItemBinding.bind(itemView)

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

        fun bind(data: Pokemon) {
            val imageLoader = ImageLoader.Builder(itemView.context).memoryCache {
                MemoryCache.Builder(itemView.context).maxSizePercent(0.25).build()
            }.diskCache {
                DiskCache.Builder()
                    .directory(itemView.context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.25)
                    .build()
            }.build()

            with(binding) {
                ivPokemonImage.load(data.imageUrl, imageLoader) {
                    crossfade(true)
                    placeholder(R.drawable.ic_pokeball_primary)
                }
                tvName.text = Format.sentenceCapital(data.name)
                tvPokemonId.text = Format.pokemonIdToString(data.id)
            }
        }
    }

}