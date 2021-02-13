package id.calocallo.sicape.ui.main.editpersonel.pernah_dihukum

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.PernahDihukumResp
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.HukumanReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_pernah_dihukum.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPernahDihukumActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var hukumanReq = HukumanReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pernah_dihukum)
        sessionManager1 = SessionManager1(this)

        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_edit_pernah_dihukum.gone()
            btn_delete_edit_pernah_dihukum.gone()
        }

        val dihukum = intent.extras?.getParcelable<PernahDihukumResp>("DIHUKUM")
        edt_perkara_hukum_edit.setText(dihukum?.perkara)

        btn_save_edit_pernah_dihukum.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_pernah_dihukum)
        btn_save_edit_pernah_dihukum.setOnClickListener {
            updateDihukum(dihukum)
        }

        btn_delete_edit_pernah_dihukum.attachTextChangeAnimator()
        bindProgressButton(btn_delete_edit_pernah_dihukum)
        btn_delete_edit_pernah_dihukum.setOnClickListener {
            deleteDihukum(dihukum)
        }
    }

    private fun updateDihukum(dihukum: PernahDihukumResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_edit_pernah_dihukum.showProgress {
            progressColor = Color.WHITE
        }
        hukumanReq.perkara = edt_perkara_hukum_edit.text.toString()
        NetworkConfig().getService().updateSingleDihukum(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            dihukum?.id.toString(),
            hukumanReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(
                    this@EditPernahDihukumActivity,
                    R.string.error_conn,
                    Toast.LENGTH_SHORT
                ).show()
                btn_save_edit_pernah_dihukum.hideDrawable(R.string.save)
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_edit_pernah_dihukum.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_edit_pernah_dihukum.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_edit_pernah_dihukum.hideDrawable(R.string.not_update)

                }
            }
        })
    }

    private fun deleteDihukum(dihukum: PernahDihukumResp?) {
        NetworkConfig().getService().deleteSingleDihukum(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            dihukum?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditPernahDihukumActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(this@EditPernahDihukumActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}
