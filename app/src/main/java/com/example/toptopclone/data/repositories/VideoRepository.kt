package com.example.toptopclone.data.repositories

import com.example.toptopclone.R
import javax.inject.Inject

class VideoRepository @Inject constructor() {
    private val videos= listOf(
        R.raw.test,
        R.raw.test2,
        R.raw.test3,
        R.raw.test4,
    )

    fun getVideo() = videos.random()
}