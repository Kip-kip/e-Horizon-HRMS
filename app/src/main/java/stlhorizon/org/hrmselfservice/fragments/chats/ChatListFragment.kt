package stlhorizon.org.hrmselfservice.fragments.chats

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableRow
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import stlhorizon.org.hrmselfservice.FRAGMENT
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.activities.Leave.LeaveItemActivity
import stlhorizon.org.hrmselfservice.activities.MainActivity
import stlhorizon.org.hrmselfservice.activities.Payslip.GroupChatItemActivity
import stlhorizon.org.hrmselfservice.activities.Payslip.SingleChatItemActivity

class ChatListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_chatlist, container, false)
        val gotosinglechatitem = root.findViewById<LinearLayout>(R.id.tosinglechatitem)
        val gotogroupchatitem = root.findViewById<LinearLayout>(R.id.togroupchatitem)

        gotosinglechatitem.setOnClickListener {
            val intent = Intent(context, SingleChatItemActivity::class.java)
            startActivity(intent)
        }

        gotogroupchatitem.setOnClickListener {
            val intent = Intent(context, GroupChatItemActivity::class.java)
            startActivity(intent)
        }
        MainActivity.Foo.lastFragment= FRAGMENT.CHATS;
        return root
    }
}