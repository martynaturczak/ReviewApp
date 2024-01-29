package com.example.zrecenzuj

import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    val allMovies: Flow<List<Movie>> = movieDao.getMovies()

    suspend fun delete(movie: Movie){
        movieDao.delete(movie)
    }

    suspend fun insert(movie: Movie){
        movieDao.addMovie(movie)
    }
}