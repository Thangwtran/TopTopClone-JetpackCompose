package com.example.toptopclone.ui.video.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.toptopclone.R
import com.example.toptopclone.ui.theme.TopTopCloneTheme

@Composable
fun AvatarView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ConstraintLayout(modifier = Modifier.clickable { onClick() }) {
        val (imgAvatar, addIcon) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.ic_dog),
            contentDescription = "icon avatar",
            modifier = Modifier
                .size(48.dp)
                .background(shape = CircleShape, color = Color.White)
                .border(width = 2.dp, color = Color.White, shape = CircleShape)
                .clip(CircleShape)
                .constrainAs(imgAvatar) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = Color.Red,
                    shape = CircleShape
                )
                .constrainAs(addIcon) {
                    top.linkTo(imgAvatar.bottom)
                    start.linkTo(imgAvatar.start)
                    end.linkTo(imgAvatar.end)
                    bottom.linkTo(imgAvatar.bottom)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "add icon",
                tint = Color.White,
                modifier = Modifier.size(12.dp)
            )
        }

    }
}

@Composable
@Preview
private fun AvatarPreview() {
    TopTopCloneTheme {
        AvatarView(modifier = Modifier, onClick = {})
    }
}

@Composable
fun AudioTrackView(
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            repeatMode = RepeatMode.Restart,
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            )
        )
    )
    Image(
        painter = painterResource(R.drawable.ic_audio_track),
        contentDescription = "audio track",
        modifier = modifier
            .size(30.dp)
            .rotate(rotate)
    )
}

@Preview
@Composable
private fun AudioTrackViewPreview() {
    TopTopCloneTheme {
        AudioTrackView(modifier = Modifier)
    }
}

@Composable
fun VideoAttractiveInfoItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    desc: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(id = icon),
            contentDescription = "icon",
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = desc,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
        )
    }
}

@Preview
@Composable
private fun VideoAttractiveInfoItemPreview() {
    TopTopCloneTheme {
        VideoAttractiveInfoItem(
            icon = R.drawable.ic_heart,
            desc = "1M",
            onClick = {}
        )
    }
}

@Composable
fun SideBarView(
    modifier: Modifier = Modifier,
    onAvatarClick: () -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(
        modifier = modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarView {
            onAvatarClick()
        }

        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_heart, desc = "1.5M") {
            onLikeClick()
        }

        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_chat, desc = "8160") {
            onCommentClick()
        }

        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_bookmark, desc = "99.6K") {
            onSaveClick()
        }

        Spacer(modifier = Modifier.height(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_share, desc = "91K") {
            onShareClick()
        }

        Spacer(modifier = Modifier.height(16.dp))
        AudioTrackView()
    }
}

@Preview
@Composable
private fun SideBarPreview(){
    TopTopCloneTheme {
        SideBarView(
            onAvatarClick = {},
            onLikeClick = {},
            onCommentClick = {},
            onShareClick = {},
            onSaveClick = {}
        )
    }
}