package com.devotion.makancuy.presentation.common.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.devotion.makancuy.R
import com.devotion.makancuy.data.model.Cart
import com.devotion.makancuy.databinding.ItemCartBinding
import com.devotion.makancuy.databinding.ItemCartCheckoutBinding
import com.devotion.makancuy.utils.ViewHolderBinder
import com.devotion.makancuy.utils.doneEditing
import com.devotion.makancuy.utils.toIndonesianFormat

class CartListAdapter(private val cartListener: CartListener? = null) :
    RecyclerView.Adapter<ViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(
                oldItem: Cart,
                newItem: Cart
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.menuId == newItem.menuId
            }

            override fun areContentsTheSame(
                oldItem: Cart,
                newItem: Cart
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    fun submitData(data: List<Cart>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (cartListener != null) CartViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), cartListener
        ) else CartCheckoutViewHolder(
            ItemCartCheckoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Cart>).bind(dataDiffer.currentList[position])
    }

}

class CartViewHolder(
    private val binding: ItemCartBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivMenuImage.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvAmount.text = item.itemQuantity.toString()
            tvMenuName.text = item.menuName
            tvMenuPrice.text = (item.itemQuantity * item.menuPrice).toIndonesianFormat()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etvNotes.setText(item.itemNotes)
        binding.etvNotes.doneEditing {
            binding.etvNotes.clearFocus()
            val newItem = item.copy().apply {
                itemNotes = binding.etvNotes.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: Cart) {
        with(binding) {
            cvSubstract.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
            cvAdd.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
            ivDelete.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
        }
    }
}

class CartCheckoutViewHolder(
    private val binding: ItemCartCheckoutBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivMenuImage.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvQuantity.text =
                itemView.rootView.context.getString(
                    R.string.total_quantity,
                    item.itemQuantity.toString()
                )
            tvMenuName.text = item.menuName
            tvMenuPrice.text =  item.menuPrice.toIndonesianFormat()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.tvNotes.text = item.itemNotes
    }

}


interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}