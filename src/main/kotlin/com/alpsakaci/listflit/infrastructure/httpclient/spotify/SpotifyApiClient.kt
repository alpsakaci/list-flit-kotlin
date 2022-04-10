package com.alpsakaci.listflit.infrastructure.httpclient.spotify

import com.alpsakaci.listflit.infrastructure.httpclient.spotify.request.CreatePlaylist
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.request.TrackURIs
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.response.GetPlaylistsResponse
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.response.GetUserResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    value = "\${feign.client.config.spotify-api.name}",
    url = "\${feign.client.config.spotify-api.url}"
)
interface SpotifyApiClient {

    @GetMapping("/me")
    fun me(): GetUserResponse

    @GetMapping("/me/playlists")
    fun getUserPlaylists(): GetPlaylistsResponse

    @PostMapping("/me/playlists")
    fun createPlaylist(playlist: CreatePlaylist): Any

    @PostMapping("/playlists/{playlistId}/tracks")
    fun addTracksToPlaylist(@PathVariable("playlistId") playlistId: String, uris: TrackURIs): Any

    @GetMapping("/search?type=track&query={query}")
    fun searchTrack(@PathVariable("query") query: String): Any

}
