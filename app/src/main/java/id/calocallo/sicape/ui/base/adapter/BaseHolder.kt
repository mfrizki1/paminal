package lib.iconpln.fieldservice.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import id.co.iconpln.smartcity.ui.base.adapter.RecyclerViewItemClickListener


abstract class BaseHolder<T> constructor(val listener: RecyclerViewItemClickListener<T>, itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private var itemPosition: Int = 0
    private var itemData: T? = null

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        listener.itemClick(itemPosition, itemData, view.id)
    }

    fun bindData(position: Int, data: T?) {
        itemPosition = position
        itemData = data
    }

    fun getData(): T? = itemData

    fun getDataPosition(): Int = itemPosition
}