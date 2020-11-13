package id.calocallo.sicape.ui.main.addpersonal.orangs

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AnakModel
import id.calocallo.sicape.model.OrangsModel
import id.calocallo.sicape.model.ParentListAnak
import id.calocallo.sicape.model.ParentListOrangs
import id.calocallo.sicape.ui.main.addpersonal.anak.AnakAdapter
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_org_selain_ortu.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddOrgSelainOrtuActivity : BaseActivity() {
    private lateinit var list: ArrayList<OrangsModel>
    private lateinit var parentList: ParentListOrangs
    lateinit var adapter: OrangsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_org_selain_ortu)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Orang Yang Membantu Selain Orang Tua"
        list = ArrayList()
        parentList = ParentListOrangs(list)
        initRecycler()

        btn_next_org_selain_ortu.setOnClickListener {
            startActivity(Intent(this, AddOrgDiseganiAdatActivity::class.java))
        }
    }

    private fun initRecycler() {
        list.add(OrangsModel("", "", "", "", ""))
        list.add(OrangsModel("", "", "", "", ""))
        rv_org_selain_ortu.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = OrangsAdapter("Orang Yang Membantu Selain Orang Tua",this, list, object : OrangsAdapter.OnClickOrangs {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rv_org_selain_ortu.adapter = adapter
        btn_add_org_selain_ortu.setOnClickListener {
            list.add(OrangsModel("", "", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}