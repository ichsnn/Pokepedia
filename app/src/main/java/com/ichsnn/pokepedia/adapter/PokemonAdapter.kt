package com.ichsnn.pokepedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ichsnn.core.domain.model.Pokemon
import com.ichsnn.pokepedia.R
import com.ichsnn.pokepedia.databinding.PokemonListItemBinding

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.ListViewHolder>() {
    private val listData = mutableListOf<Pokemon>()
    var onItemClick: ((Pokemon) -> Unit)? = null

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
                ivPokemonImage.load(data.imageUrl)
                tvName.text = data.name
            }
        }

    }

}