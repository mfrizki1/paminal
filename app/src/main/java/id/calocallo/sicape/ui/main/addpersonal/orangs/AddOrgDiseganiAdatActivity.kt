package id.calocallo.sicape.ui.main.addpersonal.orangs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.OrangsReq
import id.calocallo.sicape.model.ParentListOrangs
import id.calocallo.sicape.ui.main.addpersonal.tokoh.AddTokohDikagumiActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_org_disegani_adat.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddOrgDiseganiAdatActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var list: ArrayList<OrangsReq>
    private lateinit var parentList: ParentListOrangs
    lateinit var adapter: OrangsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_org_disegani_adat)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Orang Yang Disegani Karena Adat"
        list = ArrayList()
        parentList = ParentListOrangs(list)
        initRecycler()

        btn_next_org_disegani_adat.setOnClickListener {
            if (list.size == 1 && list[0].nama == "") {
                list.clear()
            }
            sessionManager.setOrangDisegani(list)
            Log.e("diseganiSize", sessionManager.getOrangDisegani().size.toString())
            startActivity(Intent(this, AddTokohDikagumiActivity::class.java))
        }
    }

    private fun initRecycler() {
        val disegani = sessionManager.getOrangDisegani()
        if (disegani.size == 1) {
            for (i in 0 until disegani.size) {
                list.add(
                    i, OrangsReq(
                        disegani[i].nama,
                        disegani[i].jenis_kelamin,
                        disegani[i].umur,
                        disegani[i].alamat,
                        disegani[i].pekerjaan,
                        disegani[i].keterangan
                    )
                )
            }
        } else {
            list.add(OrangsReq())
        }
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
            list.add(OrangsReq())
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}