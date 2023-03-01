package com.example.feb28.roomDB


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GalleryDetail::class], version = 1)
abstract class GalleryDatabase :RoomDatabase() {
    abstract fun galleryDao(): GalleryDao
}
