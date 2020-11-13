package id.calocallo.sicape.ui.main.addpersonal.tokoh

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.OrangsModel
import id.calocallo.sicape.model.ParentListOrangs
import id.calocallo.sicape.model.ParentListTokoh
import id.calocallo.sicape.model.TokohModel
import id.calocallo.sicape.ui.main.addpersonal.kawan.AddKawanDekatActivity
import id.calocallo.sicape.ui.main.addpersonal.orangs.OrangsAdapter
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_tokoh_dikagumi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddTokohDikagumiActivity : BaseActivity() {
    private lateinit var list: ArrayList<TokohModel>
    private lateinit var parentList: ParentListTokoh
    lateinit var adapter: TokohAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tokoh_dikagumi)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tokoh Yang Dikagumi"
        list = ArrayList()
        parentList = ParentListTokoh(list)
        initRecycler()

        btn_next_tokoh_dikagumi.setOnClickListener {
            startActivity(Intent(this, AddKawanDekatActivity::class.java))
        }
    }

    private fun initRecycler() {
        list.add(TokohModel("", "", "", ""))
        list.add(TokohModel("", "", "", ""))
        rv_tokoh_dikagumi.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = TokohAdapter(this, list, object : TokohAdapter.OnClickTokoh {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rv_tokoh_dikagumi.adapter = adapter

        btn_add_tokoh_dikagumi.setOnClickListener {
            list.add(TokohModel("", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}