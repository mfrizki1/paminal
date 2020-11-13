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
import id.calocallo.sicape.model.ParentListPendUmum
import id.calocallo.sicape.model.PendUmumModel
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.dinas.PendidikanDinasFragment
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.lainnya.PendidikanLainFragment
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.fragment_pendidikan_umum.*
import kotlinx.android.synthetic.main.fragment_pendidikan_umum.view.*

class PendidikanUmumFragment : Fragment() {
    private lateinit var sheenValidator: SheenValidator
//    private lateinit var parentPendidikan: LinearLayout

    private lateinit var list: ArrayList<PendUmumModel>
    private lateinit var adapter: UmumAdapter
    private lateinit var parent: ParentListPendUmum

    //    private lateinit var comunicator: Comunicator
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


        sheenValidator = activity?.let { SheenValidator(it) }!!

        list = ArrayList()
        parent = ParentListPendUmum(list)


//        recycler()
        setAdapter()

//        view.btn_next_pend.setOnClickListener {
//            comunicator.goToDinas()
//        }


        //go dinas
        view.btn_next_pend.setOnClickListener {
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
            sheenValidator.validate()

            val pendDinasFrag = PendidikanDinasFragment()
                .apply {
                    enterTransition = Slide(Gravity.END)
                    exitTransition = Slide(Gravity.START)
                }
            val pendOtherFrag = PendidikanLainFragment()
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
        rv_pend_umum.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        list.add(PendUmumModel("", "", "", "", "", ""))
        list.add(PendUmumModel("", "", "", "", "", ""))
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
            list.add(PendUmumModel("", "", "", "", "", ""))
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()

        }

    }


}
