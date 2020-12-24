package id.calocallo.sicape.ui.main.editpersonel.anak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AnakReq
import id.calocallo.sicape.model.AnakResp
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_anak.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditAnakActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var anakReq = AnakReq()
    private var tempSttsIktn: String? = null
    private var tempJK: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_anak)
        sessionManager = SessionManager(this)
        val namaPersonel = intent.extras?.getString("NAMA_PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = namaPersonel

        val anak = intent.extras?.getParcelable<AnakResp>("ANAK")


        //validasi
        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_single_anak_edit.gone()
            btn_delete_single_anak_edit.gone()
        }
        when (anak?.status_ikatan) {
            "kandung" -> tempSttsIktn = "Kandung"
            "angkat" -> tempSttsIktn = "Angkat"
            "tiri" -> tempSttsIktn = "Tiri"
        }
        when(anak?.jenis_kelamin){
            "laki_laki"-> tempJK= "Laki-Laki"
            "perempuan"-> tempJK= "Perempuan"
        }

        anakReq.status_ikatan = anak?.status_ikatan
        anakReq.jenis_kelamin = anak?.jenis_kelamin

        //spinner
        val listStts = listOf("Kandung", "Angkat","Tiri")
        sp_status_ikatan_edit.setText(tempSttsIktn)
        val adapterStts = ArrayAdapter(this, R.layout.item_spinner, listStts)
        sp_status_ikatan_edit.setAdapter(adapterStts)
        sp_status_ikatan_edit.setOnItemClickListener { parent, view, position, id ->
            when(position){
                0->{anakReq.status_ikatan = "kandung"}
                1->{anakReq.status_ikatan = "angkat"}
                2->{anakReq.status_ikatan = "tiri"}
            }
        }
        val listJK = listOf("Laki-Laki","Perempuan")
        spinner_jk_anak_edit.setText(tempJK)
        val adapterJK = ArrayAdapter(this, R.layout.item_spinner, listJK)
        spinner_jk_anak_edit.setAdapter(adapterJK)
        spinner_jk_anak_edit.setOnItemClickListener { parent, view, position, id ->
            when(position){
                0->{anakReq.jenis_kelamin = "laki_laki"}
                1->{anakReq.jenis_kelamin = "perempuan"}
            }
        }

        edt_nama_lengkap_anak_edit.setText(anak?.nama)
        edt_tmpt_ttl_anak_edit.setText(anak?.tempat_lahir)
        edt_tgl_ttl_anak_edit.setText(anak?.tanggal_lahir)
        edt_pekerjaan_anak_edit.setText(anak?.pekerjaan_atau_sekolah)
        edt_organisasi_anak_edit.setText(anak?.organisasi_yang_diikuti)
        edt_ket_anak_edit.setText(anak?.keterangan)

        btn_save_single_anak_edit.setOnClickListener {
            doUpdateAnak(anak)
        }
        btn_delete_single_anak_edit.setOnClickListener {
            alert("Yakin Hapus Data"){
                positiveButton("Iya"){
                    doDeleteAnak(anak)
                }
                negativeButton("Tidak"){}
            }.show()
        }
    }

    private fun doUpdateAnak(anak: AnakResp?) {
        anakReq.nama = edt_nama_lengkap_anak_edit.text.toString()
        anakReq.tempat_lahir = edt_tmpt_ttl_anak_edit.text.toString()
        anakReq.tanggal_lahir = edt_tgl_ttl_anak_edit.text.toString()
        anakReq.pekerjaan_atau_sekolah = edt_pekerjaan_anak_edit.text.toString()
        anakReq.organisasi_yang_diikuti = edt_organisasi_anak_edit.text.toString()
        anakReq.keterangan = edt_ket_anak_edit.text.toString()

        NetworkConfig().getService().updateAnakSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            anak?.id.toString(),
            anakReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditAnakActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(this@EditAnakActivity, "Berhasil Update Data Anak", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@EditAnakActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun doDeleteAnak(anak:AnakResp?){
        NetworkConfig().getService().deleteAnak(
            "Bearer ${sessionManager.fetchAuthToken()}",
            anak?.id.toString()
        ).enqueue(object :Callback<BaseResp>{
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditAnakActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(this@EditAnakActivity, "Berhasil Hapus Data Anak", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@EditAnakActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}