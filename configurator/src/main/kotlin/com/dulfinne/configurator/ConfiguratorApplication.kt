package com.dulfinne.configurator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ConfiguratorApplication

fun main(args: Array<String>) {
    runApplication<ConfiguratorApplication>(*args)
}
