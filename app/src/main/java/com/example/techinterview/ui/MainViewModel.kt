package com.example.techinterview.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.techinterview.domain.model.TVShow
import com.example.techinterview.domain.model.TVShowsHolder
import com.example.techinterview.domain.model.TVShowsListHolder
import com.example.techinterview.domain.wrapper.RepositoryResponseWrapper
import com.example.techinterview.usecase.LoadTvShowsUseCase
import com.example.techinterview.util.LiveDataEvent
import com.example.techinterview.util.postEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val loadTvShowsUseCase: LoadTvShowsUseCase) : ViewModel() {

    private val _uiEventLiveData: MutableLiveData<LiveDataEvent<MainUiEvent>> = MutableLiveData()
    val uiLiveData: LiveData<LiveDataEvent<MainUiEvent>> get() = _uiEventLiveData

    @Suppress("UNCHECKED_CAST")
    fun load(){
        viewModelScope.launch(Dispatchers.IO){
            when(val result = loadTvShowsUseCase.loadTvShows()){
                is RepositoryResponseWrapper.Error -> {
                    _uiEventLiveData.postEvent(MainUiEvent.ErrorEvent(result.message))
                }
                is RepositoryResponseWrapper.Success<*> -> {
                    (result.data as? List<TVShow>)?.also {
                        _uiEventLiveData.postEvent(MainUiEvent.TvShowsListEvent((it)))
                    }
                }
            }

        }
    }
}