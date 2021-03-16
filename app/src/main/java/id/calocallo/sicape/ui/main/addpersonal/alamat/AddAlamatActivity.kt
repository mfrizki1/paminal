package id.calocallo.sicape.ui.main.addpersonal.alamat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.AlamatReq
import id.calocallo.sicape.model.ParentListAlamat
import id.calocallo.sicape.ui.main.addpersonal.misc.MiscenaousActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_alamat.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAlamatActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapter: AddAlamatAdapter
    private lateinit var list: ArrayList<AlamatReq>
    private lateinit var parentList: ParentListAlamat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alamat)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Alamat"
        list = ArrayList()

        initRecycler(rv_alamat)
        btn_next_alamat.setOnClickListener {
            if (list.size == 0) {
                list.clear()
            }

            sessionManager1.setAlamat(list)
            startActivity(Intent(this, MiscenaousActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initRecycler(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val alamatCreated = sessionManager1.getAlamat()

        if (alamatCreated.size == 0) {
            list.add(
                AlamatReq()
            )
        }
        adapter = AddAlamatAdapter(this, list, object : AddAlamatAdapter.OnClickAlamat {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        })

        rv.adapter = adapter
        btn_add_alamat.setOnClickListener {
            list.add(AlamatReq())
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemChanged(position)
            adapter.notifyItemInserted(position)

        }
    }

    override fun onResume() {
        super.onResume()
        val alamat = sessionManager1.getAlamat()
        for (i in 0 until alamat.size) {

            list.add(
                i, AlamatReq(
                    alamat[i].alamat,
                    alamat[i].tahun_awal,
                    alamat[i].tahun_akhir,
                    alamat[i].dalam_rangka,
                    alamat[i].keterangan
                )
            )
        }
    }
}
