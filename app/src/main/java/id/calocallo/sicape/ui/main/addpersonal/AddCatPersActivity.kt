package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AddAllPersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.CatatanPersReq
import id.calocallo.sicape.network.response.AddPersonelResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_cat_pers.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCatPersActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var catpersReq = CatatanPersReq()
    private var allPersonelModel = AddAllPersonelModel()
    var jenis_catper = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cat_pers)
        sessionManager1 = SessionManager1(this)
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
            sessionManager1.setCatpers(catpersReq)
            Log.e("size Catpers", "${sessionManager1.getCatpers()}")

            doSavePersonel()

        }
    }

    private fun doSavePersonel() {
//        allPersonelModel.personel = sessionManager1.getPersonel()
//        allPersonelModel.signalement = sessionManager1.getSignalement()
//        allPersonelModel.foto = sessionManager1.getIdFoto()
        allPersonelModel.relasi = sessionManager1.getRelasi()
        allPersonelModel.pernah_dihukum = sessionManager1.getHukuman()
//        allPersonelModel.catatan_personel= sessionManager1.getCatpers()
        allPersonelModel.riwayat_pendidikan= sessionManager1.getPendUmum()
//        allPersonelModel.riwayat_pendidikan_kedinasan= sessionManager1.getPendDinas()
//        allPersonelModel.riwayat_pendidikan_lain_lain= sessionManager1.getPendOther()
        allPersonelModel.riwayat_pekerjaan= sessionManager1.getPekerjaan()
        allPersonelModel.pekerjaan_diluar_dinas= sessionManager1.getPekerjaanDiluar()
        allPersonelModel.riwayat_alamat= sessionManager1.getAlamat()
        allPersonelModel.riwayat_organisasi= sessionManager1.getOrganisasi()
        allPersonelModel.riwayat_penghargaan= sessionManager1.getPenghargaan()
        allPersonelModel.riwayat_perjuangan= sessionManager1.getPerjuanganCita()
        allPersonelModel.pasangan= sessionManager1.getPasangan()
//        allPersonelModel.ayah_kandung= sessionManager1.getAyahKandung()
//        allPersonelModel.ayah_tiri= sessionManager1.getAyahTiri()
//        allPersonelModel.ibu_kandung= sessionManager1.getIbu()
//        allPersonelModel.ibu_tiri= sessionManager1.getIbuTiri()
//        allPersonelModel.mertua_laki= sessionManager1.getMertuaLaki()
//        allPersonelModel.mertua_perempuan= sessionManager1.getMertuaPerempuan()
        allPersonelModel.anak= sessionManager1.getAnak()
        allPersonelModel.saudara= sessionManager1.getSaudara()
        allPersonelModel.orang_berjasa= sessionManager1.getOrangBerjasa()
        allPersonelModel.orang_disegani= sessionManager1.getOrangDisegani()
        allPersonelModel.tokoh_dikagumi= sessionManager1.getTokoh()
        allPersonelModel.sahabat= sessionManager1.getSahabat()
        allPersonelModel.media_disenangi= sessionManager1.getMediaInfo()
        allPersonelModel.media_sosial= sessionManager1.getMedsos()

        NetworkConfig().getServPers().addAllPersonel(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            allPersonelModel
        ).enqueue(object: Callback<Base1Resp<AddPersonelResp>> {
            override fun onFailure(call: Call<Base1Resp<AddPersonelResp>>, t: Throwable) {
                Toast.makeText(this@AddCatPersActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Base1Resp<AddPersonelResp>>, response: Response<Base1Resp<AddPersonelResp>>) {
                if(response.isSuccessful){
                    sessionManager1.clearAllPers()
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