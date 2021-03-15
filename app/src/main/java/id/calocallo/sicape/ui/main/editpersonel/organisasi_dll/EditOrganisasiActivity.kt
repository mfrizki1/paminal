package id.calocallo.sicape.ui.main.editpersonel.organisasi_dll

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.OrganisasiReq
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_organisasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditOrganisasiActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var organisasiReq = OrganisasiReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_organisasi)

        setupActionBarWithBackButton(toolbar)
        val bundle = intent.extras
        val org = bundle?.getParcelable<OrganisasiResp>("ORGANISASI")
        val detail = bundle?.getParcelable<AllPersonelModel1>("PERSONEL")
        supportActionBar?.title = detail?.nama
        sessionManager1 = SessionManager1(this)

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_organisasi_edit.gone()
            btn_delete_organisasi_edit.gone()
        }

        getDetail(org)


        btn_save_organisasi_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_organisasi_edit)
        btn_save_organisasi_edit.setOnClickListener {
            if (org != null) {
                doUpdateOrg(org)
            }
        }
        btn_delete_organisasi_edit.setOnClickListener {
            alert("Hapus Data", "Yakin Hapus?") {
                positiveButton("Iya") {
                    if (org != null) {
                        doDeleteOrg(org)
                    }
                }
                negativeButton("Tidak") {
                }
            }.show()
        }


    }

    private fun getDetail(org: OrganisasiResp?) {
        NetworkConfig().getServPers().getDetailOrga(
            "Bearer ${sessionManager1.fetchAuthToken()}", org?.id.toString()
        ).enqueue(object : Callback<DetailOrganisasiResp> {
            override fun onFailure(call: Call<DetailOrganisasiResp>, t: Throwable) {
                Toast.makeText(this@EditOrganisasiActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<DetailOrganisasiResp>,
                response: Response<DetailOrganisasiResp>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    edt_organisasi_edit.setText(data?.organisasi)
                    edt_thn_awal_organisasi_edit.setText(data?.tahun_awal)
                    edt_thn_akhir_organisasi_edit.setText(data?.tahun_akhir)
                    edt_kedudukan_organisasi_edit.setText(data?.jabatan)
                    edt_thn_ikut_organisasi_edit.setText(data?.tahun_bergabung)
                    edt_alamat_organisasi_edit.setText(data?.alamat)
                    edt_ket_organisasi_edit.setText(data?.keterangan)
                } else {
                    Toast.makeText(this@EditOrganisasiActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

    }

    private fun doUpdateOrg(org: OrganisasiResp) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        organisasiReq.organisasi = edt_organisasi_edit.text.toString()
        organisasiReq.tahun_awal = edt_thn_awal_organisasi_edit.text.toString()
        organisasiReq.tahun_akhir = edt_thn_akhir_organisasi_edit.text.toString()
        organisasiReq.jabatan = edt_kedudukan_organisasi_edit.text.toString()
        organisasiReq.tahun_bergabung = edt_thn_ikut_organisasi_edit.text.toString()
        organisasiReq.alamat = edt_alamat_organisasi_edit.text.toString()
        organisasiReq.keterangan = edt_ket_organisasi_edit.text.toString()

        btn_save_organisasi_edit.showProgress {
            progressColor = Color.WHITE
        }

        NetworkConfig().getServPers().updateOrganisasiSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            org.id.toString(),
            organisasiReq
        ).enqueue(object : Callback<Base1Resp<AddOrganisasiResp>> {
            override fun onFailure(call: Call<Base1Resp<AddOrganisasiResp>>, t: Throwable) {
                btn_save_organisasi_edit.hideDrawable(R.string.save)
                Toast.makeText(this@EditOrganisasiActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<Base1Resp<AddOrganisasiResp>>,
                response: Response<Base1Resp<AddOrganisasiResp>>
            ) {
                if (response.isSuccessful) {
                    btn_save_organisasi_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
//                    Toast.makeText(this@EditOrganisasiActivity, R.string.data_updated, Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_organisasi_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_organisasi_edit.hideDrawable(R.string.not_update)

                }
            }
        })
    }

    private fun doDeleteOrg(org: OrganisasiResp) {
        NetworkConfig().getServPers().deleteOrganisasi(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            org.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditOrganisasiActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditOrganisasiActivity,
                        "Berhasil Hapus Data Organisasi",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@EditOrganisasiActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}