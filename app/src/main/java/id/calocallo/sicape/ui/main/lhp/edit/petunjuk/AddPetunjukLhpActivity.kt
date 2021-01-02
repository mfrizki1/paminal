package id.calocallo.sicape.ui.main.lhp.edit.petunjuk

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListPetunjuk
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_petunjuk_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddPetunjukLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var petunjukReq = ListPetunjuk()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_petunjuk_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Petunjuk"

        btn_add_petunjuk_single.attachTextChangeAnimator()
        bindProgressButton(btn_add_petunjuk_single)
        btn_add_petunjuk_single.setOnClickListener {
            addPetunjuk()
        }
    }

    private fun addPetunjuk() {
        petunjukReq.petunjuk=edt_petunjuk_single.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_add_petunjuk_single.showProgress {
                progressColor = Color.WHITE
            }

        btn_add_petunjuk_single.showDrawable(animatedDrawable) {
                buttonTextRes = R.string.data_saved
                textMarginRes = R.dimen.space_10dp
            }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_add_petunjuk_single.hideDrawable(R.string.save)
        },3000)

    }

    companion object {
        const val ADD_PETUNJUK = "ADD_PETUNJUK"
    }
}