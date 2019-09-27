package io.zzl.app.model.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.zzl.app.model.data.Beauty

@Dao
interface BeautyDAO : BaseDAO<Beauty> {

    @Query("""
        SELECT CASE 
                WHEN JULIANDAY('now', 'localtime') - MAX(creation_date) > :lifetime THEN 'FALSE'
                ELSE 'TRUE'
            END AS is_new
        FROM Beauties
    """)
    fun hasNew(lifetime: Long): Boolean

    @Query("""
        SELECT *
        FROM Beauties
        ORDER BY beautyid
        LIMIT :page * :count
    """)
    suspend fun getBeautiesByPage(count: Int, page: Int): DataSource.Factory<Int, Beauty>

    @Query("DELETE FROM Beauties")
    suspend fun cleanAll()

    @Transaction
    suspend fun reloadList(beauties: List<Beauty>) {
        cleanAll()
//        insertList(beauties)
        BaseDAO.Companion.DAOWrapper(this).insertListWithTimestapData(beauties)
    }
}