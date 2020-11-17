package com.example.freenow.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freenow.R
import com.example.freenow.domain.models.PoiModel
import kotlinx.android.synthetic.main.item_poi.view.*

class PoiListAdapter(
    private var dataSet: List<PoiModel>,
    private val itemClickListener: OnPoiItemClickListener
) :
    RecyclerView.Adapter<PoiListAdapter.PoiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoiViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poi, parent, false)
        return PoiViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: PoiViewHolder, position: Int) {
        holder.bind(dataSet[position], itemClickListener)
    }

    override fun getItemCount() = dataSet.size

    class PoiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(poi: PoiModel, itemClickListener: OnPoiItemClickListener) {
            itemView.apply {
                tvFleetType.text = context.getString(R.string.fleet_type, poi.fleetType.name)
                tvId.text = context.getString(R.string.id, poi.id.toString())
                cardParent.setOnClickListener {
                    itemClickListener.onItemClicked(poi, adapterPosition)
                }
            }
        }
    }

    fun postNewDataSet(newDataSet: List<PoiModel>) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }

}

interface OnPoiItemClickListener {
    fun onItemClicked(poi: PoiModel, adapterPosition: Int)
}
