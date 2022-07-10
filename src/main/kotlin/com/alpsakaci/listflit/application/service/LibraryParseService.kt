package com.alpsakaci.listflit.application.service

import com.alpsakaci.listflit.domain.applemusic.AppleMusicLibrary
import org.springframework.web.multipart.MultipartFile

interface LibraryParseService {
    fun parseLibrary(file: MultipartFile): AppleMusicLibrary
}
