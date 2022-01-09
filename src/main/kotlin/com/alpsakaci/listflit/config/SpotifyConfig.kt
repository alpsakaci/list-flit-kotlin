package com.alpsakaci.listflit.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class SpotifyConfig {
    @Value("\${spotify.client.id}")
    val clientId: String? = null

    @Value("\${spotify.client.secret}")
    val clientSecret: String? = null
}
