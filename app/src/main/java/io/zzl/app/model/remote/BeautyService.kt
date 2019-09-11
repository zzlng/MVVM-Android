package io.zzl.app.model.remote

import com.javalong.retrofitmocker.annotation.MOCK
import io.zzl.app.model.Beauty
import retrofit2.http.GET
import retrofit2.http.Path

interface BeautyService{

    @MOCK("1.json")
    @GET("{numbers}/{page}")
    suspend fun getBeautiesByPage(@Path("numbers") numbers: Int, @Path("page") page: Int): Array<Beauty>
}