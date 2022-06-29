package com.alpsakaci.listflit.domain.spotify

import com.fasterxml.jackson.annotation.JsonProperty

data class SpotifyPlaylistSnapshot(
    @JsonProperty("snapshot_id") val snapshotId: String
)
