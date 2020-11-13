package id.co.iconpln.smartcity.ui.base.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

abstract class BaseTabPagerAdapter(val context: Context, fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

    private val listFragment = ArrayList<Fragment>()

    init {
        listFragment.addAll(addFragments())
    }

    override fun getItem(position: Int) = listFragment[position]

    override fun getCount() = listFragment.size

    abstract fun addFragments() : List<Fragment>

}