package id.calocallo.sicape.ui.main.addpersonal.orangs

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.OrangsModel
import id.calocallo.sicape.model.ParentListOrangs
import id.calocallo.sicape.ui.main.addpersonal.tokoh.AddTokohDikagumiActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_org_disegani_adat.*
import kotlinx.android.synthetic.main.activity_add_org_selain_ortu.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddOrgDiseganiAdatActivity : BaseActivity() {
    private lateinit var list: ArrayList<OrangsModel>
    private lateinit var parentList: ParentListOrangs
    lateinit var adapter: OrangsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_org_disegani_adat)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Orang Yang Disegani Karena Adat"
        list = ArrayList()
        parentList = ParentListOrangs(list)
        initRecycler()

        btn_next_org_disegani_adat.setOnClickListener {
            startActivity(Intent(this, AddTokohDikagumiActivity::class.java))
        }
    }

    private fun initRecycler() {
        list.add(OrangsModel("", "", "", "", ""))
        list.add(OrangsModel("", "", "", "", ""))
        rv_org_disegani_adat.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = OrangsAdapter(
            "Orang Yang Disegani Karena Adat",
            this,
            list,
            object : OrangsAdapter.OnClickOrangs {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        rv_org_disegani_adat.adapter = adapter
        btn_add_org_disegani_adat.setOnClickListener {
            list.add(OrangsModel("", "", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}