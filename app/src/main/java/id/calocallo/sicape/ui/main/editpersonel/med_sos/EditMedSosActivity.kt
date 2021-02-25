package id.calocallo.sicape.ui.main.editpersonel.med_sos

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.MedSosReq
import id.calocallo.sicape.network.response.MedSosResp
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_med_sos.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMedSosActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var medSosReq = MedSosReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_med_sos)

        sessionManager1 = SessionManager1(this)
        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_medsos_edit.gone()
            btn_delete_medsos_edit.gone()
        }

        val medsos = intent.extras?.getParcelable<MedSosResp>("MEDSOS")
        apiDetailSos(medsos)
        btn_save_medsos_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_medsos_edit)
        btn_save_medsos_edit.setOnClickListener {
            updateMedsos(medsos)
        }
        btn_delete_medsos_edit.attachTextChangeAnimator()
        bindProgressButton(btn_delete_medsos_edit)
        btn_delete_medsos_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteMedsos(medsos)
                }
                negativeButton("Tidak") {}
            }.show()
        }
//        getMedsos(medsos)

    }

    private fun apiDetailSos(medsos: MedSosResp?) {
        NetworkConfig().getServPers()
            .getDetailMedSos("Bearer ${sessionManager1.fetchAuthToken()}", medsos?.id.toString())
            .enqueue(object : Callback<MedSosResp> {
                override fun onFailure(call: Call<MedSosResp>, t: Throwable) {
                    Toast.makeText(this@EditMedSosActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<MedSosResp>, response: Response<MedSosResp>) {
                    if (response.isSuccessful) {
                        getMedsos(response.body())
                    } else {
                        Toast.makeText(this@EditMedSosActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

    private fun getMedsos(medsos: MedSosResp?) {
        edt_nama_medsos_edit.setText(medsos?.nama_medsos)
        edt_acc_medsos_edit.setText(medsos?.nama_akun)
        edt_alasan_medsos_edit.setText(medsos?.alasan)
        edt_ket_medsos_edit.setText(medsos?.keterangan)
    }

    private fun updateMedsos(medsos: MedSosResp?) {
        btn_save_medsos_edit.showProgress {
            progressColor = Color.WHITE
        }
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        medSosReq.nama_medsos = edt_nama_medsos_edit.text.toString()
        medSosReq.nama_akun = edt_acc_medsos_edit.text.toString()
        medSosReq.alasan = edt_alasan_medsos_edit.text.toString()
        medSosReq.keterangan = edt_ket_medsos_edit.text.toString()
        NetworkConfig().getServPers().updateMedSosSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            medsos?.id.toString(),
            medSosReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_medsos_edit.hideDrawable(R.string.save)
                Toast.makeText(this@EditMedSosActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_medsos_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
//                    Toast.makeText(this@EditMedSosActivity, R.string.data_updated, Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_medsos_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_medsos_edit.hideDrawable(R.string.not_update)
                }
            }

        })

    }

    private fun deleteMedsos(medsos: MedSosResp?) {
        NetworkConfig().getServPers().deleteMedSos(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            medsos?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditMedSosActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditMedSosActivity,
                        R.string.data_deleted,
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Toast.makeText(this@EditMedSosActivity, R.string.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }
}