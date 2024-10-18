package com.example.biddexnewsapp.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.biddexnewsapp.domain.entity.NewEntity
import com.example.biddexnewsapp.domain.usecase.GetNewsUseCase
import com.example.biddexnewsapp.domain.utils.DataState
import com.example.biddexnewsapp.domain.utils.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _newsResponse = MutableStateFlow<DataState<List<NewEntity>>>(DataState.Idle)
    val newsResponse  = _newsResponse.asStateFlow()

    private fun getNews() {
        viewModelScope.launch(ioDispatcher) {
            _newsResponse.emit(DataState.Loading)
            try {
                val news = getNewsUseCase()
                _newsResponse.emit(DataState.Success(news))
            }
            catch (e: Exception)  {
                _newsResponse.emit(DataState.Error(e))
            }
        }
    }
}