package id.calocallo.sicape.ui.main.addpersonal.saudara

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AnakModel
import id.calocallo.sicape.model.ParentListAnak
import id.calocallo.sicape.model.ParentListSaudara
import id.calocallo.sicape.model.SaudaraModel
import id.calocallo.sicape.ui.main.addpersonal.anak.AnakAdapter
import id.calocallo.sicape.ui.main.addpersonal.orangs.AddOrgSelainOrtuActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_anak.*
import kotlinx.android.synthetic.main.activity_add_saudara.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSaudaraActivity : BaseActivity() {
    private lateinit var list: ArrayList<SaudaraModel>
    private lateinit var parentList: ParentListSaudara
    lateinit var adapter: SaudaraAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_saudara)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Saudara"
        list = ArrayList()
        parentList = ParentListSaudara(list)
        initRecycler()

        btn_next_saudara.setOnClickListener {
//            Log.e("dasasf", parentList.parentList[0].nama.toString()))
            Log.e("data 1 saudara", parentList.parentList[0].nama.toString())
            Log.e("data 1 jk saudara", parentList.parentList[0].jk.toString())
            Log.e("data 2 jk saudara", parentList.parentList[1].jk.toString())
            startActivity(Intent(this, AddOrgSelainOrtuActivity::class.java))
        }
    }

    private fun initRecycler() {
        list.add(SaudaraModel("", "", "", "", "", "", "", ""))
        list.add(SaudaraModel("", "", "", "", "", "", "", ""))
        rv_saudara.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = SaudaraAdapter(this, list, object : SaudaraAdapter.OnClickSaudara {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rv_saudara.adapter = adapter
        btn_add_saudara.setOnClickListener {
            list.add(SaudaraModel("", "", "", "", "", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}