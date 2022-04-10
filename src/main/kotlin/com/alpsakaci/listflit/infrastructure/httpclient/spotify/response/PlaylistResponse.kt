package com.alpsakaci.listflit.infrastructure.httpclient.spotify.response

data class PlaylistResponse(
    var id: String,
    var href: String,
    var name: String,
    var public: Boolean,
    var collaborative: Boolean,
    var uri: String
)
