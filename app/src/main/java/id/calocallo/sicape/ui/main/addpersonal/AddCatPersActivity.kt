package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.CatatanPersReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.ui.main.personel.DetailPersonelActivity
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_cat_pers.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCatPersActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var catpersReq = CatatanPersReq()
    private var allPersonelModel = AllPersonelModel()
    var jenis_catper = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cat_pers)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Catatan Personal"

        initSP()

        btn_next_cat_pers.setOnClickListener {
            catpersReq.jenis = jenis_catper
            catpersReq.keterangan = edt_ket_cat_pers.text.toString()
            catpersReq.tanggal_dihukum = edt_tgl_dihukum_cat_pers.text.toString()
            catpersReq.tempat_dihukum = edt_tmpt_dihukum_cat_pers.text.toString()
            catpersReq.tanggal_ditahan = edt_tgl_ditahan_cat_pers.text.toString()
            catpersReq.tempat_ditahan = edt_tmpt_ditahan_cat_pers.text.toString()
            sessionManager.setCatpers(catpersReq)
            Log.e("size Catpers", "${sessionManager.getCatpers()}")

            doSavePersonel()

        }
    }

    private fun doSavePersonel() {
        allPersonelModel.personel = sessionManager.getPersonel()
        allPersonelModel.signalement = sessionManager.getSignalement()
        allPersonelModel.foto = sessionManager.getFoto()
        allPersonelModel.relasi = sessionManager.getRelasi()
        allPersonelModel.pernah_dihukum = sessionManager.getHukuman()
        allPersonelModel.catatan_personel= sessionManager.getCatpers()
        allPersonelModel.riwayat_pendidikan_umum= sessionManager.getPendUmum()
        allPersonelModel.riwayat_pendidikan_kedinasan= sessionManager.getPendDinas()
        allPersonelModel.riwayat_pendidikan_lain_lain= sessionManager.getPendOther()
        allPersonelModel.riwayat_pekerjaan= sessionManager.getPekerjaan()
        allPersonelModel.pekerjaan_diluar_dinas= sessionManager.getPekerjaanDiluar()
        allPersonelModel.riwayat_alamat= sessionManager.getAlamat()
        allPersonelModel.riwayat_organisasi= sessionManager.getOrganisasi()
        allPersonelModel.riwayat_penghargaan= sessionManager.getPenghargaan()
        allPersonelModel.riwayat_perjuangan= sessionManager.getPerjuanganCita()
        allPersonelModel.pasangan= sessionManager.getPasangan()
        allPersonelModel.ayah_kandung= sessionManager.getAyahKandung()
        allPersonelModel.ayah_tiri= sessionManager.getAyahTiri()
        allPersonelModel.ibu_kandung= sessionManager.getIbu()
        allPersonelModel.ibu_tiri= sessionManager.getIbuTiri()
        allPersonelModel.mertua_laki= sessionManager.getMertuaLaki()
        allPersonelModel.mertua_perempuan= sessionManager.getMertuaPerempuan()
        allPersonelModel.anak= sessionManager.getAnak()
        allPersonelModel.saudara= sessionManager.getSaudara()
        allPersonelModel.orang_berjasa= sessionManager.getOrangBerjasa()
        allPersonelModel.orang_disegani= sessionManager.getOrangDisegani()
        allPersonelModel.tokoh_dikagumi= sessionManager.getTokoh()
        allPersonelModel.sahabat= sessionManager.getSahabat()
        allPersonelModel.media_disenangi= sessionManager.getMediaInfo()
        allPersonelModel.media_sosial= sessionManager.getMedsos()

        NetworkConfig().getService().addAllPersonel(
            "Bearer ${sessionManager.fetchAuthToken()}",
            allPersonelModel
        ).enqueue(object: Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@AddCatPersActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    sessionManager.clearAllPers()
                    btn_next_cat_pers.showSnackbar(R.string.data_saved) { action(R.string.next) {
                            startActivity(Intent(this@AddCatPersActivity, PersonelActivity::class.java))
                        }
                    }
                }else {
                    Toast.makeText(this@AddCatPersActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun initSP() {
        val item = listOf("Pidana", "Kode Etik", "Disiplin")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_cat_pers.setAdapter(adapter)
        spinner_cat_pers.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                jenis_catper = "pidana"
            } else if (position == 1) {
                jenis_catper = "kode_etik"
            } else {
                jenis_catper = "disiplin"
            }
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }
}