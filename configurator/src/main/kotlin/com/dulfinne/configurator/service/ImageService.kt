package com.dulfinne.configurator.service

import org.springframework.web.multipart.MultipartFile

interface ImageService {
    fun uploadImage(bucketName: String, imageName: String, file: MultipartFile): String
    fun deleteImage(bucketName: String, imageName: String)
    fun getPublicImageUrl(bucketName: String, imageName: String): String
    fun getSignedImageUrl(bucketName: String, objectName: String): String
}