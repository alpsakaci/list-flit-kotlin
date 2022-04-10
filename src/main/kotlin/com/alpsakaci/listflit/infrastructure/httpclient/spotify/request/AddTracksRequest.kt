package com.alpsakaci.listflit.infrastructure.httpclient.spotify.request

import com.fasterxml.jackson.annotation.JsonProperty

data class AddTracksRequest(
    @JsonProperty("uris") val uris: ArrayList<String>
)
