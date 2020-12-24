package id.calocallo.sicape.ui.main.editpersonel.organisasi_dll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PerjuanganCitaReq
import id.calocallo.sicape.model.PerjuanganResp
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_perjuangan_cita.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPerjuanganCitaActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var perjuanganCitaReq = PerjuanganCitaReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perjuangan_cita)
        sessionManager = SessionManager(this)
        val bundle = intent.extras
        val detail = bundle?.getParcelable<PersonelModel>("PERSONEL")
        val perjuangan = bundle?.getParcelable<PerjuanganResp>("PERJUANGAN")

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detail?.nama

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_perjuangan_edit.gone()
            btn_delete_perjuangan_edit.gone()
        }

        edt_peristiwa_edit.setText(perjuangan?.peristiwa)
        edt_tempat_peristiwa_edit.setText(perjuangan?.lokasi)
        edt_thn_awal_perjuangan_edit.setText(perjuangan?.tahun_awal)
        edt_thn_akhir_perjuangan_edit.setText(perjuangan?.tahun_akhir)
        edt_rangka_perjuangan_edit.setText(perjuangan?.dalam_rangka)
        edt_ket_perjuangan_edit.setText(perjuangan?.keterangan)

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


    private fun doUpdatePerjuangan(perjuangan: PerjuanganResp?) {
        perjuanganCitaReq.peristiwa = edt_peristiwa_edit.text.toString()
        perjuanganCitaReq.lokasi = edt_tempat_peristiwa_edit.text.toString()
        perjuanganCitaReq.tahun_awal = edt_thn_awal_perjuangan_edit.text.toString()
        perjuanganCitaReq.tahun_akhir = edt_thn_akhir_perjuangan_edit.text.toString()
        perjuanganCitaReq.dalam_rangka = edt_rangka_perjuangan_edit.text.toString()
        perjuanganCitaReq.keterangan = edt_ket_perjuangan_edit.text.toString()

        NetworkConfig().getService().updatePerjuanganSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            perjuangan?.id.toString(),
            perjuanganCitaReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditPerjuanganCitaActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(this@EditPerjuanganCitaActivity, "Berhasil Update Data Perjuangan Cita-Cita", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@EditPerjuanganCitaActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun doDeletePerjuangan(perjuangan: PerjuanganResp?) {
        NetworkConfig().getService().deletePerjuangan(
            "Bearer ${sessionManager.fetchAuthToken()}",
            perjuangan?.id.toString()
        ).enqueue(object :Callback<BaseResp>{
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditPerjuanganCitaActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(this@EditPerjuanganCitaActivity, "Berhasil Hapus Data Perjuangan Cita-Cita", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@EditPerjuanganCitaActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}