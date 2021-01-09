package id.calocallo.sicape.ui.main.rehab.rpph

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.RpphResp
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_rpph.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class DetailRpphActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_rpph)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data RPPH"

        val detailRpph = intent.extras?.getParcelable<RpphResp>(DETAIL_RPPH)
        getViewRpph(detailRpph)

        btn_edit_rpph_detail.setOnClickListener {
            val intent = Intent(this, EditRpphActivity::class.java)
            intent.putExtra(EditRpphActivity.EDIT_RPPH_EDIT, detailRpph)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getViewRpph(detailRpph: RpphResp?) {
        txt_no_rpph_detail.text = detailRpph?.no_rpph
        txt_no_put_kke_rpph_detail.text = detailRpph?.putkke?.no_putkke
        txt_dasar_rpph_detail.text = detailRpph?.dasar_ph
        txt_isi_rekomendasi_rpph_detail.text = detailRpph?.isi_rekomendasi
        txt_kota_penetapan_rpph_detail.text = "Kota : ${detailRpph?.kota_penetapan}"
        txt_tanggal_penetapan_rpph_detail.text = "Tanggal : ${detailRpph?.tanggal_penetapan}"
        txt_nama_pimpinan_rpph_detail.text = "Nama : ${detailRpph?.nama_yang_menetapkan}"
        txt_pangkat_nrp_pimpinan_rpph_detail.text =
            "Pangkat : ${detailRpph?.pangkat_yang_menetapkan.toString()
                .toUpperCase()}, NRP : ${detailRpph?.nrp_yang_menetapkan}"
        txt_jabatan_pimpinan_rpph_detail.text = "Jabatan : ${detailRpph?.jabatan_yang_menetapkan}"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_delete, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete_item -> {
                alertDialogDelete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alertDialogDelete() {
        this.alert("Yakin Hapus Data?") {
            positiveButton("Iya") {
//                ApiDelete()
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    companion object {
        const val DETAIL_RPPH = "DETAIL_RPPH"
    }
}