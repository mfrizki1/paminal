package id.calocallo.sicape.ui.main.addpersonal.anak

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.AnakReq
import id.calocallo.sicape.model.ParentListAnak
import id.calocallo.sicape.ui.main.addpersonal.saudara.AddSaudaraActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_anak.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAnakActivity : BaseActivity() {
    private var anakReq = AnakReq()
    private lateinit var sessionManager1: SessionManager1
    private lateinit var list: ArrayList<AnakReq>
    private lateinit var parentList: ParentListAnak
    lateinit var adapter: AnakAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_anak)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Anak"
        list = ArrayList()

        initRecycler(rv_anak)

        btn_next_anak.setOnClickListener {
            if (list.size == 1 && list[0].nama == "") {
                list.clear()
            }

            sessionManager1.setAnak(list)
            Log.e("anak", "${sessionManager1.getAnak()}")
            startActivity(Intent(this, AddSaudaraActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initRecycler(rvAnak: RecyclerView) {
        val getAnak = sessionManager1.getAnak()
        if (getAnak.size == 1) {
            for (i in 0 until getAnak.size) {
                list.add(
                    i, AnakReq(
                        getAnak[i].status_ikatan,
                        getAnak[i].nama,
                        getAnak[i].jenis_kelamin,
                        getAnak[i].tempat_lahir,
                        getAnak[i].tanggal_lahir,
                        getAnak[i].pekerjaan_atau_sekolah,
                        getAnak[i].organisasi_yang_diikuti,
                        getAnak[i].keterangan
                    )
                )
            }
        } else {
            list.add(AnakReq())
        }
        rvAnak.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = AnakAdapter(this, list, object : AnakAdapter.OnClickAnak {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        })
        rvAnak.adapter = adapter
        btn_add_anak.setOnClickListener {
            list.add(AnakReq())
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemChanged(position)
            adapter.notifyItemInserted(position)
        }

    }
}
