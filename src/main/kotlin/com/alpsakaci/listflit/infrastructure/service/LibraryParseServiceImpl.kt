package com.alpsakaci.listflit.infrastructure.service

import com.alpsakaci.listflit.application.service.LibraryParseService
import com.alpsakaci.listflit.domain.applemusic.AppleMusicLibrary
import com.alpsakaci.listflit.domain.applemusic.AppleMusicPlaylist
import com.alpsakaci.listflit.domain.applemusic.AppleMusicTrack
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.lang.Exception
import javax.xml.parsers.DocumentBuilderFactory

@Service
class LibraryParseServiceImpl: LibraryParseService {

    private fun getTracksNode(file: MultipartFile): Node? {
        try {
            val documentBuilderFactory = DocumentBuilderFactory.newInstance()
            val documentBuilder = documentBuilderFactory.newDocumentBuilder()
            val document = documentBuilder.parse(file.inputStream)
            val root: Element = document.documentElement
            root.normalize()
            val nodes: NodeList = root.childNodes
            val rootDict: Node = nodes.item(1)
            val dictNodes: NodeList = rootDict.childNodes

            for (i in 0 until dictNodes.length) {
                val currentNode: Node = dictNodes.item(i)
                if(currentNode.nodeType == Node.ELEMENT_NODE && currentNode.textContent.equals("Tracks")) {
                    return dictNodes.item(i + 2)
                }
            }
        } catch (e: Exception) {
            return null
        }

        return null
    }

    private fun createTrack(trackNode: Node): AppleMusicTrack {
        var id: Int = -1
        var name = ""
        var artist = ""
        var album = ""
        val trackInfo = trackNode.childNodes

        for (i in 0 until trackInfo.length) {
            val field: Node = trackInfo.item(i)
            if (field.nodeType == Node.ELEMENT_NODE) {
                when (field.textContent) {
                    "Track ID" -> {
                        id = Integer.parseInt(trackInfo.item(i + 1).textContent)
                    }
                    "Name" -> {
                        name = trackInfo.item(i + 1).textContent
                    }
                    "Artist" -> {
                        artist = trackInfo.item(i + 1).textContent
                    }
                    "Album" -> {
                        album = trackInfo.item(i + 1).textContent
                    }
                }
            }
        }

        return AppleMusicTrack(id, name, artist, album)
    }

    private fun getLibraryTracks(file: MultipartFile): HashMap<Int, AppleMusicTrack> {
        val tracksMap = HashMap<Int, AppleMusicTrack>()
        val trackNodes = getTracksNode(file)?.childNodes

        trackNodes?.let {
            for (i in 0 until trackNodes.length) {
                val currentNode = trackNodes.item(i)
                if(currentNode.nodeType == Node.ELEMENT_NODE && currentNode.nodeName.equals("dict")) {
                    val track = createTrack(currentNode)
                    tracksMap[track.id] = track
                }
            }
        }

        return tracksMap
    }

    private fun getPlaylistsNode(file: MultipartFile): Node? {
        try {
            val documentBuilderFactory = DocumentBuilderFactory.newInstance()
            val documentBuilder = documentBuilderFactory.newDocumentBuilder()
            val document = documentBuilder.parse(file.inputStream)
            val root: Element = document.documentElement
            root.normalize()
            val nodes: NodeList = root.childNodes
            val rootDict: Node = nodes.item(1)
            val dictNodes = rootDict.childNodes

            for (i in 0 until rootDict.childNodes.length) {
                val currentNode: Node = dictNodes.item(i)
                if (currentNode.nodeType == Node.ELEMENT_NODE && currentNode.textContent.equals("Playlists")) {
                    return dictNodes.item(i + 2)
                }
            }
        } catch (e: Exception) {
            return null
        }

        return null
    }

    private fun createPlaylist(playlistNode: Node, tracks: HashMap<Int, AppleMusicTrack>): AppleMusicPlaylist {
        var id: Int = -1
        var name = ""
        var description: String? = null
        val items = HashMap<Int, AppleMusicTrack>()

        val playlists: NodeList = playlistNode.childNodes
        var playlistItems: NodeList? = null

        for (i in 0 until playlists.length) {
            val field: Node = playlists.item(i)

            if (field.nodeType == Node.ELEMENT_NODE) {
                when (field.textContent) {
                    "Playlist ID" -> {
                        id = Integer.parseInt(playlists.item(i + 1).textContent)
                    }
                    "Name" -> {
                        name = playlists.item(i + 1).textContent
                    }
                    "Playlist Items" -> {
                        playlistItems = playlists.item(i + 2).childNodes
                    }
                }
            }
        }

        val playlist = AppleMusicPlaylist(id, name, description, items)

        playlistItems?.let {
            for (i in 0 until playlistItems?.length) {
                val dict = playlistItems.item(i).childNodes
                for (j in 0 until dict.length) {
                    val field = dict.item(j)
                    if (field.nodeType == Node.ELEMENT_NODE) {
                        when (field.textContent) {
                            "Track ID" -> {
                                val trackId = Integer.parseInt(dict.item(j + 1).textContent)
                                val track: AppleMusicTrack? = tracks.get(trackId)
                                track?.let {
                                    playlist.items[track.id] = track
                                }
                            }
                        }
                    }
                }
            }
        }

        return playlist
    }

    private fun getPlaylists(file: MultipartFile, tracks: HashMap<Int, AppleMusicTrack>): List<AppleMusicPlaylist> {
        val playlists = mutableListOf<AppleMusicPlaylist>()
        val playlistNodes = getPlaylistsNode(file)?.childNodes
        playlistNodes?.let {
            for (i in 0 until playlistNodes.length) {
                val currentNode = playlistNodes.item(i)
                if (currentNode.nodeType == Node.ELEMENT_NODE && currentNode.nodeName.equals("dict")) {
                    playlists.add(createPlaylist(currentNode, tracks))
                }
            }
        }

        return playlists
    }

    override fun parseLibrary(file: MultipartFile): AppleMusicLibrary {
        val libraryTracks = getLibraryTracks(file)
        val playlists = getPlaylists(file, libraryTracks)

        return AppleMusicLibrary(
            playlists = playlists,
            tracks = libraryTracks.map {
                it.value
            }
        )
    }
}
