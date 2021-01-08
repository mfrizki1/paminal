package id.calocallo.sicape.ui.main.putkke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.PutKkeResp
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_put_kke.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class DetailPutKkeActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_put_kke)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Putusan Kode Etik"
        val detailPutKke =
            intent.extras?.getParcelable<PutKkeResp>(ListPutKkeActivity.DETAIL_PUTKKE)

        getDetailPutKKe(detailPutKke)

        btn_edit_put_kke_detail.setOnClickListener {
            val intent = Intent(this, EditPutKkeActivity::class.java)
            intent.putExtra(EDIT_PUT_KKE, detailPutKke)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    companion object {
        const val EDIT_PUT_KKE = "EDIT_PUT_KKE"
    }

    private fun getDetailPutKKe(detailPutKke: PutKkeResp?) {
        txt_no_put_kke_detail.text = detailPutKke?.no_putkke
        txt_no_lhp_put_kke_detail.text = detailPutKke?.lhp?.no_lhp
        txt_no_lp_put_kke_detail.text = detailPutKke?.lp?.no_lp
        txt_pembukaan_putusan_put_kke_detail.text = detailPutKke?.pembukaan_putusan
        txt_berkas_pemeriksaan_put_kke_detail.text = detailPutKke?.menimbang_p2
        txt_keputusan_kapolda_put_kke_detail.text = detailPutKke?.mengingat_p4
        txt_memperhatikan_p1_put_kke_detail.text = detailPutKke?.memperhatikan_p1
        txt_memperhatikan_p3_put_kke_detail.text = detailPutKke?.memperhatikan_p3
        txt_memperhatikan_p5_put_kke_detail.text = detailPutKke?.memperhatikan_p5
        txt_sanksi_rekomendasi_put_kke_detail.text = detailPutKke?.sanksi_rekomendasi
        txt_memutuskan_p1_put_kke_detail.text = detailPutKke?.memutuskan_p1
        txt_sanksi_hasil_putusan_put_kke_detail.text = detailPutKke?.sanksi_hasil_keputusan

        txt_nama_ketua_komisi_put_kke_detail.text = "Nama : ${detailPutKke?.nama_ketua_komisi}"
        txt_pangkat_nrp_ketua_komisi_put_kke_detail.text =
            "Pangkat : ${detailPutKke?.pangkat_ketua_komisi}, NRP : ${detailPutKke?.nrp_ketua_komisi}"

        txt_nama_wakil_ketua_komisi_put_kke_detail.text =
            "Nama : ${detailPutKke?.nama_wakil_ketua_komisi}"
        txt_pangkat_nrp_wakil_ketua_komisi_put_kke_detail.text =
            "Pangkat : ${detailPutKke?.pangkat_wakil_ketua_komisi}, NRP : ${detailPutKke?.nrp_wakil_ketua_komisi}"


        txt_nama_anggota_komisi_put_kke_detail.text = "Nama : ${detailPutKke?.nama_anggota_komisi}"
        txt_pangkat_nrp_anggota_komisi_put_kke_detail.text =
            "Pangkat : ${detailPutKke?.pangkat_anggota_komisi}, NRP : ${detailPutKke?.nrp_anggota_komisi}"


        txt_kota_putusan_put_kke_detail.text = "Kota : ${detailPutKke?.kota_putusan}"
        txt_tanggal_putusan_put_kke_detail.text = "Tanggal : ${detailPutKke?.tanggal_putusan}"
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