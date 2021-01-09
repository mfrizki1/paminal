package id.calocallo.sicape.ui.main.rehab.sktb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.SktbResp
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_sktb.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class DetailSktbActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sktb)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data SKTB"
        val detailSktb = intent.extras?.getParcelable<SktbResp>(DETAIL_SKTB)
        getDetailSktb(detailSktb)
        btn_edit_sktb_detail.setOnClickListener {
            val intent = Intent(this, EditSktbActivity::class.java)
            intent.putExtra(EditSktbActivity.EDIT_SKTB, detailSktb)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


    }

    private fun getDetailSktb(detailSktb: SktbResp?) {
        txt_no_sktb_detail.text = detailSktb?.no_sktb

        if (detailSktb?.skhd?.id != null) {
            txt_title_skhd_putkke_sktb_detail.text = "No SKHD"
            txt_no_skhd_putkke_sktb_detail.text = detailSktb.skhd!!.no_skhd
        } else if (detailSktb?.putkke?.id != null) {
            txt_title_skhd_putkke_sktb_detail.text = "No PUT KKE"
            txt_no_skhd_putkke_sktb_detail.text = detailSktb.putkke!!.no_putkke
        }
        txt_menimbang_p2_sktb_detail.text = detailSktb?.menimbang_p2
        txt_mengingat_p5_sktb_detail.text = detailSktb?.mengingat_p5
        txt_kota_penetapan_sktb_detail.text = "Kota : ${detailSktb?.kota_penetapan}"
        txt_tanggal_penetapan_sktb_detail.text = "Tanggal : ${detailSktb?.tanggal_penetapan}"
        txt_nama_pimpinan_sktb_detail.text = detailSktb?.nama_yang_menetapkan
        txt_pangkat_nrp_sktb_detail.text =
            "Pangkat ${detailSktb?.pangkat_yang_menetapkan.toString()
                .toUpperCase()}, NRP : ${detailSktb?.nrp_yang_menetapkan}"
        txt_jabatan_pimpinan_sktb_detail.text = detailSktb?.jabatan_yang_menetapkan
        txt_tembusan_sktb_detail.text = detailSktb?.tembusan
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
        const val DETAIL_SKTB = "DETAIL_SKTB"
    }
}