package com.alpsakaci.listflit.infrastructure.controller

import com.alpsakaci.listflit.infrastructure.httpclient.spotify.SpotifyApiClient
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.request.AddTracksRequest
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.request.CreatePlaylistRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1")
@Tag(name = "Playlists")
class SpotifyPlaylistController(val spotifyApiClient: SpotifyApiClient) {

    @PostMapping("/playlists")
    @Operation(summary = "Create playlist")
    fun createPlaylist(@RequestBody playlist: CreatePlaylistRequest): Any {
        return spotifyApiClient.createPlaylist(playlist)
    }

    @PostMapping("/playlists/{playlistId}/add-tracks")
    @Operation(summary = "Add tracks to playlist.")
    fun addTracksToPlaylist(@PathVariable("playlistId") playlistId: String, @RequestBody uris: AddTracksRequest): Any {
        return spotifyApiClient.addTracksToPlaylist(playlistId,uris)
    }
}
