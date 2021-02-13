package id.calocallo.sicape.utils.ext

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * Shows the Snackbar inside an Activity or Fragment
 *
 * @param messageRes Text to be shown inside the Snackbar
 * @param length Duration of the Snackbar
 * @param f Action of the Snackbar
 */

fun View.showSnackbar(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_INDEFINITE, f: Snackbar.() -> Unit) {
    val snackBar = Snackbar.make(this, resources.getString(messageRes), length)
    snackBar.f()
    snackBar.show()
}

/**
 * Adds action to the Snackbar
 *
 * @param actionRes Action text to be shown inside the Snackbar
 * @param color Color of the action text
 * @param listener Onclick listener for the action
 */

fun Snackbar.action(@StringRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
    setAction(actionRes, listener)
    color?.let { setActionTextColor(color) }
}