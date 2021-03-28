package id.calocallo.sicape.ui.main.lhp.edit.terlapor

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.KetTerlaporReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_edit_terlapor_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditTerlaporLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var terlaporReq = KetTerlaporReq()
    private var idPersonelTerlapor: Int? = null
    private var namaPersonelTerlapor: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_terlapor_lhp)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Terlapor"
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_terlapor_edit.gone()
            btn_delete_terlapor_edit.gone()
        }
        val terlapor = intent.extras?.getParcelable<PersonelPenyelidikResp>(EDIT_TERLAPOR)
        apiViewEditTerlapor(terlapor)
//        getViewTerlapor(terlapor)
        btn_save_terlapor_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_terlapor_edit)
        btn_save_terlapor_edit.setOnClickListener {
            btn_save_terlapor_edit.showProgress { progressColor = Color.WHITE }
            updateTerlapor(terlapor)

        }

        btn_delete_terlapor_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteTerlapor(terlapor)
                }
                negativeButton("Tidak") {}
            }.show()
        }

/*set button for add personel terlapor*/
        bt_choose_personel_terlapor_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_EDIT_TERLAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun apiViewEditTerlapor(terlapor: PersonelPenyelidikResp?) {
        NetworkConfig().getServLhp()
            .detailPersTerlapor("Bearer ${sessionManager1.fetchAuthToken()}", terlapor?.id)
            .enqueue(object :
                Callback<PersonelPenyelidikResp> {
                override fun onResponse(
                    call: Call<PersonelPenyelidikResp>,
                    response: Response<PersonelPenyelidikResp>
                ) {
                    if (response.isSuccessful) {
                        getViewTerlapor(response.body())
                    } else {
                        toast("Error")
                    }
                }

                override fun onFailure(call: Call<PersonelPenyelidikResp>, t: Throwable) {
                    toast("$t")
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personelTerlapor = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_EDIT_TERLAPOR) {
            idPersonelTerlapor = personelTerlapor?.id
            namaPersonelTerlapor = personelTerlapor?.nama
            txt_nama_terlapor_edit.text = "Nama : ${personelTerlapor?.nama}"
            txt_pangkat_terlapor_edit.text =
                "Pangkat : ${personelTerlapor?.pangkat.toString().toUpperCase()}"
            txt_nrp_terlapor_edit.text = "NRP : ${personelTerlapor?.nrp}"
            txt_jabatan_terlapor_edit.text = "Jabatan : ${personelTerlapor?.jabatan}"
            txt_kesatuan_terlapor_edit.text =
                "Kesatuan : ${personelTerlapor?.satuan_kerja?.kesatuan.toString().toUpperCase()}"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getViewTerlapor(terlapor: PersonelPenyelidikResp?) {
        idPersonelTerlapor = terlapor?.personel?.id
        edt_ket_terlapor_edit.setText(terlapor?.detail_keterangan)
        txt_nama_terlapor_edit.text = "Nama : ${terlapor?.personel?.nama}"
        txt_pangkat_terlapor_edit.text =
            "Pangkat : ${terlapor?.personel?.pangkat.toString().toUpperCase()}"
        txt_nrp_terlapor_edit.text = "NRP : ${terlapor?.personel?.nrp}"
        txt_jabatan_terlapor_edit.text = "Jabatan : ${terlapor?.personel?.jabatan}"
        txt_kesatuan_terlapor_edit.text =
            "Kesatuan : ${terlapor?.personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"
    }

    private fun updateTerlapor(terlapor: PersonelPenyelidikResp?) {
        Log.e("terlapor", "$terlapor")
        terlaporReq.detail_keterangan = edt_ket_terlapor_edit.text.toString()
        terlaporReq.id_personel = idPersonelTerlapor
        apUpdTerlapor(terlapor)
//        terlaporReq.nama_pesonel = namaPersonelTerlapor
//        terlaporReq.detail_keterangan = edt_ket_terlapor_edit.text.toString()
//        Log.e("editTlrepo", "$terlaporReq")
//        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
//        btn_save_terlapor_edit.showProgress {
//            progressColor = Color.WHITE
//        }
//        btn_save_terlapor_edit.showDrawable(animatedDrawable) {
//            buttonTextRes = R.string.data_updated
//            textMarginRes = R.dimen.space_10dp
//
//        }
//        Handler(Looper.getMainLooper()).postDelayed({
//            btn_save_terlapor_edit.hideDrawable(R.string.save)
//        }, 3000)
    }

    private fun apUpdTerlapor(terlapor: PersonelPenyelidikResp?) {
        NetworkConfig().getServLhp().updPersTerlapor(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            terlapor?.id,
            terlaporReq
        ).enqueue(object : Callback<Base1Resp<KetTerlaporLhpResp>> {
            override fun onResponse(
                call: Call<Base1Resp<KetTerlaporLhpResp>>,
                response: Response<Base1Resp<KetTerlaporLhpResp>>
            ) {
                if (response.body()?.message == "Data personel terlapor updated succesfully") {
                    btn_save_terlapor_edit.hideProgress(R.string.data_updated)
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 750)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_terlapor_edit.hideProgress(R.string.save)
                    }, 1000)
                    btn_save_terlapor_edit.hideProgress(R.string.not_update)
                }
            }

            override fun onFailure(call: Call<Base1Resp<KetTerlaporLhpResp>>, t: Throwable) {
                toast("$t")
            }
        })
    }

    private fun deleteTerlapor(terlapor: PersonelPenyelidikResp?) {
        NetworkConfig().getServLhp()
            .delPersTerlapor("Bearer ${sessionManager1.fetchAuthToken()}", terlapor?.id)
            .enqueue(object : Callback<BaseResp> {
                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.body()?.message == "Data personel terlapor removed succesfully") {
                        toast(R.string.data_deleted)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()

                        }, 1000)
                    } else {
                        toast("${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    toast("$t")
                }
            })
    }

    companion object {
        const val EDIT_TERLAPOR = "EDIT_TERLAPOR"
        const val REQ_EDIT_TERLAPOR = 111
    }
}