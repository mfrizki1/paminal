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
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_alamat.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddAlamatActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: AddAlamatAdapter
    private lateinit var list: ArrayList<AlamatReq>
    private lateinit var parentList: ParentListAlamat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_alamat)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Alamat"
        list = ArrayList()
//        parentList = ParentListAlamat(list)

        //personel
//        val personel = sessionManager.getPersonel()
//        Log.e("personel", personel.nama.toString())
//
//        //umum
//        val umum = sessionManager.getPendUmum()
//        Log.e("umum", umum.size.toString())
//
//        //dinas
//        val dinas = sessionManager.getPendDinas()
//        Log.e("dinas", dinas.size.toString())
//
//        //lain
//        val lain = sessionManager.getPendOther()
//        Log.e("lain", lain.size.toString())
//
//        //pekerjaan
//        val pkrjn = sessionManager.getPekerjaan()
//        Log.e("pkrjan", pkrjn.size.toString())
//
//        //diluar dinas
//        val pkrjnDinas = sessionManager.getPekerjaanDiluar()
//        Log.e("diluar", pkrjnDinas.size.toString())

        initRecycler(rv_alamat)
        btn_next_alamat.setOnClickListener {
            if (list.size == 0) {
                list.clear()
            }

            sessionManager.setAlamat(list)
            Log.e("alamatSize", "alamat Size ${sessionManager.getAlamat().size}")
            //initAPI(list)
//            Log.e("namalamat", parentList.parenListAlamat[0].alamat.toString())
//            parentList.parenListAlamat[0].dalam_rangka?.let { it1 -> Log.e("rangkaalamat", it1) }
            startActivity(Intent(this, MiscenaousActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initRecycler(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val alamatCreated = sessionManager.getAlamat()
        Log.e("alamatSize", "alamat Size ${alamatCreated.size}")
        for (i in 0 until alamatCreated.size) {
            list.add(
                i, AlamatReq(
                    alamatCreated[i].alamat,
                    alamatCreated[i].tahun_awal,
                    alamatCreated[i].tahun_akhir,
                    alamatCreated[i].dalam_rangka,
                    alamatCreated[i].keterangan
                )
            )
        }
        if (alamatCreated.size == 0) {
            list.add(
                AlamatReq(
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
        adapter = AddAlamatAdapter(this, list, object : AddAlamatAdapter.OnClickAlamat {
            override fun onDelete(position: Int) {
                list.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        })

        rv.adapter = adapter
        btn_add_alamat.setOnClickListener {
            list.add(
                AlamatReq("", "", "", "", "")
            )
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        val alamat = sessionManager.getAlamat()
//        if(alamat.size != 0) {
//            for (i in 0 until alamat.size) {
//
//                list.add(
//                    i, AlamatReq(
//                        alamat[i].alamat,
//                        alamat[i].tahun_awal,
//                        alamat[i].tahun_akhir,
//                        alamat[i].dalam_rangka,
//                        alamat[i].keterangan
//                    )
//                )
//            }
//        }
//    }
}
