package id.calocallo.sicape.utils.ext

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar


fun EditText.onFocusChanged(hasFocus: (Boolean) -> Unit) {
    this.setOnFocusChangeListener { _, b -> hasFocus(b) }
}

fun EditText.OnTextChangedListener(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            p0?.let {
                afterTextChanged(editableText.toString())
            }
        }

    })
}

fun View?.removeSelf() {
    this ?: return
    val parent = parent as? ViewGroup ?: return
    parent.removeView(this)
}

fun View?.addTo(parent: ViewGroup?) {
    this ?: return
    parent ?: return
    parent.addView(this)
}

// Null-safe extension
fun ViewGroup?.addView(view: View?) {
    this ?: return
    view ?: return
    addView(view)
}


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.toggleVisibility(): View {
    visibility = if (visibility == View.VISIBLE) {
        View.GONE
    } else {
        View.VISIBLE
    }
    return this
}


//fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) = snack(message, length) {}
//inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
//    val snack = Snackbar.make(this, message, length)
//    snack.f()
//    snack.show()
//}

//fun View.snackRes(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG) = snackRes(messageRes, length) {}