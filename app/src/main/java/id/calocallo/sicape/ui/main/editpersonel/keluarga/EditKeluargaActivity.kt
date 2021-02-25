package id.calocallo.sicape.ui.main.editpersonel.keluarga

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.KeluargaReq
import id.calocallo.sicape.network.response.KeluargaResp
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.KeluargaMinResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_keluarga.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditKeluargaActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var status_agama: String? = null
    private var status_hidup: String? = null
    private var status_kerja: String? = null
    private var keluargaReq = KeluargaReq()
    private var isEmptyKeluarga: Boolean? = null
    private var status_hub: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_keluarga)
        sessionManager1 = SessionManager1(this)
        val detailPersonel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama.toString()
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_keluarga_edit.gone()
            btn_delete_keluarga_edit.gone()
        }
        val keluarga = intent.getParcelableExtra<KeluargaMinResp>("KELUARGA")
        var title_keluarga = ""
        //untuk endpoint(status_keluarga)
        when (keluarga?.status_hubungan) {
            "ayah_kandung" -> title_keluarga = "Ayah Kandung"
            "ayah_tiri" -> title_keluarga = "Ayah Tiri"
            "ibu_kandung" -> title_keluarga = "Ibu Kandung"
            "ibu_tiri" -> title_keluarga = "Ibu Tiri"
            "mertua_laki_laki" -> title_keluarga = "Mertua Laki-Laki"
            "mertua_perempuan" -> title_keluarga = "Mertua Perempuan"
        }
        txt_keluarga_edit.text = "Edit Data $title_keluarga"
        spinner_status_hubungan_keluarga_edit.setText(title_keluarga)

        status_hub = keluarga?.status_hubungan
        bindProgressButton(btn_save_keluarga_edit)
        btn_save_keluarga_edit.attachTextChangeAnimator()
        /*ADD*/
        val isAdd = intent.getBooleanExtra("ADD_KELUARGA", false)
        if (isAdd) {
            txt_keluarga_edit.text = "Tambah Data Keluarga"
            btn_save_keluarga_edit.setOnClickListener {
                addKeluarga(detailPersonel)
            }
        } else {
            getKeluarga(keluarga)
            btn_save_keluarga_edit.setOnClickListener {
                doUpdateKeluarga(keluarga)
            }
        }



       /* if (isEmptyKeluarga == true) {
            btn_save_keluarga_edit.setOnClickListener {
                addKeluarga(keluarga)
            }
        } else {

        }*/

        /*Delete*/
        btn_delete_keluarga_edit.setOnClickListener {
            alert("Yakin Hapus Data ?") {
                positiveButton("Iya") {
                    NetworkConfig().getServPers().deleteKeluarga(
                        "Bearer ${sessionManager1.fetchAuthToken()}",
//                        sessionManager1.fetchID().toString()
                        keluarga?.id.toString()
                    ).enqueue(object : Callback<BaseResp> {
                        override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                            Toast.makeText(
                                this@EditKeluargaActivity,
                                "Error Koneksi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<BaseResp>,
                            response: Response<BaseResp>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(
                                    this@EditKeluargaActivity,
                                    "Berhasil Hapus Data",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@EditKeluargaActivity,
                                    "Error",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
                negativeButton("Tidak") {}
            }.show()
        }

        initSP()
    }


    private fun getKeluarga(keluarga: KeluargaMinResp?) {
        NetworkConfig().getServPers().getDetailKeluarga(
            "Bearer ${sessionManager1.fetchAuthToken()}",
//            sessionManager1.fetchID().toString(),
            keluarga?.id.toString()
        ).enqueue(object : Callback<KeluargaResp> {
            override fun onFailure(call: Call<KeluargaResp>, t: Throwable) {
                Toast.makeText(this@EditKeluargaActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<KeluargaResp>, response: Response<KeluargaResp>) {
                if (response.isSuccessful) {
                    val obj = response.body()
                    if (obj?.id.toString().isEmpty()) {
                        isEmptyKeluarga = true
                    } else {
                        viewKeluarga(response.body()!!)
                    }
                } else {
                    Toast.makeText(this@EditKeluargaActivity, "Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun viewKeluarga(data: KeluargaResp) {

        edt_nama_lngkp_keluarga_edit.setText(data.nama)
        edt_alias_keluarga_edit.setText(data.nama_alias)
        edt_tmpt_ttl_keluarga_edit.setText(data.tempat_lahir)
        edt_tgl_ttl_keluarga_edit.setText(data.tanggal_lahir)
        edt_suku_keluarga_edit.setText(data.ras)
        edt_kwg_keluarga_edit.setText(data.kewarganegaraan)
        edt_how_to_kwg_keluarga_edit.setText(data.cara_peroleh_kewarganegaraan)
        edt_aliran_dianut_keluarga_edit.setText(data.aliran_kepercayaan_dianut)
        edt_almt_skrg_keluarga_edit.setText(data.alamat_rumah)
        edt_no_telp_keluarga_edit.setText(data.no_telp_rumah)
        edt_almt_rmh_sblm_keluarga_edit.setText(data.alamat_rumah_sebelumnya)
        edt_thn_berhenti_keluarga_edit.setText(data.tahun_pensiun)
        edt_alsn_berhenti_keluarga_edit.setText(data.alasan_pensiun)
        edt_nama_kntr_keluarga_edit.setText(data.nama_kantor)
        edt_almt_kntr_keluarga_edit.setText(data.alamat_kantor)
        edt_no_telp_kntr_keluarga_edit.setText(data.no_telp_kantor)
        edt_pekerjaan_sblm_keluarga_edit.setText(data.pekerjaan_sebelumnya)
        edt_pend_trkhr_keluarga_edit.setText(data.pendidikan_terakhir)
        edt_pekerjaan_keluarga_edit.setText(data.pekerjaan_terakhir)
        edt_kddkn_org_diikuti_keluarga_edit.setText(data.kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_keluarga_edit.setText(data.tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_keluarga_edit.setText(data.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_keluarga_edit.setText(data.alamat_organisasi_saat_ini)
        edt_kddkn_org_prnh_keluarga_edit.setText(data.kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_keluarga_edit.setText(data.tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_keluarga_edit.setText(data.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_keluarga_edit.setText(data.alamat_organisasi_sebelumnya)
        edt_tahun_kematian_keluarga_edit.setText(data.tahun_kematian)
        edt_dimana_keluarga_edit.setText(data.lokasi_kematian)
        edt_penyebab_keluarga_edit.setText(data.sebab_kematian)

        //set stts hidup, agama, kerja for update
        keluargaReq.status_kehidupan = data.status_kehidupan
        keluargaReq.agama = data.agama
        keluargaReq.status_kerja = data.status_kerja

        //spinner
        when (data.status_kehidupan) {
            0 -> {
                spinner_stts_hidup_keluarga_edit.setText("Tidak")
                txt_layout_penyebab_keluarga_edit.visibility = View.VISIBLE
                txt_layout_bagaimana_stts_keluarga_edit.visibility = View.VISIBLE
                txt_layout_dimana_keluarga_edit.visibility = View.VISIBLE
            }
            1 -> {
                spinner_stts_hidup_keluarga_edit.setText("Ya")
                txt_layout_penyebab_keluarga_edit.visibility = View.GONE
                txt_layout_bagaimana_stts_keluarga_edit.visibility = View.GONE
                txt_layout_dimana_keluarga_edit.visibility = View.GONE
                edt_penyebab_keluarga_edit.text = null
                edt_tahun_kematian_keluarga_edit.text = null
                edt_dimana_keluarga_edit.text = null
            }
        }
        when (data.status_kerja) {
            0 -> {
                spinner_pekerjaan_keluarga_edit.setText("Tidak")
                txt_layout_nama_kantor_keluarga_edit.visibility = View.GONE
                txt_layout_alamat_kantor_keluarga_edit.visibility = View.GONE
                txt_layout_no_telp_kantor_keluarga_edit.visibility = View.GONE
                txt_layout_thn_berhenti_keluarga_edit.visibility = View.VISIBLE
                txt_layout_alsn_berhenti_keluarga_edit.visibility = View.VISIBLE
                edt_nama_kntr_keluarga_edit.text = null
                edt_almt_kntr_keluarga_edit.text = null
                edt_no_telp_kntr_keluarga_edit.text = null
            }
            1 -> {
                spinner_pekerjaan_keluarga_edit.setText("Masih")
                txt_layout_nama_kantor_keluarga_edit.visibility = View.VISIBLE
                txt_layout_alamat_kantor_keluarga_edit.visibility = View.VISIBLE
                txt_layout_no_telp_kantor_keluarga_edit.visibility = View.VISIBLE
                txt_layout_thn_berhenti_keluarga_edit.visibility = View.GONE
                txt_layout_alsn_berhenti_keluarga_edit.visibility = View.GONE
                edt_thn_berhenti_keluarga_edit.text = null
                edt_alsn_berhenti_keluarga_edit.text = null
            }
        }
        when (data.agama) {
            "islam" -> status_agama = "Islam"
            "katolik" -> status_agama = "Katolik"
            "protestan" -> status_agama = "Protestan"
            "buddha" -> status_agama = "Buddha"
            "hindu" -> status_agama = "Hindu"
            "konghuchu" -> status_agama = "Konghuchu"
        }
        sp_agama_keluarga_edit.setText(status_agama)


    }

    private fun addKeluarga(keluarga: AllPersonelModel1?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_keluarga_edit.showProgress {
            progressColor = Color.WHITE
        }
        keluargaReq.status_hubungan = status_hub
        keluargaReq.nama = edt_nama_lngkp_keluarga_edit.text.toString()
        keluargaReq.nama_alias = edt_alias_keluarga_edit.text.toString()
        keluargaReq.tempat_lahir = edt_tmpt_ttl_keluarga_edit.text.toString()
        keluargaReq.tanggal_lahir = edt_tgl_ttl_keluarga_edit.text.toString()
        keluargaReq.ras = edt_suku_keluarga_edit.text.toString()
        keluargaReq.kewarganegaraan = edt_kwg_keluarga_edit.text.toString()
        keluargaReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_keluarga_edit.text.toString()
        keluargaReq.aliran_kepercayaan_dianut = edt_aliran_dianut_keluarga_edit.text.toString()
        keluargaReq.alamat_rumah = edt_almt_skrg_keluarga_edit.text.toString()
        keluargaReq.no_telp_rumah = edt_no_telp_keluarga_edit.text.toString()
        keluargaReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_keluarga_edit.text.toString()
        keluargaReq.tahun_pensiun = edt_thn_berhenti_keluarga_edit.text.toString()
        keluargaReq.alasan_pensiun = edt_alsn_berhenti_keluarga_edit.text.toString()
        keluargaReq.nama_kantor = edt_nama_kntr_keluarga_edit.text.toString()
        keluargaReq.alamat_kantor = edt_almt_kntr_keluarga_edit.text.toString()
        keluargaReq.no_telp_kantor = edt_no_telp_kntr_keluarga_edit.text.toString()
        keluargaReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_keluarga_edit.text.toString()
        keluargaReq.pendidikan_terakhir = edt_pend_trkhr_keluarga_edit.text.toString()
        keluargaReq.pekerjaan_terakhir = edt_pekerjaan_keluarga_edit.text.toString()
        keluargaReq.kedudukan_organisasi_saat_ini =
            edt_kddkn_org_diikuti_keluarga_edit.text.toString()
        keluargaReq.tahun_bergabung_organisasi_saat_ini =
            edt_thn_org_diikuti_keluarga_edit.text.toString()
        keluargaReq.alasan_bergabung_organisasi_saat_ini =
            edt_alasan_org_diikuti_keluarga_edit.text.toString()
        keluargaReq.alamat_organisasi_saat_ini = edt_almt_org_diikuti_keluarga_edit.text.toString()
        keluargaReq.kedudukan_organisasi_sebelumnya =
            edt_kddkn_org_prnh_keluarga_edit.text.toString()
        keluargaReq.tahun_bergabung_organisasi_sebelumnya =
            edt_thn_org_prnh_keluarga_edit.text.toString()
        keluargaReq.alasan_bergabung_organisasi_sebelumnya =
            edt_alasan_org_prnh_keluarga_edit.text.toString()
        keluargaReq.alamat_organisasi_sebelumnya = edt_almt_org_prnh_keluarga_edit.text.toString()
        keluargaReq.tahun_kematian = edt_tahun_kematian_keluarga_edit.text.toString()
        keluargaReq.lokasi_kematian = edt_dimana_keluarga_edit.text.toString()
        keluargaReq.sebab_kematian = edt_penyebab_keluarga_edit.text.toString()

        NetworkConfig().getServPers().addKeluargaSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            keluarga?.id.toString(),
            keluargaReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditKeluargaActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_keluarga_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_saved
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    },500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_keluarga_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_keluarga_edit.hideDrawable(R.string.not_save)
                }
            }

        })
    }

    private fun doUpdateKeluarga(keluarga: KeluargaMinResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_keluarga_edit.showProgress {
            progressColor = Color.WHITE
        }
        keluargaReq.status_hubungan = status_hub
        keluargaReq.nama = edt_nama_lngkp_keluarga_edit.text.toString()
        keluargaReq.nama_alias = edt_alias_keluarga_edit.text.toString()
        keluargaReq.tempat_lahir = edt_tmpt_ttl_keluarga_edit.text.toString()
        keluargaReq.tanggal_lahir = edt_tgl_ttl_keluarga_edit.text.toString()
        keluargaReq.ras = edt_suku_keluarga_edit.text.toString()
        keluargaReq.kewarganegaraan = edt_kwg_keluarga_edit.text.toString()
        keluargaReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_keluarga_edit.text.toString()
        keluargaReq.aliran_kepercayaan_dianut = edt_aliran_dianut_keluarga_edit.text.toString()
        keluargaReq.alamat_rumah = edt_almt_skrg_keluarga_edit.text.toString()
        keluargaReq.no_telp_rumah = edt_no_telp_keluarga_edit.text.toString()
        keluargaReq.alamat_rumah_sebelumnya = edt_almt_rmh_sblm_keluarga_edit.text.toString()
        keluargaReq.tahun_pensiun = edt_thn_berhenti_keluarga_edit.text.toString()
        keluargaReq.alasan_pensiun = edt_alsn_berhenti_keluarga_edit.text.toString()
        keluargaReq.nama_kantor = edt_nama_kntr_keluarga_edit.text.toString()
        keluargaReq.alamat_kantor = edt_almt_kntr_keluarga_edit.text.toString()
        keluargaReq.no_telp_kantor = edt_no_telp_kntr_keluarga_edit.text.toString()
        keluargaReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_keluarga_edit.text.toString()
        keluargaReq.pendidikan_terakhir = edt_pend_trkhr_keluarga_edit.text.toString()
        keluargaReq.pekerjaan_terakhir = edt_pekerjaan_keluarga_edit.text.toString()
        keluargaReq.kedudukan_organisasi_saat_ini =
            edt_kddkn_org_diikuti_keluarga_edit.text.toString()
        keluargaReq.tahun_bergabung_organisasi_saat_ini =
            edt_thn_org_diikuti_keluarga_edit.text.toString()
        keluargaReq.alasan_bergabung_organisasi_saat_ini =
            edt_alasan_org_diikuti_keluarga_edit.text.toString()
        keluargaReq.alamat_organisasi_saat_ini = edt_almt_org_diikuti_keluarga_edit.text.toString()
        keluargaReq.kedudukan_organisasi_sebelumnya =
            edt_kddkn_org_prnh_keluarga_edit.text.toString()
        keluargaReq.tahun_bergabung_organisasi_sebelumnya =
            edt_thn_org_prnh_keluarga_edit.text.toString()
        keluargaReq.alasan_bergabung_organisasi_sebelumnya =
            edt_alasan_org_prnh_keluarga_edit.text.toString()
        keluargaReq.alamat_organisasi_sebelumnya = edt_almt_org_prnh_keluarga_edit.text.toString()
        keluargaReq.tahun_kematian = edt_tahun_kematian_keluarga_edit.text.toString()
        keluargaReq.lokasi_kematian = edt_dimana_keluarga_edit.text.toString()
        keluargaReq.sebab_kematian = edt_penyebab_keluarga_edit.text.toString()
        Log.e("keluarga", "$keluargaReq")

        NetworkConfig().getServPers().updateKeluargaSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
//            sessionManager1.fetchID().toString(),
            keluarga?.id.toString(),
            keluargaReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditKeluargaActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_keluarga_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
//                    Toast.makeText(this@EditKeluargaActivity, R.string.data_updated, Toast.LENGTH_SHORT).show()
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_keluarga_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_keluarga_edit.hideDrawable(R.string.not_update)
//                    Toast.makeText(this@EditKeluargaActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initSP() {
        val agama = listOf("Islam", "Katolik", "Protestan", "Buddha", "Hindu", "Konghuchu")

        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agama)
        sp_agama_keluarga_edit.setAdapter(adapterAgama)
        sp_agama_keluarga_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    keluargaReq.agama = "islam"
                }
                1 -> {
                    keluargaReq.agama = "katolik"
                }
                2 -> {
                    keluargaReq.agama = "protestan"
                }
                3 -> {
                    keluargaReq.agama = "buddha"
                }
                4 -> {
                    keluargaReq.agama = "hindu"
                }
                5 -> {
                    keluargaReq.agama = "konghuchu"
                }
            }
        }

        val kerja = listOf("Masih", "Tidak")

        val adapterKerja = ArrayAdapter(this, R.layout.item_spinner, kerja)
        spinner_pekerjaan_keluarga_edit.setAdapter(adapterKerja)
        spinner_pekerjaan_keluarga_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                /*masih*/
                0 -> {
                    txt_layout_nama_kantor_keluarga_edit.visibility = View.VISIBLE
                    txt_layout_alamat_kantor_keluarga_edit.visibility = View.VISIBLE
                    txt_layout_no_telp_kantor_keluarga_edit.visibility = View.VISIBLE
                    txt_layout_thn_berhenti_keluarga_edit.visibility = View.GONE
                    txt_layout_alsn_berhenti_keluarga_edit.visibility = View.GONE
                    keluargaReq.status_kerja = 1
                    edt_nama_kntr_keluarga_edit.text = null
                    edt_almt_kntr_keluarga_edit.text = null
                    edt_no_telp_kntr_keluarga_edit.text = null
                }
                /*tidak*/
                1 -> {
                    txt_layout_nama_kantor_keluarga_edit.visibility = View.GONE
                    txt_layout_alamat_kantor_keluarga_edit.visibility = View.GONE
                    txt_layout_no_telp_kantor_keluarga_edit.visibility = View.GONE
                    txt_layout_thn_berhenti_keluarga_edit.visibility = View.VISIBLE
                    txt_layout_alsn_berhenti_keluarga_edit.visibility = View.VISIBLE
                    keluargaReq.status_kerja = 0
                    edt_thn_berhenti_keluarga_edit.text = null
                    edt_alsn_berhenti_keluarga_edit.text = null
                }
//
            }
        }

        val hidup = listOf("Ya", "Tidak")

        val adapterHidup = ArrayAdapter(this, R.layout.item_spinner, hidup)
        spinner_stts_hidup_keluarga_edit.setAdapter(adapterHidup)
        spinner_stts_hidup_keluarga_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    txt_layout_penyebab_keluarga_edit.visibility = View.GONE
                    txt_layout_bagaimana_stts_keluarga_edit.visibility = View.GONE
                    txt_layout_dimana_keluarga_edit.visibility = View.GONE
                    keluargaReq.status_kehidupan = 1
                }
                1 -> {
                    txt_layout_penyebab_keluarga_edit.visibility = View.VISIBLE
                    txt_layout_bagaimana_stts_keluarga_edit.visibility = View.VISIBLE
                    txt_layout_dimana_keluarga_edit.visibility = View.VISIBLE
                    keluargaReq.status_kehidupan = 0
                }
            }
        }

        val item = listOf(
            "Ayah Kandung", "Ayah Tiri", "Ibu Kandung", "Ibu Tiri",
            "Mertua Laki-Laki", "Mertua Perempuan"
        )
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_status_hubungan_keluarga_edit.setAdapter(adapter)
        spinner_status_hubungan_keluarga_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> status_hub = "ayah_kandung"
                1 -> status_hub = "ayah_tiri"
                2 -> status_hub = "ibu_kandung"
                3 -> status_hub = "ibu_tiri"
                4 -> status_hub = "mertua_laki_laki"
                5 -> status_hub = "mertua_perempuan"
            }
        }
    }

}