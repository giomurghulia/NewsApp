package com.example.newsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newsapp.databinding.FragmentAllNewsBinding
import kotlinx.coroutines.launch

class AllNewsFragment : Fragment() {
    private val viewModel: AllNewsViewModel by viewModels()

    private lateinit var binding: FragmentAllNewsBinding
    private val myAdapter = MyAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentAllNewsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainRecycleView.layoutManager =
            GridLayoutManager(context, 2)
        binding.mainRecycleView.adapter = myAdapter

        viewModel.getNews()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsState.collect {
                    binding.loaderProgressBar.visibility = View.GONE
                    when (it) {
                        is Resource.Success -> {
                            myAdapter.submitList(it.items)
                            Toast.makeText(context, "Success Add Items", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {
                            binding.loaderProgressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        myAdapter.setCallBack(object : MyAdapter.CallBack {
            override fun onItemClick(itemId: String) {
                viewModel.onItemClick(itemId)
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.openDetailFragment.collect() { item ->
                findNavController().navigate(
                    AllNewsFragmentDirections.actionAllNewsFragmentToNewsFragment(item)
                )
            }
        }
    }
}