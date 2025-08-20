package com.example.toptopclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.toptopclone.ui.MainScreen
import com.example.toptopclone.ui.following.FollowingScreen
import com.example.toptopclone.ui.foru.ListForUVideoScreen
import com.example.toptopclone.ui.theme.TopTopCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TopTopCloneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    ListForUVideoScreen()
//                    FollowingScreen()
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun DemoPager() {
    val colors = listOf(
        Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta, Color.Cyan
    )
    val pagerState = rememberPagerState(pageCount = { 10 })
    VerticalPager(pagerState) { page ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(colors[page]),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "Page: $page",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
    }
}

@Composable
@Preview
private fun DemoPagerPreview() {
    TopTopCloneTheme {
        DemoPager()
    }
}