package com.alpsakaci.listflit.infrastructure.httpclient.spotify.response

data class ItemsResponse<T>(
    val href: String?,
    val items: ArrayList<T>,
    val limit: Int,
    val next: String?,
    val offset: Int,
    val previous: Any?,
    val total: Int
)
