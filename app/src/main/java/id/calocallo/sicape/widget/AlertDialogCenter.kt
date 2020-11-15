package id.calocallo.sicape.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import id.calocallo.sicape.R
import kotlinx.android.synthetic.main.view_info_dialog.view.*

/**
 * Created by Rizki Maulana on 21/02/19.
 * email : rizmaulana@live.com
 * Mobile App Developer
 */

class AlertDialogCenter : DialogFragment() {
    var message: String? = null
    var positiveButton: String? = null
    var negativeButton: String? = null
    var positiveButtonListener: OnClickListener? = null
    var negativeButtonListener: OnClickListener? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_info_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false
//        view.btn_ok.setOnClickListener { dismiss() }
//        view.btn_cancel.setOnClickListener { dismiss() }


        message?.let {
//            view.txt_msg.text = message
        }
        positiveButton?.let {
//            view.btn_ok.visibility = View.VISIBLE
//            view.btn_ok.text = positiveButton
        }
        negativeButton?.let {
//            view.btn_cancel.visibility = View.VISIBLE
//            view.btn_cancel.text = negativeButton
        }
        positiveButtonListener?.let {
//            view.btn_ok.setOnClickListener {v->
//                it.onClick()
//                dismiss()
//            }
        }
        negativeButtonListener?.let {
//            view.btn_cancel.setOnClickListener {v->
//                it.onClick()
//                dismiss()
//            }
        }
    }

    fun show(fm: FragmentManager?) {
        fm?.let {
            show(it)
        }
    }

    interface OnClickListener {
        fun onClick()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }
}