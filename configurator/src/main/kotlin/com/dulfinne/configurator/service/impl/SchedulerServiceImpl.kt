package com.dulfinne.configurator.service.impl

import com.dulfinne.configurator.repository.TextureRepository
import com.dulfinne.configurator.service.ImageService
import com.dulfinne.configurator.service.SchedulerService
import com.dulfinne.configurator.util.BucketNames
import jakarta.transaction.Transactional
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class SchedulerServiceImpl(
    @Value("\${scheduler.link-updating.cron}") val cronExpression: String,
    val textureRepository: TextureRepository,
    val imageService: ImageService
) : SchedulerService {

    private val log: Logger = LoggerFactory.getLogger(SchedulerService::class.java)

    @Override
    @Scheduled(cron = "#{'\${scheduler.link-updating.cron}'}")
    @SchedulerLock(
        name = "urlUpdating",
        lockAtLeastFor = "PT10M", lockAtMostFor = "PT20M"
    )
    @Transactional
    override fun scheduledUrlUpdating() {
        log.info("Processing scheduled url updating. Started.")

        val textures = textureRepository.findAllWithIcons()
        for (texture in textures) {
            val textureName = texture.name

            texture.baseTextureUrl = imageService.getPublicImageUrl(BucketNames.TEXTURE_BUCKET, textureName)
            texture.bumpMapUrl = imageService.getPublicImageUrl(BucketNames.BUMP_MAP_BUCKET, textureName)
            texture.alphaMapUrl = imageService.getPublicImageUrl(BucketNames.ALPHA_MAP_BUCKET, textureName)
            texture.icon.url = imageService.getPublicImageUrl(BucketNames.ICON_BUCKET, textureName)
        }
        textureRepository.saveAll(textures)
    }
}