package io.zzl.app.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.zzl.app.model.Beauty

@Dao
interface BeautyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetAll(articles: List<Beauty>)

    @Query("SELECT * FROM Beauties ORDER BY `desc` LIMIT (:page-1)*:numbers , :page*:numbers")
    suspend fun getBeautiesByPage(numbers: Int, page: Int): Beauty
}