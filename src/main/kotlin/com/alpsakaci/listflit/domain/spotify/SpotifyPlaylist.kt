package com.alpsakaci.listflit.domain.spotify

data class SpotifyPlaylist(
    var id: String,
    var href: String,
    var name: String,
    var public: Boolean,
    var collaborative: Boolean,
    var uri: String
)
