package com.halulkin.storm.base

import androidx.recyclerview.widget.DiffUtil
import com.halulkin.storm.data.model.GeneralEntity

class BaseDiffUtil<T : GeneralEntity> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}
