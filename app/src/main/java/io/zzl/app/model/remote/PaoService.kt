package io.zzl.app.model.remote

import com.javalong.retrofitmocker.annotation.MOCK
import io.zzl.app.model.data.Article
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 页面描述：PaoService
 *
 * Created by ditclear on 2017/11/19.
 */
interface PaoService{
    /**
     * 文章详情
     */
    @MOCK("1.json")
    @GET("article_detail.php")
    fun getArticleById(@Query("id") id: Int): Single<Article>

}