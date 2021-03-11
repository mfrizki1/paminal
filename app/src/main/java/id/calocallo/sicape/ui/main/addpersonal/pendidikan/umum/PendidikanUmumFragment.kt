package id.calocallo.sicape.ui.main.addpersonal.pendidikan.umum

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
import id.calocallo.sicape.model.*
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.dinas.PendidikanDinasFragment
import id.calocallo.sicape.utils.SessionManager1
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.fragment_pendidikan_umum.*
import kotlinx.android.synthetic.main.fragment_pendidikan_umum.view.*
import kotlinx.android.synthetic.main.layout_pendidikan_umum.*

class PendidikanUmumFragment : Fragment() {
    private lateinit var sheenValidator: SheenValidator
    private lateinit var sessionManager1: SessionManager1
    private lateinit var list: ArrayList<AddPendidikanModel>
    private lateinit var parentUmum: ParentListPendUmum
    private lateinit var adapter: PendUmumAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pendidikan_umum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager1 = activity?.let { SessionManager1(it) }!!

        sheenValidator = activity?.let { SheenValidator(it) }!!
        list = ArrayList()
        parentUmum = ParentListPendUmum(list)
        setAdapter()


        //go dinas
        view.btn_next_pend.setOnClickListener {
            if (list.size == 1 && list[0].pendidikan == "") {
                list.clear()
            }
            sessionManager1.setPendUmum(list)
            Log.e("pendUmum", "${sessionManager1.getPendUmum()}")
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


        }

    }

    private fun setAdapter() {
        val umum = sessionManager1.getPendUmum()
        if (umum.size == 0) {
            list.add(AddPendidikanModel())
            list.add(AddPendidikanModel())
            list.add(AddPendidikanModel())
        }
        rv_pend_umum.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        adapter = activity?.let {
            PendUmumAdapter(it, list, object : PendUmumAdapter.OnClick {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            })
        }!!
        rv_pend_umum.adapter = adapter

        btn_add_pend_umum.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(AddPendidikanModel())
            adapter.notifyItemChanged(position)
            adapter.notifyItemInserted(position)

        }
    }

    override fun onResume() {
        super.onResume()
        val umum = sessionManager1.getPendUmum()
        for (i in 0 until umum.size) {
            list.add(
                i, AddPendidikanModel(
                    umum[i].pendidikan,
                    umum[i].jenis,
                    umum[i].tahun_awal,
                    umum[i].tahun_akhir,
                    umum[i].kota,
                    umum[i].yang_membiayai,
                    umum[i].keterangan
                )
            )

        }
    }
}
