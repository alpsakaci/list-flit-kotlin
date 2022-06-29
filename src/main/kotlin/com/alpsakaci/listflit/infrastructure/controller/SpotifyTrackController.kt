package com.alpsakaci.listflit.infrastructure.controller

import com.alpsakaci.listflit.infrastructure.httpclient.spotify.SpotifyApiClient
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
@Tag(name = "Tracks")
class SpotifyTrackController(val spotifyApiClient: SpotifyApiClient) {

    @GetMapping("/tracks")
    @Operation(summary = "Search track.")
    fun searchTrack(@RequestParam("query") query: String, @RequestParam("limit", defaultValue = "3") limit: Int): Any {
        return spotifyApiClient.searchTrack(query, limit)
    }

}
