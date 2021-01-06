package id.calocallo.sicape.ui.main.lhp.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.request.KetTerlaporReq
import id.calocallo.sicape.ui.main.lhp.edit.terlapor.AddTerlaporLhpActivity
import id.calocallo.sicape.utils.LhpDataManager
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_ket_terlapor_lhp.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ListKetTerlaporLhpActivity : BaseActivity() {
    private lateinit var lhpDataManager: LhpDataManager
    private lateinit var sessionManager: SessionManager
    private var listKetTerlaporReq = ArrayList<KetTerlaporReq>()
    private var lhpRespAll = LhpResp()

    private var adapterKetTerlapor = ReusableAdapter<KetTerlaporReq>(this)
    private lateinit var callbackKetTerlapor: AdapterCallback<KetTerlaporReq>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_ket_terlapor_lhp)
        lhpDataManager = LhpDataManager(this)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"

        /*set button for save all lhp from awal*/
        btn_next_terlapor.setOnClickListener {
            lhpDataManager.setListTerlaporLHP(listKetTerlaporReq)
//            Log.e("After add", "$listKetTerlaporReq")
            val intent = Intent(this, OtherAddLhpActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        /*set button for add single data keterangan terlapor*/
        btn_add_ket_terlapor_lhp.setOnClickListener {
            val intent = Intent(this, AddTerlaporLhpActivity::class.java)
            intent.putExtra(LIST_KET_TERLAPOR, LIST_KET_TERLAPOR)
            startActivityForResult(intent, PERSONEL_TERLAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        /*getList*/
        getListDataKetTerlapor(listKetTerlaporReq)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val terlapor = data?.getParcelableExtra<KetTerlaporReq>(GET_TERLAPOR)
        if (resultCode == Activity.RESULT_OK && requestCode == PERSONEL_TERLAPOR) {
            terlapor?.let { listKetTerlaporReq.add(it) }
            getListDataKetTerlapor(listKetTerlaporReq)

        }
    }

    private fun getListDataKetTerlapor(list: ArrayList<KetTerlaporReq>) {
        callbackKetTerlapor = object : AdapterCallback<KetTerlaporReq> {
            override fun initComponent(itemView: View, data: KetTerlaporReq, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama_pesonel
            }

            override fun onItemClicked(itemView: View, data: KetTerlaporReq, itemIndex: Int) {
            }
        }
        adapterKetTerlapor.adapterCallback(callbackKetTerlapor)
            .isVerticalView()
            .addData(list)
            .build(rv_list_ket_terlapor_lhp)
            .setLayout(R.layout.layout_edit_1_text)
    }

    companion object {
        const val LIST_KET_TERLAPOR = "KET_TERLAPOR"
        const val PERSONEL_TERLAPOR = 122
        const val GET_TERLAPOR = "GET_TERLAPOR"
    }
}