package com.alpsakaci.listflit.application.query

import com.alpsakaci.listflit.domain.spotify.SpotifyTrack
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.SpotifyApiClient
import com.trendyol.kediatr.QueryHandler
import feign.FeignException
import org.springframework.stereotype.Component

@Component
class GetSpotifyTrackQueryHandler(
    private val spotifyApiClient: SpotifyApiClient
): QueryHandler<GetSpotifyTrackQuery, SpotifyTrack?> {

    override fun handle(query: GetSpotifyTrackQuery): SpotifyTrack? {
        try {
            val searchTrackResponse = spotifyApiClient.searchTrack(query.searchTerm, 1)
            if (searchTrackResponse.tracks.items.isNotEmpty()) {
                return searchTrackResponse.tracks.items[0]
            }
        } catch (e: FeignException.NotFound) {
            println("Track not found. Search Term: ${query.searchTerm}")
        }

        return null
    }
}
