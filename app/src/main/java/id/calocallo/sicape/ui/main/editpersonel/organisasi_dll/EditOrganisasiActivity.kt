package id.calocallo.sicape.ui.main.editpersonel.organisasi_dll

import android.os.Bundle
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.OrganisasiReq
import id.calocallo.sicape.model.OrganisasiResp
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_organisasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditOrganisasiActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var organisasiReq = OrganisasiReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_organisasi)

        setupActionBarWithBackButton(toolbar)
        val bundle = intent.extras
        val org = bundle?.getParcelable<OrganisasiResp>("ORGANISASI")
        val detail = bundle?.getParcelable<PersonelModel>("PERSONEL")
        supportActionBar?.title = detail?.nama
        sessionManager = SessionManager(this)

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_organisasi_edit.gone()
            btn_delete_organisasi_edit.gone()
        }


        edt_organisasi_edit.setText(org?.organisasi)
        edt_thn_awal_organisasi_edit.setText(org?.tahun_awal)
        edt_thn_akhir_organisasi_edit.setText(org?.tahun_akhir)
        edt_kedudukan_organisasi_edit.setText(org?.jabatan)
        edt_thn_ikut_organisasi_edit.setText(org?.tahun_bergabung)
        edt_alamat_organisasi_edit.setText(org?.alamat)
        edt_ket_organisasi_edit.setText(org?.keterangan)

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

    private fun doUpdateOrg(org: OrganisasiResp) {
        organisasiReq.organisasi = edt_organisasi_edit.text.toString()
        organisasiReq.tahun_awal = edt_thn_awal_organisasi_edit.text.toString()
        organisasiReq.tahun_akhir = edt_thn_akhir_organisasi_edit.text.toString()
        organisasiReq.jabatan = edt_kedudukan_organisasi_edit.text.toString()
        organisasiReq.tahun_bergabung = edt_thn_ikut_organisasi_edit.text.toString()
        organisasiReq.alamat = edt_alamat_organisasi_edit.text.toString()
        organisasiReq.keterangan = edt_ket_organisasi_edit.text.toString()
        NetworkConfig().getService().updateOrganisasiSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            org.id.toString(),
            organisasiReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditOrganisasiActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(this@EditOrganisasiActivity, "Berhasil Update Data Organisasi", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@EditOrganisasiActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun doDeleteOrg(org: OrganisasiResp) {
        NetworkConfig().getService().deleteOrganisasi(
            "Bearer ${sessionManager.fetchAuthToken()}",
            org.id.toString()
        ).enqueue(object :Callback<BaseResp>{
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditOrganisasiActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(this@EditOrganisasiActivity, "Berhasil Hapus Data Organisasi", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@EditOrganisasiActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}