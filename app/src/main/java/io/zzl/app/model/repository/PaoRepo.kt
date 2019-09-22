package io.zzl.app.model.repository

import androidx.lifecycle.MutableLiveData
import io.zzl.app.model.Result.Error
import io.zzl.app.model.Result.Success
import io.zzl.app.model.data.Beauty
import io.zzl.app.model.local.dao.BaseDAO
import io.zzl.app.model.local.dao.BeautyDAO
import io.zzl.app.model.remote.BeautyService
import kotlinx.coroutines.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.TimeUnit

class PaoRepo constructor(
        private val remote: BeautyService,
        private val local: BeautyDAO,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    companion object {
        val FRESH_TIMEOUT_HOURS = TimeUnit.HOURS.toMillis(2)
    }

    private val daoWrapper = BaseDAO.Companion.DAOWrapper(local)

    private var cacheMemory: ConcurrentMap<String, Beauty>? = null

    suspend fun getBeauties(forceUpdate: Boolean) = withContext(Dispatchers.IO) {
        // Respond immediately with cache if available and not dirty
        if (!forceUpdate) {

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

    suspend fun getBeautiesByPage(numbers: Int, page: Int) = withContext(ioDispatcher) {

        cacheMemory?.let { cachedBeauties ->
            return@withContext Success(cachedBeauties.values.sortedBy { it.creationDate })
        }

        val updated = local.hasNew(FRESH_TIMEOUT_HOURS)

        try {
            Success(local.getBeautiesByPage(numbers, page))
        } catch (e: Exception) {
            Error(e)
        }

        val loadLocal = async {

        }.await()

        val refresh = launch {
            var cm: ConcurrentMap<String, Beauty>? = null
            withContext(Dispatchers.Default) {
                cm?.entries?.removeAll { it.value.used }
            }
        }
    }

    private fun cacheBeauty(beauty: Beauty): Beauty {
        val cachedData = beauty.copy()
        // Create if it doesn't exist.
        if (cacheMemory == null) {
            cacheMemory = ConcurrentHashMap()
        }
        cacheMemory?.put(cachedData.id, cachedData)
        return cachedData
    }

    private inline fun cacheAndPerform(beauty: Beauty, perform: (Beauty) -> Unit) {
        val cachedData = cacheBeauty(beauty)
        perform(cachedData)
    }
}