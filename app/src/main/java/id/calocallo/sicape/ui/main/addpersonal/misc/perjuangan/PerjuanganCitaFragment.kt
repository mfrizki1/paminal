package id.calocallo.sicape.ui.main.addpersonal.misc.perjuangan

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.transition.Slide
import android.util.Log
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.request.PerjuanganCitaReq
import id.calocallo.sicape.ui.main.addpersonal.misc.penghargaan.PenghargaanFragment
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_perjuangan_cita.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*


class PerjuanganCitaFragment : Fragment() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var list: ArrayList<PerjuanganCitaReq>
    private lateinit var parentList: ParentListPerjuanganCita
    private lateinit var adapter: PerjuanganAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perjuangan_cita, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager1 = activity?.let { SessionManager1(it) }!!

        (activity as BaseActivity).setupActionBarWithBackButton(toolbar)
        (activity as BaseActivity).supportActionBar?.title = "Perjuangan Mencapai Cita-Cita"
        list = ArrayList()
        parentList = ParentListPerjuanganCita(list)

        initRecycler(rv_perjuangan_cita)

        btn_next_perjuangan.setOnClickListener {
            if (list.size == 1 && list[0].peristiwa == "") {
                list.clear()
            }
            sessionManager1.setPerjuanganCita(list)
            Log.e("size", "size perjuangan ${sessionManager1.getPerjuanganCita()}}")

            val penghargaanFragment = PenghargaanFragment()
                .apply {
                    enterTransition = Slide(Gravity.END)
                    exitTransition = Slide(Gravity.START)
                }
            val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.fl_misc, penghargaanFragment)
            ft.addToBackStack(null)
            ft.commit()
        }
    }

    private fun initRecycler(rv: RecyclerView) {
        val perjuangan = sessionManager1.getPerjuanganCita()
        Log.e("size", "size perjuangan ${perjuangan.size}}")

        if (perjuangan.size == 0) {
            list.add(PerjuanganCitaReq())
        }
        Log.e("size Perjuangan", perjuangan.size.toString())
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = activity?.let {
            PerjuanganAdapter(it, list, object : PerjuanganAdapter.OnClickPerjuangan {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            })
        }!!
        rv.adapter = adapter

        btn_add_perjuangan.setOnClickListener {
            list.add(PerjuanganCitaReq())
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemChanged(position)
            adapter.notifyItemInserted(position)
        }

    }

    override fun onResume() {
        super.onResume()
        val perjuangan = sessionManager1.getPerjuanganCita()
        for (i in 0 until perjuangan.size) {
            list.add(
                i, PerjuanganCitaReq(
                    perjuangan[i].peristiwa,
                    perjuangan[i].lokasi,
                    perjuangan[i].tahun_awal,
                    perjuangan[i].tahun_akhir,
                    perjuangan[i].dalam_rangka,
                    perjuangan[i].keterangan
                )
            )
        }
    }
}