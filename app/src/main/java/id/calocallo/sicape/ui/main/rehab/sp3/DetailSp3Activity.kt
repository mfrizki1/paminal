package id.calocallo.sicape.ui.main.rehab.sp3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.Sp3Resp
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_sp3.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class DetailSp3Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sp3)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data SP3"

        val getSp3 = intent.extras?.getParcelable<Sp3Resp>(DETAIL_SP3)
        getDataSp3View(getSp3)
        btn_edit_sp3_detail.setOnClickListener {
            val intent = Intent(this, EditSp3Activity::class.java)
            intent.putExtra(EditSp3Activity.EDT_SP3, getSp3)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_generate_sp3_detail.attachTextChangeAnimator()
        bindProgressButton(btn_generate_sp3_detail)
        btn_generate_sp3_detail.setOnClickListener {
            btn_generate_sp3_detail.showProgress {
                progressColor = Color.WHITE
            }

            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_sp3_detail.hideProgress(R.string.success_generate_doc)
                alert(R.string.download) {
                    positiveButton(R.string.iya) {
                        btn_generate_sp3_detail.hideProgress(R.string.generate_dokumen)

                    }
                    negativeButton(R.string.tidak) {
                        btn_generate_sp3_detail.hideProgress(R.string.generate_dokumen)

                    }
                }.show()
            }, 2000)
        }

    }

    private fun getDataSp3View(sp3: Sp3Resp?) {
        txt_no_sp3_detail.text = sp3?.no_sp4
        txt_mengingat_p4_sp3_detail.text = sp3?.mengingat_p4
        txt_mengingat_p5_sp3_detail.text = sp3?.mengingat_p5
        txt_menetapkan_p1_sp3_detail.text = sp3?.menetapkan_p1
        txt_kota_keluar_sp3_detail.text = "Kota : ${sp3?.kota_keluar}"
        txt_tanggal_keluar_sp3_detail.text = "Tanggal : ${formatterTanggal(sp3?.tanggal_keluar)}"
        txt_nama_akreditor_sp3_detail.text = "Nama : ${sp3?.nama_akreditor}"
        txt_pangkat_nrp_akreditor_sp3_detail.text = "Pangkat : ${sp3?.pangkat_akreditor.toString()
            .toUpperCase()}, NRP : ${sp3?.nrp_akreditor}"

        if (sp3?.sktb?.id != null) {
            txt_title_sktb_sktt__sp3_detail.text = "No SKTB"
            txt_no_sktb_sktt_sp3_detail.text = sp3.sktb?.no_sktb
        } else {
            txt_title_sktb_sktt__sp3_detail.text = "No SKTT"
            txt_no_sktb_sktt_sp3_detail.text = sp3?.sktt?.no_sktt
        }

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
        const val DETAIL_SP3 = "DETAIL_SP3"
    }
}