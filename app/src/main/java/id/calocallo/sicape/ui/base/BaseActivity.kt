package id.calocallo.sicape.ui.base

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import id.calocallo.sicape.R
import id.calocallo.sicape.widget.TopSnackbar

/**
 * rizmaulana@live.com 2019-06-14.
 */
abstract class BaseActivity : AppCompatActivity() {

    var progressDialog: ProgressDialog? = null


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    private fun showTopSnackbar(root: View, message: String, @ColorRes color: Int) {
        val topSnackbar = TopSnackbar.make(root, message, TopSnackbar.LENGTH_LONG)
        val snackbarView = topSnackbar.getView()
        snackbarView.setBackgroundColor(ContextCompat.getColor(this, color))
        val textView = snackbarView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        topSnackbar.show()
    }

    private fun hideSoftKeyboard() {
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    open fun setupActionBarWithBackButton(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        toolbar.setTitleTextAppearance(this, R.style.TextAppearance_App_TextView_Toolbar)
    }

    open fun changeStatusBarWhite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
    }
}