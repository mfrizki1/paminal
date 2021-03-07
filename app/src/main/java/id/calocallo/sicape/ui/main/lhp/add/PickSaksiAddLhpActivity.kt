package id.calocallo.sicape.ui.main.lhp.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.SaksiLhpReq
import id.calocallo.sicape.ui.main.lhp.edit.saksi.AddSingleSaksiLhpActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import id.calocallo.sicape.utils.LhpDataManager
import kotlinx.android.synthetic.main.activity_pick_saksi_add_lhp.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickSaksiAddLhpActivity : BaseActivity() {
    private lateinit var lhpDataManager: LhpDataManager
    private var saksiLhpReq = ArrayList<SaksiLhpReq>()

    private var adapterSaksiResp = ReusableAdapter<SaksiLhpReq>(this)
    private lateinit var callbackSaksiResp: AdapterCallback<SaksiLhpReq>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_saksi_add_lhp)
        lhpDataManager = LhpDataManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"

        //set button next
        btn_next_saksi_lhp.setOnClickListener {
            lhpDataManager.setListSaksiLHP(saksiLhpReq)
            val intent = Intent(this, OtherAddLhpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        //set add Single Saksi
        btn_add_saksi_lhp.setOnClickListener {
            val intent = Intent(this, AddSingleSaksiLhpActivity::class.java)
            intent.putExtra(ADD_SAKSI, ADD_SAKSI)
            startActivityForResult(intent, GET_SAKSI)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        //set List

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val saksiREq =
            data?.getParcelableExtra<SaksiLhpReq>(AddSingleSaksiLhpActivity.SET_DATA_SAKSI)
        if (resultCode == Activity.RESULT_OK && requestCode == GET_SAKSI) {
            saksiREq?.let { saksiLhpReq.add(it) }
            getListSaksi(saksiLhpReq)
        }
    }

    private fun getListSaksi(saksiReq: ArrayList<SaksiLhpReq>) {
        callbackSaksiResp = object : AdapterCallback<SaksiLhpReq> {
            override fun initComponent(itemView: View, data: SaksiLhpReq, itemIndex: Int) {
                itemView.txt_detail_1.text = data.nama
                if (data.status_saksi == "personel" && data.is_korban == 1) {
                    itemView.txt_detail_2.text = "Polisi \nKorban"
                } else {
                    itemView.txt_detail_2.text = "Sipil \nBukan Korban"
                }
            }

            override fun onItemClicked(itemView: View, data: SaksiLhpReq, itemIndex: Int) {
            }

        }
        adapterSaksiResp.adapterCallback(callbackSaksiResp)
            .isVerticalView()
            .addData(saksiReq)
            .setLayout(R.layout.item_2_text)
            .build(rv_list_add_saksi_lhp)
    }

    companion object {
        const val ADD_SAKSI = "ADD_SAKSI"
        const val GET_SAKSI = 123

    }
}