package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.props.MinioProperties
import com.dulfinne.configurator.service.ImageService
import com.dulfinne.configurator.util.BucketNames
import com.dulfinne.configurator.util.ExceptionMessages
import com.dulfinne.configurator.util.MinioConstants
import io.minio.BucketExistsArgs
import io.minio.GetObjectArgs
import io.minio.GetPresignedObjectUrlArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import io.minio.SetBucketPolicyArgs
import io.minio.http.Method
import jakarta.persistence.EntityNotFoundException
import org.apache.tika.Tika
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageServiceImpl(val minioClient: MinioClient, val minioProperties: MinioProperties) : ImageService {

    override fun uploadImage(bucketName: String, imageName: String, file: MultipartFile): String {
        if (!checkBucketExists(bucketName)) {
            createBucket(bucketName)
        }

        saveImage(imageName, bucketName, file)
        return getPublicImageUrl(bucketName, imageName)
    }

    override fun uploadImage(bucketName: String, file: MultipartFile): String {
        val name = file.originalFilename ?: throw EntityNotFoundException(ExceptionMessages.FILE_NAME_NOT_FOUND)
        if (!checkBucketExists(bucketName)) {
            createBucket(bucketName)
        }
        saveImage(name, bucketName, file)
        return getPublicImageUrl(bucketName, name)
    }

    override fun uploadOrKeep(bucket: String, textureName: String, newFile: MultipartFile?, oldUrl: String?, needRemovement: Boolean) =
        when {
            newFile != null -> uploadImage(bucket, textureName, newFile)
            needRemovement -> null
            else -> oldUrl
        }

    override fun deleteImage(bucketName: String, imageName: String) {
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(bucketName)
                .`object`(imageName)
                .build()
        )
    }

    override fun renameFile(bucketName: String, newName: String, oldName: String, oldUrl: String?): String? {
        if (oldUrl == null) {
            return oldUrl
        }
        val oldObjectStream = minioClient.getObject(
            GetObjectArgs.builder()
                .bucket(bucketName)
                .`object`(oldName)
                .build()
        )

        val byteArray = oldObjectStream.readBytes()
        minioClient.putObject(
            PutObjectArgs.builder()
                .stream(byteArray.inputStream(), byteArray.size.toLong(), -1)
                .bucket(bucketName)
                .`object`(newName)
                .contentType(getFileType(byteArray))
                .build()
        )
        deleteImage(bucketName, oldName)

        return getPublicImageUrl(bucketName, newName)
    }

    override fun getPublicImageUrl(bucketName: String, imageName: String): String {
        return "${minioProperties.publicUrl}/$bucketName/$imageName"
    }

    override fun getSignedImageUrl(bucketName: String, objectName: String): String {
        val url = minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .`object`(objectName)
                .build()
        )
        return url.toString()
    }

    private fun saveImage(name: String, bucketName: String, file: MultipartFile) {
        val partSize = 5L * 1024 * 1024

        val inputStream = file.inputStream
        minioClient.putObject(
            PutObjectArgs.builder()
                .stream(inputStream, -1, partSize)
                .bucket(bucketName)
                .`object`(name)
                .contentType(file.contentType)
                .build()
        )
    }

    private fun checkBucketExists(bucketName: String): Boolean {
        return minioClient
            .bucketExists(
                BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build()
            )
    }

    private fun createBucket(bucketName: String) {
        minioClient.makeBucket(
            MakeBucketArgs.builder()
                .bucket(bucketName)
                .build()
        )
        applyPublicPolicy(bucketName)
    }

    private fun applyPublicPolicy(bucketName: String) {
        val policyJson = MinioConstants.PUBLIC_POLICY
            .trimIndent()
            .replace(BucketNames.POLICY_GENERATION_BUCKET, bucketName)

        minioClient.setBucketPolicy(
            SetBucketPolicyArgs.builder()
                .bucket(bucketName)
                .config(policyJson)
                .build()
        )
    }

    fun getFileType(byteArray: ByteArray): String {
        val tika = Tika()
        return tika.detect(byteArray)
    }
}