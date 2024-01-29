package com.example.zrecenzuj

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.zrecenzuj.ui.theme.ZrecenzujTheme

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZrecenzujTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val movieId = intent.getIntExtra("movieId", -1)
                    ThirdScreen(this, movieId)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdScreen(context: Context, movieId: Int) {
    val viewModel: MovieViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "MovieViewModel",
        MovieViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val movies by viewModel.moviesState.collectAsStateWithLifecycle()
    val item = movies.find {it.id == movieId}
    var titleText by remember { mutableStateOf("") }
    var pointsText by remember { mutableStateOf("") }

    if (item != null && titleText.isEmpty() && titleText.isEmpty()){
        titleText = item.title
        pointsText = item.points.toString()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier
                .padding(15.dp),
            text = "Edytuj recenzję",
            fontSize = 30.sp,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier
                .padding(15.dp),
            value = titleText,
            onValueChange = { newText -> titleText = newText },
            placeholder = { Text("Edytuj tytuł") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier
                .padding(15.dp),
            value = pointsText,
            onValueChange = { newText -> pointsText = newText },
            placeholder = { Text("Edytuj punkty") }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = {
                viewModel.addMovie(Movie(0, titleText, pointsText.toDouble()))
                viewModel.deleteMovie(item!!)
                context.startActivity(Intent(context, MainActivity::class.java))},
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333))
        ) {
            Text(
                text = "Zapisz zmiany",
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}