package com.alpsakaci.listflit.infrastructure.controller

import com.alpsakaci.listflit.infrastructure.httpclient.spotify.SpotifyApiClient
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.request.AddTracksRequest
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.request.CreatePlaylistRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/spotify")
class SpotifyController(val spotifyApiClient: SpotifyApiClient) {

    @GetMapping("/user")
    fun user(): Any {
        return spotifyApiClient.me()
    }

    @GetMapping("/search-track/{query}")
    fun searchTrack(@PathVariable("query") query: String): Any {
        return spotifyApiClient.searchTrack(query, 3)
    }

    @GetMapping("/playlists")
    fun getUserPlaylists(): Any {
        return spotifyApiClient.getUserPlaylists()
    }

    @PostMapping("/playlists")
    fun createPlaylist(@RequestBody playlist: CreatePlaylistRequest): Any {
        return spotifyApiClient.createPlaylist(playlist)
    }

    @PostMapping("/playlists/{playlistId}/tracks")
    fun addTracksToPlaylist(@PathVariable("playlistId") playlistId: String, @RequestBody uris: AddTracksRequest): Any {
        return spotifyApiClient.addTracksToPlaylist(playlistId,uris)
    }
}
