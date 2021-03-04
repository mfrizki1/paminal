package id.calocallo.sicape.ui.main.lhp.edit.terlapor

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.response.KetTerlaporLhpResp
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.terlapor.EditTerlaporLhpActivity.Companion.EDIT_TERLAPOR
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_terlapor_lhp.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickTerlaporLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterTerlapor: ReusableAdapter<KetTerlaporLhpResp>
    private lateinit var callbackTerlapor: AdapterCallback<KetTerlaporLhpResp>

    //    private var listTerlapor = arrayListOf<ListTerlapor>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_terlapor_lhp)
        sessionManager1 = SessionManager1(this)
        adapterTerlapor = ReusableAdapter(this)
        val detailLhp = intent.extras?.getParcelable<LhpResp>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Terlapor"

        getListTerlapor(detailLhp)
        btn_add_single_terlapor.setOnClickListener {
            val intent = Intent(this, AddTerlaporLhpActivity::class.java)
//            intent.putExtra(ADD_TERLAPOR, ADD_TERLAPOR)
            startActivity(intent)
        }
    }

    private fun getListTerlapor(detailLhp: LhpResp?) {
        callbackTerlapor = object : AdapterCallback<KetTerlaporLhpResp> {
            override fun initComponent(itemView: View, data: KetTerlaporLhpResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama
            }

            override fun onItemClicked(itemView: View, data: KetTerlaporLhpResp, itemIndex: Int) {
                val intent =
                    Intent(this@PickTerlaporLhpActivity, EditTerlaporLhpActivity::class.java)
                intent.putExtra(EDIT_TERLAPOR, data)
                startActivity(intent)
            }
        }
//        detailLhp?.keterangan_terlapor?.let {
//            adapterTerlapor.adapterCallback(callbackTerlapor)
//                .isVerticalView().addData(it).setLayout(R.layout.layout_edit_1_text)
//                .build(rv_list_terlapor)
//        }
    }
}