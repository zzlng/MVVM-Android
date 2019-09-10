package io.zzl.app.di

import com.javalong.retrofitmocker.createMocker
import io.zzl.app.helper.Constants
import io.zzl.app.model.local.AppDatabase
import io.zzl.app.model.remote.PaoService
import io.zzl.app.model.repository.PaoRepo
import io.zzl.app.viewmodel.PaoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { PaoViewModel(get()) }
    //or use reflection
//    viewModel<PaoViewModel>()

}

val repoModule = module {

    factory { PaoRepo(get(), get()) }
    //其实就是
    //factory <PaoRepo> { PaoRepo(get<PaoService>(), get<PaoDao>())  }

}

val remoteModule = module {

    single<Retrofit> {
        Retrofit.Builder()
                .baseUrl(Constants.HOST_API)
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

//    single<PaoService> { get<Retrofit>().create(PaoService::class.java) }
    single { get<Retrofit>().createMocker(PaoService::class.java) }
//    single { get<Retrofit>().createMocker(PaoService::class.java, BuildConfig.DEBUG) }
}


val localModule = module {

    single { AppDatabase.getInstance(androidApplication()) }

    single { get<AppDatabase>().paoDao() }
}


val appModule = listOf(viewModelModule, repoModule, remoteModule, localModule)