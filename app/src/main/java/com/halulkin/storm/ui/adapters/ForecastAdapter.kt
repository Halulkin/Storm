package com.halulkin.storm.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.storm.databinding.ItemForecastBinding
import com.halulkin.storm.base.BaseAdapter
import com.halulkin.storm.base.BaseViewHolder
import com.halulkin.storm.data.model.Forecast


class ForecastAdapter(
) : BaseAdapter<Forecast, ItemForecastBinding>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Forecast, ItemForecastBinding> = FavoriteHolder(
        ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    class FavoriteHolder(
        private val itemForecastBinding: ItemForecastBinding
    ) : BaseViewHolder<Forecast, ItemForecastBinding>(itemForecastBinding) {

        override fun onBind(itemData: Forecast) {
            super.onBind(itemData)
            itemForecastBinding.forecast = itemData
        }
    }

    fun getWeatherByPosition(adapterPosition: Int): Forecast {
        return getItem(adapterPosition)
    }
}