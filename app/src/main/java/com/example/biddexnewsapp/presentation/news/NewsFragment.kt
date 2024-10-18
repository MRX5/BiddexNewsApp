package com.example.biddexnewsapp.presentation.news

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.biddexnewsapp.R
import com.example.biddexnewsapp.databinding.FragmentNewsBinding
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.utils.DataState
import com.example.biddexnewsapp.domain.utils.NetworkException
import com.example.biddexnewsapp.presentation.base.BaseFragment
import com.example.biddexnewsapp.presentation.news.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
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
    }

    override fun onViewCreated() {
        initRv()
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
                when(it) {
                    is DataState.Loading -> {
                        binding.homeProgressBar.isVisible = true
                    }
                    is DataState.Error -> {
                        binding.homeProgressBar.isVisible = false
                        //Toast.makeText(requireContext(), it.throwable.message, Toast.LENGTH_SHORT).show()
                        Log.i("mostafa", "error:${it.throwable}")
                    }
                    is DataState.Success -> {
                        Log.i("mostafa", "success:${it.data}")
                        newsAdapter.setData(it.data)
                        binding.homeProgressBar.isVisible =  false
                    }
                    else -> {}
                }
            }
        }
    }

    private fun onNewClicked(newEntity: NewEntity) {

    }

}