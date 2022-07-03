package com.alpsakaci.listflit.application.model.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.RuntimeException

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class NotFoundException(): RuntimeException() {
}
