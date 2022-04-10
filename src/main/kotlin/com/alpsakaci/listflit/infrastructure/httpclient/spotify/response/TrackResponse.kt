package com.alpsakaci.listflit.infrastructure.httpclient.spotify.response

data class TrackResponse(
    val href: String?,
    val items: ArrayList<SpotifyTrack>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: Any?,
    val total: Int
)
