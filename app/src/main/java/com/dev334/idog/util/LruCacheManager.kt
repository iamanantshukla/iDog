package com.dev334.idog.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import com.jakewharton.disklrucache.DiskLruCache
import java.io.File
import java.io.IOException

class LruCacheManager(context: Context) {

    private val MAX_DISK_CACHE_SIZE = 40 * 1024 * 1024 // 40MB
    private val DISK_CACHE_SUBDIR = "image_cache"
    private var diskCache: DiskLruCache
    private var keys = ArrayList<String>()

    companion object {
        private var instance: LruCacheManager? = null

        fun getInstance(context: Context): LruCacheManager {
            if (instance == null) {
                instance = LruCacheManager(context)
            }
            return instance!!
        }
    }

    init {
        val diskCacheDir = getDiskCacheDir(context, DISK_CACHE_SUBDIR)
        try {
            diskCache = DiskLruCache.open(diskCacheDir, 1, 1, MAX_DISK_CACHE_SIZE.toLong())
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }

        // Populating keys array with existing keys in disk cache
        val cacheDir = diskCache.directory
        val files = cacheDir.listFiles()
        if (files != null) {
            for (file in files) {
                val key = file.name
                if(key=="journal"){
                    continue
                }
                keys.add(key.dropLast(2))
            }
        }
    }

    fun addImageToCache(bitmap: Bitmap) {
        if(keys.size==20){
            val key = keys[0]
            keys.remove(key);
            diskCache.remove(key);
        }
        val key = System.currentTimeMillis().toString()

        val diskCacheKey = getDiskCacheKey(key)

        try {
            val editor = diskCache.edit(diskCacheKey)
            if(editor!=null) {
                editor.newOutputStream(0).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    editor.commit()
                    keys.add(diskCacheKey)
                }
            }
        } catch (e: IOException) {
            Log.e("LruCacheManagerDebugger", "addImageToCache: "+e.message)
        }
    }

    fun getAllImages(): MutableList<Bitmap> {
        val images = mutableListOf<Bitmap>()
        // Retrieve images from disk cache
        val diskCacheIterator = diskCache.directory.listFiles()?.iterator()
        while (diskCacheIterator!!.hasNext()) {
            val snapshot = diskCacheIterator.next()
            val inputStream = snapshot.inputStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            if (bitmap != null) {
                images.add(bitmap)
            }
        }
        return images
    }

    fun clearCache() {
        // Close the disk cache
        diskCache.close()

        // Clear disk cache directory
        val cacheDir = diskCache.directory
        deleteCacheDir(cacheDir)

        // Re-create disk cache
        diskCache = DiskLruCache.open(cacheDir, 1, 1, MAX_DISK_CACHE_SIZE.toLong())

        // Clear keys list
        keys.clear()
    }

    private fun deleteCacheDir(cacheDir: File) {
        val files = cacheDir.listFiles()
        if (files != null) {
            for (file in files) {
                file.delete()
            }
        }
    }

    private fun getDiskCacheDir(context: Context, subdir: String): File {
        val cachePath = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
            !Environment.isExternalStorageRemovable()) {
            context.externalCacheDir?.path ?: context.cacheDir.path
        } else {
            context.cacheDir.path
        }
        return File(cachePath + File.separator + subdir)
    }

    private fun getDiskCacheKey(key: String): String {
        // Convert the key to a valid disk cache key
        return key.hashCode().toString()
    }
}