package com.example.zrecenzuj

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.ui.Modifier
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.zrecenzuj.ui.theme.ZrecenzujTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZrecenzujTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(this)
                }
            }
        }
    }
}

@Composable
fun MainScreen(context: Context) {
    val viewModel: MovieViewModel = viewModel(
        LocalViewModelStoreOwner.current!!,
        "MovieViewModel",
        MovieViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val movies by viewModel.moviesState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Moja punktacja",
            fontSize = 30.sp,
            color = Color.Black,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0f)
        ) {
            items(movies.size) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable {
                            editItem(context, movies[it])
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = movies[it].title,
                            fontSize = 22.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Start
                        )
                        Text(
                            text = movies[it].points.toString(),
                            fontSize = 22.sp,
                            color = Color.Black,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
        Button(
            onClick = {
                context.startActivity(Intent(context, SecondActivity::class.java))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333333))
        ) {
            Text(
                text = "Dodaj punktację",
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}
fun editItem(context: Context, movie: Movie){
    val intent = Intent(context, ThirdActivity::class.java).apply {
        putExtra("movieId", movie.id)
    }
    context.startActivity(intent)
}