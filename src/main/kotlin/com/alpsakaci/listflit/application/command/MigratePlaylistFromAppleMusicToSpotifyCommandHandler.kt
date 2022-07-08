package com.alpsakaci.listflit.application.command

import com.alpsakaci.listflit.application.query.GetSpotifyTrackQuery
import com.alpsakaci.listflit.application.util.Partition
import com.alpsakaci.listflit.domain.applemusic.AppleMusicTrack
import com.alpsakaci.listflit.domain.spotify.SpotifyTrack
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.SpotifyApiClient
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.request.AddTracksRequest
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.request.CreatePlaylistRequest
import com.trendyol.kediatr.CommandBus
import com.trendyol.kediatr.CommandHandler
import org.springframework.stereotype.Component

@Component
class MigratePlaylistFromAppleMusicToSpotifyCommandHandler(
    val spotifyApiClient: SpotifyApiClient,
    val commandBus: CommandBus
) : CommandHandler<MigratePlaylistFromAppleMusicToSpotifyCommand> {

    override fun handle(command: MigratePlaylistFromAppleMusicToSpotifyCommand) {
        val createPlaylistRequest = CreatePlaylistRequest(
            name = command.appleMusicPlaylist.name,
            public = true,
            collaborative = false,
            description =  command.appleMusicPlaylist.description
        )
        val spotifyPlaylist = spotifyApiClient.createPlaylist(createPlaylistRequest)
        val spotifyPlaylistItems: MutableList<SpotifyTrack> = mutableListOf()

        command.appleMusicPlaylist.items.forEach{ (id, track) ->
            val query = createGetSpotifyTrackQuery(track)
            val spotifyTrack = commandBus.executeQuery(query)

            spotifyTrack ?.let {
                spotifyPlaylistItems.add(spotifyTrack)
            }

        }

        val addTracksRequests = createAddTracksRequests(spotifyPlaylistItems)

        for (addTracksRequest in addTracksRequests) {
            spotifyApiClient.addTracksToPlaylist(spotifyPlaylist.id, addTracksRequest)
        }
    }

    private fun createGetSpotifyTrackQuery(track: AppleMusicTrack): GetSpotifyTrackQuery {
        return GetSpotifyTrackQuery(track.name+ " " + track.artist + " " + track.album)
    }

    private fun convertSpotifyTracksToAddTracksRequest(spotifyTracks: List<SpotifyTrack>): AddTracksRequest {
        val uris: ArrayList<String> = ArrayList(spotifyTracks.size)
        spotifyTracks.forEach{
            uris.add(it.uri)
        }
        return AddTracksRequest(uris)
    }

    private fun createAddTracksRequests(spotifyPlaylistItems: List<SpotifyTrack>): List<AddTracksRequest> {
        val requests = mutableListOf<AddTracksRequest>()

        if (spotifyPlaylistItems.size > 100) {
            val partitions = Partition.ofSize(spotifyPlaylistItems, 100)
            for (partition in partitions) {
                val request = convertSpotifyTracksToAddTracksRequest(partition as List<SpotifyTrack>)
                requests.add(request)
            }

            return requests
        }

        val request = convertSpotifyTracksToAddTracksRequest(spotifyPlaylistItems)
        requests.add(request)

        return requests
    }
}
