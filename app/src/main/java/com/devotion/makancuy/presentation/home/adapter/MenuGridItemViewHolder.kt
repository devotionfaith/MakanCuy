package com.devotion.makancuy.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.databinding.ItemMenuGridBinding
import com.devotion.makancuy.utils.ViewHolderBinder
import com.devotion.makancuy.utils.toIndonesianFormat

class MenuGridItemViewHolder(
    private val binding: ItemMenuGridBinding,
    private val listener: OnItemCLickedListener<Menu>
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.ivMenuImage.load(item.imageurl) {
                crossfade(true)
            }
            binding.tvMenuName.text = it.name
            binding.tvMenuPrice.text = it.price.toIndonesianFormat()
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}