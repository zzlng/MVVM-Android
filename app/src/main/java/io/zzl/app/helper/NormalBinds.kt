package io.zzl.app.helper

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter

/**
 * 页面描述：NormalBinds
 *
 * Created by ditclear on 2017/11/19.
 */
@BindingAdapter(value = ["markdown"])
fun bindMarkDown(v: TextView, markdown: String?) {
    markdown?.let {
        v.setText(it)
    }
}

@BindingAdapter(value = ["toast"])
fun bindToast(v: View,msg:Throwable ?){
    msg?.let {
        Toast.makeText(v.context,it.message,Toast.LENGTH_LONG).show()
    }
}