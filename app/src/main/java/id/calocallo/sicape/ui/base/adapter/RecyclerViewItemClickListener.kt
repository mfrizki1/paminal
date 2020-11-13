package id.co.iconpln.smartcity.ui.base.adapter

import androidx.annotation.IdRes


/**
 * Created by izul on 10/31/17.
 */

interface RecyclerViewItemClickListener<in T> {
    fun itemClick(position: Int, item: T?, @IdRes viewId: Int)
}