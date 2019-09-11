package io.zzl.app

import android.app.Application
import io.zzl.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * 页面描述：PaoApp
 *
 * Created by ditclear on 2018/6/25.
 */
class PaoApp : Application() {


    override fun onCreate() {
        super.onCreate()

//        startKoin(this, appModule, logger = AndroidLogger(showDebug = BuildConfig.DEBUG))
        startKoin {
            androidContext(this@PaoApp)
            modules(appModule)
            androidLogger(Level.DEBUG)
            androidFileProperties()
        }
    }


}