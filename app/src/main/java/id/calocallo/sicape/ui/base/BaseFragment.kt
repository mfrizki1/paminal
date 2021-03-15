package id.co.iconpln.smartcity.ui.base

import androidx.fragment.app.Fragment
import id.calocallo.sicape.ui.base.BaseActivity


/**
 * Created by Rizki Maulana on 21/02/19.
 * email : rizmaulana@live.com
 * Mobile App Developer
 */

abstract class BaseFragment : Fragment() {
    private fun getBaseActivity() = activity as BaseActivity



}