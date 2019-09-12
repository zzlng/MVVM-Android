package io.zzl.app.model.repository

import io.zzl.app.model.Beauty
import io.zzl.app.model.local.dao.BeautyDao
import io.zzl.app.model.remote.BeautyService
import java.lang.Exception

class PaoRepo constructor(private val remote: BeautyService, private val local: BeautyDao) {

    suspend fun getArticleDetail(numbers: Int, page: Int) : Array<Beauty> {
        try {
            return local.getBeautiesByPage(numbers, page)
        } catch (e: Exception) {
            return remote.getBeautiesByPage(numbers, page)
        }

        return local.getBeautiesByPage(numbers, page)
    }

//            = local.getBeautiesByPage(numbers, page)
//            .onErrorResumeNext {
//                remote.getBeautiesByPage(numbers, page)
//                        .doOnSuccess { local.insertArticle(it) }
//            }
}