package id.calocallo.sicape.ui.main.addpersonal.relasi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AddAllPersonelModel
import id.calocallo.sicape.model.AddPendidikanModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.HukumanReq
import id.calocallo.sicape.network.request.KeluargaReq
import id.calocallo.sicape.network.request.RelasiReq
import id.calocallo.sicape.network.response.AddPersonelResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.ui.main.MainActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.utils.hideKeyboard
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_relasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddRelasiActivity : BaseActivity() {
    private lateinit var listRelasi: ArrayList<RelasiReq>
    private lateinit var listHukum: ArrayList<HukumanReq>
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterRelasi: RelasiAdapter
    private lateinit var adapterHukum: PernahHukumAdapter
    private var allPersonelModel = AddAllPersonelModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_relasi)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Relasi"
        Logger.addLogAdapter(AndroidLogAdapter())
        listRelasi = ArrayList()
        listHukum = ArrayList()

        hideKeyboard()
        initRV(rv_relasi, rv_pernah_dihukum)

        btn_next_relasi.setOnClickListener {
            if (listRelasi.size == 1 && listRelasi[0].nama == "") {
                listRelasi.clear()
            }

            if (listHukum.size == 1 && listHukum[0].perkara == "") {
                listHukum.clear()
            }

            sessionManager1.setRelasi(listRelasi)
            sessionManager1.setHukuman(listHukum)
//            Log.e("size Relasi", sessionManager.getRelasi().size.toString())
//            Log.e("size Relasi", sessionManager.getRelasi()[0].lokasi.toString())
//            Log.e("size Relasi", sessionManager.getRelasi()[1].lokasi.toString())
//            Log.e("size Hukum", sessionManager.getHukuman().size.toString())
//            Log.e("size Relasi", sessionManager.getHukuman()[0].perkara.toString())
//            startActivity(Intent(this, AddCatPersActivity::class.java))
            doSavePersonel(listRelasi, listHukum)
        }
    }

    private fun doSavePersonel(
        listRelasi: ArrayList<RelasiReq>,
        listHukum: ArrayList<HukumanReq>
    ) {
        Log.e("relasi", "$listRelasi")
        val personel = sessionManager1.getPersonel()

        allPersonelModel.nama = personel.nama
        allPersonelModel.nama_alias = personel.nama_alias
        allPersonelModel.jenis_kelamin = personel.jenis_kelamin
        allPersonelModel.tempat_lahir = personel.tempat_lahir
        allPersonelModel.tanggal_lahir = personel.tanggal_lahir
        allPersonelModel.ras = personel.ras
        allPersonelModel.jabatan = personel.jabatan
        allPersonelModel.pangkat = personel.pangkat.toString().toUpperCase()
        allPersonelModel.nrp = personel.nrp
        allPersonelModel.id_satuan_kerja = personel.id_satuan_kerja
        allPersonelModel.alamat_rumah = personel.alamat_rumah
        allPersonelModel.no_telp_rumah = personel.no_telp_rumah
        allPersonelModel.kewarganegaraan = personel.kewarganegaraan
        allPersonelModel.cara_peroleh_kewarganegaraan = personel.cara_peroleh_kewarganegaraan
        allPersonelModel.agama_sekarang = personel.agama_sekarang
        allPersonelModel.agama_sebelumnya = personel.agama_sebelumnya.toString()
        allPersonelModel.aliran_kepercayaan = ""
        allPersonelModel.status_perkawinan = personel.status_perkawinan
        allPersonelModel.tempat_perkawinan = personel.tempat_perkawinan
        allPersonelModel.tanggal_perkawinan = personel.tanggal_perkawinan
        allPersonelModel.perkawinan_keberapa = personel.perkawinan_keberapa
        allPersonelModel.jumlah_anak = personel.jumlah_anak
        allPersonelModel.alamat_sesuai_ktp = personel.alamat_sesuai_ktp
        allPersonelModel.no_telp = personel.no_telp
        allPersonelModel.no_ktp = personel.no_ktp
        allPersonelModel.hobi = personel.hobi
        allPersonelModel.kebiasaan = personel.kebiasaan
        allPersonelModel.bahasa = personel.bahasa
//        = sessionManager1.getPersonel()

        val signalement = sessionManager1.getSignalement()
        allPersonelModel.tinggi = signalement.tinggi
        allPersonelModel.rambut = signalement.rambut
        allPersonelModel.muka = signalement.muka
        allPersonelModel.mata = signalement.mata
        allPersonelModel.sidik_jari = signalement.sidik_jari
        allPersonelModel.cacat = signalement.cacat
        allPersonelModel.kesenangan = signalement.kesenangan
        allPersonelModel.kelemahan = signalement.kelemahan
        allPersonelModel.yang_mempengaruhi = signalement.yang_mempengaruhi
        allPersonelModel.keluarga_dekat = signalement.keluarga_dekat
        allPersonelModel.lain_lain = signalement.lain_lain

        val foto = sessionManager1.getIdFoto()
        allPersonelModel.id_foto_kanan = foto.id_foto_kanan
        allPersonelModel.id_foto_muka = foto.id_foto_muka
        allPersonelModel.id_foto_kiri = foto.id_foto_kiri

        allPersonelModel.relasi = listRelasi
        allPersonelModel.pernah_dihukum = listHukum
//        allPersonelModel.catatan_personel = sessionManager1.getCatpers()
        val listPend = arrayListOf<AddPendidikanModel>()
        listPend.addAll(sessionManager1.getPendUmum())
        listPend.addAll(sessionManager1.getPendDinas())
        listPend.addAll(sessionManager1.getPendOther())
        allPersonelModel.riwayat_pendidikan = listPend
//        allPersonelModel.riwayat_pendidikan_kedinasan = sessionManager1.getPendDinas()
//        allPersonelModel.riwayat_pendidikan_lain_lain = sessionManager1.getPendOther()
        allPersonelModel.riwayat_pekerjaan = sessionManager1.getPekerjaan()
        val pekerjaanLuar = sessionManager1.getPekerjaanDiluar()
        if (pekerjaanLuar[0].pekerjaan != "") {
            allPersonelModel.pekerjaan_diluar_dinas = pekerjaanLuar
        }
        allPersonelModel.riwayat_alamat = sessionManager1.getAlamat()
        allPersonelModel.riwayat_organisasi = sessionManager1.getOrganisasi()
        allPersonelModel.riwayat_penghargaan = sessionManager1.getPenghargaan()
        allPersonelModel.riwayat_perjuangan = sessionManager1.getPerjuanganCita()

//        allPersonelModel.ayah_tiri = sessionManager1.getAyahTiri()
//        allPersonelModel.ibu_kandung = sessionManager1.getIbu()
//        allPersonelModel.ibu_tiri = sessionManager1.getIbuTiri()
//        allPersonelModel.mertua_laki = sessionManager1.getMertuaLaki()
//        allPersonelModel.mertua_perempuan = sessionManager1.getMertuaPerempuan()
        allPersonelModel.anak = sessionManager1.getAnak()
        allPersonelModel.saudara = sessionManager1.getSaudara()
        allPersonelModel.orang_berjasa = sessionManager1.getOrangBerjasa()
        allPersonelModel.orang_disegani = sessionManager1.getOrangDisegani()
        allPersonelModel.tokoh_dikagumi = sessionManager1.getTokoh()
        allPersonelModel.sahabat = sessionManager1.getSahabat()
        allPersonelModel.media_disenangi = sessionManager1.getMediaInfo()
        allPersonelModel.media_sosial = sessionManager1.getMedsos()

        val pasangan = sessionManager1.getPasangan()
        if (pasangan[0].nama != "") {
            allPersonelModel.pasangan = sessionManager1.getPasangan()
        }
        val ayah = sessionManager1.getAyahKandung()
        val listKeluarga = ArrayList<KeluargaReq>()
        if (ayah.nama != null) {
            listKeluarga.add(ayah)
        }
        val ayahTiri = sessionManager1.getAyahTiri()
        if (ayahTiri.nama != "")
            listKeluarga.add(ayahTiri)
        val ibu = sessionManager1.getIbu()
        if (ibu.nama != "")
            listKeluarga.add(ibu)
        val ibuTiri = sessionManager1.getIbuTiri()
        if (ibuTiri.nama != "")
            listKeluarga.add(ibuTiri)
        val mertuaLaki = sessionManager1.getMertuaLaki()
        if (mertuaLaki.nama != "")
            listKeluarga.add(mertuaLaki)
        val mertuaPerempuan = sessionManager1.getMertuaPerempuan()
        if (mertuaPerempuan.nama != "")
            listKeluarga.add(mertuaPerempuan)
        allPersonelModel.keluarga = listKeluarga

        Logger.e("$allPersonelModel")

        NetworkConfig().getServPers().addAllPersonel(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            allPersonelModel
        ).enqueue(object : Callback<Base1Resp<AddPersonelResp>> {
            override fun onFailure(call: Call<Base1Resp<AddPersonelResp>>, t: Throwable) {
                Log.e("error", "$t")
                Toast.makeText(this@AddRelasiActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Base1Resp<AddPersonelResp>>,
                response: Response<Base1Resp<AddPersonelResp>>
            ) {
                if (response.isSuccessful) {
                    hideKeyboard()
                    sessionManager1.clearAllPers()
                    btn_next_relasi.showSnackbar(R.string.data_saved) {
                        action(R.string.next) {
                            val intent = Intent(this@AddRelasiActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_TASK_ON_HOME)
                            startActivity(intent)
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
        val getRelasi = sessionManager1.getRelasi()
        if (getRelasi.size == 1) {
            for (i in 0 until getRelasi.size) {
                listRelasi.add(
                    i, RelasiReq(
                        getRelasi[i].nama,
                        getRelasi[i].lokasi
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
        val getHukum = sessionManager1.getHukuman()
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