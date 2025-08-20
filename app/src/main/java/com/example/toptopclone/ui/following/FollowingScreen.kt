package com.example.toptopclone.ui.following

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import com.example.toptopclone.R
import com.example.toptopclone.designsystem.TopTopVideoPlayer
import com.example.toptopclone.ui.theme.TopTopCloneTheme
import kotlin.math.absoluteValue

@Composable
fun FollowingScreen() {

    val pagerState = rememberPagerState(pageCount = { 10 })

    val cardWidth = (LocalConfiguration.current.screenWidthDp * 2 / 3) - 24

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.Black
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Trending Creator",
            style = MaterialTheme.typography.displaySmall.copy(color = Color.White)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Follow an account to see their latest videos here.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            pageSize = PageSize.Fixed(cardWidth.dp),
            pageSpacing = 16.dp,
            contentPadding = PaddingValues(
                start = 24.dp
            )
        ) { page ->
            Card(
                modifier = Modifier
                    .width(cardWidth.dp)
                    .aspectRatio(0.7f)
                    .graphicsLayer {

                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        // We animate the alpha, between 50% and 100%
                        scaleY = lerp(
                            start = 0.7f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .clip(RoundedCornerShape(16.dp))
            ) {
                CreatorCard(
                    name = "ThangTT $page",
                    nickname = "ThangTT $page",
                    modifier = Modifier.fillMaxSize(),
                    onFollow = { },
                    onClose = { }
                )
            }
        }
    }

}

@OptIn(UnstableApi::class)
@Composable
fun CreatorCard(
    modifier: Modifier = Modifier,
    name: String,
    nickname: String,
    onFollow: () -> Unit,
    onClose: () -> Unit
) {

    val player = ExoPlayer.Builder(LocalContext.current).build()
    player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
    player.playWhenReady = true
    player.prepare()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
    ) {
        val (
            videoIntro,
            btnClose,
            btnFollow,
            imgAvatar,
            tvName,
            tvNickName
        ) = createRefs()

        TopTopVideoPlayer(player = player, modifier = modifier.constrainAs(videoIntro) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        })

        IconButton(
            onClick = { onClose() }, modifier = Modifier
                .constrainAs(btnClose) {
                    top.linkTo(parent.top, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .size(16.dp)) {
            Icon(
                imageVector = Icons.Sharp.Close,
                contentDescription = null,
            )
        }

        Button(
            onClick = { onFollow() }, modifier = Modifier
                .constrainAs(btnFollow) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
                .padding(horizontal = 56.dp, vertical = 12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xffe94359),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Follow", style = MaterialTheme.typography.bodyMedium)
        }

        Text(
            text = nickname,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
            modifier = Modifier.constrainAs(tvNickName) {
                bottom.linkTo(btnFollow.top, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

            }
        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
            modifier = Modifier.constrainAs(tvName) {
                bottom.linkTo(tvNickName.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        AvatarFollowing(modifier = Modifier.constrainAs(imgAvatar) {
            bottom.linkTo(tvName.top, margin = 16.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }

    val uri = RawResourceDataSource.buildRawResourceUri(R.raw.test4)
    val mediaItem = MediaItem.fromUri(uri)
    player.setMediaItem(mediaItem)
    SideEffect {
        player.play()
    }
}

@Composable
fun AvatarFollowing(
    modifier: Modifier = Modifier
) {
    val sizeAvatar = LocalConfiguration.current.screenWidthDp * 0.2
    Image(
        painter = painterResource(R.drawable.img_avatar_toptop),
        contentDescription = null,
        modifier = modifier
            .size(sizeAvatar.dp)
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .border(
                color = Color.White,
                width = 2.dp,
                shape = CircleShape
            )
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
@Preview
fun AvatarFollowingPreview() {
    TopTopCloneTheme {
        AvatarFollowing()
    }
}