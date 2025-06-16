package com.dulfinne.configurator.service

import org.springframework.web.multipart.MultipartFile

interface ImageService {
    fun uploadImage(bucketName: String, imageName: String, file: MultipartFile): String
    fun uploadImage(bucketName: String, file: MultipartFile): String
    fun uploadOrKeep(bucket: String, textureName: String, newFile: MultipartFile?, oldUrl: String?, needRemovement: Boolean): String?
    fun deleteImage(bucketName: String, imageName: String)
    fun getPublicImageUrl(bucketName: String, imageName: String): String
    fun renameFile(bucketName: String, newName: String, oldName: String, oldUrl: String?): String?
}