package id.calocallo.sicape.utils.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import id.calocallo.sicape.R

inline fun Context.alert(title: CharSequence? = null, message: CharSequence? = null, func: AlertDialogHelper.() -> Unit): AlertDialog {
    return AlertDialogHelper(this, title, message).apply {
        func()
    }.create()
}

inline fun Context.alert(titleResource: Int = 0, messageResource: Int = 0, func: AlertDialogHelper.() -> Unit): AlertDialog {
    val title = if (titleResource == 0) null else getString(titleResource)
    val message = if (messageResource == 0) null else getString(messageResource)
    return AlertDialogHelper(this, title, message).apply {
        func()
    }.create()
}

class AlertDialogHelper(context: Context, title: CharSequence?, message: CharSequence?){
    private val dialogView: View by lazyFast {
        LayoutInflater.from(context).inflate(R.layout.view_info_dialog, null)
    }

    private val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        .setView(dialogView)

    private val title: TextView by lazyFast {
        dialogView.findViewById<TextView>(R.id.dialogInfoTitleTextView)
    }

    private val message: TextView by lazyFast {
        dialogView.findViewById<TextView>(R.id.dialogInfoMessageTextView)
    }

    private val positiveButton: Button by lazyFast {
        dialogView.findViewById<Button>(R.id.dialogInfoPositiveButton)
    }

    private val negativeButton: Button by lazyFast {
        dialogView.findViewById<Button>(R.id.dialogInfoNegativeButton)
    }

    private var dialog: AlertDialog? = null

    var cancelable: Boolean = true

    init {
        this.title.text = title
        this.message.text = message
    }

    fun positiveButton(@StringRes textResource: Int, func: (() -> Unit)? = null) {
        with(positiveButton) {
            text = builder.context.getString(textResource)
            setClickListenerToDialogButton(func)
        }
    }

    fun positiveButton(text: CharSequence, func: (() -> Unit)? = null) {
        with(positiveButton) {
            this.text = text
            setClickListenerToDialogButton(func)
        }
    }

    fun negativeButton(@StringRes textResource: Int, func: (() -> Unit)? = null) {
        with(negativeButton) {
            text = builder.context.getString(textResource)
            setClickListenerToDialogButton(func)
        }
    }

    fun negativeButton(text: CharSequence, func: (() -> Unit)? = null) {
        with(negativeButton) {
            this.text = text
            setClickListenerToDialogButton(func)
        }
    }

    fun onCancel(func: () -> Unit) {
        builder.setOnCancelListener { func() }
    }

    fun create(): AlertDialog {
        title.goneIfTextEmpty()
        message.goneIfTextEmpty()
        positiveButton.goneIfTextEmpty()
        negativeButton.goneIfTextEmpty()

        dialog = builder
            .setCancelable(cancelable)
            .create()
        return dialog!!
    }

    private fun TextView.goneIfTextEmpty() {
        visibility = if (text.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun Button.setClickListenerToDialogButton(func: (() -> Unit)?) {
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }
    }
}