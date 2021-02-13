package id.calocallo.sicape.ui.main.lhp.edit.saksi

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.response.SaksiLhpResp
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity.Companion.EDIT_LHP
import id.calocallo.sicape.ui.main.lhp.edit.saksi.AddSingleSaksiLhpActivity.Companion.ADD_SAKSI_LHP
import id.calocallo.sicape.ui.main.lhp.edit.saksi.EditSaksiLhpActivity.Companion.EDIT_SAKSI_LHP
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_edit_saksi_lhp.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickEditSaksiLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterSaksiLhp: ReusableAdapter<SaksiLhpResp>
    private lateinit var callbackSaksiLhp: AdapterCallback<SaksiLhpResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_edit_saksi_lhp)
        sessionManager1 = SessionManager1(this)
        adapterSaksiLhp = ReusableAdapter(this)

        val detailLhp = intent.extras?.getParcelable<LhpResp>(EditLhpActivity.EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Saksi"

        btn_add_single_saksi_lhp.setOnClickListener {
            val intent = Intent(this, AddSingleSaksiLhpActivity::class.java)
            intent.putExtra(ADD_SAKSI_LHP, detailLhp)
            startActivity(intent)

        }
        val dataLHP = intent.extras?.getParcelable<LhpResp>(EDIT_LHP)
        getListSaksiLhp(dataLHP)
    }

    private fun getListSaksiLhp(dataLHP: LhpResp?) {

        callbackSaksiLhp = object : AdapterCallback<SaksiLhpResp> {
            override fun initComponent(itemView: View, data: SaksiLhpResp, itemIndex: Int) {
                itemView.txt_detail_1.text = data.nama
                when (data.status_saksi) {
                    "polisi" -> itemView.txt_detail_2.text = "Polisi"
                    "sipil" -> itemView.txt_detail_2.text = "Sipil"

                }
            }

            override fun onItemClicked(itemView: View, data: SaksiLhpResp, itemIndex: Int) {
                val intent = Intent(this@PickEditSaksiLhpActivity, EditSaksiLhpActivity::class.java)
                intent.putExtra(EDIT_SAKSI_LHP, data)
                startActivity(intent)
            }
        }
        dataLHP?.saksi?.let {
            adapterSaksiLhp.adapterCallback(callbackSaksiLhp)
                .isVerticalView().addData(it).setLayout(R.layout.item_2_text)
                .build(rv_list_saksi_lhp)
        }
    }

    override fun onResume() {
        super.onResume()
//        getListSaksiLhp()
    }
}