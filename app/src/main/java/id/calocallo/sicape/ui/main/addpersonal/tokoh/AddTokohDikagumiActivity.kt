package id.calocallo.sicape.ui.main.addpersonal.tokoh

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListTokoh
import id.calocallo.sicape.network.request.TokohReq
import id.calocallo.sicape.ui.main.addpersonal.kawan.AddKawanDekatActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_tokoh_dikagumi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddTokohDikagumiActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var list: ArrayList<TokohReq>
    private lateinit var parentList: ParentListTokoh
    lateinit var adapter: TokohAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tokoh_dikagumi)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tokoh Yang Dikagumi"
        list = ArrayList()
        parentList = ParentListTokoh(list)
        initRecycler()

        btn_next_tokoh_dikagumi.setOnClickListener {
            if (list.size == 1 && list[0].nama == "") {
                list.clear()
            }

            sessionManager.setTokoh(list)
            Log.e("size Tokoh", sessionManager.getTokoh().size.toString())
            startActivity(Intent(this, AddKawanDekatActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initRecycler() {
        val tokoh = sessionManager.getTokoh()
        if (tokoh.size == 1) {
            for (i in 0 until tokoh.size) {
                list.add(
                    i, TokohReq(
                        tokoh[i].nama,
                        tokoh[i].asal_negara,
                        tokoh[i].alasan,
                        tokoh[i].keterangan
                    )
                )
            }
        } else {
            list.add(TokohReq())
        }
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
            list.add(TokohReq())
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}