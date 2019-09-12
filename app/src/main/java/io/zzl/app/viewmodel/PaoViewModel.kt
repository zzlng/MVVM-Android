package io.zzl.app.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.zzl.app.helper.Utils
import io.zzl.app.model.repository.PaoRepo
import io.reactivex.Single
import io.zzl.app.model.Beauty

/**
 * 页面描述：PaoViewModel
 * @param animal 数据源Model(MVVM 中的M),负责提供ViewModel中需要处理的数据
 * Created by zzl on 9/11.
 */
class PaoViewModel constructor(private val repo: PaoRepo) : ViewModel(){

    val data = MutableLiveData<Array<Beauty>>()




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