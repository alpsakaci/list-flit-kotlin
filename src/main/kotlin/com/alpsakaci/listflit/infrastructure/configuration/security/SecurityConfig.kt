package com.alpsakaci.listflit.infrastructure.configuration.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
class SecurityConfig(
    @Value("\${spotify.client-id}")
    val spotifyClientId: String,
    @Value("\${spotify.client-secret}")
    val spotifyClientSecret: String
) : WebSecurityConfigurerAdapter() {

    private val publicMatchers = arrayOf(
        "/",
        "/error",
        "/css/**",
        "/js/**",
        "/img/**",
        "/webjars/**"
    )

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests { a -> a
                .antMatchers(*publicMatchers).permitAll()
                .anyRequest().authenticated()
            }
            .oauth2Login()
            .authorizationEndpoint()
            .authorizationRequestResolver(AuthorizationRequestResolver(clientRegistrationRepository(), OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI))
            .and()
            .defaultSuccessUrl("/", true)
            .and()
            .logout{ l -> l
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .csrf()
                .disable()
            }
            .cors()
            .configurationSource(configurationSource())
    }

    fun configurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.addAllowedOrigin("*")
        config.allowCredentials = true
        config.addAllowedHeader("X-Requested-With")
        config.addAllowedHeader("Content-Type")
        config.addAllowedMethod(HttpMethod.POST)
        source.registerCorsConfiguration("/logout", config);

        return source
    }

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        return InMemoryClientRegistrationRepository(spotifyClientRegistration())
    }

    private fun spotifyClientRegistration(): ClientRegistration? {
        return ClientRegistration
            .withRegistrationId("spotify")
            .clientId(spotifyClientId)
            .clientSecret(spotifyClientSecret)
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            // TODO: Add scopes
            .scope("user-library-read", "playlist-modify-public", "playlist-modify-private")
            .authorizationUri("https://accounts.spotify.com/authorize")
            .tokenUri("https://accounts.spotify.com/api/token")
            .userInfoUri("https://api.spotify.com/v1/me")
            .userNameAttributeName("display_name")
            .clientName("spotify")
            .build()
    }
}
