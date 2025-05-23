package com.dulfinne.configurator.config

import com.dulfinne.configurator.props.MinioProperties
import io.minio.MinioClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class MinioConfig(val minioProperties: MinioProperties) {

    @Bean
    fun minioClient(): MinioClient = MinioClient.builder()
        .endpoint(minioProperties.url)
        .credentials(
            minioProperties.accessKey,
            minioProperties.secretKey
        )
        .build()
}
