package id.calocallo.sicape.ui.main.editpersonel.keluarga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PasanganReq
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_pasangan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditPasanganActivity : BaseActivity() {
    private lateinit var sessionManager : SessionManager
    private var pasanganReq = PasanganReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pasangan)
        sessionManager = SessionManager(this)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama.toString()
        val hak = sessionManager.fetchHakAkses()
        if(hak == "operator"){
            btn_save_pasangan_edit.gone()
            btn_delete_pasangan_edit.gone()
        }

        getPasanganData()

        btn_save_pasangan_edit.setOnClickListener{
            doUpdatePasangan()
        }

        btn_delete_pasangan_edit.setOnClickListener {
            alert("Yakin Hapus Data"){
                positiveButton("Ya"){
                    doDeletePasangan()
                }
                negativeButton("Tidak"){
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
        val agama = listOf("Islam","Kristen","Protestan","Budha","Hindu","Khonghucu")
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
        pasanganReq.nama = edt_nama_lngkp_pasangan_edit_edit.text.toString()
        pasanganReq.nama_alias =edt_alias_pasangan_edit_edit.text.toString()
        pasanganReq.tempat_lahir = edt_tmpt_ttl_pasangan_edit_edit.text.toString()
        pasanganReq.tanggal_lahir = edt_tgl_ttl_pasangan_edit_edit.text.toString()
        pasanganReq.ras = edt_suku_pasangan_edit_edit.text.toString()
        pasanganReq.kewarganegaraan = edt_kwg_pasangan_edit_edit.text.toString()
        pasanganReq.cara_peroleh_kewarganegaraan = edt_how_to_kwg_pasangan_edit_edit.text.toString()
        pasanganReq.aliran_kepercayaan_dianut = edt_aliran_dianut_pasangan_edit_edit.text.toString()
        pasanganReq.aliran_kepercayaan_diikuti = edt_aliran_diikuti_pasangan_edit_edit.text.toString()
        pasanganReq.alamat_terakhir_sebelum_kawin = edt_almt_sblm_kwin_pasangan_edit_edit.text.toString()
        pasanganReq.alamat_rumah = edt_almt_skrg_pasangan_edit_edit.text.toString()
        pasanganReq.no_telp_rumah = edt_no_telp_pasangan_edit_edit.text.toString()
        pasanganReq.pendidikan_terakhir = edt_pend_trkhr_pasangan_edit_edit.text.toString()
        pasanganReq.perkawinan_keberapa = edt_kwin_berapa_pasangan_edit_edit.text.toString()
        pasanganReq.pekerjaan = edt_pekerjaan_pasangan_edit_edit.text.toString()
        pasanganReq.alamat_kantor = edt_almt_pekerjaan_pasangan_edit_edit.text.toString()
        pasanganReq.no_telp_kantor = edt_no_telp_pekerjaan_pasangan_edit.text.toString()
        pasanganReq.pekerjaan_sebelumnya = edt_pekerjaan_sblm_pasangan_edit.text.toString()
        pasanganReq.kedudukan_organisasi_saat_ini = edt_kddkn_org_diikuti_pasangan_edit.text.toString()
        pasanganReq.tahun_bergabung_organisasi_saat_ini = edt_thn_org_diikuti_pasangan_edit.text.toString()
        pasanganReq.alasan_bergabung_organisasi_saat_ini = edt_alasan_org_diikuti_pasangan_edit.text.toString()
        pasanganReq.alamat_organisasi_saat_ini = edt_almt_org_diikuti_pasangan_edit.text.toString()
        pasanganReq.kedudukan_organisasi_sebelumnya = edt_kddkn_org_prnh_pasangan_edit.text.toString()
        pasanganReq.tahun_bergabung_organisasi_sebelumnya = edt_thn_org_prnh_pasangan_edit.text.toString()
        pasanganReq.alasan_bergabung_organisasi_sebelumnya = edt_alasan_org_prnh_pasangan_edit.text.toString()
        pasanganReq.alamat_organisasi_sebelumnya = edt_almt_org_prnh_pasangan_edit.text.toString()

        val agama = listOf("Islam","Kristen","Protestan","Budha","Hindu","Khonghucu")
        val agamaNowAdapter = ArrayAdapter(this, R.layout.item_spinner, agama)
        val agamaSblmAdapter = ArrayAdapter(this, R.layout.item_spinner, agama)
        sp_agama_skrg_pasangan_edit_edit.setAdapter(agamaNowAdapter)
        sp_agama_sblm_pasangan_edit_edit.setOnItemClickListener { parent, view, position, id ->

        }
        sp_agama_sblm_pasangan_edit_edit.setAdapter(agamaSblmAdapter)
        sp_agama_sblm_pasangan_edit_edit.setOnItemClickListener { parent, view, position, id ->

        }
    }

    private fun doDeletePasangan(){

    }
}