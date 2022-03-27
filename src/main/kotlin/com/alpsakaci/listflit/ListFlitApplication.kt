package com.alpsakaci.listflit

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class ListFlitApplication : CommandLineRunner {
    override fun run(vararg args: String?) {
        var restTemplate = RestTemplate()
        val url: String = "https://my-json-server.typicode.com/typicode/demo/profile"
        println(url)
        val response = restTemplate.getForObject(url, Any::class.java)
        val res = restTemplate.getForEntity(url, Map::class.java)
        println(response)
        println(res.body?.get("name"))
    }
}

fun main(args: Array<String>) {
    runApplication<ListFlitApplication>(*args)
}
