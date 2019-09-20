package io.zzl.app.model.repository

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.CacheMemoryUtils
import io.zzl.app.model.Result.Error
import io.zzl.app.model.Result.Success
import io.zzl.app.model.data.Beauty
import io.zzl.app.model.local.dao.BeautyDao
import io.zzl.app.model.remote.BeautyService
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.TimeUnit

class PaoRepo constructor(private val remote: BeautyService, private val local: BeautyDao) {

    companion object {
        val FRESH_TIMEOUT = TimeUnit.HOURS.toMillis(2)
    }

    private var cacheMemory: CacheMemoryUtils? = null

    suspend fun getBeauties(forceUpdate: Boolean) = withContext(Dispatchers.IO) {
        // Respond immediately with cache if available and not dirty
        if (!forceUpdate) {
            cacheMemory?.let { cachedTasks ->
                return@withContext Success(cachedTasks.values.sortedBy { it.id })
            }
        }

        val newTasks = fetchTasksFromRemoteOrLocal(forceUpdate)

        // Refresh the cache with the new tasks
        (newTasks as? Success)?.let { refreshCache(it.data) }

        cacheMemory?.values?.let { tasks ->
            return@withContext Success(tasks.sortedBy { it.id })
        }

        (newTasks as? Success)?.let {
            if (it.data.isEmpty()) {
                return@withContext Success(it.data)
            }
        }

        return@withContext Error(Exception("Illegal state"))
    }

    suspend fun getBeautiesByPage(numbers: Int, page: Int) = coroutineScope {

            val data = MutableLiveData<List<Beauty>>()

            val updated = local.hasNew(FRESH_TIMEOUT)

            val loadLocal = async {
                val localData = local.getBeautiesByPage(numbers, page)
    //            localDatadata?.aslo(data.postValue(localData))
                data.value = localData
            }.await()

            val refresh = launch {
                var cm: ConcurrentMap<String, Beauty>? = null
                withContext(Dispatchers.Default) {
                    cm?.entries?.removeAll{  it.value.used }
                }
            }
    }

    private fun cacheBeauty(beauty: Beauty): Beauty {
        val cachedData = beauty.copy()
        // Create if it doesn't exist.
        if (cacheMemory == null) {
            cacheMemory = CacheMemoryUtils.getInstance()
        }
        cacheMemory?.put(cachedData.id, cachedData)
        return cachedData
    }

    private inline fun cacheAndPerform(beauty: Beauty, perform: (Beauty) -> Unit) {
        val cachedData = cacheBeauty(beauty)
        perform(cachedData)
    }
}