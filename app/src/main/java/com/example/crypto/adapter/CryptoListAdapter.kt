package com.example.crypto.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto.databinding.ItemCryptoBinding
import com.example.crypto.model.Data
import java.text.NumberFormat
import java.util.*


class CryptoListAdapter(val setOnCryptoClick: SetOnCryptoClick) :
    PagingDataAdapter<Data, CryptoListAdapter.MyViewHolder>(DataDifferntiator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCryptoBinding.inflate(inflater)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        setOnCryptoClickListener = setOnCryptoClick
        getItem(position)?.let { holder.bind(it) }
    }

    class MyViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {

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

            binding.cardCrypto.setOnClickListener(View.OnClickListener{ item.id?.toInt()?.let { it1 ->
                setOnCryptoClickListener?.onCryptoClick(
                    it1
                )
            } })
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
    interface SetOnCryptoClick {
        fun onCryptoClick(id: Int)
    }
}
