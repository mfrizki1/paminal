package id.calocallo.sicape.ui.main.lp.kke

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpKkeResp
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.ui.main.lp.kke.EditLpKkeActivity.Companion.EDIT_KKE
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalLpEditActivity
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpEditActivity
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpEditActivity.Companion.EDIT_SAKSI_KKE
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lp_kke.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class DetailLpKkeActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var adapterDetailPasalKke = ReusableAdapter<LpPasalResp>(this)
    private lateinit var callbackDetailPasalKke: AdapterCallback<LpPasalResp>

    private var adapterDetailSaksiKke = ReusableAdapter<LpSaksiResp>(this)
    private lateinit var callbackDetailSaksiKke: AdapterCallback<LpSaksiResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lp_kke)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Laporan Polisi Kode Etik"
        val detailKKe = intent.extras?.getParcelable<LpKkeResp>(DETAIL_KKE)
        getViewKke(detailKKe)
        sessionManager = SessionManager(this)
//        adapterDetailPasalKke = ReusableAdapter(this)
//        adapterDetailSaksiKke = ReusableAdapter(this)

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_edit_pasal_kke.gone()
            btn_edit_saksi_kke.gone()
            btn_edit_kke.gone()
        }

        //EDIT LP KKE
        btn_edit_kke.setOnClickListener {
            val intent = Intent(this, EditLpKkeActivity::class.java)
            intent.putExtra(EDIT_KKE, detailKKe)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }
        //EDIT LP KKE (SAKSI)
        btn_edit_saksi_kke.setOnClickListener {
            val intent = Intent(this, PickSaksiLpEditActivity::class.java)
            intent.putExtra(EDIT_SAKSI_KKE, detailKKe)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

        //EDIT LP KKE (PASAL)
        btn_edit_pasal_kke.setOnClickListener {
            val intent = Intent(this, PickPasalLpEditActivity::class.java)
            intent.putExtra(PickPasalLpEditActivity.EDIT_PASAL_KKE, detailKKe)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

    }

    private fun getViewKke(detailKKe: LpKkeResp?) {
        //general
        txt_detail_no_lp_kke.text = "No LP :  ${detailKKe?.no_lp}"
        txt_detail_isi_laporan_kke.text = detailKKe?.isi_laporan

        //terlapor
        txt_detail_nama_terlapor_kke.text = "Nama : ${detailKKe?.personel_terlapor?.nama}"
        txt_detail_pangkat_nrp_terlapor_kke.text =
            "Pangkat : ${detailKKe?.personel_terlapor?.pangkat}, NRP : ${detailKKe?.personel_terlapor?.nrp}"
        txt_detail_jabatan_terlapor_kke.text = "Jabatan : ${detailKKe?.personel_terlapor?.jabatan}"
        txt_detail_kesatuan_terlapor_kke.text =
            "Kesatuan : ${detailKKe?.personel_terlapor?.kesatuan}"

        //pelapor
        txt_detail_nama_pelapor_kke.text = "Nama : ${detailKKe?.personel_pelapor?.nama}"
        txt_detail_pangkat_nrp_pelapor_kke.text =
            "Pangkat : ${detailKKe?.personel_pelapor?.pangkat}, NRP : ${detailKKe?.personel_pelapor?.nrp}"
        txt_detail_jabatan_pelapor_kke.text = "Jabatan : ${detailKKe?.personel_pelapor?.jabatan}"
        txt_detail_kesatuan_pelapor_kke.text = "Kesatuan : ${detailKKe?.personel_pelapor?.kesatuan}"

        txt_detail_alat_bukti_kke.text = detailKKe?.alat_bukti
        txt_detail_kota_buat_kke.text = "Kota : ${detailKKe?.kota_buat_laporan}"
        txt_detail_tgl_buat_kke.text = "Tanggal : ${detailKKe?.tanggal_buat_laporan}"
        txt_detail_nama_pimpinan_kke.text = "Nama : ${detailKKe?.nama_yang_mengetahui}"
        txt_detail_pangkat_nrp_pimpinan_kke.text =
            "Pangkat : ${detailKKe?.pangkat_yang_mengetahui}, NRP : ${detailKKe?.nrp_yang_mengetahui}"
        txt_detail_jabatan_pimpinan_kke.text = "Jabatan : ${detailKKe?.jabatan_yang_mengetahui}"

        //pasal
        callbackDetailPasalKke = object : AdapterCallback<LpPasalResp> {
            override fun initComponent(itemView: View, data: LpPasalResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.nama_pasal
            }

            override fun onItemClicked(itemView: View, data: LpPasalResp, itemIndex: Int) {
            }
        }
        detailKKe?.pasal_dilanggar?.let {
            adapterDetailPasalKke.adapterCallback(callbackDetailPasalKke)
                .isVerticalView().addData(it).setLayout(R.layout.item_pasal_lp)
                .build(rv_detail_pasal_kke)
        }

        //saksi
        callbackDetailSaksiKke = object : AdapterCallback<LpSaksiResp> {
            override fun initComponent(itemView: View, data: LpSaksiResp, itemIndex: Int) {
                itemView.txt_detail_1.text = data.nama
                when (data.is_korban) {
                    1 -> itemView.txt_detail_2.text = "Korban"
                    0 -> itemView.txt_detail_2.text = "Saksi"
                }

            }

            override fun onItemClicked(itemView: View, data: LpSaksiResp, itemIndex: Int) {
            }
        }
        detailKKe?.saksi_kode_etik?.let {
            adapterDetailSaksiKke.adapterCallback(callbackDetailSaksiKke)
                .isVerticalView().addData(it).setLayout(R.layout.item_2_text)
                .build(rv_detail_saksi_kke)
        }
    }

    companion object {
        const val DETAIL_KKE = "DETAIL_KKE"
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