package io.zzl.app.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.zzl.app.helper.Utils
import io.zzl.app.helper.async
import io.zzl.app.model.data.Article
import io.zzl.app.model.repository.PaoRepo
import io.reactivex.Single

/**
 * 页面描述：PaoViewModel
 * @param animal 数据源Model(MVVM 中的M),负责提供ViewModel中需要处理的数据
 * Created by ditclear on 2017/11/17.
 */
class PaoViewModel constructor(private val repo: PaoRepo) : ViewModel(){

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