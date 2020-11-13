package id.calocallo.sicape.ui.main.addpersonal.pekerjaan

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
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListPekerjaan
import id.calocallo.sicape.model.PekerjaanModel
import kotlinx.android.synthetic.main.fragment_pekerjaan.*


class PekerjaanFragment : Fragment() {
    private lateinit var list: ArrayList<PekerjaanModel>
    private lateinit var adapter: PekerjaanAdapter
    private lateinit var parent: ParentListPekerjaan

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pekerjaan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        parent = ParentListPekerjaan(list)
        initRecycler()

        btn_next_pekerjaan.setOnClickListener {
            //iniAPI(list)
            //berhasil -> GO
            //gagal -> TOAST

//            Log.e("data 1", parent.parentList[0].nama_pkrjn.toString())
//            Log.e("data 2", parent.parentList[1].pangkat_pkrjn.toString())
//            Log.e("data 3", parent.parentList[2].kesatuan_pkrjn.toString())
            val pkrjanOutDinas = PekerjaanOutDinasFragment()
                .apply {
                    enterTransition = Slide(Gravity.END)
                    exitTransition = Slide(Gravity.START)
                }
            val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.fl_pekerjaan, pkrjanOutDinas)
            ft.addToBackStack(null)
            ft.commit()
        }

    }

    private fun initRecycler() {
        rv_pekerjaan.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        list.add(PekerjaanModel("", "", "", "", ""))
        list.add(PekerjaanModel("", "", "", "", ""))
        adapter = activity?.let {
            PekerjaanAdapter(it, list, object : PekerjaanAdapter.OnCLickPekerjaan {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }!!
        rv_pekerjaan.adapter = adapter

        btn_add_pekerjaan.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(PekerjaanModel("", "", "", "", ""))
            adapter.notifyItemChanged(position)
            adapter.notifyDataSetChanged()

        }


    }
}