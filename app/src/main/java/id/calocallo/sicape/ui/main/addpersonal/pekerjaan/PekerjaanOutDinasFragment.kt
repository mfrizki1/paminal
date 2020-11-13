package id.calocallo.sicape.ui.main.addpersonal.pekerjaan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListPekerjaanODinas
import id.calocallo.sicape.model.PekerjaanODinasModel
import id.calocallo.sicape.ui.main.addpersonal.alamat.AddAlamatActivity
import kotlinx.android.synthetic.main.fragment_pekerjaan_out_dinas.*


class PekerjaanOutDinasFragment : Fragment() {
    private lateinit var list: ArrayList<PekerjaanODinasModel>
    private lateinit var parent: ParentListPekerjaanODinas
    private lateinit var adapter: PekerjaanODinasAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pekerjaan_out_dinas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = ArrayList()
        parent = ParentListPekerjaanODinas(list)

        initRecycler()
        btn_next_pekerjaan_oDinas.setOnClickListener {

            //iniAPI(list)
            //berhasil -> GO
            //gagal -> TOAST

            Log.e("dataODINAS1", parent.parentList[0].nama_pkrjan.toString())
            Log.e("dataODINAS2", parent.parentList[1].instansi.toString())
//            Log.e("dataODINAS2", parent.parentList[2].nama_pkrjan.toString())

            startActivity(Intent(activity, AddAlamatActivity::class.java))
        }
    }

    private fun initRecycler() {
        rv_pekerjaan_ODinas.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        list.add(PekerjaanODinasModel("", "", "", "", ""))
        list.add(PekerjaanODinasModel("", "", "", "", ""))
        adapter = activity?.let {
            PekerjaanODinasAdapter(it, list, object : PekerjaanODinasAdapter.OnClickODinas {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }!!
        rv_pekerjaan_ODinas.adapter = adapter

        btn_add_pekerjaan_oDinas.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(PekerjaanODinasModel("", "", "", "", ""))
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
        }
    }
}