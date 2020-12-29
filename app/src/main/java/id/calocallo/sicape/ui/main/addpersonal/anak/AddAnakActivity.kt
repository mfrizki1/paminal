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
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_anak.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAnakActivity : BaseActivity() {
    private var anakReq = AnakReq()
    private lateinit var sessionManager: SessionManager
    private lateinit var list: ArrayList<AnakReq>
    private lateinit var parentList: ParentListAnak
    lateinit var adapter: AnakAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_anak)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Anak"
        list = ArrayList()

        initRecycler(rv_anak)

        btn_next_anak.setOnClickListener {
            //work list[0].nama == ""
            if (list.size == 1 && list[0].nama == "") {
                list.clear()
            }

            sessionManager.setAnak(list)
            Log.e("anak", sessionManager.getAnak().size.toString())
//            Log.e("data 1 anak", list[0].nama.toString())
//            Log.e("data 1 jk anak", list[0].jenis_kelamin.toString())
//            Log.e("data 1 jk anak", list[0].status_ikatan.toString())
//            Log.e("data 2 jk anak", list[1].jenis_kelamin.toString())
//            Log.e("data 2 jk anak", list[1].status_ikatan.toString())
            startActivity(Intent(this, AddSaudaraActivity::class.java))
        }
    }

    private fun initRecycler(rvAnak: RecyclerView) {
        val getAnak = sessionManager.getAnak()
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
            list.add(
                AnakReq(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
        rvAnak.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = AnakAdapter(this, list, object : AnakAdapter.OnClickAnak {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rvAnak.adapter = adapter
        btn_add_anak.setOnClickListener {
            list.add(AnakReq())
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }

    }

//    override fun onResume() {
//        super.onResume()
//        list = ArrayList()
//        val anak = sessionManager.getAnak()
//        if(anak.size == 1){
//            for (i in 0 until anak.size) {
//                list.add(i, AnakReq(anak[i].nama,
//                    anak[i].jenis_kelamin,
//                    anak[i].tempat_lahir,
//                    anak[i].tanggal_lahir,
//                    anak[i].pekerjaan_atau_sekolah,
//                    anak[i].organisasi_yang_diikuti,
//                    anak[i].keterangan,
//                    anak[i].status_ikatan))
//            }
//        }
//        }else{
//            list.add(AnakReq("", "", "", "", "", "", "", ""))
//        }

}
