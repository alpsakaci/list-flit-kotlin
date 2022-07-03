package com.alpsakaci.listflit.domain.applemusic

data class AppleMusicPlaylist(
    val id: Int,
    val name: String,
    val description: String?,
    val items: HashMap<Int, AppleMusicTrack>
)
