package com.abdelfattah.cmpbeta

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_info.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UserInfo.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UserInfo.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class UserInfo : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rv=inflater.inflate(R.layout.fragment_user_info, container, false)
        rv.nameidd.text=param1
        rv.emailidd.text=param2
        Picasso.get().load(param3).into(rv.PhotoIID)
        rv.signout.setOnClickListener{
            mAuth!!.signOut()
            signout()
        }
        return rv
    }


    fun signout()
    {
        if(mAuth!!.currentUser==null) {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    /**
     * This interface must be cbimplemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserInfo.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String,param3:String) =
                UserInfo().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                        putString(ARG_PARAM3, param3)
                    }
                }
    }
}
