package com.alpsakaci.listflit.infrastructure.httpclient.spotify.response

import com.alpsakaci.listflit.domain.spotify.SpotifyTrack
import com.fasterxml.jackson.annotation.JsonProperty

data class SearchTrackResponse(
    @JsonProperty("tracks") val tracks: ItemsResponse<SpotifyTrack>
)
