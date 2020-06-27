package com.mycanada.poc.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.mycanada.poc.model.InformationChildModel

class InfoChilItemDiffCallback: DiffUtil.ItemCallback<InformationChildModel>() {
    override fun areItemsTheSame(
        oldItem: InformationChildModel,
        newItem: InformationChildModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: InformationChildModel,
        newItem: InformationChildModel
    ): Boolean {
        return oldItem == newItem
    }
}