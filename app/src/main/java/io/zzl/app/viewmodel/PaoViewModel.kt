package io.zzl.app.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.zzl.app.helper.Utils
import io.zzl.app.model.repository.PaoRepo
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * 页面描述：PaoViewModel
 * 处理（分拆、对应）从 repository 获取后的数据
 * Created by zzl on 9/11.
 */
class PaoViewModel constructor(private val repo: PaoRepo) : ViewModel(){

    // livedata

    suspend fun loadBeauty() = coroutineScope {
        async {
            repo.getBeautiesByPage(10, 1)
        }.await()
    }


        //////////////////data//////////////
        val loading= ObservableBoolean(false)
        val content = ObservableField<String>()
        val title = ObservableField<String>()
        val error = ObservableField<Throwable>()

        //////////////////binding//////////////
        fun loadArticle():Single<Article> =
                repo.getArticleDetail(8773)
                        .async(1000)
                        .doOnSuccess { t: Article? ->
                            t?.let {
                                title.set(it.title)
                                it.content?.let {
                                    val articleContent=Utils.processImgSrc(it)
                                    content.set(articleContent)
                                }

                            }
                        }
                        .doOnSubscribe { startLoad()}
                        .doAfterTerminate { stopLoad() }



        private fun startLoad()=loading.set(true)
        private fun stopLoad()=loading.set(false)
    }
}