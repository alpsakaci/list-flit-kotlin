package com.alpsakaci.listflit.infrastructure.controller

import io.swagger.v3.oas.annotations.Hidden
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
}
