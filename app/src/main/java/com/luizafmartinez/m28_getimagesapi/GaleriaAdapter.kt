package com.luizafmartinez.m28_getimagesapi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.luizafmartinez.m28_getimagesapi.databinding.ItemGaleriaBinding
import com.squareup.picasso.Picasso

class GaleriaAdapter: Adapter<GaleriaAdapter.GaleriaViewHolder> () {

    private var listaImagens = emptyList<String>()

    fun adicionarLista( lista: List<String> ) {
        listaImagens = lista
        notifyDataSetChanged()
    }

    inner class GaleriaViewHolder(val binding: ItemGaleriaBinding)
        : ViewHolder(binding.root) {

            fun bind(url: String) {
                Picasso.get()
                    .load(url)
                    .into(binding.imageItem )
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GaleriaViewHolder {

        val layoutInflater = LayoutInflater.from( parent.context )

        val itemView = ItemGaleriaBinding.inflate(
            layoutInflater, parent, false
        )

        return GaleriaViewHolder( itemView )
    }

    override fun getItemCount(): Int {
        return listaImagens.size
    }

    override fun onBindViewHolder(holder: GaleriaViewHolder, position: Int) {

        val url = listaImagens[position]

        holder.bind( url)






    }

}