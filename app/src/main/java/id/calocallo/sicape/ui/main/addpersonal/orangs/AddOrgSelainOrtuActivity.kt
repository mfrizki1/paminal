package id.calocallo.sicape.ui.main.addpersonal.orangs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.OrangsReq
import id.calocallo.sicape.model.ParentListOrangs
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_org_selain_ortu.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddOrgSelainOrtuActivity : BaseActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var list: ArrayList<OrangsReq>
    private lateinit var parentList: ParentListOrangs
    lateinit var adapter: OrangsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_org_selain_ortu)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Orang Yang Membantu Selain Orang Tua"
        list = ArrayList()
        parentList = ParentListOrangs(list)
        initRecycler()

        btn_next_org_selain_ortu.setOnClickListener {
            if (list.size == 1 && list[0].nama == "") {
                list.clear()
            }
            sessionManager.setOrangBerjasa(list)
            Log.e("JasaSize", sessionManager.getOrangBerjasa().size.toString())
            startActivity(Intent(this, AddOrgDiseganiAdatActivity::class.java))
        }
    }

    private fun initRecycler() {
        val orangBerjasa = sessionManager.getOrangBerjasa()
        if (orangBerjasa.size == 1) {
            for (i in 0 until orangBerjasa.size) {
                list.add(
                    i, OrangsReq(
                        orangBerjasa[i].nama,
                        orangBerjasa[i].jenis_kelamin,
                        orangBerjasa[i].umur,
                        orangBerjasa[i].alamat,
                        orangBerjasa[i].pekerjaan,
                        orangBerjasa[i].keterangan
                    )
                )
            }
        } else {
            list.add(OrangsReq())
        }
        rv_org_selain_ortu.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = OrangsAdapter(
            "Orang Yang Membantu Selain Orang Tua", this, list, object : OrangsAdapter.OnClickOrangs {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        rv_org_selain_ortu.adapter = adapter
        btn_add_org_selain_ortu.setOnClickListener {
            list.add(OrangsReq())
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}