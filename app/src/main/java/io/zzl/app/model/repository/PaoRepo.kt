package io.zzl.app.model.repository

import io.zzl.app.model.local.dao.PaoDao
import io.zzl.app.model.remote.PaoService

/**
 * 页面描述：PaoRepo
 *
 * Created by zzl on 9/11.
 */
class PaoRepo constructor(private val remote:PaoService, private val local :PaoDao){

    fun getArticleDetail(numbers: Int, page: Int)= local.getArticleById(numbers, page)
            .onErrorResumeNext {
                remote.getArticleById(numbers, page)
                        .doOnSuccess { local.insertArticle(it) }
            }


}