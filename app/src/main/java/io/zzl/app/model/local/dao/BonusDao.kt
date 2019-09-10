package io.zzl.app.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.zzl.app.model.data.Article
import io.reactivex.Single

/**
 * 页面描述：BonusDao
 *
 * Created by zzl on 9/11.
 */
@Dao
interface BeautyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insetAll(articles: List<Article>)

    // TODO
    @Query("SELECT * FROM Beauties ORDER BY desc LIMIT start= :(page-1)*numbers , :page*numbers")
    suspend fun getArticlesById(numbers: Int, page: Int): Article

    @Query("SELECT * FROM Beauties WHERE beautyid= :id")
    suspend fun getArticleById(id: Int): Article

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

}