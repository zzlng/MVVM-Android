package io.zzl.app.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.zzl.app.BuildConfig
import io.zzl.app.R
import io.zzl.app.databinding.PaoActivityBinding
import io.zzl.app.viewmodel.PaoViewModel
import io.reactivex.Single
import org.koin.android.viewmodel.ext.android.viewModel

class PaoActivity : AppCompatActivity() {

    private val mBinding: PaoActivityBinding by lazy {
        DataBindingUtil.setContentView<PaoActivityBinding>(this, R.layout.pao_activity)
    }

    //di
    private val mViewModel: PaoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(mBinding.toolbar)
        ////binding
        mBinding.vm = mViewModel
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.detail_menu, it)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_refresh -> mViewModel.loadArticle()
                    .bindLifeCycle(this)
                    .subscribe({}, { dispatchFailure(it) })
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //处理错误
    private fun dispatchFailure(error: Throwable?, length: Int = Toast.LENGTH_SHORT) {
        error?.let {
            if (BuildConfig.DEBUG) {
                it.printStackTrace()
            }
            Toast.makeText(this, it.message, length).show()
        }
    }
    fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> =
            this.`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY)))

}
