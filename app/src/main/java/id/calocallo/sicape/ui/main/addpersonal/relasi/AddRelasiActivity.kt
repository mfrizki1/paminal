package id.calocallo.sicape.ui.main.addpersonal.relasi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.OrganisasiReq
import id.calocallo.sicape.network.request.HukumanReq
import id.calocallo.sicape.network.request.RelasiReq
import id.calocallo.sicape.ui.main.addpersonal.AddCatPersActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_relasi.*
import kotlinx.android.synthetic.main.layout_hukuman.*
import kotlinx.android.synthetic.main.layout_relasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddRelasiActivity : BaseActivity() {
    private lateinit var listRelasi: ArrayList<RelasiReq>
    private lateinit var listHukum: ArrayList<HukumanReq>
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterRelasi: RelasiAdapter
    private lateinit var adapterHukum: PernahHukumAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_relasi)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Relasi"
        listRelasi = ArrayList()
        listHukum = ArrayList()

        initRV(rv_relasi, rv_pernah_dihukum)

        btn_next_relasi.setOnClickListener {
            if (listRelasi.size == 1 && listRelasi[0].nama == "") {
                listRelasi.clear()
            }

            if (listHukum.size == 1 && listHukum[0].perkara == "") {
                listHukum.clear()
            }

            sessionManager.setRelasi(listRelasi)
            sessionManager.setHukuman(listHukum)
            Log.e("size Relasi", sessionManager.getRelasi().size.toString())
//            Log.e("size Relasi", sessionManager.getRelasi()[0].lokasi.toString())
//            Log.e("size Relasi", sessionManager.getRelasi()[1].lokasi.toString())
            Log.e("size Hukum", sessionManager.getHukuman().size.toString())
//            Log.e("size Relasi", sessionManager.getHukuman()[0].perkara.toString())
            startActivity(Intent(this, AddCatPersActivity::class.java))
        }
    }

    private fun initRV(rvRelasi: RecyclerView, rvHukum: RecyclerView) {
        val getRelasi = sessionManager.getRelasi()
        if (getRelasi.size == 1) {
            for (i in 0 until getRelasi.size) {
                listRelasi.add(
                    i, RelasiReq(
                        getRelasi[i].nama,
                        getRelasi[i].lokasi
                    )
                )
            }
        } else {
            listRelasi.add(RelasiReq())
        }
        rvRelasi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterRelasi = RelasiAdapter(this, listRelasi, object : RelasiAdapter.OnClickRelasi {
            override fun onDelete(position: Int) {
                listRelasi.removeAt(position)
                adapterRelasi.notifyDataSetChanged()
            }

            override fun onAdd() {
                listRelasi.add(RelasiReq())
                val position = if (listRelasi.isEmpty()) 0 else listRelasi.size - 1
                adapterRelasi.notifyItemInserted(position)
                adapterRelasi.notifyDataSetChanged()
            }
        })
        rvRelasi.adapter = adapterRelasi

//        btn_add_relasi.setOnClickListener {
//            listRelasi.add(RelasiReq())
//            val position = if (listRelasi.isEmpty()) 0 else listRelasi.size - 1
//            adapterRelasi.notifyItemInserted(position)
//            adapterRelasi.notifyDataSetChanged()
//        }


        //Hukum
        val getHukum = sessionManager.getHukuman()
        if (getHukum.size == 1) {
            for (i in 0 until getHukum.size) {
                listHukum.add(
                    i, HukumanReq(
                        getHukum[i].perkara
                    )
                )
            }
        } else {
            listHukum.add(HukumanReq())
        }
        rvHukum.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterHukum = PernahHukumAdapter(this, listHukum, object :PernahHukumAdapter.OnClickHukum {
            override fun onDelete(position: Int) {
                listHukum.removeAt(position)
                adapterHukum.notifyDataSetChanged()
            }

            override fun onAdd() {
                listHukum.add(HukumanReq())
                val position = if (listHukum.isEmpty()) 0 else listHukum.size - 1
                adapterHukum.notifyItemInserted(position)
                adapterHukum.notifyDataSetChanged()
            }
        })
        rvHukum.adapter = adapterHukum
//        btn_add_pernah_dihukum.setOnClickListener {
//            listHukum.add(HukumanReq())
//            val position = if (listHukum.isEmpty()) 0 else listHukum.size - 1
//            adapterHukum.notifyItemInserted(position)
//            adapterHukum.notifyDataSetChanged()
//        }
    }
}