package io.zzl.app.model.repository

import io.zzl.app.model.local.dao.BeautyDao
import io.zzl.app.model.remote.BeautyService

class PaoRepo constructor(private val remote: BeautyService, private val local: BeautyDao) {

    fun getBeauties(numbers: Int, page: Int) = withScope

    fun getArticleDetail(numbers: Int, page: Int) = local.getBeautiesByPage(numbers, page)
            .onErrorResumeNext {
                remote.getBeautiesByPage(numbers, page)
                        .doOnSuccess { local.insertArticle(it) }
            }
}