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
import id.calocallo.sicape.model.OrganisasiModel
import id.calocallo.sicape.model.ParentListOrganisasi
import id.calocallo.sicape.ui.main.addpersonal.misc.perjuangan.PerjuanganCitaFragment
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_organisasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*


class OrganisasiFragment : Fragment() {
    private lateinit var list: ArrayList<OrganisasiModel>
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

        (activity as BaseActivity).setupActionBarWithBackButton(toolbar)
        (activity as BaseActivity).supportActionBar?.title = "Organisasi"

        list = ArrayList()
        parentList = ParentListOrganisasi(list)

        initRV(rv_organisasi)

        btn_next_org.setOnClickListener {

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
//            ft.addToBackStack(null)
            ft.commit()
        }
    }

    private fun initRV(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        list.add(OrganisasiModel("", "", "", "","", "",""))
        list.add(OrganisasiModel("", "", "", "","", "",""))

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
            list.add(OrganisasiModel("", "", "", "","", "",""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}