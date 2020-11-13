package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_pasangan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddPasanganActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pasangan)

        setupActionBarWithBackButton(toolbar)

        supportActionBar?.title = "Pasangan"

        val nama_lngkp = edt_nama_lngkp_pasangan.text.toString()
        val alias = edt_alias_pasangan.text.toString()
        val tmpt_lhr = edt_tmpt_ttl_pasangan.text.toString()
        val tgl_lhr = edt_tgl_ttl_pasangan.text.toString()
        val suku = edt_suku_pasangan.text.toString()
        val kwg = edt_kwg_pasangan.text.toString()
        val how_to_kwg = edt_how_to_kwg_pasangan.text.toString()
        val agama_skrg = edt_agm_skrg_pasangan.text.toString()
        val agama_sblm = edt_agm_sblm_pasangan.text.toString()
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

        btn_next_pasangan.setOnClickListener {
            //iniAPI(allModel)
            //berhasil -> GO
            //GAGAL -> TOAST
            startActivity(Intent(this, AddAyahKandungActivity::class.java))

        }
    }
}