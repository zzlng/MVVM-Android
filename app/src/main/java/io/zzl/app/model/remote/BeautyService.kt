package io.zzl.app.model.remote

import com.javalong.retrofitmocker.annotation.MOCK
import io.zzl.app.model.data.Beauty
import retrofit2.http.GET
import retrofit2.http.Path

interface BeautyService{

    @MOCK("1.json")
    @GET("/data/%E7%A6%8F%E5%88%A9/{numbers}/{page}")
    suspend fun getBeautiesByPage(@Path("numbers")numbers: Int, @Path("page") page: Int): List<Beauty>
}