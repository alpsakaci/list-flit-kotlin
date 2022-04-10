package com.alpsakaci.listflit.infrastructure.httpclient.spotify.response

data class GetUserResponse(
    val display_name: String,
    val external_urls: Any,
    val followers: Any,
    val href: String,
    val id: String,
    val images: Any,
    val type: String,
    val uri: String
)
