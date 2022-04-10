package com.alpsakaci.listflit.infrastructure.configuration.security

import org.springframework.security.crypto.keygen.Base64StringKeyGenerator
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.servlet.http.HttpServletRequest

class AuthorizationRequestResolver(
    clientRegistrationRepository: ClientRegistrationRepository,
    authorizationRequestBaseUri: String
) : OAuth2AuthorizationRequestResolver {

    private var defaultResolver: OAuth2AuthorizationRequestResolver

    init {
        defaultResolver = DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, authorizationRequestBaseUri)
    }

    private val secureKeyGenerator = Base64StringKeyGenerator(Base64.getUrlEncoder().withoutPadding(), 96)

    override fun resolve(request: HttpServletRequest?): OAuth2AuthorizationRequest? {
        val req: OAuth2AuthorizationRequest? = defaultResolver.resolve(request)
        return customizeAuthorizationRequest(req)
    }

    override fun resolve(request: HttpServletRequest, clientRegistrationId: String): OAuth2AuthorizationRequest? {
        val req: OAuth2AuthorizationRequest = defaultResolver.resolve(request, clientRegistrationId)
        return customizeAuthorizationRequest(req)
    }

    private fun customizeAuthorizationRequest(req: OAuth2AuthorizationRequest?): OAuth2AuthorizationRequest? {
        if (req == null) return null
        val attributes = HashMap(req?.attributes)
        val additionalParameters = HashMap(req?.additionalParameters)
        addPkceParameters(attributes, additionalParameters)

        return OAuth2AuthorizationRequest.from(req)
            .attributes(attributes)
            .additionalParameters(additionalParameters)
            .build()
    }

    private fun addPkceParameters(attributes: MutableMap<String, Any>, additionalParameters: MutableMap<String, Any>) {
        val codeVerifier = secureKeyGenerator.generateKey()
        attributes[PkceParameterNames.CODE_VERIFIER] = codeVerifier
        try {
            val codeChallenge: String = createHash(codeVerifier)
            additionalParameters[PkceParameterNames.CODE_CHALLENGE] = codeChallenge
            additionalParameters[PkceParameterNames.CODE_CHALLENGE_METHOD] = "S256"
        } catch (e: NoSuchAlgorithmException) {
            additionalParameters[PkceParameterNames.CODE_CHALLENGE] = codeVerifier
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun createHash(value: String): String {
        val md: MessageDigest = MessageDigest.getInstance("SHA-256")
        val digest: ByteArray = md.digest(value.byteInputStream(StandardCharsets.US_ASCII).readAllBytes())

        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
    }
}
