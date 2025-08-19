package com.example.toptopclone.ui.video

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import com.example.toptopclone.designsystem.TopTopVideoPlayer

@Composable
fun VideoDetailScreen(
    videoId: Int,
    viewModel: VideoDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    if(uiState.value == VideoDetailUIState.Default){
        // Loading
        viewModel.handleAction(VideoDetailAction.LoadData(videoId))
    }

    VideoDetailScreen(
        uiState = uiState.value,
        player = viewModel.videoPlayer
    )
}

@Composable
fun VideoDetailScreen(
    uiState: VideoDetailUIState,
    player: Player
){
    when(uiState){
        is VideoDetailUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Loading...")
            }
        }
        is VideoDetailUIState.Success -> {
            VideoDetailScreen(
                player = player
            )
        }
        else ->{

        }
    }
}

@Composable
fun VideoDetailScreen(
    player: Player
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (videoPlayer, sideBar) = createRefs()
        TopTopVideoPlayer(
            modifier = Modifier.constrainAs(videoPlayer) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            player = player
        )
    }
}