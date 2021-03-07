package id.calocallo.sicape.ui.main.editpersonel.keluarga

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PasanganReq
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_pasangan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPasanganActivity : BaseActivity() {
    private var agama_skrg: String? = null
    private var agama_sblm = ""
    private var stts_pasangan = ""
    private lateinit var sessionManager1: SessionManager1
    private var pasanganReq = PasanganReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pasangan)
        sessionManager1 = SessionManager1(this)
        val detailPersonel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama.toString()
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_pasangan_edit.gone()
            btn_delete_pasangan_edit.gone()
        }

        val pasangan = intent.getParcelableExtra<PasanganMinResp>("PASANGAN_DETAIL")
        getPasanganData(pasangan)
        btn_save_pasangan_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_pasangan_edit)
        btn_save_pasangan_edit.setOnClickListener {
            doUpdatePasangan(pasangan)
        }

        btn_delete_pasangan_edit.setOnClickListener {
            alert("Yakin Hapus Data") {
                positiveButton("Ya") {
                    doDeletePasangan(pasangan)
                }
                negativeButton("Tidak") {
                }
            }.show()
        }
        initSP()

    }

    private fun getPasanganData(pasangan: PasanganMinResp?) {
        NetworkConfig().getServPers().getDetailPasangan(
            "Bearer ${sessionManager1.fetchAuthToken()}", pasangan?.id.toString()
        ).enqueue(object : Callback<PasanganResp> {
            override fun onFailure(call: Call<PasanganResp>, t: Throwable) {
                Toast.makeText(this@EditPasanganActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<PasanganResp>, response: Response<PasanganResp>) {
                if (response.isSuccessful) {
                    viewDetailPasangan(response.body())
                } else {
                    Toast.makeText(this@EditPasanganActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        /**
         * ambil data dari api
         * terus masukkan ke editText yang ada
         */


    }

    private fun viewDetailPasangan(body: PasanganResp?) {
        stts_pasangan = body?.status_pasangan.toString()
        spinner_status_pasangan_edit.setText(body?.status_pasangan)
        edt_nama_lngkp_pasangan_edit_edit.setText(body?.nama)
        edt_alias_pasangan_edit_edit.setText(body?.nama_alias)
        edt_tmpt_ttl_pasangan_edit_edit.setText(body?.tempat_lahir)
        edt_tgl_ttl_pasangan_edit_edit.setText(body?.tanggal_lahir)
        edt_suku_pasangan_edit_edit.setText(body?.ras)
        edt_kwg_pasangan_edit_edit.setText(body?.kewarganegaraan)
        edt_how_to_kwg_pasangan_edit_edit.setText(body?.cara_peroleh_kewarganegaraan)
        edt_aliran_dianut_pasangan_edit_edit.setText(body?.aliran_kepercayaan_dianut)
        edt_aliran_diikuti_pasangan_edit_edit.setText(body?.aliran_kepercayaan_diikuti)
        edt_almt_sblm_kwin_pasangan_edit_edit.setText(body?.alamat_terakhir_sebelum_kawin)
        edt_almt_skrg_pasangan_edit_edit.setText(body?.alamat_rumah)
        edt_no_telp_pasangan_edit_edit.setText(body?.no_telp_rumah)
        edt_pend_trkhr_pasangan_edit_edit.setText(body?.pendidikan_terakhir)
        edt_kwin_berapa_pasangan_edit_edit.setText(body?.perkawinan_keberapa)
        edt_pekerjaan_pasangan_edit_edit.setText(body?.pekerjaan)
        edt_almt_pekerjaan_pasangan_edit_edit.setText(body?.alamat_kantor)
        edt_no_telp_pekerjaan_pasangan_edit.setText(body?.no_telp_kantor)
        edt_pekerjaan_sblm_pasangan_edit.setText(body?.pekerjaan_sebelumnya)
        edt_kddkn_org_diikuti_pasangan_edit.setText(body?.kedudukan_organisasi_saat_ini)
        edt_alasan_org_diikuti_pasangan_edit.setText(body?.alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_pasangan_edit.setText(body?.alamat_organisasi_saat_ini)

        edt_kddkn_org_prnh_pasangan_edit.setText(body?.kedudukan_organisasi_sebelumnya)
        edt_alasan_org_prnh_pasangan_edit.setText(body?.alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_pasangan_edit.setText(body?.alamat_organisasi_sebelumnya)
        edt_thn_org_diikuti_pasangan_edit.setText(body?.tahun_bergabung_organisasi_saat_ini.toString())
        edt_thn_org_prnh_pasangan_edit.setText(body?.tahun_bergabung_organisasi_sebelumnya.toString())
        agama_skrg = body?.agama_sekarang.toString()
        agama_sblm = body?.agama_sebelumnya.toString()
        when {
            body?.tahun_bergabung_organisasi_saat_ini == null -> {
                edt_thn_org_diikuti_pasangan_edit.setText("")
            }
            body.tahun_bergabung_organisasi_sebelumnya == null -> {
                edt_thn_org_prnh_pasangan_edit.setText("")
            }
            body.agama_sebelumnya == null -> {
                agama_sblm = ""
            }
        }

        when (body?.agama_sebelumnya) {
            "" -> sp_agama_sblm_pasangan_edit_edit.setText("Tidak Ada")
            "islam" -> sp_agama_sblm_pasangan_edit_edit.setText("Islam")
            "katolik" -> sp_agama_sblm_pasangan_edit_edit.setText("Katolik")
            "protestan" -> sp_agama_sblm_pasangan_edit_edit.setText("Protestan")
            "buddha" -> sp_agama_sblm_pasangan_edit_edit.setText("Buddha")
            "hindu" -> sp_agama_sblm_pasangan_edit_edit.setText("Hindu")
            "konghuchu" -> sp_agama_sblm_pasangan_edit_edit.setText("Konghuchu")
        }

        when (body?.agama_sekarang) {
            "islam" -> sp_agama_skrg_pasangan_edit_edit.setText("Islam")
            "katolik" -> sp_agama_skrg_pasangan_edit_edit.setText("Katolik")
            "protestan" -> sp_agama_skrg_pasangan_edit_edit.setText("Protestan")
            "buddha" -> sp_agama_skrg_pasangan_edit_edit.setText("Buddha")
            "hindu" -> sp_agama_skrg_pasangan_edit_edit.setText("Hindu")
            "konghuchu" -> sp_agama_skrg_pasangan_edit_edit.setText("Konghuchu")
        }


        initSP()

    }

    private fun doUpdatePasangan(pasangan: PasanganMinResp?) {
        btn_save_pasangan_edit.showProgress {
            progressColor = Color.WHITE
        }
        pasanganReq.status_pasangan = stts_pasangan
        pasanganReq.nama = edt_nama_lngkp_pasangan_edit_edit.text.toString()
        pasanganReq.nama_alias = edt_alias_pasangan_edit_edit.text.toString()
        pasanganReq.tempat_lahir = edt_tmpt_ttl_pasangan_edit_edit.text.toString()
        pasanganReq.tanggal_lahir = edt_tgl_ttl_pasangan_edit_edit.text.toString()
        pasanganReq.ras = edt_suku_pasangan_edit_edit.text.toString()
        pasanganReq.kewarganegaraan = edt_kwg_pasangan_edit_edit.text.toString()
        pasanganReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_pasangan_edit_edit.text.toString()
        pasanganReq.aliran_kepercayaan_dianut = edt_aliran_dianut_pasangan_edit_edit.text.toString()
        pasanganReq.aliran_kepercayaan_diikuti =
            edt_aliran_diikuti_pasangan_edit_edit.text.toString()
        pasanganReq.alamat_terakhir_sebelum_kawin =
            edt_almt_sblm_kwin_pasangan_edit_edit.text.toString()
        pasanganReq.alamat_rumah = edt_almt_skrg_pasangan_edit_edit.text.toString()
        pasanganReq.no_telp_rumah = edt_no_telp_pasangan_edit_edit.text.toString()
        pasanganReq.pendidikan_terakhir = edt_pend_trkhr_pasangan_edit_edit.text.toString()
        pasanganReq.perkawinan_keberapa = edt_kwin_berapa_pasangan_edit_edit.text.toString()
        pasanganReq.pekerjaan = edt_pekerjaan_pasangan_edit_edit.text.toString()
        pasanganReq.alamat_kantor = edt_almt_pekerjaan_pasangan_edit_edit.text.toString()
        pasanganReq.no_telp_kantor = edt_no_telp_pekerjaan_pasangan_edit.text.toString()
        pasanganReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_pasangan_edit.text.toString()
        pasanganReq.kedudukan_organisasi_saat_ini =
            edt_kddkn_org_diikuti_pasangan_edit.text.toString()
        pasanganReq.tahun_bergabung_organisasi_saat_ini =
            edt_thn_org_diikuti_pasangan_edit.text.toString()
        pasanganReq.alasan_bergabung_organisasi_saat_ini =
            edt_alasan_org_diikuti_pasangan_edit.text.toString()
        pasanganReq.alamat_organisasi_saat_ini = edt_almt_org_diikuti_pasangan_edit.text.toString()
        pasanganReq.kedudukan_organisasi_sebelumnya =
            edt_kddkn_org_prnh_pasangan_edit.text.toString()
        pasanganReq.tahun_bergabung_organisasi_sebelumnya =
            edt_thn_org_prnh_pasangan_edit.text.toString()
        pasanganReq.alasan_bergabung_organisasi_sebelumnya =
            edt_alasan_org_prnh_pasangan_edit.text.toString()
        pasanganReq.alamat_organisasi_sebelumnya = edt_almt_org_prnh_pasangan_edit.text.toString()
        pasanganReq.agama_sebelumnya = agama_sblm
        pasanganReq.agama_sekarang = agama_skrg

        NetworkConfig().getServPers().updatePasanganSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            pasangan?.id.toString(), pasanganReq
        ).enqueue(object : Callback<Base1Resp<AddPersonelResp>> {
            override fun onFailure(call: Call<Base1Resp<AddPersonelResp>>, t: Throwable) {
                btn_save_pasangan_edit.hideDrawable(R.string.not_update)
            }

            override fun onResponse(
                call: Call<Base1Resp<AddPersonelResp>>,
                response: Response<Base1Resp<AddPersonelResp>>
            ) {
                if (response.isSuccessful) {
                    btn_save_pasangan_edit.hideDrawable(R.string.data_updated)
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    btn_save_pasangan_edit.hideDrawable(R.string.not_update)

                }
            }
        })
//        Netw
        /* Handler(Looper.getMainLooper()).postDelayed({
             finish()
         }, 500)

         //no Inet
         btn_save_pasangan_edit.hideDrawable(R.string.save)

         //error
         Handler(Looper.getMainLooper()).postDelayed({
             btn_save_pasangan_edit.hideDrawable(R.string.save)
         }, 3000)
         btn_save_pasangan_edit.hideDrawable(R.string.not_update)*/

    }

    private fun doDeletePasangan(pasangan: PasanganMinResp?) {
        NetworkConfig().getServPers().deletePasangan(
            "Bearer ${sessionManager1.fetchAuthToken()}", pasangan?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditPasanganActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditPasanganActivity,
                        R.string.data_deleted,
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Toast.makeText(this@EditPasanganActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun initSP() {
        //spinner
        val agama =
            listOf("Tidak ada", "Islam", "Kristen", "Protestan", "Buddha", "Hindu", "Khonghucu")
        val agamaNowAdapter = ArrayAdapter(this, R.layout.item_spinner, agama)
        val agamaSblmAdapter = ArrayAdapter(this, R.layout.item_spinner, agama)
        sp_agama_skrg_pasangan_edit_edit.setAdapter(agamaNowAdapter)
        sp_agama_skrg_pasangan_edit_edit.setOnItemClickListener { parent, view, position, id ->
            val tes = parent.getItemAtPosition(position).toString().toLowerCase()
            Log.e("3", tes)
            agama_skrg = tes
            Log.e("4", agama_skrg!!)

        }
        val itemPasangan = listOf("Suami", "Istri")
        val adapterPasangan = ArrayAdapter(this, R.layout.item_spinner, itemPasangan)
        spinner_status_pasangan_edit.setAdapter(adapterPasangan)
        spinner_status_pasangan_edit.setOnItemClickListener { parent, view, position, id ->
            val tes = parent.getItemAtPosition(position).toString().toLowerCase()
            Log.e("1", "$tes")
            stts_pasangan = tes
            Log.e("2", "$stts_pasangan")
        }

        sp_agama_sblm_pasangan_edit_edit.setAdapter(agamaSblmAdapter)
        sp_agama_sblm_pasangan_edit_edit.setOnItemClickListener { parent, view, position, id ->
            agama_sblm = when (position) {
                0 -> ""
                1 -> "islam"
                2 -> "katolik"
                3 -> "protestan"
                4 -> "buddha"
                5 -> "hindu"
                else -> "konghuchu"
            }
        }
    }
}
