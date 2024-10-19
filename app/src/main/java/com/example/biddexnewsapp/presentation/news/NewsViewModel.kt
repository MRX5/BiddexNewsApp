package com.example.biddexnewsapp.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.usecase.GetNewsUseCase
import com.example.biddexnewsapp.domain.utils.DataState
import com.example.biddexnewsapp.domain.utils.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val ioDispatcher: CoroutineContext
): ViewModel() {

    init {
        getNews()
    }

    private val _newsResponse =
        MutableStateFlow<PagingData<NewEntity>>(PagingData.empty())
    val newsResponse = _newsResponse.asStateFlow()


    fun getNews() {
        viewModelScope.launch(ioDispatcher) {
            getNewsUseCase().flow.cachedIn(viewModelScope).collectLatest {
                _newsResponse.value = it
            }
        }
    }
}