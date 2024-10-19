package com.example.biddexnewsapp.presentation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.biddexnewsapp.presentation.utils.loadImageFromUrl
import com.example.biddexnewsapp.databinding.LayoutNewsBinding
import com.example.biddexnewsapp.domain.entity.NewEntity

class NewsAdapter(private val onClick: (newEntity: NewEntity) -> Unit) :
    PagingDataAdapter<NewEntity, NewsAdapter.NewsViewHolder>(NewsComparator) {

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class NewsViewHolder(private val binding: LayoutNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewEntity) {
            with(binding){
                tvName.text = item.author
                tvTitle.text = item.title
                tvTime.text = item.publishedAt
                tvDescription.text = item.description
                ivImage.loadImageFromUrl(item.image)
            }
            itemView.rootView.setOnClickListener {
                onClick.invoke(item)
            }
        }
    }

    object NewsComparator : DiffUtil.ItemCallback<NewEntity>() {
        override fun areItemsTheSame(oldItem: NewEntity, newItem: NewEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewEntity, newItem: NewEntity): Boolean {
            return oldItem == newItem
        }
    }
}
