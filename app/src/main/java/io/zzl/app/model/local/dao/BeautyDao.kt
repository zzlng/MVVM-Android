package io.zzl.app.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.zzl.app.model.data.Beauty

@Dao
interface BeautyDao {

    @Query("SELECT CASE WHEN julianday('now') - max(`time_stamp`) > :lifetime" +
            " THEN 'FALSE' ELSE 'TRUE' END bool FROM Beauties")
    fun hasNew(lifetime: Long): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBeauty(beauty: Beauty)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(beauties: List<Beauty>)

    @Query("SELECT * FROM Beauties ORDER BY `desc`" +
            " LIMIT (:page-1)*:numbers , :page*:numbers")
    suspend fun getBeautiesByPage(numbers: Int, page: Int): List<Beauty>
}