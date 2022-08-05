package com.example.newsapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.LayoutNewsBinding

class MyAdapter : ListAdapter<Content, MyAdapter.ProductViewHolder>(MyDiffUtil()) {
    private var callBack: CallBack? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            LayoutNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    inner class ProductViewHolder(
        private val binding: LayoutNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Content) {
            binding.newsTitleText.text = item.titleKA
            binding.newsDateText.text = item.publish_date

            Glide
                .with(binding.root.context)
                .load(item.cover)
                .placeholder(R.drawable.ic_news_cover)
                .into(binding.newsCoverImage)

            binding.newsCardCardView.setOnClickListener{
                callBack?.onItemClick(item.id)
            }
        }
    }

    interface CallBack {
        fun onItemClick(itemId: String)
    }
}