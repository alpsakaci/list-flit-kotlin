package com.alpsakaci.listflit.application.command

import com.alpsakaci.listflit.domain.applemusic.AppleMusicPlaylist
import com.trendyol.kediatr.Command

data class MigratePlaylistFromAppleMusicToSpotifyCommand(
    val appleMusicPlaylist: AppleMusicPlaylist
): Command
