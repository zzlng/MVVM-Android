package io.zzl.app.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.zzl.app.model.Beauty
import io.zzl.app.model.local.dao.BeautyDao
import io.zzl.app.model.remote.BeautyService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception

class PaoRepo constructor(private val remote: BeautyService, private val local: BeautyDao) {

    suspend fun getBeautiesByPage(numbers: Int, page: Int) = coroutineScope {

        val data = MutableLiveData<Array<Beauty>>()

        val loadLocal = async {
            val localData = local.getBeautiesByPage(numbers, page)
//            localDatadata?.aslo(data.postValue(localData))
            data.value = localData
        }.await()

        val refresh = async {

        }
    }

//            = local.getBeautiesByPage(numbers, page)
//            .onErrorResumeNext {
//                remote.getBeautiesByPage(numbers, page)
//                        .doOnSuccess { local.insertArticle(it) }
//            }
}