package com.example.zrecenzuj

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : ViewModel() {

    private val repository: MovieRepository
    private val _moviesState = MutableStateFlow<List<Movie>>(emptyList())
    val moviesState: StateFlow<List<Movie>>
        get() = _moviesState

    init {
        val db = MovieDatabase.getDatabase(application)
        val dao = db.movieDao()
        repository = MovieRepository(dao)

        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            repository.allMovies.collect { movies ->
                _moviesState.value = movies
            }
        }
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            repository.delete(movie)
        }
    }

    fun addMovie(movie: Movie) {
        viewModelScope.launch {
            repository.insert(movie)
        }
    }
}