package com.devotion.makancuy.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.devotion.makancuy.data.model.Category
import com.devotion.makancuy.databinding.ItemCategoryBinding
import com.devotion.makancuy.utils.ViewHolderBinder

class CategoryItemViewHolder(
    private val binding: ItemCategoryBinding,
    private val itemClick: (Category) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {
    override fun bind(item: Category) {
        item.let {
            binding.ivCategoryImage.load(item.imageurl) {
                crossfade(true)
            }
            binding.tvCategoryName.text = item.name
            itemView.setOnClickListener{itemClick(item)}
        }
    }
}