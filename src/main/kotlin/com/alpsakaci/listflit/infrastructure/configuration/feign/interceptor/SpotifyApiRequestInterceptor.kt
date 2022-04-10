package com.alpsakaci.listflit.infrastructure.configuration.feign.interceptor

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Component

@Component
@Lazy
class SpotifyApiRequestInterceptor(
    var clientService: OAuth2AuthorizedClientService
) : RequestInterceptor {

    override fun apply(requestTemplate: RequestTemplate) {
        val authentication = SecurityContextHolder.getContext().authentication
        val oauthToken = authentication as OAuth2AuthenticationToken
        val clientRegistrationId = oauthToken.authorizedClientRegistrationId
        lateinit var accessToken: String
        if (clientRegistrationId.equals("spotify")) {
            val client = clientService.loadAuthorizedClient<OAuth2AuthorizedClient>(clientRegistrationId, oauthToken.name)
            accessToken = client.accessToken.tokenValue
        }

        requestTemplate.header("Authorization", "Bearer $accessToken")
    }
}
