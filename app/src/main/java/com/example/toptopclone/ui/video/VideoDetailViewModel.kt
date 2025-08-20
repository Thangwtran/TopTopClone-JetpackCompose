package com.example.toptopclone.ui.video

import android.util.Log
import androidx.annotation.OptIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.toptopclone.data.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    val videoPlayer: ExoPlayer,
    val videoRepository: VideoRepository,
) : ViewModel() {

    init {
        Log.d("VideoDetailViewModel", "init: ")
        videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
        videoPlayer.playWhenReady = true
        videoPlayer.prepare()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("VideoDetailViewModel", "release: ")
        videoPlayer.release()
    }

    private var _uiState = MutableStateFlow<VideoDetailUIState>(VideoDetailUIState.Default)
    val uiState: StateFlow<VideoDetailUIState>
        get() = _uiState

    fun handleAction(action: VideoDetailAction) {
        when (action) {
            is VideoDetailAction.LoadData -> {
                val videoId = action.id
                loadVideo(videoId)
            }

            is VideoDetailAction.ToggleVideo -> {
                toggleVideo()
            }

        }
    }

    private fun toggleVideo() {
        if (videoPlayer.isLoading) {

        } else {
//            if (videoPlayer.isPlaying) {
//                videoPlayer.pause()
//            } else {
//                videoPlayer.play()
//            }
        videoPlayer.playWhenReady = !videoPlayer.playWhenReady

        }
    }

    private fun loadVideo(videoId: Int) {
        _uiState.value = VideoDetailUIState.Loading
        viewModelScope.launch {
            delay(100L)
            val videoRes = videoRepository.getVideo()
            playVideo(videoRes)
            _uiState.value = VideoDetailUIState.Success
        }
    }

    @OptIn(UnstableApi::class)
    private fun playVideo(videoResId: Int) {
        val videoUri = RawResourceDataSource.buildRawResourceUri(videoResId)
        val mediaItem = MediaItem.fromUri(videoUri)
        videoPlayer.setMediaItem(mediaItem)
        videoPlayer.play()
    }

    private fun onDispose(){
        videoPlayer.release()
    }


}

// MVVM MVI
sealed interface VideoDetailUIState {
    object Default : VideoDetailUIState
    object Loading : VideoDetailUIState
    object Success : VideoDetailUIState
    data class Error(val message: String) : VideoDetailUIState
}

sealed class VideoDetailAction {
    data class LoadData(val id: Int) : VideoDetailAction()
    object ToggleVideo : VideoDetailAction()
}