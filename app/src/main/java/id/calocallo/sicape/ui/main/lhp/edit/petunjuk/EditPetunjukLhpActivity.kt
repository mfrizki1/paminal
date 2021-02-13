package id.calocallo.sicape.ui.main.lhp.edit.petunjuk

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListPetunjuk
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_petunjuk_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditPetunjukLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var petunjukReq = ListPetunjuk()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_petunjuk_lhp)
        sessionManager1 = SessionManager1(this)
        val petunjuk = intent.extras?.getParcelable<ListPetunjuk>(EDIT_PETUNJUK)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Petunjuk LHP"

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_add_petunjuk_edit.gone()
            btn_delete_petunjuk_edit.gone()
        }

        edt_petunjuk_edit.setText(petunjuk?.petunjuk)

        btn_add_petunjuk_edit.attachTextChangeAnimator()
        bindProgressButton(btn_add_petunjuk_edit)
        btn_add_petunjuk_edit.setOnClickListener {
            updatePetunjuk(petunjuk)
        }
        btn_delete_petunjuk_edit.attachTextChangeAnimator()
        bindProgressButton(btn_delete_petunjuk_edit)
        btn_delete_petunjuk_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deletePetunjuk(petunjuk)
                }
                negativeButton("Tidak") {}
            }.show()
        }

    }

    private fun updatePetunjuk(petunjuk: ListPetunjuk?) {
        petunjukReq.petunjuk = edt_petunjuk_edit.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_add_petunjuk_edit.showProgress {
            progressColor = Color.WHITE
        }
        btn_add_petunjuk_edit.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_updated
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_add_petunjuk_edit.hideDrawable(R.string.save)
        }, 3000)
    }

    private fun deletePetunjuk(petunjuk: ListPetunjuk?) {

    }

    companion object {
        const val EDIT_PETUNJUK = "EDIT_PETUNJUK"
    }
}