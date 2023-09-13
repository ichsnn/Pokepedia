package com.ichsnn.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ichsnn.core.R
import com.ichsnn.core.databinding.PokemonListItemBinding
import com.ichsnn.core.domain.model.Pokemon
import com.ichsnn.core.utils.Format

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
            with(binding) {
                Glide.with(itemView)
                    .load(data.imageUrl)
                    .placeholder(R.drawable.ic_pokeball_primary)
                    .into(ivPokemonImage)
                tvName.text = Format.sentenceCapital(data.name)
                tvPokemonId.text = Format.pokemonIdToString(data.id)
            }
        }
    }

}