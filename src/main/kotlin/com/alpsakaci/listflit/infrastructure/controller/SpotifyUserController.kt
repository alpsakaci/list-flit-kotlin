package com.alpsakaci.listflit.infrastructure.controller

import com.alpsakaci.listflit.infrastructure.httpclient.spotify.SpotifyApiClient
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
@Tag(name = "User")
class SpotifyUserController(val spotifyApiClient: SpotifyApiClient) {

    @GetMapping("/me")
    @Operation(summary = "Get user info.")
    fun user(): Any {
        return spotifyApiClient.me()
    }

    @GetMapping("/me/playlists")
    @Operation(summary = "Get user playlists.")
    fun getUserPlaylists(): Any {
        return spotifyApiClient.getUserPlaylists()
    }
}
