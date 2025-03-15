package com.dlucci.baseapplication.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dlucci.baseapplication.model.SampleResponse
import com.dlucci.baseapplication.viewmodel.SampleState
import com.dlucci.baseapplication.viewmodel.SampleViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SampleScreen(navController: NavController) {
    val viewModel : SampleViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        if(uiState is SampleState.Error) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = (uiState as SampleState.Error).message,
                    duration = SnackbarDuration.Indefinite
                )
            }
        }
    }

    Scaffold(
        content = {
            Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
                when (uiState) {
                    is SampleState.RemoteSuccess -> {
                        val successState = uiState as SampleState.RemoteSuccess
                        SampleList(sampleData = successState.sample, navController = navController)
                    }

                    is SampleState.LocalSuccess -> {
                        val successState = uiState as SampleState.LocalSuccess
                        SampleList(sampleData = successState.sample, navController = navController)
                    }

                    is SampleState.Loading -> {
                        val loadingState = uiState as SampleState.Loading
                        Loading(loadingState.isLoading)
                    }

                    is SampleState.Error -> {
                        val errorState = uiState as SampleState.Error
                        SampleList(sampleData = errorState.sample, navController = navController)
                    }
                }

                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomCenter))

            }
        }
    )
}

@Composable
fun Loading(isLoading: Boolean) {
    if (isLoading) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Loading...")
        }
    }
}

@Composable
fun SampleList(sampleData : List<SampleResponse>, navController: NavController) {

    LazyColumn {
        items(sampleData) {
            SampleRow(sampleResponse = it, navController = navController)
        }
    }

}

@Composable
fun SampleRow(sampleResponse: SampleResponse, navController: NavController) {

    Column(
        modifier = Modifier
            .clickable(
                onClick = {
                    //navController.navigate(Routes new route)
                }
            )
            .background(
                color = Color(0xFFFFFFFF)
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Row {
            AsyncImage(
                model = "https://www.espn.com/nba/player/_/id/4600663/zach-edey",
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

        }
        Text(
            text = sampleResponse.foo ?: "",
            color = Color.Black,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic
        )
        Row {
            Text(
                text = sampleResponse.foo ?: "",
                fontSize = 20.sp,
                color = Color.Black,
            )
        }
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
    }

}
