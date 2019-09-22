package io.zzl.app.model.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.zzl.app.model.data.BaseModel
import java.util.*

interface BaseDAO<T> where T: BaseModel {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(modelData: T)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertAll(modelDataList: List<T>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(modelData: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateList(modelDataList: List<T>)

    companion object {

        open class DAOWrapper<P, T>(private val daoInstance: T) where T: BaseDAO<P>, P: BaseModel {

            private fun preInsert(modelData: P) {
                modelData.creationDate = Date(System.currentTimeMillis())
                modelData.modificationDate = Date(System.currentTimeMillis())
            }

            private fun preUpdate(modelData: P) {
                modelData.modificationDate = Date(System.currentTimeMillis())
            }

            fun insertWithTimestapData(modelData: P) {
                preInsert(modelData)
//                this@DAOWrapper.daoInstance.insert(modelData)
                daoInstance.insert(modelData)
            }

            fun updateWithTimestapData(modelData: P) {
                preUpdate(modelData)
                daoInstance.update(modelData)
            }

            fun insertAllWithTimestapData(listData: List<P>) {
                listData.map { preInsert(it) }
                daoInstance.insertAll(listData)
            }

            fun updateListWithTimestapData(listData: List<P>) {
                listData.map { preUpdate(it) }
                daoInstance.updateList(listData)
            }
        }
    }
}