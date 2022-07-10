package com.alpsakaci.listflit.infrastructure.controller

import com.alpsakaci.listflit.domain.applemusic.AppleMusicPlaylist
import com.alpsakaci.listflit.domain.applemusic.AppleMusicTrack
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
class IndexController {

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
        model.addAttribute("playlists", listOf(
            AppleMusicPlaylist(1, "testPlaylist1", null, hashMapOf(
                Pair(1, AppleMusicTrack(1, "testName1", "testArtist1", "testAlbum1")),
                Pair(2, AppleMusicTrack(2, "testName2", "testArtist2", "testAlbum2")),
                Pair(3, AppleMusicTrack(3, "testName3", "testArtist3", "testAlbum3"))
            ))
        ))
        model.addAttribute("tracks", listOf(
            AppleMusicTrack(1, "testName1", "testArtist1", "testAlbum1"),
            AppleMusicTrack(2, "testName2", "testArtist2", "testAlbum2"),
            AppleMusicTrack(3, "testName3", "testArtist3", "testAlbum3")
        ))

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
