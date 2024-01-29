package com.example.zrecenzuj

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table ORDER BY title ASC")
    fun getMovies(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)
}
