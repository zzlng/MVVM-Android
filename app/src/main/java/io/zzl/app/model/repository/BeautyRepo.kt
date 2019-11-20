package io.zzl.app.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.paging.toLiveData
import io.zzl.app.model.Resource
import io.zzl.app.model.Resource.Error
import io.zzl.app.model.Resource.Loading
import io.zzl.app.model.data.Beauty
import io.zzl.app.model.local.dao.BaseDAO
import io.zzl.app.model.local.dao.BeautyDAO
import io.zzl.app.model.remote.BeautyService
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class BeautyRepo constructor(
        private val remote: BeautyService,
        private val local: BeautyDAO
) {

    companion object {
        const val BEAUTIES_SIZE = 10
        val FRESH_TIMEOUT_HOURS = TimeUnit.HOURS.toMillis(4)
    }

    // 异常处理逻辑有待细化，暂先粗放处理
    suspend fun getBeautiesByPage(page: Int): LiveData<out Resource<List<Beauty>>> = coroutineScope {

        try {
            launch { tryToRefresh(page) }

            withContext(Dispatchers.IO) {
//                local.getBeautiesByPage(BEAUTIES_SIZE, page).create().invalidate()
                map(local.getBeautiesByPage().toLiveData(BEAUTIES_SIZE)) {
                    Loading(it)
                }
            }

        } catch (e: Exception) {
            val message = e.message?:""
            cancel(message, e)
            MutableLiveData<Error>(Error(message, e))
        }
    }

    private suspend fun tryToRefresh(page: Int) {

        val willFetch = local.shouldFetch(FRESH_TIMEOUT_HOURS)

        if (willFetch) {
            val remoteData = remote.getBeautiesByPage(BEAUTIES_SIZE, page)
            when (page) {
                1 -> local.reloadList(remoteData)
                else -> BaseDAO.Companion.DAOWrapper(local).insertListWithTimestapData(remoteData)
            }
        }
    }
}