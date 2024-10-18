package com.example.biddexnewsapp.presentation.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.biddexnewsapp.databinding.LayoutNewsBinding
import com.example.biddexnewsapp.domain.entity.NewEntity

class NewsAdapter(private val onClick: (newEntity: NewEntity) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewEntityViewHolder>() {

    private val newsList = mutableListOf<NewEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewEntityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutNewsBinding.inflate(layoutInflater, parent, false)
        return NewEntityViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NewEntityViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount() = newsList.size

    fun setData(list: List<NewEntity>) {
        newsList.clear()
        newsList.addAll(list)
        notifyDataSetChanged()
    }


    inner class NewEntityViewHolder(private val binding: LayoutNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NewEntity) {
            with(binding){
                name.text = item.author
                newsTitle.text = item.title
                newsDescription.text = item.description
                Glide.with(newsImage.context)
                    .load(item.image)
                    .into(newsImage)
            }
        }
    }

}