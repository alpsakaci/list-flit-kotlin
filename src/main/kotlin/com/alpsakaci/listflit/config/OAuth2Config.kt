package com.alpsakaci.listflit.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod

@Configuration
class OAuth2Config(val spotifyConfig: SpotifyConfig): WebSecurityConfigurerAdapter() {

    private val PUBLIC_MATCHERS = arrayOf(
            "/",
            "/css/**",
            "/js/**",
            "/img/**")

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .cors().and().csrf().disable()
                .authorizeRequests().antMatchers(*PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
    }

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository? {
        return InMemoryClientRegistrationRepository(spotifyClientRegistration())
    }

    private fun spotifyClientRegistration(): ClientRegistration? {
        return ClientRegistration
                .withRegistrationId("spotify")
                .clientId(spotifyConfig.clientId)
                .clientSecret(spotifyConfig.clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("user-library-read", "playlist-modify-public", "playlist-modify-private")
                .authorizationUri("https://accounts.spotify.com/authorize")
                .tokenUri("https://accounts.spotify.com/api/token")
                .userInfoUri("https://api.spotify.com/v1/me")
                .userNameAttributeName("display_name")
                .clientName("spotify")
                .build()
    }
}
