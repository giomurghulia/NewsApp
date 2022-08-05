package com.example.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = NewsFragmentArgs.fromBundle(requireArguments()).item

        Glide
            .with(binding.root.context)
            .load(item.cover)
            .placeholder(R.drawable.ic_news_cover)
            .into(binding.newsCoverImage)

        binding.newsTitleText.text = item.titleKA
        binding.newsDescriptionText.text = item.descriptionKA
        binding.newsPublishDateText.text = item.publish_date
    }
}