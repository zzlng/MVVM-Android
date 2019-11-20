package io.zzl.app.view

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.zzl.app.model.data.Beauty

class BeautiesAdapter : PagedListAdapter<Beauty, RecyclerView.ViewHolder>(BEAUTY_COMPARATOR) {

    companion object {
        val BEAUTY_COMPARATOR = object : DiffUtil.ItemCallback<Beauty>() {
            override fun areContentsTheSame(oldItem: Beauty, newItem: Beauty): Boolean =
                    oldItem == newItem
            override fun areItemsTheSame(oldItem: Beauty, newItem: Beauty): Boolean =
                    oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}