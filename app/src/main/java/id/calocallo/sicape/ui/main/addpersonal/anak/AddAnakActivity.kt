package id.calocallo.sicape.ui.main.addpersonal.anak

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AnakModel
import id.calocallo.sicape.model.ParentListAnak
import id.calocallo.sicape.ui.main.addpersonal.saudara.AddSaudaraActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_anak.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAnakActivity : BaseActivity() {
    private lateinit var list: ArrayList<AnakModel>
    private lateinit var parentList: ParentListAnak
    lateinit var adapter: AnakAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_anak)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Anak"
        list = ArrayList()
        parentList = ParentListAnak(list)

        initRecycler(rv_anak)

        btn_next_anak.setOnClickListener {
//            Log.e("data 1 anak", parentList.parentList[0].nama.toString())
//            Log.e("data 1 jk anak", parentList.parentList[0].jk.toString())
//            Log.e("data 2 jk anak", parentList.parentList[1].jk.toString())
            startActivity(Intent(this, AddSaudaraActivity::class.java))
        }
    }

    private fun initRecycler(rvAnak: RecyclerView) {
        list.add(AnakModel("", "", "", "", "", "", "", ""))
        list.add(AnakModel("", "", "", "", "", "", "", ""))
        rvAnak.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = AnakAdapter(this, list, object : AnakAdapter.OnClickAnak {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rvAnak.adapter = adapter
        btn_add_anak.setOnClickListener {
            list.add(AnakModel("", "", "", "", "", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }

    }
}