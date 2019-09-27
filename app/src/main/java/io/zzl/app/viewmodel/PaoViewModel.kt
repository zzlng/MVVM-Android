package io.zzl.app.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.zzl.app.helper.Utils
import io.zzl.app.model.repository.BeautyRepo
import io.reactivex.Single
import io.zzl.app.model.data.Beauty
import kotlinx.coroutines.*

/**
 * 页面描述：PaoViewModel
 * 处理（分拆、对应）从 repository 获取后的数据
 * Created by zzl on 9/11.
 */
class PaoViewModel constructor(private val repo: BeautyRepo) : ViewModel(){

    val data = MutableLiveData<List<Beauty>>()

    // livedata
    fun loadBeauty() = viewModelScope.async {
        repo.getBeautiesByPage(10, 1)
    }

    fun a() = CoroutineScope(Dispatchers.IO).async {  }

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