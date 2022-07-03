package com.alpsakaci.listflit.application.query

import com.alpsakaci.listflit.domain.spotify.SpotifyTrack
import com.trendyol.kediatr.Query

data class GetSpotifyTrackQuery(
    val searchTerm: String
): Query<SpotifyTrack?>
