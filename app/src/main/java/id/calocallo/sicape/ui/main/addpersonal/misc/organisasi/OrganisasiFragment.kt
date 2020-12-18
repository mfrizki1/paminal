package id.calocallo.sicape.ui.main.addpersonal.misc.organisasi

import android.os.Bundle
import android.transition.Slide
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.OrganisasiReq
import id.calocallo.sicape.model.ParentListOrganisasi
import id.calocallo.sicape.ui.main.addpersonal.misc.perjuangan.PerjuanganCitaFragment
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_organisasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*


class OrganisasiFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    private lateinit var list: ArrayList<OrganisasiReq>
    private lateinit var parentList: ParentListOrganisasi
    private lateinit var adapter: OrganisasiAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_organisasi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager(it) }!!
        (activity as BaseActivity).setupActionBarWithBackButton(toolbar)
        (activity as BaseActivity).supportActionBar?.title = "Organisasi"
        list = ArrayList()
//        parentList = ParentListOrganisasi(list)

        initRV(rv_organisasi)

        btn_next_org.setOnClickListener {
            if(list.size == 1 && list[0].organisasi == ""){
                list.clear()
            }

            sessionManager.setOrganisasi(list)
            val org_temp = sessionManager.getOrganisasi()
            Log.e("orgSize", "orgSize ${org_temp.size}")
            //initAPI(param: list)
            //berhasil -> GO
            //gagal -> toast
//            Log.e("namaOrg", parentList.parentList[0].alamat.toString())
//            Log.e("namaOrg", parentList.parentList[1].kedudukan.toString())

            val perjuanganCitaFragment = PerjuanganCitaFragment().apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }
            val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.fl_misc, perjuanganCitaFragment)
            ft.addToBackStack(null)
            ft.commit()
        }
    }

    private fun initRV(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        if (sessionManager.getOrganisasi().size == 0) {
            list.add(OrganisasiReq("", "", "", "", "", "", ""))
        }
        Log.e("size Organisasi", sessionManager.getOrganisasi().size.toString())
        adapter = activity?.let {
            OrganisasiAdapter(it, list, object : OrganisasiAdapter.OnCLickOrg {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }!!

        rv.adapter = adapter

        btn_add_org.setOnClickListener {
            list.add(OrganisasiReq("", "", "", "", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        val organisasi = sessionManager.getOrganisasi()
        for (i in 0 until organisasi.size) {
            list.add(
                i, OrganisasiReq(
                    organisasi[i].organisasi,
                    organisasi[i].tahun_awal,
                    organisasi[i].tahun_akhir,
                    organisasi[i].jabatan,
                    organisasi[i].tahun_bergabung,
                    organisasi[i].alamat,
                    organisasi[i].keterangan
                )
            )
        }

    }
}