package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.PendPersonelActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_add_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*


class AddPersonelActivity : BaseActivity() {
    private lateinit var sheenValidator: SheenValidator
    private var jk: String? = null
    private var sttsKawin: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_personel)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Personel"
        sheenValidator = SheenValidator(this)


        initSpinner(spinner_jk, spinner_stts_kwn)
//        sheenValidator.registerAsRequired(edt_nama_lengkap)
//        sheenValidator.registerAsRequired(edt_nama_alias)
//        sheenValidator.registerAsRequired(edt_tmpt_ttl)
//        sheenValidator.registerAsRequired(edt_tgl_ttl)
//        sheenValidator.registerAsRequired(edt_pekerjaan)
//        sheenValidator.registerAsRequired(edt_pangkat)
//        sheenValidator.registerAsRequired(edt_nrp)
//        sheenValidator.registerAsRequired(edt_kesatuan)
//        sheenValidator.registerAsRequired(edt_almt_kntr)
//        sheenValidator.registerAsPhone(edt_no_telp_kntr)
//        sheenValidator.registerAsRequired(edt_almt_rmh)
//        sheenValidator.registerAsPhone(edt_no_telp_rmh)
//        sheenValidator.registerAsRequired(edt_kwg)
//        sheenValidator.registerAsRequired(edt_how_to_kwg)
//        sheenValidator.registerAsRequired(edt_tmpt_kwn)
//        sheenValidator.registerAsRequired(edt_tgl_kwn)
//        sheenValidator.registerAsRequired(edt_almt_ktp)
//        sheenValidator.registerAsRequired(edt_no_ktp)
//        sheenValidator.registerAsRequired(edt_hobi)
//        sheenValidator.registerAsRequired(edt_kebiasaan)
//        sheenValidator.registerAsRequired(edt_bahasa)
        btn_next_personel.setOnClickListener {
            sheenValidator.validate()
            val namaLengkap = edt_nama_lengkap.text.toString()
            val namaAlias = edt_nama_alias.text.toString()
            val tmptLahir = edt_tmpt_ttl.text.toString()
            val tglLahir = edt_tgl_ttl.text.toString()
            val pekerjaann = edt_pekerjaan.text.toString()
            val pangkat = edt_pangkat.text.toString()
            val nrp = edt_nrp.text.toString()
            val kesatuan = edt_kesatuan.text.toString()
            val almtKantor = edt_almt_kntr.text.toString()
            val noTelpKantor = edt_no_telp_kntr.text.toString()
            val almtRumah = edt_almt_rmh.text.toString()
            val noTelpRumah = edt_no_telp_rmh.text.toString()
            val kwg = edt_kwg.text.toString()
            val howToKWG = edt_how_to_kwg.text.toString()
            val tmptKawin = edt_tmpt_kwn.text.toString()
            val tglKawin = edt_tgl_kwn.text.toString()
            val almtKtp = edt_almt_ktp.text.toString()
            val noKtp = edt_no_ktp.text.toString()
            val hobi = edt_hobi.text.toString()
            val kebiasaan = edt_kebiasaan.text.toString()
            val bahasa = edt_bahasa.text.toString()
            val suku = edt_suku.text.toString()
            val agama_skrg = edt_agm_now.text.toString()
            val agama_sblm = edt_agm_before.text.toString()
            val jmlh_anak = edt_jmlh_anak.text.toString()

            Log.e(
                "IDEN", "$namaLengkap, $namaAlias, $tmptLahir, $tglLahir," +
                        "$pekerjaann, $pangkat / $nrp, $kesatuan, $almtKantor / $noTelpKantor," +
                        "$almtRumah / $noTelpRumah, $kwg, $howToKWG, $tmptKawin / $tglKawin," +
                        "$almtKtp, $noKtp, $hobi, $kebiasaan, $bahasa, $jk, $sttsKawin, $suku, $agama_skrg," +
                        "$agama_sblm, $jmlh_anak"
            )

            startActivity(Intent(this@AddPersonelActivity, PendPersonelActivity::class.java))

        }

    }

    private fun initSpinner(spinner_jk: AutoCompleteTextView, stts_kawin: AutoCompleteTextView) {
        val jkItems = listOf("Laki-Laki", "Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.item_spinner, jkItems)
        spinner_jk.setAdapter(adapterJk)
        spinner_jk.setOnItemClickListener { parent, view, position, id ->
            jk = parent.getItemAtPosition(position).toString()
//            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
        }

        val sttsKawinItem = listOf("Ya", "Tidak")
        val adapterSttusKawin = ArrayAdapter(this, R.layout.item_spinner, sttsKawinItem)
        stts_kawin.setAdapter(adapterSttusKawin)
        stts_kawin.setOnItemClickListener { parent, view, position, id ->
            sttsKawin = parent.getItemAtPosition(position).toString()
//            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show()
        }
    }
}