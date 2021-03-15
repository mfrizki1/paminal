package id.calocallo.sicape.ui.main.addpersonal.medsos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.MedSosReq
import id.calocallo.sicape.model.ParentListMedsos
import id.calocallo.sicape.ui.main.addpersonal.AddFotoActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_medsos.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMedsosActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var list: ArrayList<MedSosReq>
    private lateinit var parentList: ParentListMedsos
    lateinit var adapter: MedSosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medsos)
        sessionManager1 = SessionManager1(this)
        list = ArrayList()
        parentList = ParentListMedsos(list)

        intiRV()

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Media Sosial"
        btn_next_medsos.setOnClickListener {
            if (list.size == 1 && list[0].nama_medsos == "") {
                list.clear()
            }

            sessionManager1.setMedsos(list)
            Log.e("size Medsos", "${sessionManager1.getMedsos()}")
            startActivity(Intent(this, AddFotoActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun intiRV() {
        val medsos = sessionManager1.getMedsos()
        if (medsos.size == 1) {
            for (i in 0 until medsos.size) {
                list.add(
                    i, MedSosReq(
                        medsos[i].nama_medsos,
                        medsos[i].nama_akun,
                        medsos[i].alasan,
                        medsos[i].keterangan
                    )
                )
            }
        } else {
            list.add(MedSosReq())
        }
        rv_medsos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MedSosAdapter(this, list, object : MedSosAdapter.OnClickMedsos {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        })
        rv_medsos.adapter = adapter

        btn_add_medsos.setOnClickListener {
            list.add(MedSosReq())
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemChanged(position)
            adapter.notifyItemInserted(position)

        }
    }
}