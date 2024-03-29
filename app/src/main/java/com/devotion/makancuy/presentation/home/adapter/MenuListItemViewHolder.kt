package com.devotion.makancuy.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.devotion.makancuy.data.model.Menu
import com.devotion.makancuy.databinding.ItemMenuListBinding
import com.devotion.makancuy.utils.ViewHolderBinder
import com.devotion.makancuy.utils.toIndonesianFormat


class MenuListItemViewHolder(
    private val binding: ItemMenuListBinding,
    private val listener: OnItemCLickedListener<Menu>
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.ivMenuImage.load(item.imageUrl) {
                crossfade(true)
            }
            binding.tvMenuName.text = it.name
            binding.tvMenuPrice.text = it.price.toIndonesianFormat()
            binding.tvMenuLocation.text = it.locationAddress
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}