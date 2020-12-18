package id.calocallo.sicape.ui.main.addpersonal.saudara

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListSaudara
import id.calocallo.sicape.model.SaudaraReq
import id.calocallo.sicape.ui.main.addpersonal.orangs.AddOrgSelainOrtuActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_saudara.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSaudaraActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var list: ArrayList<SaudaraReq>
    private lateinit var parentList: ParentListSaudara
    lateinit var adapter: SaudaraAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_saudara)
        sessionManager = SessionManager(this)
        list = ArrayList()

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Saudara"
//        parentList = ParentListSaudara(list)
        initRecycler()

        btn_next_saudara.setOnClickListener {
            if (list.size == 1 && list[0].nama == "") {
                list.clear()
            }

            sessionManager.setSaudara(list)


            Log.e("saudara", sessionManager.getSaudara().size.toString())
//            Log.e("dasasf", parentList.parentList[0].nama.toString()))
//            Log.e("data 1 saudara", parentList.parentList[0].nama.toString())
//            Log.e("data 1 jk saudara", parentList.parentList[0].jenis_kelamin.toString())
//            Log.e("data 2 jk saudara", parentList.parentList[1].jenis_kelamin.toString())
            startActivity(Intent(this, AddOrgSelainOrtuActivity::class.java))
        }
    }

    private fun initRecycler() {
        val getSaudara = sessionManager.getSaudara()
        if (getSaudara.size == 1) {
            for (i in 0 until getSaudara.size) {
                list.add(
                    i,
                    SaudaraReq(
                        getSaudara[i].status_ikatan,
                        getSaudara[i].nama,
                        getSaudara[i].jenis_kelamin,
                        getSaudara[i].tempat_lahir,
                        getSaudara[i].tanggal_lahir,
                        getSaudara[i].pekerjaan_atau_sekolah,
                        getSaudara[i].organisasi_yang_diikuti,
                        getSaudara[i].keterangan
                    )
                )
            }
        } else {
            list.add(SaudaraReq("", "", "", "", "", "", "", ""))
        }
        rv_saudara.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = SaudaraAdapter(this, list,
            object : SaudaraAdapter.OnClickSaudara {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        rv_saudara.adapter = adapter
        btn_add_saudara.setOnClickListener {
            list.add(SaudaraReq("", "", "", "", "", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}