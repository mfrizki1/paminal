package id.calocallo.sicape.ui.main.editpersonel.media_info

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.MedInfoReq
import id.calocallo.sicape.network.response.MedInfoResp
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_med_info.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMedInfoActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var medInfoReq = MedInfoReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_med_info)

        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL")

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama
        sessionManager1 = SessionManager1(this)

        val medInfo = intent.extras?.getParcelable<MedInfoResp>("MED_INFO")
        apiDetailMedInfo(medInfo)
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_med_info_edit.gone()
            btn_delete_med_info_edit.gone()
        }
        bindProgressButton(btn_save_med_info_edit)
        btn_save_med_info_edit.attachTextChangeAnimator()
        btn_save_med_info_edit.setOnClickListener {
            doUpdateMedInfo(medInfo)

        }

        btn_delete_med_info_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    doDeleteMedIfo(medInfo)
                }
                negativeButton("Tidak") {}
            }.show()
        }
//        getMedInfo(medInfo)
    }

    private fun apiDetailMedInfo(medInfo: MedInfoResp?) {
        NetworkConfig().getServPers()
            .getDetailMedInfo("Bearer ${sessionManager1.fetchAuthToken()}", medInfo?.id.toString())
            .enqueue(object : Callback<MedInfoResp> {
                override fun onFailure(call: Call<MedInfoResp>, t: Throwable) {
                    Toast.makeText(this@EditMedInfoActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<MedInfoResp>, response: Response<MedInfoResp>) {
                    if (response.isSuccessful) {
                        getMedInfo(response.body())
                    } else {
                        Toast.makeText(
                            this@EditMedInfoActivity,
                            "Error Koneksi",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            })
    }

    private fun getMedInfo(medInfo: MedInfoResp?) {
        edt_nama_med_info_edit.setText(medInfo?.sumber)
        edt_topik_med_info_edit.setText(medInfo?.topik)
        edt_alasan_med_info_edit.setText(medInfo?.alasan)
        edt_ket_med_info_edit.setText(medInfo?.keterangan)
    }

    private fun doUpdateMedInfo(medInfo: MedInfoResp?) {
        btn_save_med_info_edit.showProgress {
            progressColor = Color.WHITE
        }
        //animatedButton
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
//        Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        medInfoReq.sumber = edt_nama_med_info_edit.text.toString()
        medInfoReq.topik = edt_topik_med_info_edit.text.toString()
        medInfoReq.alasan = edt_alasan_med_info_edit.text.toString()
        medInfoReq.keterangan = edt_ket_med_info_edit.text.toString()

        NetworkConfig().getServPers().updateMedInfoSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            medInfo?.id.toString(),
            medInfoReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_med_info_edit.hideDrawable(R.string.save)
                Toast.makeText(this@EditMedInfoActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_med_info_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
//                    Toast.makeText(this@EditMedInfoActivity, R.string.data_saved, Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_med_info_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_med_info_edit.hideDrawable(R.string.not_update)
                }
            }
        })
    }

    private fun doDeleteMedIfo(medInfo: MedInfoResp?) {
        NetworkConfig().getServPers().deleteMedInfo(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            medInfo?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditMedInfoActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditMedInfoActivity,
                        R.string.data_deleted,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@EditMedInfoActivity, R.string.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }
}