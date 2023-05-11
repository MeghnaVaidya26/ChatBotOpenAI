
import MyViewModelFactory
import Pref
import Pref.Companion.getPrefSubscription
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.database.RecentChatModel
import com.app.utils.Constant
import com.app.utils.Utils
import com.app.view.adapter.RecentChatHistoryAdapter
import com.app.view.base.BaseFragment

import com.app.viewmodel.RecentChatViewModel
import com.example.chatbotopenai.R
import com.example.chatbotopenai.databinding.FragmentHistoryBinding
import makeGone
import makeVisible
import setOnMyClickListener

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : BaseFragment() {
    private var _binding: FragmentHistoryBinding? = null
    var chatHistoryAdapter: RecentChatHistoryAdapter? = null
    var chatHistoryList: ArrayList<RecentChatModel> = ArrayList()
    private lateinit var recentChatViewModel: RecentChatViewModel
    lateinit var backPressedCallback: OnBackPressedCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = _binding!!.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        _binding!!.toolbar.tvTitle.text = getString(R.string.history)
        setAdapter()
        setOnClickListener()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAdapter() {
        var chatLinearLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        _binding!!.rvHistory.layoutManager = chatLinearLayoutManager
        //  chatHistoryList.add(RecentChatModel(message = "iiiiiiiiiiiiiiiii"))
        chatHistoryAdapter = RecentChatHistoryAdapter(
            activity!!, chatHistoryList, this@HistoryFragment
        )
        _binding!!.rvHistory.adapter = chatHistoryAdapter

        recentChatViewModel.getAllNotes().observe(
            this
        ) { myTransactionList ->
            // chatHistoryList = myTransactionList as ArrayList<RecentChatModel>
            chatHistoryList.clear()
            chatHistoryList.addAll(myTransactionList as ArrayList<RecentChatModel>)
            chatHistoryAdapter!!.notifyDataSetChanged()
            _binding!!.rlMain.makeVisible()
            if (chatHistoryList.isEmpty()) {
                _binding!!.tvNoChatFound.makeVisible()
                _binding!!.llClearall.makeGone()
            } else {
                _binding!!.tvNoChatFound.makeGone()
                _binding!!.llClearall.makeVisible()
            }
        }


    }

    override fun onListClick(position: Int, obj: Any?) {
        super.onListClick(position, obj)
            val bundle = bundleOf("message" to chatHistoryList!![position].message)



    }

    override fun onListClickSimple(position: Int, obj: Any?) {
        super.onListClickSimple(position, obj)

            recentChatViewModel.delete(obj as RecentChatModel)
            chatHistoryList.removeAt(position)
            chatHistoryAdapter!!.notifyDataSetChanged()


    }

    /**
     * Clear all positive button click ill be triggered here as a callback
     */
    override fun onListClickView(position: Int, obj: Any?) {

        recentChatViewModel.deleteAllNotes()
        chatHistoryList!!.clear()
        chatHistoryAdapter!!.notifyDataSetChanged()
    }

    fun setOnClickListener() {
        _binding!!.llClearall.setOnMyClickListener {
                Utils.commonDialog(
                    activity!!,
                    this,
                    resources.getString(R.string.are_you_sure_you_want_to_clear_chat)
                )

        }




    }


    private fun init() {

        recentChatViewModel =
            ViewModelProvider(this, MyViewModelFactory(RecentChatViewModel(activity!!))).get(
                RecentChatViewModel::class.java
            )

    }

    override fun onReardedSucessView(obj: Any?) {
        super.onReardedSucessView(obj)

        findNavController().popBackStack()
    }

}