package com.alpsakaci.listflit.infrastructure.httpclient.spotify.request

data class CreatePlaylist(
    val name: String,
    val public: Boolean,
    val collaborative: Boolean,
    val description: String
)
