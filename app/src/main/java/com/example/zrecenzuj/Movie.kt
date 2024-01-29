package com.example.zrecenzuj

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val points: Double
)