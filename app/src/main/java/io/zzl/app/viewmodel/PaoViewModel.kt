package io.zzl.app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.zzl.app.model.data.Beauty
import io.zzl.app.model.repository.BeautyRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PaoViewModel constructor(private val repo: BeautyRepo) : ViewModel(){

    // databiding
    val data = MutableLiveData<List<Beauty>>()

    // livedata 何处进行观察数据变化
    fun loadBeauty() = viewModelScope.launch {
        repo.getBeautiesByPage(1)
    }

    fun a() = CoroutineScope(Dispatchers.IO).async {  }

/*
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
    }*/
}