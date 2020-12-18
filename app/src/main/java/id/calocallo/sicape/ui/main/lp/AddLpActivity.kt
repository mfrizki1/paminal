package id.calocallo.sicape.ui.main.lp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Laporan Polisi"
        var jenis = ""

        val jenisPelanggaran = listOf("Pidana", "Kode Etik", "Disiplin")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, jenisPelanggaran)
        sp_jenis_lp.setAdapter(adapter)
        sp_jenis_lp.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                jenis = "pidana"
            }else if(position == 1){
                jenis = "kode_etik"
            }else{
                jenis = "disiplin"
            }
        }

        btn_save_lp.setOnClickListener {
            edt_no_lp.text.toString()
            edt_nama_lp.text.toString()
            edt_pangkat_lp.text.toString()
            edt_nrp_lp.text.toString()
            edt_kesatuan_lp.text.toString()
            edt_jabatan_lp.text.toString()
            jenis
            edt_pasal_lp.text.toString()
            edt_hukuman_lp.text.toString()
            edt_ket_lp.text.toString()
            Toast.makeText(this, "Berhasil Masuk", Toast.LENGTH_SHORT).show()
        }


    }
}