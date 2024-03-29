package id.calocallo.sicape.ui.main.addpersonal.pendidikan.umum

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.dinas.PendidikanDinasFragment
import id.calocallo.sicape.utils.SessionManager
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.fragment_pendidikan_umum.*
import kotlinx.android.synthetic.main.fragment_pendidikan_umum.view.*

class PendidikanUmumFragment : Fragment() {
    private lateinit var sheenValidator: SheenValidator
    private lateinit var sessionManager: SessionManager
    private lateinit var list: ArrayList<AddPendidikanModel>
    private lateinit var parentUmum: ParentListPendUmum
    private lateinit var adapter: UmumAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pendidikan_umum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager(it) }!!

        sheenValidator = activity?.let { SheenValidator(it) }!!
        list = ArrayList()
        parentUmum = ParentListPendUmum(list)
//        parentDinas = ParentListPendDinas(listDinas)
//        parentLain = ParentListPendOther(listOther)

//        recycler()
        setAdapter()


        //go dinas
        view.btn_next_pend.setOnClickListener {
            if (list.size == 1 && list[0].pendidikan == "") {
                list.clear()
            }
            sessionManager.setPendUmum(list)
            sheenValidator.validate()

            val pendDinasFrag = PendidikanDinasFragment()
                .apply {
                    enterTransition = Slide(Gravity.END)
                    exitTransition = Slide(Gravity.START)
                }

            val ft: FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            ft.replace(R.id.fl_pendidikan, pendDinasFrag)
            ft.addToBackStack(null)
            ft.commit()


            //berhasil

            /*
            //initAPI(param: list(ArrayList<PendUmumModel>))
            berhasil -> lanjut ke pendDinasFrag
            gagal -> Toast(Gangguan)

             */

            /*
//            Log.e("parent", parent.pendUmumList[0].nama_pend)
//            Log.e("parent", parent.pendUmumList[1].nama_pend)
//            Log.e("parent", parent.pendUmumList[2].nama_pend)
             */
        }

    }

    private fun setAdapter() {
        val umum = sessionManager.getPendUmum()
        if (umum.size == 0) {
            list.add(AddPendidikanModel("", "", "", "", "", ""))
        }
        rv_pend_umum.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        adapter = activity?.let {
            UmumAdapter(it, list, object : UmumAdapter.OnClick {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }!!
        rv_pend_umum.adapter = adapter

        btn_add_pend_umum.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(AddPendidikanModel("", "", "", "", "", ""))
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()

        }

    }

    override fun onResume() {
        super.onResume()
        val umum = sessionManager.getPendUmum()
        for (i in 0 until umum.size) {
            list.add(
                i, AddPendidikanModel(
                    umum[i].pendidikan,
                    umum[i].tahun_awal,
                    umum[i].tahun_akhir,
                    umum[i].kota,
                    umum[i].yang_membiayai,
                    umum[i].keterangan
                )
            )
//            list.add(AddPendidikanModel("", "", "", "", "", ""))
//            edt_nama_umum.setText(umum[i].pendidikan)
//            edt_thn_awal_umum.setText(umum[i].tahun_awal)
//            edt_thn_akhir_umum.setText(umum[i].tahun_akhir)
//            edt_tempat_umum.setText(umum[i].kota)
//            edt_membiayai_umum.setText(umum[i].yang_membiayai)
//            edt_ket_umum.setText(umum[i].keterangan)
        }
    }
}
