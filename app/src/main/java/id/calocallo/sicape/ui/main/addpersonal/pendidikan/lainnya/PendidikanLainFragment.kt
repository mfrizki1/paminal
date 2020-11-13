package id.calocallo.sicape.ui.main.addpersonal.pendidikan.lainnya

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListPendOther
import id.calocallo.sicape.model.PendOtherModel
import id.calocallo.sicape.ui.main.addpersonal.pekerjaan.PekerjaanPersonelActivity
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.fragment_pendidikan_lain.*
import kotlinx.android.synthetic.main.fragment_pendidikan_lain.view.*

class PendidikanLainFragment : Fragment() {

    private lateinit var sheenValidator: SheenValidator
//    private lateinit var parentPendidikan: LinearLayout

    private lateinit var list: ArrayList<PendOtherModel>
    private lateinit var adapter: PendOtherAdapter
    private lateinit var parentList: ParentListPendOther
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pendidikan_lain, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sheenValidator = activity?.let { SheenValidator(it) }!!

        list = ArrayList()
        parentList = ParentListPendOther(list)

        setAdapter()
        view.btn_next_pend_other.setOnClickListener {
//            Log.e("parent", parentList.parentOther[0].nama_pend)
//            Log.e("parent", parentList.parentOther[1].nama_pend)
//            Log.e("parent", parentList.parentOther[2].nama_pend)
            sheenValidator.validate()
            val intent = Intent(activity, PekerjaanPersonelActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setAdapter() {
        rv_pend_other.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        list.add(PendOtherModel("", "", "", "", "", ""))
        list.add(PendOtherModel("", "", "", "", "", ""))
        list.add(PendOtherModel("", "", "", "", "", ""))
        adapter = activity?.let {
            PendOtherAdapter(it, list, object : PendOtherAdapter.OnCLickOther {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }!!
        rv_pend_other.adapter = adapter
        btn_add_pend_other.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(PendOtherModel("", "", "", "", "", ""))
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()

        }
    }
}