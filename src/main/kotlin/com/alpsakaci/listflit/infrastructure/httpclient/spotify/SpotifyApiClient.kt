package com.alpsakaci.listflit.infrastructure.httpclient.spotify

import com.alpsakaci.listflit.infrastructure.configuration.feign.interceptor.SpotifyApiRequestInterceptor
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.request.CreatePlaylistRequest
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.request.AddTracksRequest
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.response.PlaylistResponse
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.response.GetPlaylistsResponse
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.response.GetUserResponse
import com.alpsakaci.listflit.infrastructure.httpclient.spotify.response.SearchTrackResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    value = "\${feign.client.config.spotify-api.name}",
    url = "\${feign.client.config.spotify-api.url}",
    configuration = [SpotifyApiRequestInterceptor::class]
)
interface SpotifyApiClient {

    @GetMapping("/me")
    fun me(): GetUserResponse

    @GetMapping("/search?type=track&query={query}&limit={limit}")
    fun searchTrack(@PathVariable("query") query: String, @PathVariable("limit") limit: Int = 20): SearchTrackResponse

    @GetMapping("/me/playlists")
    fun getUserPlaylists(): GetPlaylistsResponse

    @PostMapping("/me/playlists")
    fun createPlaylist(@RequestBody playlist: CreatePlaylistRequest): PlaylistResponse

    @PostMapping("/playlists/{playlistId}/tracks")
    fun addTracksToPlaylist(@PathVariable("playlistId") playlistId: String, @RequestBody uris: AddTracksRequest): Any
}
