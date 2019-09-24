package io.zzl.app.model.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.zzl.app.model.data.BaseModel
import java.util.*

interface BaseDAO<T> where T: BaseModel {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(modelData: T)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertList(modelDataList: List<T>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(modelData: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateList(modelDataList: List<T>)

    companion object {

        open class DAOWrapper<P, T>(private val daoInstance: T) where T: BaseDAO<P>, P: BaseModel {

            private fun preInsert(modelData: P) {
                modelData.creationDate = Date(System.currentTimeMillis())
                modelData.modificationDate = Date(System.currentTimeMillis())
            }

            private fun preUpdate(modelData: P) {
                modelData.modificationDate = Date(System.currentTimeMillis())
            }

            suspend fun insertWithTimestapData(modelData: P) {
                preInsert(modelData)
//                this@DAOWrapper.daoInstance.insert(modelData)
                daoInstance.insert(modelData)
            }

            suspend fun updateWithTimestapData(modelData: P) {
                preUpdate(modelData)
                daoInstance.update(modelData)
            }

            suspend fun insertListWithTimestapData(listData: List<P>) {
                listData.map { preInsert(it) }
                daoInstance.insertList(listData)
            }

            suspend fun updateListWithTimestapData(listData: List<P>) {
                listData.map { preUpdate(it) }
                daoInstance.updateList(listData)
            }
        }
    }
}