package com.dev334.idog.util

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import java.io.*

class LruCacheManager(private val context: Context) {
    private val cache: LruCache<String, Bitmap>
    private val cacheFileName = "image_cache"

    init {
        // Initialize the cache with maximum size of 20 images
        val maxCacheSize = 20
        cache = object : LruCache<String, Bitmap>(maxCacheSize) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                // Return the size of the bitmap in bytes (assuming each pixel is 4 bytes)
                return value.byteCount / 1024
            }
        }

        // Load cache from persistent storage
        val cachedImages = loadCacheFromStorage()
        for ((key, bitmap) in cachedImages) {
            cache.put(key, bitmap)
        }
    }

    fun addImageToCache(key: String, bitmap: Bitmap) {
        cache.put(key, bitmap)
        saveCacheToStorage()
    }

    fun getAllImagesInCache(): List<Bitmap> {
        val images = mutableListOf<Bitmap>()
        val snapshot = cache.snapshot()
        val keys = snapshot.keys

        for(key in keys) {
            val bitmap = snapshot[key]
            bitmap?.let {
                images.add(it)
            }
        }

        return images
    }

    private fun saveCacheToStorage() {
        val cacheDir = context.cacheDir
        val cacheFile = File(cacheDir, cacheFileName)
        val outputStream = ObjectOutputStream(FileOutputStream(cacheFile))
        outputStream.writeObject(cache.snapshot())
        outputStream.close()
    }

    private fun loadCacheFromStorage(): HashMap<String, Bitmap> {
        val cacheDir = context.cacheDir
        val cacheFile = File(cacheDir, cacheFileName)
        val cachedImages = HashMap<String, Bitmap>()

        if (cacheFile.exists()) {
            val inputStream = ObjectInputStream(FileInputStream(cacheFile))
            val cachedData = inputStream.readObject() as HashMap<String, Bitmap>
            inputStream.close()

            // Ensure the loaded data is within the cache size limit
            val maxCacheSize = cache.maxSize()
            for ((key, bitmap) in cachedData) {
                if (cachedImages.size >= maxCacheSize) {
                    break
                }
                cachedImages[key] = bitmap
            }
        }

        return cachedImages
    }
}
