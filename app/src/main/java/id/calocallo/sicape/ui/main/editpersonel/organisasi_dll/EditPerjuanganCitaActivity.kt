package id.calocallo.sicape.ui.main.editpersonel.organisasi_dll

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PerjuanganCitaReq
import id.calocallo.sicape.network.response.PerjuanganResp
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.DetailPerjuanganResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_edit_perjuangan_cita.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPerjuanganCitaActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var perjuanganCitaReq =
        PerjuanganCitaReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perjuangan_cita)
        sessionManager1 = SessionManager1(this)
        val bundle = intent.extras
        val detail = bundle?.getParcelable<AllPersonelModel1>("PERSONEL")
        val perjuangan = bundle?.getParcelable<PerjuanganResp>("PERJUANGAN")

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detail?.nama

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_perjuangan_edit.gone()
            btn_delete_perjuangan_edit.gone()
        }
        getDetailPerjuangan(perjuangan)


        btn_save_perjuangan_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_perjuangan_edit)
        btn_save_perjuangan_edit.setOnClickListener {
            doUpdatePerjuangan(perjuangan)
        }

        btn_delete_perjuangan_edit.setOnClickListener {
            alert("Hapus Data", "Yakin Hapus?") {
                positiveButton("Iya") {
                    doDeletePerjuangan(perjuangan)
                }
                negativeButton("Tidak") {
                }
            }.show()
        }
    }

    private fun getDetailPerjuangan(perjuangan: PerjuanganResp?) {
        NetworkConfig().getServPers().getDetailPerjuangan(
            "Bearer ${sessionManager1.fetchAuthToken()}", perjuangan?.id.toString()
        ).enqueue(object : Callback<DetailPerjuanganResp> {
            override fun onFailure(call: Call<DetailPerjuanganResp>, t: Throwable) {
                toast("$t")
            }

            override fun onResponse(
                call: Call<DetailPerjuanganResp>,
                response: Response<DetailPerjuanganResp>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    edt_peristiwa_edit.setText(data?.peristiwa)
                    edt_tempat_peristiwa_edit.setText(data?.lokasi)
                    edt_thn_awal_perjuangan_edit.setText(data?.tahun_awal)
                    edt_thn_akhir_perjuangan_edit.setText(data?.tahun_akhir)
                    edt_rangka_perjuangan_edit.setText(data?.dalam_rangka)
                    edt_ket_perjuangan_edit.setText(data?.keterangan)
                } else {
                    toast(R.string.error_conn)
                }
            }
        })

    }


    private fun doUpdatePerjuangan(perjuangan: PerjuanganResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
        btn_save_perjuangan_edit.showProgress {
            progressColor = Color.WHITE
        }

        perjuanganCitaReq.peristiwa = edt_peristiwa_edit.text.toString()
        perjuanganCitaReq.lokasi = edt_tempat_peristiwa_edit.text.toString()
        perjuanganCitaReq.tahun_awal = edt_thn_awal_perjuangan_edit.text.toString()
        perjuanganCitaReq.tahun_akhir = edt_thn_akhir_perjuangan_edit.text.toString()
        perjuanganCitaReq.dalam_rangka = edt_rangka_perjuangan_edit.text.toString()
        perjuanganCitaReq.keterangan = edt_ket_perjuangan_edit.text.toString()

        NetworkConfig().getServPers().updatePerjuanganSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            perjuangan?.id.toString(),
            perjuanganCitaReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_perjuangan_edit.hideDrawable(R.string.save)
                toast("$t")
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_perjuangan_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_perjuangan_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_perjuangan_edit.hideDrawable(R.string.not_update)
                }
            }
        }
        )
    }

    private fun doDeletePerjuangan(perjuangan: PerjuanganResp?) {
        NetworkConfig().getServPers().deletePerjuangan(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            perjuangan?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                toast("$t")

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    toast(R.string.data_deleted)
                    finish()
                } else {
                    toast("${response.body()?.message}")
                }
            }
        })
    }
}