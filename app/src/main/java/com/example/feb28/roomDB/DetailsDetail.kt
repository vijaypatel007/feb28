package com.example.feb28.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Gallery_DB")
data class GalleryDetail(

    @PrimaryKey(autoGenerate = true)
    val id:Long,
    val nameUrl: String,
)
