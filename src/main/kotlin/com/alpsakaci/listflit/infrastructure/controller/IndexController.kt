package com.alpsakaci.listflit.infrastructure.controller

import com.alpsakaci.listflit.infrastructure.controller.response.UserResponse
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/")
class IndexController {

    @GetMapping
    @Hidden
    fun redirectToSwagger(response: HttpServletResponse): RedirectView {
        response.setHeader("Cache-Control", "no-cache")
        return RedirectView("/swagger-ui/index.html")
    }

    @GetMapping("/user")
    @Parameter(
        name = "principle",
        hidden = true
    )
    fun getAuthenticatedUser(@AuthenticationPrincipal principle: OAuth2User): UserResponse {
        return UserResponse(principle.name)
    }
}
