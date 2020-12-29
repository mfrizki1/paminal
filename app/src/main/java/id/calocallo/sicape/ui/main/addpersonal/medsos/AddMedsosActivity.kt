package id.calocallo.sicape.ui.main.addpersonal.medsos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.MedSosReq
import id.calocallo.sicape.model.ParentListMedsos
import id.calocallo.sicape.ui.main.addpersonal.AddFotoActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_medsos.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddMedsosActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var list: ArrayList<MedSosReq>
    private lateinit var parentList: ParentListMedsos
    lateinit var adapter: MedSosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medsos)
        sessionManager = SessionManager(this)
        list = ArrayList()
        parentList = ParentListMedsos(list)

        intiRV()

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Media Sosial"
        btn_next_medsos.setOnClickListener {
            if (list.size == 1 && list[0].nama_medsos == "") {
                list.clear()
            }

            sessionManager.setMedsos(list)
            Log.e("size Medsos", sessionManager.getMedsos().size.toString())
            startActivity(Intent(this, AddFotoActivity::class.java))
        }
    }

    private fun intiRV() {
        val medsos = sessionManager.getMedsos()
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
            list.add(
                MedSosReq(
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
        rv_medsos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MedSosAdapter(this, list, object : MedSosAdapter.OnClickMedsos {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })
        rv_medsos.adapter = adapter

        btn_add_medsos.setOnClickListener {
            list.add(
                MedSosReq(
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