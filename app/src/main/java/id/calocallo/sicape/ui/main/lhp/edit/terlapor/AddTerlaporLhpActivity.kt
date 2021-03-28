package id.calocallo.sicape.ui.main.lhp.edit.terlapor

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.KetTerlaporReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.KetTerlaporLhpResp
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.lhp.add.ListKetTerlaporLhpActivity
import id.calocallo.sicape.ui.main.lhp.add.ListKetTerlaporLhpActivity.Companion.GET_TERLAPOR
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_terlapor_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTerlaporLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var ketTerlaporReq = KetTerlaporReq()
    private var idTerlapor: Int? = null
    private var namaTerlapor: String? = null
    private var detailLhp: LhpResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_terlapor_lhp)
        sessionManager1 = SessionManager1(this)
        detailLhp = intent.extras?.getParcelable<LhpResp>(PickTerlaporLhpActivity.ID_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Terlapor"

        /*SET INTENT VALUE*/
        val addKetTerlaporFromAllLHP =
            intent.extras?.getString(ListKetTerlaporLhpActivity.LIST_KET_TERLAPOR)
        btn_add_terlapor_single.attachTextChangeAnimator()
        bindProgressButton(btn_add_terlapor_single)
        btn_add_terlapor_single.setOnClickListener {
            addTerlapor(addKetTerlaporFromAllLHP)
        }

        /*set button for get personel*/
        bt_choose_personel_terlapor.setOnClickListener {
            //TODO CHANGE FILTERING PERSONEL(BERDASARKAN LP YG ADA DI LHP TRS PILIH PERSONELNYA)
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, ADD_TERLAPOR_PERSONEL)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }


    private fun addTerlapor(ketTerlapor: String?) {
        if (ketTerlapor == null) {
//        terlaporReq.nama_terlapor = edt_nama_terlapor_single.text.toString()
            ketTerlaporReq.detail_keterangan = edt_ket_terlapor_add.text.toString()
            ketTerlaporReq.id_personel = idTerlapor
            ketTerlaporReq.nama_pesonel = namaTerlapor

            btn_add_terlapor_single.showProgress {
                progressColor = Color.WHITE
            }
            apiAddTerlapor()
        } else {
            ketTerlaporReq.detail_keterangan = edt_ket_terlapor_add.text.toString()
            ketTerlaporReq.id_personel = idTerlapor
            ketTerlaporReq.nama_pesonel = namaTerlapor
            val intent = Intent()
            intent.putExtra(GET_TERLAPOR, ketTerlaporReq)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun apiAddTerlapor() {
        NetworkConfig().getServLhp().addSingleTerlapor(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            detailLhp?.id,
            ketTerlaporReq
        ).enqueue(object :
            Callback<Base1Resp<KetTerlaporLhpResp>> {
            override fun onResponse(
                call: Call<Base1Resp<KetTerlaporLhpResp>>,
                response: Response<Base1Resp<KetTerlaporLhpResp>>
            ) {
                if (response.body()?.message == "Data personel terlapor saved succesfully") {
                    btn_add_terlapor_single.hideProgress(R.string.data_saved)
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 750)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_add_terlapor_single.hideProgress(R.string.save)
                    }, 1000)
                    btn_add_terlapor_single.hideProgress(R.string.not_save)
                    toast("${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<Base1Resp<KetTerlaporLhpResp>>, t: Throwable) {
                toast("$t")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_TERLAPOR_PERSONEL) {
            idTerlapor = personel?.id
            namaTerlapor = personel?.nama
            txt_nama_terlapor_add.text = "Nama : ${personel?.nama}"
            txt_pangkat_terlapor_add.text =
                "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
            txt_nrp_terlapor_add.text = "NRP : ${personel?.nrp}"
            txt_jabatan_terlapor_add.text = "Jabatan : ${personel?.jabatan}"
            txt_kesatuan_terlapor_add.text =
                "Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"
        }
    }

    companion object {
        const val ADD_TERLAPOR = "ADD_TERLAPOR"
        const val ADD_TERLAPOR_PERSONEL = 121
    }
}