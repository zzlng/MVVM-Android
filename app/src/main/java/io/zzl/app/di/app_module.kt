package io.zzl.app.di

import com.google.gson.GsonBuilder
import com.javalong.retrofitmocker.createMocker
import io.zzl.app.helper.Constants
import io.zzl.app.model.local.AppDatabase
import io.zzl.app.model.remote.BeautyService
import io.zzl.app.model.repository.PaoRepo
import io.zzl.app.viewmodel.PaoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {

    viewModel { PaoViewModel(get()) }
    //or use reflection
//    viewModel<PaoViewModel>()
}

val repoModule = module {

    single { PaoRepo(get(), get()) }
}

val remoteModule = module {

    single {
        val gson = GsonBuilder()
//                .setDateFormat("yyyy-MM-dd")
                .create()

        Retrofit.Builder()
                .baseUrl(Constants.HOST_API)
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }

//    single<BeautyService> { get<Retrofit>().create(BeautyService::class.java) }
    single { get<Retrofit>().createMocker(BeautyService::class.java) }
//    single { get<Retrofit>().createMocker(BeautyService::class.java, BuildConfig.DEBUG) }
}


val localModule = module {

    single { AppDatabase.getInstance(androidApplication()) }

    single { get<AppDatabase>().beautyDao() }
}

val appModule = listOf(viewModelModule, repoModule, remoteModule, localModule)