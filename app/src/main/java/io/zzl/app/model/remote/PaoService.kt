package io.zzl.app.model.remote

import com.javalong.retrofitmocker.annotation.MOCK
import io.zzl.app.model.data.Article
import retrofit2.http.GET

/**
 * 页面描述：PaoService
 *
 * Created by zzl on 09/10.
 */
interface PaoService{
    /**
     * 图片列表
     */
    @MOCK("1.json")
    @GET("{numbers}/{page}")
    suspend fun getArticleById(@Path("numbers") numbers: Int, @Path("page") page: Int): Array<Article>
}