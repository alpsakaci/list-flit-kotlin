package com.alpsakaci.listflit.infrastructure.httpclient.spotify.response

data class GetPlaylistsResponse(
    val href: String,
    // TODO: any -> playlist item response
    val items: ArrayList<Any>,
    val limit: Int,
    val next: Any?,
    val offset: Int,
    val previous: Any?,
    val total: Int
)
