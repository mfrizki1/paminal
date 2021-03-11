package id.calocallo.sicape.ui.gelar

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.LhgReq
import id.calocallo.sicape.network.response.AddLhgResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.LhgResp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_gelar.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditGelarActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var detailLhg: LhgResp? = null
    private var lhgReq = LhgReq()
    private var pangkatPimpinan: String? = null
    private var namaPimpinan: String? = null
    private var nrpPimpinan: String? = null
    private var pangkatPemapar: String? = null
    private var namaPemapar: String? = null
    private var namaNotulen: String? = null
    private var pangkatNotulen: String? = null
    private var nrpNotulen: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_gelar)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Gelar Perkara"

        detailLhg = intent.getParcelableExtra(DetailGelarActivity.DETAIL_LHG)
        viewEditLhg(detailLhg)

        btn_choose_personel_pemapar_lhg_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PEMAPAR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_choose_personel_pimpinan_lhg_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PIMPINAN)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_choose_personel_notulen_lhg_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_NOTULEN)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_save_lhg_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_lhg_edit)
        btn_save_lhg_edit.setOnClickListener {
            btn_save_lhg_edit.showProgress { progressColor = Color.WHITE }
            apiUpdLhg(detailLhg)
        }
    }

    private fun setLhgReq(): LhgReq {
        lhgReq.dugaan = edt_dugaan_tindak_lhg_edit.editText?.text.toString()
        lhgReq.pangkat_yang_menangani = edt_pangkat_menangani_lhg_edit.editText?.text.toString()
        lhgReq.nama_yang_menangani = edt_nama_menangani_lhg_edit.editText?.text.toString()
        lhgReq.dasar = edt_dasar_lhg_edit.editText?.text.toString()
        lhgReq.tanggal = edt_tanggal_lhg_edit.editText?.text.toString()
        lhgReq.waktu_mulai = edt_waktu_mulai_lhg_edit.editText?.text.toString()
        lhgReq.waktu_selesai = edt_waktu_selesai_lhg_edit.editText?.text.toString()
        lhgReq.tempat = edt_tempat_lhg_edit.editText?.text.toString()
        lhgReq.pangkat_pimpinan = pangkatPimpinan
        lhgReq.nama_pimpinan = namaPimpinan
        lhgReq.nrp_pimpinan = nrpPimpinan
        lhgReq.pangkat_pemapar = pangkatPemapar
        lhgReq.nama_pemapar = namaPemapar
        lhgReq.kronologis_kasus = edt_kronologis_kasus_gelar_edit.editText?.text.toString()
        lhgReq.no_surat_perintah_penyidikan =
            edt_surat_penyidikan_gelar_edit.editText?.text.toString()
        lhgReq.nama_notulen = namaNotulen
        lhgReq.pangkat_notulen = pangkatNotulen
        lhgReq.nrp_notulen = nrpNotulen
        return lhgReq
    }

    private fun apiUpdLhg(dataLhg: LhgResp?) {
        NetworkConfig().getServLhg()
            .updLhg("Bearer ${sessionManager1.fetchAuthToken()}", dataLhg?.id, setLhgReq()).enqueue(
                object :
                    Callback<Base1Resp<AddLhgResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddLhgResp>>,
                        response: Response<Base1Resp<AddLhgResp>>
                    ) {
                        if (response.body()?.message == "Data lhg updated succesfully") {
                            btn_save_lhg_edit.hideProgress(R.string.data_updated)
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            },750)
                        } else {
                            btn_save_lhg_edit.hideProgress(R.string.not_update)

                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddLhgResp>>, t: Throwable) {
                        Toast.makeText(this@EditGelarActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_save_lhg_edit.hideProgress(R.string.not_update)
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun viewEditLhg(detailLhg: LhgResp?) {
//        txt_no_lp_choose_lhg_edit.text= "No LP: ${detailLhg?.}"
        edt_dugaan_tindak_lhg_edit.editText?.setText(detailLhg?.dugaan)
        edt_dasar_lhg_edit.editText?.setText(detailLhg?.dasar)
        edt_tanggal_lhg_edit.editText?.setText(detailLhg?.tanggal)
        edt_tempat_lhg_edit.editText?.setText(detailLhg?.tempat)
        edt_kronologis_kasus_gelar_edit.editText?.setText(detailLhg?.kronologis_kasus)
        edt_surat_penyidikan_gelar_edit.editText?.setText(detailLhg?.no_surat_perintah_penyidikan)

        txt_nama_pimpinan_lhg_edit.text = "Nama: ${detailLhg?.nama_pimpinan}"
        txt_pangkat_pimpinan_lhg_edit.text =
            "Pangkat: ${detailLhg?.pangkat_pimpinan.toString().toUpperCase(Locale.ROOT)}"
        txt_nrp_pimpinan_lhg_edit.text = "NRP: ${detailLhg?.nrp_pimpinan}"

        namaPimpinan = detailLhg?.nama_pimpinan
        pangkatPimpinan = detailLhg?.pangkat_pimpinan
        nrpPimpinan = detailLhg?.nrp_pimpinan

        txt_nama_pemapar_lhg_edit.text = "Nama: ${detailLhg?.nama_pemapar}"
        txt_pangkat_pemapar_lhg_edit.text =
            "Pangkat: ${detailLhg?.pangkat_pemapar.toString().toUpperCase(Locale.ROOT)}"
        namaPemapar = detailLhg?.nama_pemapar
        pangkatPemapar = detailLhg?.pangkat_pemapar

        txt_nama_notulen_lhg_edit.text = "Nama: ${detailLhg?.nama_notulen}"
        txt_pangkat_notulen_lhg_edit.text =
            "Pangkat: ${detailLhg?.pangkat_notulen.toString().toUpperCase(Locale.ROOT)}"
        txt_nrp_notulen_lhg_edit.text = "NRP: ${detailLhg?.nrp_notulen}"
        namaNotulen = detailLhg?.nama_notulen
        pangkatNotulen = detailLhg?.pangkat_notulen
        nrpNotulen = detailLhg?.nrp_notulen

        edt_waktu_mulai_lhg_edit.editText?.setText(detailLhg?.waktu_mulai)
        edt_waktu_selesai_lhg_edit.editText?.setText(detailLhg?.waktu_selesai)
        edt_nama_menangani_lhg_edit.editText?.setText(detailLhg?.nama_yang_menangani)
        edt_pangkat_menangani_lhg_edit.editText?.setText(
            detailLhg?.pangkat_yang_menangani.toString().toUpperCase(Locale.ROOT)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_PIMPINAN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val pimpinan = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_nama_pimpinan_lhg_edit.text = "Nama: ${pimpinan?.nama}"
                    txt_pangkat_pimpinan_lhg_edit.text = "Pangkat: ${
                        pimpinan?.pangkat.toString().toUpperCase(Locale.ROOT)
                    }"
                    txt_nrp_pimpinan_lhg_edit.text = "NRP: ${pimpinan?.nrp}"
                    namaPimpinan = pimpinan?.nama
                    nrpPimpinan = pimpinan?.nrp
                    pangkatPimpinan = pimpinan?.pangkat.toString().toUpperCase(Locale.ROOT)
                }
            }
            AddGelarActivity.REQ_PEMAPAR -> {
                if (resultCode == Activity.RESULT_OK) {
                    val pemapar = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_nama_pemapar_lhg_edit.text = "Nama: ${pemapar?.nama}"
                    txt_pangkat_pemapar_lhg_edit.text = "Pangkat: ${
                        pemapar?.pangkat.toString().toUpperCase(Locale.ROOT)
                    }"
                    namaPemapar = pemapar?.nama
                    pangkatPemapar = pemapar?.pangkat.toString().toUpperCase()
                }
            }
            AddGelarActivity.REQ_NOTULEN -> {
                if (resultCode == Activity.RESULT_OK) {
                    val notulen = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_nama_notulen_lhg_edit.text = "Nama: ${notulen?.nama}"
                    txt_pangkat_notulen_lhg_edit.text = "Pangkat: ${
                        notulen?.pangkat.toString().toUpperCase(Locale.ROOT)
                    }"
                    txt_nrp_notulen_lhg_edit.text = "NRP: ${notulen?.nrp}"
                    namaNotulen = notulen?.nama
                    nrpNotulen = notulen?.nrp
                    pangkatNotulen = notulen?.pangkat.toString().toUpperCase()
                }
            }
        }
    }

    companion object {
        const val REQ_PEMAPAR = 2
        const val REQ_PIMPINAN = 3
        const val REQ_NOTULEN = 5
    }
}