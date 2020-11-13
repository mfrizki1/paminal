package id.calocallo.sicape.ui.main.addpersonal.misc.perjuangan

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.transition.Slide
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.ui.main.addpersonal.misc.organisasi.OrganisasiAdapter
import id.calocallo.sicape.ui.main.addpersonal.misc.penghargaan.PenghargaanFragment
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_perjuangan_cita.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*


class PerjuanganCitaFragment : Fragment() {
    private lateinit var list: ArrayList<PerjuanganCitaModel>
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
        (activity as BaseActivity).setupActionBarWithBackButton(toolbar)
        (activity as BaseActivity).supportActionBar?.title = "Perjuangan Mencapai Cita-Cita"
        list = ArrayList()
        parentList = ParentListPerjuanganCita(list)

        initRecycler(rv_perjuangan_cita)

        btn_next_perjuangan.setOnClickListener {
//            Log.e("peristiwa", parentList.parentList[0].peristiwa.toString())
//            Log.e("peristiwa", parentList.parentList[0].tmpt_peristiwa.toString())
//            Log.e("peristiwa", parentList.parentList[1].peristiwa.toString())

            val penghargaanFragment = PenghargaanFragment()
                .apply {
                    enterTransition = Slide(Gravity.END)
                    exitTransition = Slide(Gravity.START)
                }
            val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.fl_misc, penghargaanFragment)
//            ft.addToBackStack(null)
            ft.commit()
        }
    }

    private fun initRecycler(rv: RecyclerView) {
        list.add(PerjuanganCitaModel("", "", "", "", "", ""))
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = activity?.let {
            PerjuanganAdapter(it, list, object : PerjuanganAdapter.OnClickPerjuangan {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }!!
        rv.adapter = adapter

        btn_add_perjuangan.setOnClickListener {
            list.add(PerjuanganCitaModel("", "", "", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }

    }
}