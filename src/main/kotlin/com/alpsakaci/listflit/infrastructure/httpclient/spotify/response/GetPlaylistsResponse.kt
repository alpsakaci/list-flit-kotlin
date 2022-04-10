package com.alpsakaci.listflit.infrastructure.httpclient.spotify.response

data class GetPlaylistsResponse(
    val href: String,
    val items: ArrayList<PlaylistResponse>,
    val limit: Int,
    val next: Any?,
    val offset: Int,
    val previous: Any?,
    val total: Int
)
