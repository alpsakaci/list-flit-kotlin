package com.alpsakaci.listflit

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class ListFlitApplication : CommandLineRunner {

    override fun run(vararg args: String?) {
    }
}

fun main(args: Array<String>) {
    runApplication<ListFlitApplication>(*args)
}
