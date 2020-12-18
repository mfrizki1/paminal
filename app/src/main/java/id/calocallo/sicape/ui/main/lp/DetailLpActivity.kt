package id.calocallo.sicape.ui.main.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpDisiplinModel
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class DetailLpActivity : BaseActivity() {
    private lateinit var sessionManager : SessionManager
    companion object{
        const val DETAIL_LP = "DETAIL_LP"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        val detailLP = intent.extras?.getParcelable<LpDisiplinModel>(DETAIL_LP)
        supportActionBar?.title = "Detail Laporan Polisi ${detailLP?.jenis_pelanggaran}"
        Log.e("detailLP", "$detailLP")

        txt_hukuman_lp_detail.text = detailLP?.hukuman
        txt_kesatuan_lp_detail.text = detailLP?.kesatuan
        txt_ket_lp_detail.text = detailLP?.keterangan
        txt_nama_lp_detail.text = detailLP?.nama_personel
        txt_no_lp_detail.text = detailLP?.no_lp
        txt_pangkat_nrp_lp_detail.text = "Pangkat : ${detailLP?.no_lp}    NRP : ${detailLP?.nrp_personel}"
        txt_pasal_lp_detail.text = detailLP?.pasal

        //edit
        val hakAkses = sessionManager.fetchHakAkses()
        if (hakAkses == "operator") {
            btn_edit_lp.toggleVisibility()
//            btn_edit_personel.toggleVisibility()
        }
        btn_edit_lp.setOnClickListener {
            val intent = Intent(this, EditLpActivity::class.java)
            intent.putExtra(EditLpActivity.EDIT_LP, detailLP)
            startActivity(intent)
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
        this.alert("Hapus Data", "Yakin Hapus?") {
            positiveButton("Iya") {
//                ApiDelete()
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }




}