package id.calocallo.sicape.ui.main.addpersonal.kawan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.SahabatReq
import id.calocallo.sicape.model.ParentListKawanDekat
import id.calocallo.sicape.ui.main.addpersonal.mediainfo.AddMedianfoActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_kawan_dekat.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddKawanDekatActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var list: ArrayList<SahabatReq>
    private lateinit var parentList: ParentListKawanDekat
    private lateinit var adapter: KawanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_kawan_dekat)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Kawan Dekat"

        list = ArrayList()
        parentList = ParentListKawanDekat(list)
        initRV()

        btn_next_kawan.setOnClickListener {
            if (list.size == 1 && list[0].nama == "") {
                list.clear()
            }
            sessionManager.setSahabat(list)
            Log.e("size Sahabat", sessionManager.getSahabat().size.toString())
            startActivity(Intent(this, AddMedianfoActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initRV() {
        val sahabat = sessionManager.getSahabat()
        if(sahabat.size == 1){
            for(i in 0 until sahabat.size){
                list.add(i, SahabatReq(
                    sahabat[i].nama,
                    sahabat[i].jenis_kelamin,
                    sahabat[i].umur,
                    sahabat[i].alamat,
                    sahabat[i].pekerjaan,
                    sahabat[i].alasan,
                    sahabat[i].keterangan
                )
                )
            }
        }else{
            list.add(
                SahabatReq(
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
        rv_kawan_dekat.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = KawanAdapter(this, list, object : KawanAdapter.OnClickKawan {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rv_kawan_dekat.adapter = adapter

        btn_add_kawan.setOnClickListener {
            list.add(
                SahabatReq(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}