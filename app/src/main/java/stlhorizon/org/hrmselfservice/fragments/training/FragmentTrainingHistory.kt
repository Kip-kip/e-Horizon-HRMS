package stlhorizon.org.hrmselfservice.fragments.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import stlhorizon.org.hrmselfservice.R

class FragmentTrainingHistory : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_leavehistory, container, false)

        return root
    }
}