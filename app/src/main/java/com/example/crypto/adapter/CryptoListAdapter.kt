package com.example.crypto.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.crypto.databinding.ItemCryptoBinding
import com.example.crypto.model.Data


class CryptoListAdapter :
    PagingDataAdapter<Data, CryptoListAdapter.MyViewHolder>(DataDifferntiator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_crypto, parent, false)
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCryptoBinding.inflate(inflater)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class MyViewHolder(val binding: ItemCryptoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Data) {
            binding.item = item
            binding.textViewName.text = item.name
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
}
