package id.calocallo.sicape.ui.main.addpersonal.relasi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.HukumanReq
import id.calocallo.sicape.network.request.RelasiReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_cat_pers.*
import kotlinx.android.synthetic.main.activity_add_relasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRelasiActivity : BaseActivity() {
    private lateinit var listRelasi: ArrayList<RelasiReq>
    private lateinit var listHukum: ArrayList<HukumanReq>
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterRelasi: RelasiAdapter
    private lateinit var adapterHukum: PernahHukumAdapter
    private var allPersonelModel = AllPersonelModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_relasi)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Relasi"
        listRelasi = ArrayList()
        listHukum = ArrayList()

        initRV(rv_relasi, rv_pernah_dihukum)

        btn_next_relasi.setOnClickListener {
            if (listRelasi.size == 1 && listRelasi[0].nama == "") {
                listRelasi.clear()
            }

            if (listHukum.size == 1 && listHukum[0].perkara == "") {
                listHukum.clear()
            }

            sessionManager.setRelasi(listRelasi)
            sessionManager.setHukuman(listHukum)
//            Log.e("size Relasi", sessionManager.getRelasi().size.toString())
//            Log.e("size Relasi", sessionManager.getRelasi()[0].lokasi.toString())
//            Log.e("size Relasi", sessionManager.getRelasi()[1].lokasi.toString())
//            Log.e("size Hukum", sessionManager.getHukuman().size.toString())
//            Log.e("size Relasi", sessionManager.getHukuman()[0].perkara.toString())
//            startActivity(Intent(this, AddCatPersActivity::class.java))
            doSavePersonel()
        }
    }

    private fun doSavePersonel() {
        allPersonelModel.personel = sessionManager.getPersonel()
        allPersonelModel.signalement = sessionManager.getSignalement()
        allPersonelModel.foto = sessionManager.getFoto()
        allPersonelModel.relasi = sessionManager.getRelasi()
        allPersonelModel.pernah_dihukum = sessionManager.getHukuman()
        allPersonelModel.catatan_personel = sessionManager.getCatpers()
        allPersonelModel.riwayat_pendidikan_umum = sessionManager.getPendUmum()
        allPersonelModel.riwayat_pendidikan_kedinasan = sessionManager.getPendDinas()
        allPersonelModel.riwayat_pendidikan_lain_lain = sessionManager.getPendOther()
        allPersonelModel.riwayat_pekerjaan = sessionManager.getPekerjaan()
        allPersonelModel.pekerjaan_diluar_dinas = sessionManager.getPekerjaanDiluar()
        allPersonelModel.riwayat_alamat = sessionManager.getAlamat()
        allPersonelModel.riwayat_organisasi = sessionManager.getOrganisasi()
        allPersonelModel.riwayat_penghargaan = sessionManager.getPenghargaan()
        allPersonelModel.riwayat_perjuangan = sessionManager.getPerjuanganCita()
        allPersonelModel.pasangan = sessionManager.getPasangan()
        allPersonelModel.ayah_kandung = sessionManager.getAyahKandung()
        allPersonelModel.ayah_tiri = sessionManager.getAyahTiri()
        allPersonelModel.ibu_kandung = sessionManager.getIbu()
        allPersonelModel.ibu_tiri = sessionManager.getIbuTiri()
        allPersonelModel.mertua_laki = sessionManager.getMertuaLaki()
        allPersonelModel.mertua_perempuan = sessionManager.getMertuaPerempuan()
        allPersonelModel.anak = sessionManager.getAnak()
        allPersonelModel.saudara = sessionManager.getSaudara()
        allPersonelModel.orang_berjasa = sessionManager.getOrangBerjasa()
        allPersonelModel.orang_disegani = sessionManager.getOrangDisegani()
        allPersonelModel.tokoh_dikagumi = sessionManager.getTokoh()
        allPersonelModel.sahabat = sessionManager.getSahabat()
        allPersonelModel.media_disenangi = sessionManager.getMediaInfo()
        allPersonelModel.media_sosial = sessionManager.getMedsos()

        NetworkConfig().getService().addAllPersonel(
            "Bearer ${sessionManager.fetchAuthToken()}",
            allPersonelModel
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@AddRelasiActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    sessionManager.clearAllPers()
                    btn_next_cat_pers.showSnackbar(R.string.data_saved) {
                        action(R.string.next) {
                            startActivity(
                                Intent(
                                    this@AddRelasiActivity,
                                    PersonelActivity::class.java
                                )
                            )
                        }
                    }
                } else {
                    Toast.makeText(this@AddRelasiActivity, "Gagal Menyimpan", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun initRV(rvRelasi: RecyclerView, rvHukum: RecyclerView) {
        val getRelasi = sessionManager.getRelasi()
        if (getRelasi.size == 1) {
            for (i in 0 until getRelasi.size) {
                listRelasi.add(
                    i, RelasiReq(
                        getRelasi[i].nama
                    )
                )
            }
        } else {
            listRelasi.add(RelasiReq())
        }
        rvRelasi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterRelasi = RelasiAdapter(this, listRelasi, object : RelasiAdapter.OnClickRelasi {
            override fun onDelete(position: Int) {
                listRelasi.removeAt(position)
                adapterRelasi.notifyDataSetChanged()
            }

            override fun onAdd() {
                listRelasi.add(RelasiReq())
                val position = if (listRelasi.isEmpty()) 0 else listRelasi.size - 1
                adapterRelasi.notifyItemInserted(position)
                adapterRelasi.notifyDataSetChanged()
            }
        })
        rvRelasi.adapter = adapterRelasi

//        btn_add_relasi.setOnClickListener {
//            listRelasi.add(RelasiReq())
//            val position = if (listRelasi.isEmpty()) 0 else listRelasi.size - 1
//            adapterRelasi.notifyItemInserted(position)
//            adapterRelasi.notifyDataSetChanged()
//        }


        //Hukum
        val getHukum = sessionManager.getHukuman()
        if (getHukum.size == 1) {
            for (i in 0 until getHukum.size) {
                listHukum.add(
                    i, HukumanReq(
                        getHukum[i].perkara
                    )
                )
            }
        } else {
            listHukum.add(HukumanReq())
        }
        rvHukum.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterHukum =
            PernahHukumAdapter(this, listHukum, object : PernahHukumAdapter.OnClickHukum {
                override fun onDelete(position: Int) {
                    listHukum.removeAt(position)
                    adapterHukum.notifyDataSetChanged()
                }

                override fun onAdd() {
                    listHukum.add(HukumanReq())
                    val position = if (listHukum.isEmpty()) 0 else listHukum.size - 1
                    adapterHukum.notifyItemInserted(position)
                    adapterHukum.notifyDataSetChanged()
                }
            })
        rvHukum.adapter = adapterHukum
//        btn_add_pernah_dihukum.setOnClickListener {
//            listHukum.add(HukumanReq())
//            val position = if (listHukum.isEmpty()) 0 else listHukum.size - 1
//            adapterHukum.notifyItemInserted(position)
//            adapterHukum.notifyDataSetChanged()
//        }
    }
}