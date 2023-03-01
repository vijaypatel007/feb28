package com.example.feb28.roomDB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GalleryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(galleryDetail: GalleryDetail)

    @Query("Select * FROM Gallery_DB")
    fun getAllRecord(): LiveData<List<GalleryDetail>>

}


