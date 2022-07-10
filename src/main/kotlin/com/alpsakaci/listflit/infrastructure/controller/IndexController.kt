package com.alpsakaci.listflit.infrastructure.controller

import com.alpsakaci.listflit.application.service.LibraryParseService
import com.alpsakaci.listflit.infrastructure.controller.response.UserResponse
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.view.RedirectView
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/")
class IndexController(
    val libraryParseService: LibraryParseService
) {

    @GetMapping
    fun login(): String {
        return "login"
    }

    @GetMapping("/uploadLibrary")
    fun getUploadLibraryPage(): String {
        return "uploadLibrary"
    }

    @PostMapping("/uploadLibrary")
    fun handleLibraryUpload(@RequestParam("libraryFile") file: MultipartFile, model: Model): String {
        val library = libraryParseService.parseLibrary(file)
		model.addAttribute("playlists", library.playlists);
		model.addAttribute("tracks", library.tracks);

        return "library"
    }

    @GetMapping("/api")
    @Hidden
    fun redirectToSwagger(response: HttpServletResponse): RedirectView {
        response.setHeader("Cache-Control", "no-cache")
        return RedirectView("/swagger-ui/index.html")
    }

    @GetMapping("/user")
    @Hidden
    @Parameter(
        name = "principle",
        hidden = true
    )
    fun getAuthenticatedUser(@AuthenticationPrincipal principle: OAuth2User): UserResponse {
        return UserResponse(principle.name)
    }
}
