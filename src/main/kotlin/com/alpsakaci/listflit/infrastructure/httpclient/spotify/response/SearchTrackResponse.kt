package com.alpsakaci.listflit.infrastructure.httpclient.spotify.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SearchTrackResponse(
    @JsonProperty("tracks") val tracks: TrackResponse
)
