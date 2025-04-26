package com.mmj.movieapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mmj.movieapp.domain.model.Movie
import com.mmj.movieapp.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    // PagingData<Movie> is the type of data being collected
    private val _moviesState: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(value = PagingData.empty())
    val moviesState: StateFlow<PagingData<Movie>> get() = _moviesState

    init {
        onEvent(HomeEvent.GetHome)
    }

    fun onEvent(event: HomeEvent) {
        viewModelScope.launch {
            when (event) {
                is HomeEvent.GetHome -> {
                    getMovies()
                }
                // Handle other events if needed in the future
                else -> {}
            }
        }
    }

    private suspend fun getMovies() {
        // Collect the paging data and update the state
        getMoviesUseCase.execute(Unit)
            .distinctUntilChanged()  // Ensure the state updates only when the data changes
            .cachedIn(viewModelScope)  // Cache the data in the ViewModel scope for lifecycle awareness
            .collectLatest { pagingData ->
                // Update the state with the latest PagingData
                _moviesState.value = pagingData
            }
    }
}

sealed class HomeEvent {
    object GetHome : HomeEvent()
}

data class HomeState(
    val movies: PagingData<Movie> = PagingData.empty()  // Use PagingData here instead of a list
)
