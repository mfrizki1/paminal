package id.calocallo.sicape.ui.main.addpersonal.misc.penghargaan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListPenghargaan
import id.calocallo.sicape.model.PenghargaanModel
import id.calocallo.sicape.ui.main.addpersonal.AddPasanganActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.fragment_penghargaan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*


class PenghargaanFragment : Fragment() {
    private lateinit var list: ArrayList<PenghargaanModel>
    private lateinit var parentList: ParentListPenghargaan
    private lateinit var adapter: PenghargaanAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_penghargaan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = ArrayList()
        parentList = ParentListPenghargaan(list)
        (activity as BaseActivity).setupActionBarWithBackButton(toolbar)
        (activity as BaseActivity).supportActionBar?.title = "Penghargaan"
        initRecycler(rv_pernghargaan)

        btn_next_penghargaan.setOnClickListener {
            Log.e("data penghargaan", parentList.parentList[0].jenis.toString())
            Log.e("data penghargaan", parentList.parentList[0].diterima.toString())

            //initAPI(list)
            //berhasil -> GO
            //gagal -> TOAST

            startActivity(Intent(activity, AddPasanganActivity::class.java))
        }
    }

    private fun initRecycler(rv: RecyclerView) {
        list.add(PenghargaanModel("", "", "", "", ""))
        rv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = activity?.let {
            PenghargaanAdapter(it, list, object : PenghargaanAdapter.OnClickPenghargaan {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }!!

        rv.adapter = adapter

        btn_add_penghargaan.setOnClickListener {
            list.add(PenghargaanModel("", "", "", "", ""))
            val position = if (list.isEmpty()) 0 else list.size - 1
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}