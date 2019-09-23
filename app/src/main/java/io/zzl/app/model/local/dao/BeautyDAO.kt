package io.zzl.app.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import io.zzl.app.model.data.Beauty

@Dao
interface BeautyDAO : BaseDAO<Beauty> {

    @Query("SELECT CASE WHEN julianday('now','localtime') - max(creation_date) > :lifetime" +
            " THEN 'FALSE' ELSE 'TRUE' END has_new FROM Beauties")
    fun hasNew(lifetime: Long): Boolean

    @Query("SELECT * FROM Beauties ORDER BY beautyid" +
            " LIMIT (:page-1)*:numbers , :page*:numbers")
    suspend fun getBeautiesByPage(numbers: Int, page: Int): List<Beauty>

    @Query("DELETE FROM Beauties")
    suspend fun cleanAll()
}