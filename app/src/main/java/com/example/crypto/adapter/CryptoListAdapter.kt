package com.example.crypto.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto.R
import com.example.crypto.databinding.ItemCryptoBinding
import com.example.crypto.model.Data


class CryptoListAdapter(private val setOnCryptoClick: SetOnCryptoClick) :
    PagingDataAdapter<Data, CryptoListAdapter.MyViewHolder>(DataDifferntiator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCryptoBinding.inflate(inflater)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        setOnCryptoClickListener = setOnCryptoClick
        getItem(position)?.let { holder.bind(it) }
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.translate_alpha_anim)
    }

    class MyViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Data) {
            binding.item = item
            binding.name.text = item.name
            binding.symbol.text = item.symbol
            binding.price.text = "Price : $" + String.format("%,f", item.quote?.usd?.price)
            binding.percentChange24h.text = "Percent Change 24h : $" +
                    String.format("%,f", item.quote?.usd?.percentChange24h)
            binding.marketCap.text = "Market Cap : $" +
                    String.format(
                        "%,f", item.quote?.usd?.marketCap
                    )

            binding.cardCrypto.setOnClickListener {
                item.id?.let { it1 ->
                    setOnCryptoClickListener?.onCryptoClick(
                        it1
                    )
                }
            }
            binding.executePendingBindings()
        }
    }

    object DataDifferntiator : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        var setOnCryptoClickListener: SetOnCryptoClick? = null
    }

    //interface for item click

    interface SetOnCryptoClick {
        fun onCryptoClick(id: Int)
    }
}
