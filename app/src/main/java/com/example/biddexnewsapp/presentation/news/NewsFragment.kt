package com.example.biddexnewsapp.presentation.news

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.biddexnewsapp.R
import com.example.biddexnewsapp.data.utils.handlePagingResponse
import com.example.biddexnewsapp.databinding.FragmentNewsBinding
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.utils.DataState
import com.example.biddexnewsapp.domain.utils.NetworkException
import com.example.biddexnewsapp.presentation.base.BaseFragment
import com.example.biddexnewsapp.presentation.news.adapter.NewsAdapter
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

    private val viewModel: NewsViewModel by viewModels()

    private val newsAdapter by lazy {
        NewsAdapter(::onNewClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsObserver()
        handlePagingResponse()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar.layoutToolbar, getString(R.string.news), null)
        initRv()
        handleSwipe()
    }

    private fun handleSwipe() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = false
            newsAdapter.refresh()
        }
    }

    private fun initRv() {
        binding.homeRecyclerView.apply {
            adapter = newsAdapter
            setHasFixedSize(true)
        }
    }

    private fun newsObserver() {
        lifecycleScope.launch {
            viewModel.newsResponse.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collectLatest {
                newsAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun handlePagingResponse(){
        lifecycleScope.launch {
            newsAdapter.loadStateFlow.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collectLatest {
                when(it.refresh){
                    is LoadState.Loading -> {
                        binding.homeProgressBar.isVisible = true
                    }
                    is LoadState.Error -> {
                        binding.homeProgressBar.isVisible = false
                        val error = (it.refresh as LoadState.Error).error
                        val state = DataState.Error(error)
                        state.applyCommonSideEffects()
                    }
                    is LoadState.NotLoading -> {
                        binding.homeProgressBar.isVisible = false
                    }
                }
            }
        }
    }

    private fun onNewClicked(newEntity: NewEntity) {
        findNavController().navigate(
            NewsFragmentDirections.actionToNewDetailsFragment(Gson().toJson(newEntity))
        )
    }

}