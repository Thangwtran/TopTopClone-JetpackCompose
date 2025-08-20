package com.example.toptopclone.ui.foru

import android.util.Log
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toptopclone.ui.video.VideoDetailScreen
import com.example.toptopclone.ui.video.VideoDetailViewModel

@Composable
fun ListForUVideoScreen(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { 10 })
    VerticalPager(
        state = pagerState
    ) { page ->
        Log.d("ListForUVideoScreen", "ListForUVideoScreen: $page")
        val viewModel: VideoDetailViewModel = hiltViewModel(key = page.toString())
        VideoDetailScreen(videoId = page, viewModel = viewModel)
    }
}