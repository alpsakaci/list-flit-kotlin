package com.alpsakaci.listflit.infrastructure.httpclient.spotify.response

data class SpotifyTrack(
    val artists: Any,
    val external_urls: Any,
    val href: String,
    val id: String,
    val name: String,
    val preview_url: String?,
    val uri: String
)
