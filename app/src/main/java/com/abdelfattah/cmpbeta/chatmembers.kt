package com.abdelfattah.cmpbeta

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.abdelfattah.cmpbeta.R.layout.ticket
import com.abdelfattah.cmpbeta.RecyclerItemClickListener.OnItemClickListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_chatmembers.view.*
import kotlinx.android.synthetic.main.fragment_note2.view.*

class chatmembers : Fragment() {

    private var listofchaters:ArrayList<chatinfo>?=null
    var adapter:ChatRecycleAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listofchaters=ArrayList<chatinfo>()
        loadpost()
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val rvv =  inflater.inflate(R.layout.fragment_chatmembers, container, false)
        var simplelayoutmanager= LinearLayoutManager(this.context)
        rvv.chatRV.layoutManager=simplelayoutmanager
        adapter= ChatRecycleAdapter(this.context!!,listofchaters!!)
        rvv.chatRV.adapter=adapter
        rvv.chatRV.addOnItemTouchListener(
                RecyclerItemClickListener(context!!, rvv.chatRV, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        Toast.makeText(context,"chater numb : "+position.toString(),Toast.LENGTH_SHORT).show()
                        val intent=Intent(context,inboxchat::class.java)
                       val mAuth = FirebaseAuth.getInstance();
                       intent.putExtra("other",listofchaters!![position].uid)
                        intent.putExtra("me",mAuth.currentUser!!.uid)
                        intent.putExtra("imageurl",mAuth.currentUser!!.photoUrl.toString())
                        startActivity(intent)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {

                    }
                })
        )


        return  rvv
    }

    fun loadpost()
    {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference().child("users")
        myRef!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                try {
                    listofchaters!!.clear()
                    var td=p0!!.value as HashMap<String,Any>
                    for(key in td.keys)
                    {
                        var post=td[key] as HashMap<String,Any>
                        if(post["uid"].toString()!=FirebaseAuth.getInstance().currentUser!!.uid)
                        {
                            listofchaters!!.add(chatinfo(post["email"] as String,
                                    post["name"] as String,post["photourl"] as String,post["uid"] as String))
                        }


                    }

                    adapter!!.notifyDataSetChanged()

                }catch (ex:Exception){}
            }



        })

    }
}
