package com.example.toptopclone.ui.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.Player
import com.example.toptopclone.designsystem.TopTopVideoPlayer
import com.example.toptopclone.ui.video.composables.SideBarView
import com.example.toptopclone.ui.video.composables.VideoInfoArea

@Composable
fun VideoDetailScreen(
    videoId: Int,
    viewModel: VideoDetailViewModel
) {
    val uiState = viewModel.uiState.collectAsState()

    if(uiState.value == VideoDetailUIState.Default){
        // Loading
        viewModel.handleAction(VideoDetailAction.LoadData(videoId))
    }

    VideoDetailScreen(
        uiState = uiState.value,
        player = viewModel.videoPlayer,
    ){ action ->
        viewModel.handleAction(action)
    }
}

@Composable
fun VideoDetailScreen(
    uiState: VideoDetailUIState,
    player: Player,
    handleAction:(VideoDetailAction) -> Unit
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
                player = player,
                handleAction = handleAction
            )
        }
        else ->{

        }
    }
}

@Composable
fun VideoDetailScreen(
    player: Player,
    handleAction:(VideoDetailAction) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize().clickable(
            onClick = {
                handleAction(VideoDetailAction.ToggleVideo)
            }
        )
    ) {
        val (videoPlayer, sideBar,videoInfo) = createRefs()
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

        SideBarView(
            onAvatarClick = {},
            onLikeClick = {},
            onCommentClick = {},
            onShareClick = {},
            onSaveClick = {},
            modifier = Modifier.constrainAs(sideBar) {
                bottom.linkTo(parent.bottom, margin = 16.dp)
                end.linkTo(parent.end, margin = 16.dp)
                height = Dimension.fillToConstraints
            }
        )

        VideoInfoArea(
            accountName = "ThangTT",
            videoName = "Clone TopTop",
            hasTags = listOf("#jetpack compose", "#android","#tiktok"),
            songName = "Making my way",
            modifier = Modifier.constrainAs(videoInfo) {
                start.linkTo(parent.start,margin = 16.dp)
                bottom.linkTo(sideBar.bottom)
                end.linkTo(sideBar.start)
                width = Dimension.fillToConstraints
            }
        )
    }
}