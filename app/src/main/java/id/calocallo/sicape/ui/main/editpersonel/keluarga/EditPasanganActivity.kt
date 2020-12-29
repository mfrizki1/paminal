package id.calocallo.sicape.ui.main.editpersonel.keluarga

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PasanganReq
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_pasangan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditPasanganActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var pasanganReq = PasanganReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pasangan)
        sessionManager = SessionManager(this)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama.toString()
        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_pasangan_edit.gone()
            btn_delete_pasangan_edit.gone()
        }

        getPasanganData()
        btn_save_pasangan_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_pasangan_edit)
        btn_save_pasangan_edit.setOnClickListener {
            doUpdatePasangan()
        }

        btn_delete_pasangan_edit.setOnClickListener {
            alert("Yakin Hapus Data") {
                positiveButton("Ya") {
                    doDeletePasangan()
                }
                negativeButton("Tidak") {
                }
            }.show()
        }
    }

    private fun getPasanganData() {
        /**
         * ambil data dari api
         * terus masukkan ke editText yang ada
         */
        edt_nama_lngkp_pasangan_edit_edit
        edt_alias_pasangan_edit_edit
        edt_tmpt_ttl_pasangan_edit_edit
        edt_tgl_ttl_pasangan_edit_edit
        edt_suku_pasangan_edit_edit
        edt_kwg_pasangan_edit_edit
        edt_how_to_kwg_pasangan_edit_edit
        edt_aliran_dianut_pasangan_edit_edit
        edt_aliran_diikuti_pasangan_edit_edit
        edt_almt_sblm_kwin_pasangan_edit_edit
        edt_almt_skrg_pasangan_edit_edit
        edt_no_telp_pasangan_edit_edit
        edt_pend_trkhr_pasangan_edit_edit
        edt_kwin_berapa_pasangan_edit_edit
        edt_pekerjaan_pasangan_edit_edit
        edt_almt_pekerjaan_pasangan_edit_edit
        edt_no_telp_pekerjaan_pasangan_edit
        edt_pekerjaan_sblm_pasangan_edit
        edt_kddkn_org_diikuti_pasangan_edit
        edt_thn_org_diikuti_pasangan_edit
        edt_alasan_org_diikuti_pasangan_edit
        edt_almt_org_diikuti_pasangan_edit
        edt_kddkn_org_prnh_pasangan_edit
        edt_thn_org_prnh_pasangan_edit
        edt_alasan_org_prnh_pasangan_edit
        edt_almt_org_prnh_pasangan_edit
        //spinner
        val agama = listOf("Islam", "Kristen", "Protestan", "Budha", "Hindu", "Khonghucu")
        val agamaNowAdapter = ArrayAdapter(this, R.layout.item_spinner, agama)
        val agamaSblmAdapter = ArrayAdapter(this, R.layout.item_spinner, agama)
        sp_agama_skrg_pasangan_edit_edit.setAdapter(agamaNowAdapter)
        sp_agama_sblm_pasangan_edit_edit.setOnItemClickListener { parent, view, position, id ->

        }
        sp_agama_sblm_pasangan_edit_edit.setAdapter(agamaSblmAdapter)
        sp_agama_sblm_pasangan_edit_edit.setOnItemClickListener { parent, view, position, id ->

        }


    }

    private fun doUpdatePasangan() {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_pasangan_edit.showProgress {
            progressColor = Color.WHITE
        }

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

        val agama = listOf("Islam", "Kristen", "Protestan", "Budha", "Hindu", "Konghuchu")
        val agamaNowAdapter = ArrayAdapter(this, R.layout.item_spinner, agama)
        val agamaSblmAdapter = ArrayAdapter(this, R.layout.item_spinner, agama)
        sp_agama_skrg_pasangan_edit_edit.setAdapter(agamaNowAdapter)
        sp_agama_skrg_pasangan_edit_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    pasanganReq.agama_sekarang = "islam"
                }
                1 -> {
                    pasanganReq.agama_sekarang = "katolik"
                }
                2 -> {
                    pasanganReq.agama_sekarang = "protestan"
                }
                3 -> {
                    pasanganReq.agama_sekarang = "buddha"
                }
                4 -> {
                    pasanganReq.agama_sekarang = "hindu"
                }
                5 -> {
                    pasanganReq.agama_sekarang = "konghuchu"
                }
            }
        }
        sp_agama_sblm_pasangan_edit_edit.setAdapter(agamaSblmAdapter)
        sp_agama_sblm_pasangan_edit_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    pasanganReq.agama_sebelumnya = "islam"
                }
                1 -> {
                    pasanganReq.agama_sebelumnya = "katolik"
                }
                2 -> {
                    pasanganReq.agama_sebelumnya = "protestan"
                }
                3 -> {
                    pasanganReq.agama_sebelumnya = "buddha"
                }
                4 -> {
                    pasanganReq.agama_sebelumnya = "hindu"
                }
                5 -> {
                    pasanganReq.agama_sebelumnya = "konghuchu"
                }
            }
        }

        btn_save_pasangan_edit.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_saved
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 500)

        //no Inet
        btn_save_pasangan_edit.hideDrawable(R.string.save)

        //error
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_pasangan_edit.hideDrawable(R.string.save)
        },3000)
        btn_save_pasangan_edit.hideDrawable(R.string.not_update)

    }


    private fun doDeletePasangan() {

    }
}
