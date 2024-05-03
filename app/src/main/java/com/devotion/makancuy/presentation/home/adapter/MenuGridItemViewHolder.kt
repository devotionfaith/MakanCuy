package com.devotion.makancuy.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.databinding.ItemMenuGridBinding
import com.devotion.makancuy.utils.ViewHolderBinder

class MenuGridItemViewHolder(
    private val binding: ItemMenuGridBinding,
    private val itemClick: (Menu) -> Unit,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.ivMenuImage.load(item.imageUrl) {
                crossfade(true)
            }
            binding.tvMenuName.text = it.name
            binding.tvMenuPrice.text = it.formattedprice
            itemView.setOnClickListener {
                itemClick(item)
            }
        }
    }
}
