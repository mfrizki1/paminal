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
import id.calocallo.sicape.network.request.OrganisasiReq
import id.calocallo.sicape.model.ParentListOrganisasi
import id.calocallo.sicape.ui.main.addpersonal.misc.perjuangan.PerjuanganCitaFragment
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_organisasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class OrganisasiFragment : Fragment() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var list: ArrayList<OrganisasiReq>
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
        sessionManager1 = activity?.let { SessionManager1(it) }!!
        (activity as BaseActivity).setupActionBarWithBackButton(toolbar)
        (activity as BaseActivity).supportActionBar?.title = "Organisasi"
        list = ArrayList()

        initRV(rv_organisasi)

        btn_next_org.setOnClickListener {
            if(list.size == 1 && list[0].organisasi == ""){
                list.clear()
            }

            sessionManager1.setOrganisasi(list)
            val org_temp = sessionManager1.getOrganisasi()
            Log.e("orgSize", "$org_temp")

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
        if (sessionManager1.getOrganisasi().size == 0) {
            list.add(OrganisasiReq())
        }
        Log.e("size Organisasi", sessionManager1.getOrganisasi().size.toString())
        adapter = activity?.let {
            OrganisasiAdapter(it, list, object : OrganisasiAdapter.OnCLickOrg {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            })
        }!!

        rv.adapter = adapter

        btn_add_org.setOnClickListener {
            list.add(OrganisasiReq())
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemChanged(position)
            adapter.notifyItemInserted(position)
        }
    }

    override fun onResume() {
        super.onResume()
        val organisasi = sessionManager1.getOrganisasi()
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