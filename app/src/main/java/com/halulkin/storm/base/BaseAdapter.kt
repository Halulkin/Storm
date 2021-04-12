package com.halulkin.storm.base

import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.halulkin.storm.data.model.GeneralEntity

abstract class BaseAdapter<T : GeneralEntity, B : ViewBinding>
    : ListAdapter<T, BaseViewHolder<T, B>>(BaseDiffUtil<T>()), BindDataAdapter<List<T>> {

    override fun onBindViewHolder(holder: BaseViewHolder<T, B>, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun setData(data: List<T>?) {
        submitList(data)
    }
}
