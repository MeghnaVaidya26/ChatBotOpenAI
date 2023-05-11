
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.app.database.RecentChatModel
import com.app.model.MessageModel
import com.app.model.ParamModel
import com.app.utils.Constant
import com.app.utils.Utils
import com.app.view.adapter.ChatMessageAdapter
import com.app.view.base.BaseFragment

import com.app.viewmodel.ChatMessageViewModel
import com.example.chatbotopenai.R
import com.example.chatbotopenai.databinding.FragmentHomeBinding

import setOnMyClickListener


class HomeFragment : BaseFragment() {

    private lateinit var recentChatViewModel: RecentChatViewModel
    var chatViewModel: ChatMessageViewModel? = null
    private var _binding: FragmentHomeBinding? = null
    var messageList: ArrayList<MessageModel?> = ArrayList()
    var chatMessageAdapter: ChatMessageAdapter? = null
    var chatLinearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = _binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (arguments != null) {
            _binding!!.etMessage.setText(arguments!!.getString("message").toString())
        }
        chatLinearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
        chatLinearLayoutManager?.stackFromEnd = true
        messageList.clear()
        messageList.add(MessageModel("0", Constant.initialMessage, "Assistant"))
        chatMessageAdapter = ChatMessageAdapter(
            activity!!, messageList, this

        )
        _binding!!.rvChat.layoutManager = chatLinearLayoutManager
        _binding!!.rvChat.adapter = chatMessageAdapter


        init()
        // Utils.showBottomSheetDialog(activity!!)
        onClickListeners()
        addObserver()
    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onListClickView(position: Int, obj: Any?) {

    }


    fun onClickListeners() {

        _binding!!.rvChat.setOnTouchListener { v, event ->
            hideSoftKeyboard()
            false
        }

        _binding!!.ivHistory.setOnMyClickListener {
            findNavController().navigate(R.id.history_fragment)
        }
        _binding!!.ivSendMessage.setOnMyClickListener {
            callApi()

        }
        _binding!!.rvChat.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->


            if (oldBottom < bottom) {
                _binding!!.rvChat.postDelayed(
                    Runnable { _binding!!.rvChat.smoothScrollToPosition(0) },
                    100
                )
            }
        }

    }

    private fun init() {

        chatViewModel =
            ViewModelProvider(this, MyViewModelFactory(ChatMessageViewModel(activity!!))).get(
                ChatMessageViewModel::class.java
            )

        recentChatViewModel =
            ViewModelProvider(this, MyViewModelFactory(RecentChatViewModel(activity!!))).get(
                RecentChatViewModel::class.java
            )
    }

    private fun addObserver() {

        chatViewModel!!.isLoading!!.observe(viewLifecycleOwner, Observer {

        })

        chatViewModel!!.responseError!!.observe(viewLifecycleOwner, Observer {

        })

        chatViewModel!!.messageListModel!!.observe(viewLifecycleOwner, Observer {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                messageList.removeAt(0)
                scrollToBottom()
                chatMessageAdapter!!.notifyDataSetChanged()


                val message = it.choices.get(0).text.replace("\n", "")
                messageList.add(
                    0, MessageModel(
                        messageList.size.toString(), message, "ChatGPT"
                    )

                )
                if (messageList.size > 2) {
                    (_binding!!.rvChat.adapter as ChatMessageAdapter?)?.let {
                        /**already adapter set just need to notify*/
                        Log.v("====", "=adapter")
                        _binding!!.rvChat.layoutManager = chatLinearLayoutManager
                        _binding!!.rvChat.adapter = chatMessageAdapter
                        chatMessageAdapter?.notifyDataSetChanged()
                        scrollToBottom()

                    } ?: run {
                        /**need to set new adapter*/
                        chatMessageAdapter = ChatMessageAdapter(
                            activity!!,
                            messageList,
                            this,

                            )

                        _binding!!.rvChat.layoutManager = chatLinearLayoutManager
                        _binding!!.rvChat.adapter = chatMessageAdapter
                        scrollToBottom()
                    }
                } else {
                    chatMessageAdapter = ChatMessageAdapter(
                        activity!!,
                        messageList,
                        this,

                        )

                    _binding!!.rvChat.layoutManager = chatLinearLayoutManager
                    _binding!!.rvChat.adapter = chatMessageAdapter
                    scrollToBottom()
                }


            }, 1000)

        })

    }

    private fun scrollToBottom() {
        try {
            if (messageList.size > 0) {
                _binding!!.rvChat.smoothScrollToPosition(0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callApi() {
        messageList.add(
            0, MessageModel(
                messageList.size.toString(), _binding!!.etMessage.text.toString(), "User"
            )
        )
        if (!NetworkUtil.isInternetAvailable(activity!!)) {

            shoToast(getString(R.string.no_internet_connection))

            return
        } else {
            var is_sub = Pref.getPrefSubscription(activity!!)
            var maxToken = if (is_sub) Constant.tokenSubscribed else Constant.tokenUnsubscribed

            var message =
                ""


            for (i in messageList.size - 1 downTo 0) {
                if (messageList[i]!!.role != "Assistant") message =
                    "${message}${messageList[i]!!.role}:${messageList[i]!!.message}\n"
            }
            message = "${message}ChatGPT:"


            var paramModel = ParamModel(
                model = Constant.modelname, prompt = message, max_tokens = maxToken
            )
            messageList
            messageList.add(0, null)
            chatMessageAdapter!!.notifyDataSetChanged()

            chatViewModel!!.getMessage(paramModel)
            recentChatViewModel.insert(RecentChatModel(message = _binding!!.etMessage.text.toString()))
            _binding!!.etMessage.setText("")
            hideSoftKeyboard()
        }
    }


    override fun onPause() {
        super.onPause()

    }


}