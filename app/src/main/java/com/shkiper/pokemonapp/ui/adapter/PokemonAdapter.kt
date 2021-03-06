package com.shkiper.pokemonapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shkiper.pokemonapp.R
import com.shkiper.pokemonapp.firebase.FirebaseDatabase
import com.shkiper.pokemonapp.model.Pokemon
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rv_pokemon_item.view.*

class PokemonAdapter: RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>(){

    private var items: MutableList<Pokemon> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.rv_pokemon_item, parent, false)
        return PokemonViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) = holder.bind(items[position], position)


    fun updateData(data: List<Pokemon>) {

        val diffCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
                    items[oldPos].id == data[newPos].id

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
                    items[oldPos].hashCode() == data[newPos].hashCode()
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data as MutableList<Pokemon>
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PokemonViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView),
            LayoutContainer {

        override val containerView: View
            get() = itemView

        fun bind(pokemon: Pokemon, position: Int){
            Glide.with(itemView)
                    .load(pokemon.getImageUrl())
                    .into(itemView.iv_pokemon_image_item)

            itemView.tv_pokemon_name_item.text = pokemon.name
            itemView.tv_base_experience_scores_item.text = pokemon.experience.toString()

            itemView.iv_delete_from_favorites.setOnClickListener {
                FirebaseDatabase.deleteFromFavorites(pokemon.id)
                items.removeAt(position)
                notifyDataSetChanged()
            }

        }
    }
}