package id.calocallo.sicape.ui.main.addpersonal.misc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.fragment.app.FragmentTransaction
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.main.addpersonal.misc.organisasi.OrganisasiFragment
import id.co.iconpln.smartcity.ui.base.BaseActivity

class MiscenaousActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_miscenaous)

        val orgFrag = OrganisasiFragment()
            .apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_misc, orgFrag)
        ft.commit()

    }
}