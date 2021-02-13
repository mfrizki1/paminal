package id.calocallo.sicape.ui.main.skhd.tinddisiplin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.TindDisiplinResp
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_tind_disiplin.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class DetailTindDisiplinActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tind_disiplin)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Tindakan Disiplin"

        val detailTindDisiplin =
            intent.extras?.getParcelable<TindDisiplinResp>(SkhdTindDisiplinActivity.EDIT_TIND_DISIPLIN)
        btn_edit_tind_disiplin.setOnClickListener {
            val intent = Intent(this, EditTindDisiplinSkhdActivity::class.java)
            intent.putExtra(SkhdTindDisiplinActivity.EDIT_TIND_DISIPLIN, detailTindDisiplin)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getDetailTindDisiplin(detailTindDisiplin)
    }

    private fun getDetailTindDisiplin(detailTindDisiplin: TindDisiplinResp?) {
        txt_tindakan_detail.text = detailTindDisiplin?.isi_tindakan_disiplin
        txt_nama_personel_tind_disiplin_detail.text ="Nama : ${detailTindDisiplin?.personel?.nama}"
        txt_pangkat_personel_tind_disiplin_detail.text ="Pangkat : ${detailTindDisiplin?.personel?.pangkat.toString().toUpperCase()}"
        txt_nrp_personel_tind_disiplin_detail.text ="NRP : ${detailTindDisiplin?.personel?.nrp}"
        txt_jabatan_personel_tind_disiplin_detail.text ="Jabatan : ${detailTindDisiplin?.personel?.jabatan}"
        txt_kesatuan_personel_tind_disiplin_detail.text ="Kesatuan : ${detailTindDisiplin?.personel?.kesatuan}"
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
                ApiDelete()
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete() {

    }
}