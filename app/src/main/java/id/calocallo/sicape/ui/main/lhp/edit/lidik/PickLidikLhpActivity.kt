package id.calocallo.sicape.ui.main.lhp.edit.lidik

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.model.ListLidik
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.add.AddPersonelLidikActivity
import id.calocallo.sicape.ui.main.lhp.edit.lidik.AddSingleLidikLhpActivity.Companion.ADD_LIDIK
import id.calocallo.sicape.ui.main.lhp.edit.lidik.EditLidikLhpActivity.Companion.EDIT_LIDIK
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_lidik_lhp.*
import kotlinx.android.synthetic.main.item_lidik.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickLidikLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterLidik: ReusableAdapter<PersonelPenyelidikResp>
    private lateinit var callbackLidik: AdapterCallback<PersonelPenyelidikResp>
    private lateinit var detailLhp: LhpResp
    private var listLidik = arrayListOf<ListLidik>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_lidik_lhp)
        sessionManager = SessionManager(this)
        adapterLidik = ReusableAdapter(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Personel Penyelidik"
        detailLhp = intent.extras?.getParcelable<LhpResp>(EditLhpActivity.EDIT_LHP)!!
//        rv_list_lidik

        btn_add_single_lidik.setOnClickListener {
            val intent = Intent(this, AddPersonelLidikActivity::class.java)
            intent.putExtra(ADD_LIDIK, ADD_LIDIK)
            startActivity(intent)
        }
        getListLidik(detailLhp)
    }

    override fun onResume() {
        super.onResume()
        getListLidik(detailLhp)
    }

    private fun getListLidik(detailLhp: LhpResp?) {

        callbackLidik = object : AdapterCallback<PersonelPenyelidikResp> {
            override fun initComponent(itemView: View, data: PersonelPenyelidikResp, itemIndex: Int) {
                when (data.is_ketua) {
                    1 -> itemView.ttx_status_lidik.text = "Ketua Tim"
                    0 -> itemView.ttx_status_lidik.text = "Anggota"
                }
                itemView.txt_nama_lidik.text = data.nama
                itemView.txt_pangkat_nrp_lidik.text =
                    "Pangkat ${data.pangkat}, NRP: ${data.nrp}"

            }

            override fun onItemClicked(itemView: View, data: PersonelPenyelidikResp, itemIndex: Int) {
                    val intent = Intent(this@PickLidikLhpActivity, EditLidikLhpActivity::class.java)
                    intent.putExtra(EDIT_LIDIK, data)
                    startActivity(intent)
            }
        }
        detailLhp?.personel_penyelidik?.let {
            adapterLidik.adapterCallback(callbackLidik)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.item_lidik)
                .build(rv_list_lidik)
        }

    }
}