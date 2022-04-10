package com.alpsakaci.listflit.infrastructure.controller

import com.alpsakaci.listflit.infrastructure.httpclient.spotify.SpotifyApiClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/spotify")
class SpotifyController(val spotifyApiClient: SpotifyApiClient) {

    @GetMapping("/user")
    fun user(): Any {
        return spotifyApiClient.me()
    }

    @GetMapping("/playlists")
    fun getUserPlaylists(): Any {
        return spotifyApiClient.getUserPlaylists()
    }

    @GetMapping("/search-track/{query}")
    fun searchTrack(@PathVariable("query") query: String): Any {
        return spotifyApiClient.searchTrack(query)
    }
}
