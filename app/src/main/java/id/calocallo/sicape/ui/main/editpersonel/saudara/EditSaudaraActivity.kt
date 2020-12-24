package id.calocallo.sicape.ui.main.editpersonel.saudara

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SaudaraReq
import id.calocallo.sicape.model.SaudaraResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_saudara.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSaudaraActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var saudaraReq = SaudaraReq()
    private var tempSttsIktn: String? = null
    private var tempJK: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_saudara)
        sessionManager = SessionManager(this)
        val namaPersonel = intent.extras?.getString("NAMA_PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = namaPersonel

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_delete_saudara_edit.gone()
            btn_save_saudara_edit.gone()
        }

        val saudara = intent.extras?.getParcelable<SaudaraResp>("SAUDARA")
        when (saudara?.status_ikatan) {
            "kandung" -> tempSttsIktn = "Kandung"
            "angkat" -> tempSttsIktn = "Angkat"
            "tiri" -> tempSttsIktn = "Tiri"
        }
        when (saudara?.jenis_kelamin) {
            "laki_laki" -> tempJK = "Laki-Laki"
            "perempuan" -> tempJK = "Perempuan"
        }

        saudaraReq.status_ikatan = saudara?.status_ikatan
        saudaraReq.jenis_kelamin = saudara?.jenis_kelamin


        btn_save_saudara_edit.setOnClickListener {
            doUpdateSaudara(saudara)
        }

        btn_delete_saudara_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    doDeleteSaudara(saudara)
                }
                negativeButton("Tidak") {}
            }.show()
        }

        val listStts = listOf("Kandung", "Angkat", "Tiri")
        sp_ikatan_saudara_edit.setText(tempSttsIktn)
        val adapterStts = ArrayAdapter(this, R.layout.item_spinner, listStts)
        sp_ikatan_saudara_edit.setAdapter(adapterStts)
        sp_ikatan_saudara_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> saudaraReq.status_ikatan = "kandung"
                1 -> saudaraReq.status_ikatan = "angkat"
                2 -> saudaraReq.status_ikatan = "tiri"

            }
        }
        val listJK = listOf("Laki-Laki", "Perempuan")
        spinner_jk_saudara_edit.setText(tempJK)
        val adapterJK = ArrayAdapter(this, R.layout.item_spinner, listJK)
        spinner_jk_saudara_edit.setAdapter(adapterJK)
        spinner_jk_saudara_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> saudaraReq.jenis_kelamin = "laki_laki"
                1 -> saudaraReq.jenis_kelamin = "perempuan"
            }
        }

        edt_nama_lengkap_saudara_edit.setText(saudara?.nama)
        edt_tmpt_ttl_saudara_edit.setText(saudara?.tempat_lahir)
        edt_tgl_ttl_saudara_edit.setText(saudara?.tanggal_lahir)
        edt_pekerjaan_saudara_edit.setText(saudara?.pekerjaan_atau_sekolah)
        edt_organisasi_saudara_edit.setText(saudara?.organisasi_yang_diikuti)
        edt_ket_saudara_edit.setText(saudara?.keterangan)
    }

    private fun doUpdateSaudara(saudara: SaudaraResp?) {
        saudaraReq.nama = edt_nama_lengkap_saudara_edit.text.toString()
        saudaraReq.tempat_lahir = edt_tmpt_ttl_saudara_edit.text.toString()
        saudaraReq.tanggal_lahir = edt_tgl_ttl_saudara_edit.text.toString()
        saudaraReq.pekerjaan_atau_sekolah = edt_pekerjaan_saudara_edit.text.toString()
        saudaraReq.organisasi_yang_diikuti = edt_organisasi_saudara_edit.text.toString()
        saudaraReq.keterangan = edt_ket_saudara_edit.text.toString()

        NetworkConfig().getService().updateSaudaraSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            saudara?.id.toString(),
            saudaraReq
        ).enqueue(object: Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditSaudaraActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(this@EditSaudaraActivity, "Berhasil Update Data Saudara", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@EditSaudaraActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun doDeleteSaudara(saudara: SaudaraResp?) {
        NetworkConfig().getService().deleteSaudara(
            "Bearer ${sessionManager.fetchAuthToken()}",
            saudara?.id.toString()
        ).enqueue(object: Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditSaudaraActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(this@EditSaudaraActivity, "Berhasil Hapus Data Saudara", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@EditSaudaraActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}