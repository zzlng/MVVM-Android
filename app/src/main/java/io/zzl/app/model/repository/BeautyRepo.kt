package io.zzl.app.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.liveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import io.zzl.app.helper.Constants
import io.zzl.app.model.Resource
import io.zzl.app.model.Resource.*
import io.zzl.app.model.data.Beauty
import io.zzl.app.model.local.dao.BaseDAO
import io.zzl.app.model.local.dao.BeautyDAO
import io.zzl.app.model.remote.BeautyService
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.TimeUnit

class BeautyRepo constructor(
        private val remote: BeautyService,
        private val local: BeautyDAO
) {

    companion object {
        val BEAUTIES_SIZE = 10
        val FRESH_TIMEOUT_HOURS = TimeUnit.HOURS.toMillis(4)
    }

    suspend fun getBeautiesByPage(page: Int): LiveData<out Resource<List<Beauty>>> = coroutineScope {

        val result = MutableLiveData<Resource<Nothing>>()

        launch { tryToRefresh(page) }

        async {
            try {
                map(local.getBeautiesByPage(BEAUTIES_SIZE, page).toLiveData(BEAUTIES_SIZE)) {
                    Success(it)
                }
            } catch (e: Exception) {
                local.cleanAll()
                MutableLiveData<Error>(Error("", e){})
            }
        }.await()
    }

    private suspend fun tryToRefresh(page: Int) {

        val isNew = local.hasNew(FRESH_TIMEOUT_HOURS)

        if (!isNew) {
            val remoteData = remote.getBeautiesByPage(BEAUTIES_SIZE, page)
        }
    }

    private suspend fun fetchTasksFromRemoteOrLocal(numbers: Int, page: Int): LiveData<out Resource<List<Beauty>>> {

        //TODO use paging

        if (isNew) {
            return try {
//                Success(local.getBeautiesByPage(numbers, page))
                map(local.getBeautiesByPage(numbers, page).toLiveData(numbers)) {
                    Success(it)
                }
            } catch (e: Exception) {
                local.cleanAll()
                MutableLiveData<Error>(Error(e))
            }
        }

        val resource = try {
            Success(remote.getBeautiesByPage(numbers, page))
        } catch (e: Exception) {
            Error(e)
        }

        (resource as? Success)?.let {
            coroutineScope {
                launch {
                    local.reloadList(resource.data)
                }
            }
        }

        return resource
    }
}