
package com.mycanada.poc.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mycanada.poc.R
import com.mycanada.poc.databinding.InformationListItemBinding
import com.mycanada.poc.model.InformationChildModel


/**
 * A RecyclerView adapter for [InformationChildModel] class.
 */

class InfoListAdapter(): ListAdapter<InformationChildModel, InfoListAdapter.InfoViewHolder>(InfoChilItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val binding: InformationListItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.information_list_item, parent, false)
        return InfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.bindChapterData(getItem(position))
        holder.view.executePendingBindings()
    }

    class InfoViewHolder(val view: InformationListItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bindChapterData(info: InformationChildModel) {
            view.informationChild = info
            Glide.with(view.imgThumbnail.context).load(info.imageHref).diskCacheStrategy(DiskCacheStrategy.ALL).circleCrop().placeholder(R.mipmap.ic_launcher).dontAnimate().into(view.imgThumbnail)
        }
    }

}
