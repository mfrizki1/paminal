package id.calocallo.sicape.ui.main.addpersonal.alamat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AlamatModel
import id.calocallo.sicape.model.ParentListAlamat
import id.calocallo.sicape.ui.main.addpersonal.misc.MiscenaousActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_alamat.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAlamatActivity : BaseActivity() {
    private lateinit var adapter: AddAlamatAdapter
    private lateinit var list: ArrayList<AlamatModel>
    private lateinit var parentList: ParentListAlamat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alamat)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Alamat"
        list = ArrayList()
        parentList = ParentListAlamat(list)

        initRecycler(rv_alamat)
        btn_next_alamat.setOnClickListener {

            //initAPI(list)
            Log.e("namalamat", parentList.parenListAlamat[0].nama_alamat.toString())
            parentList.parenListAlamat[0].rangka?.let { it1 -> Log.e("rangkaalamat", it1) }
            startActivity(Intent(this, MiscenaousActivity::class.java))
        }
    }

    private fun initRecycler(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list.add(AlamatModel("", "", "", "", ""))
        list.add(AlamatModel("", "", "", "", ""))
        adapter = AddAlamatAdapter(this, list, object : AddAlamatAdapter.OnClickAlamat {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })

        rv.adapter = adapter
        btn_add_alamat.setOnClickListener {
            list.add(AlamatModel("", "", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}