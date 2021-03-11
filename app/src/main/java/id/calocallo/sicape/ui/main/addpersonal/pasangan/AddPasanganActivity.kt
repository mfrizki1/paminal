package id.calocallo.sicape.ui.main.addpersonal.pasangan

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PasanganReq
import id.calocallo.sicape.network.response.AddPersonelResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.ui.main.addpersonal.AddAyahKandungActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_pasangan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPasanganActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var pasanganReq = arrayListOf<PasanganReq>()
    private var agama_skrg = ""
    private var agama_sblm = ""
    private var stts_pasangan = ""
    private var isAddSingle = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pasangan)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Pasangan"
        isAddSingle = intent.getBooleanExtra("ADD_PASANGAN", false)
        if (isAddSingle) {
            txt_pasangan.gone()
            txt_layout_status_pasangan_add.visible()
        }

        btn_next_pasangan.attachTextChangeAnimator()
        bindProgressButton(btn_next_pasangan)
        btn_next_pasangan.setOnClickListener {
            val nama_lngkp = edt_nama_lngkp_pasangan.text.toString()
            val alias = edt_alias_pasangan.text.toString()
            val tmpt_lhr = edt_tmpt_ttl_pasangan.text.toString()
            val tgl_lhr = edt_tgl_ttl_pasangan.text.toString()
            val suku = edt_suku_pasangan.text.toString()
            val kwg = edt_kwg_pasangan.text.toString()
            val how_to_kwg = edt_how_to_kwg_pasangan.text.toString()
            val aliran_dianut = edt_aliran_dianut_pasangan.text.toString()
            val aliran_diikuti = edt_aliran_diikuti_pasangan.text.toString()
            val almt_sblm_kwn = edt_almt_sblm_kwin_pasangan.text.toString()
            val almt_rmh_skrg = edt_almt_skrg_pasangan.text.toString()
            val no_telp_rmh = edt_no_telp_pasangan.text.toString()
            val pend_terakhir = edt_pend_trkhr_pasangan.text.toString()
            val pernikahan_kbrpa = edt_kwin_berapa_pasangan.text.toString()
            val pekerjaan = edt_pekerjaan_pasangan.text.toString()
            val almt_kntr = edt_almt_pekerjaan_pasangan.text.toString()
            val no_telp_kntr = edt_no_telp_pekerjaan_pasangan.text.toString()
            val pekerjaan_sblm = edt_pekerjaan_sblm_pasangan.text.toString()

            //organisasi yang diikuti
            val kedudukan_org_diikuti = edt_kddkn_org_diikuti_pasangan.text.toString()
            val thn_org_diikuti = edt_thn_org_diikuti_pasangan.text.toString()
            val alasan_org_diikuti = edt_alasan_org_diikuti_pasangan.text.toString()
            val almt_org_diikuti = edt_almt_org_diikuti_pasangan.text.toString()

            //organisasi lain yang pernah diikuti
            val kedudukan_org_prnh_diikuti = edt_kddkn_org_prnh_pasangan.text.toString()
            val thn_org_prnh_diikuti = edt_thn_org_prnh_pasangan.text.toString()
            val alasan_org_prnh_diikuti = edt_alasan_org_prnh_pasangan.text.toString()
            val almt_org_prnh_diikuti = edt_almt_org_prnh_pasangan.text.toString()

            pasanganReq.add(
                PasanganReq(
                    stts_pasangan,
                    nama_lngkp,
                    alias,
                    tmpt_lhr,
                    tgl_lhr,
                    suku,
                    kwg,
                    how_to_kwg,
                    agama_skrg,
                    agama_sblm,
                    aliran_dianut,
                    aliran_diikuti,
                    almt_sblm_kwn,
                    almt_rmh_skrg,
                    no_telp_rmh,
                    pend_terakhir,
                    pernikahan_kbrpa,
                    pekerjaan,
                    almt_kntr,
                    no_telp_kntr,
                    pekerjaan_sblm,
                    kedudukan_org_diikuti,
                    thn_org_diikuti,
                    alasan_org_diikuti,
                    almt_org_diikuti,
                    kedudukan_org_prnh_diikuti,
                    thn_org_prnh_diikuti,
                    alasan_org_prnh_diikuti,
                    almt_org_prnh_diikuti
                )
            )
            val personel = intent.getParcelableExtra<AllPersonelModel1>("PERSONEL_DETAIL")
            if (isAddSingle) {
                addSingle()
                Log.e("pasangan", "$pasanganReq")
            } else {
                if (pasanganReq[0].nama != null) {
                    Log.e("pasangan", "$pasanganReq")
                    sessionManager1.setPasangan(pasanganReq)
                }
                Log.e("pasanganOn", "${sessionManager1.getPasangan()}")
                startActivity(Intent(this, AddAyahKandungActivity::class.java))
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        }
        SP()
    }

    private fun addSingle() {
        Log.e("stts_pasangan", "$stts_pasangan")
        btn_next_pasangan.text = "Simpan"
        btn_next_pasangan.showProgress {
            progressColor = Color.WHITE
        }
        val personel = intent.getParcelableExtra<AllPersonelModel1>("PERSONEL_DETAIL")
        NetworkConfig().getServPers().addPasanganSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}", personel?.id.toString(), pasanganReq[0]
        ).enqueue(object : Callback<Base1Resp<AddPersonelResp>> {
            override fun onFailure(call: Call<Base1Resp<AddPersonelResp>>, t: Throwable) {
                btn_next_pasangan.hideProgress(R.string.not_save)
                pasanganReq.clear()
            }

            override fun onResponse(
                call: Call<Base1Resp<AddPersonelResp>>,
                response: Response<Base1Resp<AddPersonelResp>>
            ) {
                if (response.isSuccessful) {
                    btn_next_pasangan.hideProgress(R.string.data_saved)
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                        pasanganReq.clear()
                    }, 500)
                } else {
                    btn_next_pasangan.hideProgress(R.string.not_save)
                    pasanganReq.clear()
                }
            }
        })
    }

    private fun tes(pasangan: ArrayList<PasanganReq>) {
        edt_nama_lngkp_pasangan.setText(pasangan[0].nama)
        edt_alias_pasangan.setText(pasangan[0].nama_alias)
        edt_tmpt_ttl_pasangan.setText(pasangan[0].tempat_lahir)
        edt_tgl_ttl_pasangan.setText(pasangan[0].tanggal_lahir)
        edt_suku_pasangan.setText(pasangan[0].ras)
        edt_kwg_pasangan.setText(pasangan[0].kewarganegaraan)
        edt_how_to_kwg_pasangan.setText(pasangan[0].cara_peroleh_kewarganegaraan)
//        val agama_skrg = sp_agama_skrg_pasangan.setText()
//        val agama_sblm = sp_agama_sblm_pasangan.setText()
        edt_aliran_dianut_pasangan.setText(pasangan[0].aliran_kepercayaan_dianut)
        edt_aliran_diikuti_pasangan.setText(pasangan[0].aliran_kepercayaan_diikuti)
        edt_almt_sblm_kwin_pasangan.setText(pasangan[0].alamat_terakhir_sebelum_kawin)
        edt_almt_skrg_pasangan.setText(pasangan[0].alamat_rumah)
        edt_no_telp_pasangan.setText(pasangan[0].no_telp_rumah)
        edt_pend_trkhr_pasangan.setText(pasangan[0].pendidikan_terakhir)
        edt_kwin_berapa_pasangan.setText(pasangan[0].perkawinan_keberapa)
        edt_pekerjaan_pasangan.setText(pasangan[0].pekerjaan)
        edt_almt_pekerjaan_pasangan.setText(pasangan[0].alamat_kantor)
        edt_no_telp_pekerjaan_pasangan.setText(pasangan[0].no_telp_kantor)
        edt_pekerjaan_sblm_pasangan.setText(pasangan[0].pekerjaan_sebelumnya)

        //organisasi yang diikuti
        edt_kddkn_org_diikuti_pasangan.setText(pasangan[0].kedudukan_organisasi_saat_ini)
        edt_thn_org_diikuti_pasangan.setText(pasangan[0].tahun_bergabung_organisasi_saat_ini)
        edt_alasan_org_diikuti_pasangan.setText(pasangan[0].alasan_bergabung_organisasi_saat_ini)
        edt_almt_org_diikuti_pasangan.setText(pasangan[0].alamat_organisasi_saat_ini)

        //organisasi lain yang pernah diikuti
        edt_kddkn_org_prnh_pasangan.setText(pasangan[0].kedudukan_organisasi_sebelumnya)
        edt_thn_org_prnh_pasangan.setText(pasangan[0].tahun_bergabung_organisasi_sebelumnya)
        edt_alasan_org_prnh_pasangan.setText(pasangan[0].alasan_bergabung_organisasi_sebelumnya)
        edt_almt_org_prnh_pasangan.setText(pasangan[0].alamat_organisasi_sebelumnya)
        agama_skrg = pasangan[0].agama_sekarang.toString()
        agama_sblm = pasangan[0].agama_sebelumnya.toString()
        sp_agama_skrg_pasangan.setText(pasangan[0].agama_sekarang)
        sp_agama_sblm_pasangan.setText(pasangan[0].agama_sebelumnya)
        SP()
    }

    private fun SP() {
        val agama = listOf("Islam", "Katolik", "Protestan", "Buddha", "Hindu", "Konghuchu")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, agama)
        sp_agama_skrg_pasangan.setAdapter(adapter)
        sp_agama_skrg_pasangan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                agama_skrg = "islam"
            } else if (position == 1) {
                agama_skrg = "katolik"
            } else if (position == 2) {
                agama_skrg = "protestan"
            } else if (position == 3) {
                agama_skrg = "buddha"
            } else if (position == 4) {
                agama_skrg = "hindu"
            } else {
                agama_skrg = "konghuchu"
            }
        }

        sp_agama_sblm_pasangan.setAdapter(adapter)
        sp_agama_sblm_pasangan.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                agama_sblm = "islam"
            } else if (position == 1) {
                agama_sblm = "katolik"
            } else if (position == 2) {
                agama_sblm = "protestan"
            } else if (position == 3) {
                agama_sblm = "buddha"
            } else if (position == 4) {
                agama_sblm = "hindu"
            } else {
                agama_sblm = "konghuchu"
            }
        }

        val itemPasangan = listOf("Suami", "Istri")
        val adapterPasangan = ArrayAdapter(this, R.layout.item_spinner, itemPasangan)
        spinner_status_pasangan_add.setAdapter(adapterPasangan)
        spinner_status_pasangan_add.setOnItemClickListener { parent, view, position, id ->
            val tes = parent.getItemAtPosition(position).toString().toLowerCase()
            stts_pasangan = tes
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isAddSingle) {
            val pasangan = sessionManager1.getPasangan()
            Log.e("pasangan", "$pasangan")
            if (pasangan.isNotEmpty()) {
                tes(pasangan)
            }
        }
        SP()
    }
}