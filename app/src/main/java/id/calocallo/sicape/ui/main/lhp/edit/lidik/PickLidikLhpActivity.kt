package id.calocallo.sicape.ui.main.lhp.edit.lidik

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpModel
import id.calocallo.sicape.model.ListLidik
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.lidik.AddSingleLidikLhpActivity.Companion.ADD_LIDIK
import id.calocallo.sicape.ui.main.lhp.edit.lidik.EditLidikLhpActivity.Companion.EDIT_LIDIK
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_lidik_lhp.*
import kotlinx.android.synthetic.main.item_lidik.view.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickLidikLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterLidik: ReusableAdapter<ListLidik>
    private lateinit var callbackLidik: AdapterCallback<ListLidik>
    private lateinit var detailLhp: LhpModel
    private var listLidik = arrayListOf<ListLidik>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_lidik_lhp)
        sessionManager = SessionManager(this)
        adapterLidik = ReusableAdapter(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Personel Penyelidik"
        detailLhp = intent.extras?.getParcelable<LhpModel>(EditLhpActivity.EDIT_LHP)!!
//        rv_list_lidik

        btn_add_single_lidik.setOnClickListener {
            val intent = Intent(this, AddSingleLidikLhpActivity::class.java)
            intent.putExtra(ADD_LIDIK, detailLhp)
            startActivity(intent)
        }
        getListLidik(detailLhp)
        listLidik.add(ListLidik("Utuh", "Briptu", "987655123", "ketua_tim"))
        listLidik.add(ListLidik("Galuh", "Bripda", "1345678", "anggota"))
        listLidik.add(ListLidik("Julak", "Jenderal", "2121124", "anggota"))
        Log.e("list", "$listLidik")
    }

    private fun getListLidik(detailLhp: LhpModel?) {

        callbackLidik = object : AdapterCallback<ListLidik> {
            override fun initComponent(itemView: View, data: ListLidik, itemIndex: Int) {
                when (data.status_penyelidik) {
                    "ketua_tim" -> itemView.ttx_status_lidik.text = "Ketua Tim"
                    "anggota" -> itemView.ttx_status_lidik.text = "Anggota"
                }
                itemView.txt_nama_lidik.text = data.nama_lidik
                itemView.txt_pangkat_nrp_lidik.text =
                    "Pangkat ${data.pangkat_lidik}, NRP: ${data.nrp_lidik}"

            }

            override fun onItemClicked(itemView: View, data: ListLidik, itemIndex: Int) {
                    val intent = Intent(this@PickLidikLhpActivity, EditLidikLhpActivity::class.java)
                    intent.putExtra(EDIT_LIDIK, data)
                    startActivity(intent)
            }
        }
        adapterLidik.adapterCallback(callbackLidik)
            .isVerticalView()
            .addData(listLidik)
            .setLayout(R.layout.item_lidik)
            .build(rv_list_lidik)

    }
}