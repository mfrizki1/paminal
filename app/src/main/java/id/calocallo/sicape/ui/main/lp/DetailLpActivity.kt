package id.calocallo.sicape.ui.main.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpResp
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalLpEditActivity
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpEditActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lp.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import java.util.ArrayList

class DetailLpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterDetailPasal: ReusableAdapter<LpPasalResp>
    private lateinit var callbackDetailPasal: AdapterCallback<LpPasalResp>
    private lateinit var adapterDetailSaksi: ReusableAdapter<LpSaksiResp>
    private lateinit var callbackDetailSaksi: AdapterCallback<LpSaksiResp>

    companion object {
        const val DETAIL_LP = "DETAIL_LP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lp)
        sessionManager1 = SessionManager1(this)
        adapterDetailPasal = ReusableAdapter(this)
        adapterDetailSaksi = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
//        val detailLP = intent.extras?.getParcelable<LpResp>(DETAIL_LP)
        val detailLP = intent.extras?.getParcelable<LpResp>(DETAIL_LP)
        when (detailLP?.jenis) {
            "pidana" -> supportActionBar?.title = "Detail Laporan Polisi Pidana"
            "disiplin" -> supportActionBar?.title = "Detail Laporan Polisi Disiplin"
            "kode_etik" -> supportActionBar?.title = "Detail Laporan Polisi Kode Etik"
        }
        Log.e("detailLP", "$detailLP")

        txt_no_lp_detail.text = detailLP?.no_lp

        txt_nama_lp_dilapor_detail.text = detailLP?.id_personel_dilapor.toString()
        txt_pangkat_nrp_lp_dilapor_detail
        txt_jabatan_lp_dilapor_detail
        txt_kesatuan_lp_dilapor_detail

        txt_nama_lp_terlapor_detail.text = detailLP?.id_personel_terlapor.toString()
        txt_pangkat_nrp_lp_terlapor_detail
        txt_jabatan_lp_terlapor_detail
        txt_kesatuan_lp_terlapor_detail

        txt_alat_bukti_lp_detail.text = detailLP?.alatBukti

        txt_ket_lp_detail.text = detailLP?.keterangan
        getPasalDetail(detailLP?.listPasal)
        getSaksiDetail(detailLP?.listSaksi)
        //edit
        val hakAkses = sessionManager1.fetchHakAkses()
        if (hakAkses == "operator") {
            btn_edit_lp.toggleVisibility()
//            btn_edit_personel.toggleVisibility()
        }
        btn_edit_lp.setOnClickListener {
            val intent = Intent(this, EditLpActivity::class.java)
            intent.putExtra(EditLpActivity.EDIT_LP, detailLP)
            startActivity(intent)
        }

        btn_edit_pasal_lp.setOnClickListener {
            val intent = Intent(this, PickPasalLpEditActivity::class.java)
            intent.putExtra(EditLpActivity.EDIT_LP, detailLP)
            startActivity(intent)
        }

        btn_edit_saksi_lp.setOnClickListener {
            val intent = Intent(this, PickSaksiLpEditActivity::class.java)
            intent.putExtra(EditLpActivity.EDIT_LP, detailLP)
            startActivity(intent)
        }

    }



    private fun getSaksiDetail(listSaksi: ArrayList<LpSaksiResp>?) {
        callbackDetailSaksi = object : AdapterCallback<LpSaksiResp> {
            override fun initComponent(itemView: View, data: LpSaksiResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.nama

            }

            override fun onItemClicked(itemView: View, data: LpSaksiResp, itemIndex: Int) {
            }
        }
        listSaksi?.let {
            adapterDetailSaksi.adapterCallback(callbackDetailSaksi)
                .isVerticalView().addData(it).setLayout(R.layout.item_pasal_lp)
                .build(rv_saksi_detail_lp)
        }
    }

    private fun getPasalDetail(listPasal: ArrayList<LpPasalResp>?) {
        callbackDetailPasal = object : AdapterCallback<LpPasalResp> {
            override fun initComponent(itemView: View, data: LpPasalResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.nama_pasal
            }

            override fun onItemClicked(itemView: View, data: LpPasalResp, itemIndex: Int) {
            }
        }
        listPasal?.let {
            adapterDetailPasal.adapterCallback(callbackDetailPasal)
                .isVerticalView().addData(it).setLayout(R.layout.item_pasal_lp)
                .build(rv_pasal_detail_lp)
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