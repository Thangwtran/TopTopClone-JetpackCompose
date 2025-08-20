package com.example.toptopclone.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.toptopclone.R
import com.example.toptopclone.ui.comment.CommentScreen
import com.example.toptopclone.ui.following.FollowingScreen
import com.example.toptopclone.ui.foru.ListForUVideoScreen
import com.example.toptopclone.ui.theme.TopTopCloneTheme
import com.example.toptopclone.ui.user.ProfileScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val pagerState = rememberPagerState(initialPage = 1, pageCount = {
        3
    })
    val coroutineScope = rememberCoroutineScope()

    val scrollToPage: (Boolean) -> Unit = { isForU ->
        val index = if (isForU) 1 else 0
        coroutineScope.launch {
            pagerState.animateScrollToPage(index)
        }
    }

    var isShowTabContent by remember {
        mutableStateOf(true)
    }

    val toggleTabContent = { isShow: Boolean ->
        if (isShowTabContent != isShow) {
            isShowTabContent = isShow
        }
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page == 2) {
                // hide tab content
                toggleTabContent(false)
            } else {
                // show tab content
                toggleTabContent(true)
            }
        }
    }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    var currentVideoId by remember {
        mutableIntStateOf(-1)
    }

    val showCommentBottomSheet: (Int) -> Unit = {
        currentVideoId = it
        coroutineScope.launch {
            sheetState.show()
        }
    }

    val hideCommentBottomSheet: () -> Unit = {
        currentVideoId = -1
        coroutineScope.launch {
            sheetState.hide()
        }
    }


    if(showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            if(currentVideoId != -1){
                CommentScreen(
                    videoId = currentVideoId,
                    hideBottomSheet = {
                        showBottomSheet = false
                    }
                )
            }
        }
    }

    Scaffold(bottomBar = {
        AnimatedVisibility(visible = isShowTabContent) {
            TopTopBottomAppbar(
                onOpenHome = {

                },
                onAddVideo = {

                }
            )
        }

    }) { paddingValues ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val (tabContent, body) = createRefs()
            HorizontalPager(
                modifier = Modifier.constrainAs(body) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
                state = pagerState
            ) { page ->
                when (page) {
                    0 -> FollowingScreen()
                    2 -> ProfileScreen()
                    else -> ListForUVideoScreen()
                }
            }

            AnimatedVisibility(visible = isShowTabContent) {
                TabContentView(
                    isTabSelectedIndex = pagerState.currentPage,
                    onSelectedTab = { isForU ->
                        scrollToPage(isForU)
                    },
                    modifier = Modifier.constrainAs(tabContent) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )
            }

        }

    }
}

@Composable
fun TopTopBottomAppbar(
    onOpenHome: () -> Unit,
    onAddVideo: () -> Unit,
) {
    NavigationBar(
        containerColor =  Color.Black,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { onOpenHome() },
            icon = {
                Icon(painterResource(id = R.drawable.ic_home), contentDescription = "Home")
            },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO */ },
            icon = {
                Icon(painterResource(id = R.drawable.ic_now), contentDescription = "Friends")
            },
            label = { Text("Friends") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { onAddVideo() },
            icon = {
                Icon(painterResource(id = R.drawable.ic_add_video), contentDescription = "Add video")
            },
        )

        NavigationBarItem(
            selected = false,
            onClick = { /* TODO */ },
            icon = {
                Icon(painterResource(id = R.drawable.ic_inbox), contentDescription = "Inbox")
            },
            label = { Text("Inbox") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { /* TODO */ },
            icon = {
                Icon(painterResource(id = R.drawable.ic_profile), contentDescription = "Profile")
            },
            label = { Text("Profile") }
        )

    }
}

@Composable
fun TabContentItemView(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    isForU: Boolean,
    onSelected: (isForU: Boolean) -> Unit
) {
    val alpha = if (isSelected) 1f else 0.5f
    Column(
        modifier = modifier
            .wrapContentSize()
            .clickable { onSelected(isForU) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = alpha)))
        Spacer(modifier = Modifier.height(8.dp))
        if (isSelected) {
            Divider(color = Color.White, thickness = 2.dp, modifier = Modifier.width(24.dp))
        }
    }
}

@Composable
fun TabContentView(
    modifier: Modifier = Modifier,
    isTabSelectedIndex: Int,
    onSelectedTab: (isForU: Boolean) -> Unit,
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (tabContent, imgSearch) = createRefs()
        Row(
            modifier = Modifier
                .wrapContentSize()
//                .background(color = Color.Red)
                .constrainAs(tabContent) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
            TabContentItemView(
                title = "Following",
                isSelected = isTabSelectedIndex == 0,
                isForU = false,
                onSelected = {
                    onSelectedTab(it)
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
            TabContentItemView(
                title = "For You",
                isSelected = isTabSelectedIndex == 1,
                isForU = true,
                onSelected = {
                    onSelectedTab(it)
                }
            )
        }
        IconButton(onClick = {}, modifier = Modifier.constrainAs(imgSearch) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end, margin = 16.dp)
        }) {
            Icon(
                Icons.Default.Search,
                contentDescription = "icon search",
                tint = Color.White,
                modifier = modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
private fun TabContentViewPreview() {
    TopTopCloneTheme {
        TabContentView(isTabSelectedIndex = 1, onSelectedTab = {})
    }
}

@Preview
@Composable
fun TabContentItemViewPreviewSelected() {
    TopTopCloneTheme {
        TabContentItemView(
            title = "Following",
            isSelected = true,
            isForU = true,
            onSelected = {}
        )
    }
}

@Preview
@Composable
fun TabContentItemViewPreviewUnSelected() {
    TopTopCloneTheme {
        TabContentItemView(
            title = "Following",
            isSelected = false,
            isForU = false,
            onSelected = {}
        )
    }
}

