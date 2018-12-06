package com.abdelfattah.cmpbeta

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_inboxchat.*
import kotlinx.android.synthetic.main.fragment_note2.view.*
import java.util.*


class inboxchat : AppCompatActivity() {
        var bundle:Bundle?=null
        var myid:String?=null
        var otherid:String?=null
        var textlist:ArrayList<Texts>?=null
        var loginkey:String?=null
        var  adapter:ConvarsationAdapter?=null
        var  myimage:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inboxchat)
        textlist=ArrayList<Texts>()
        bundle=intent.extras
        myid=bundle!!.getString("me")
        otherid=bundle!!.getString("other")
        myimage=bundle!!.getString("imageurl")
        var simplelayoutmanager= LinearLayoutManager(this)
        listofchats.layoutManager=simplelayoutmanager
          adapter=ConvarsationAdapter(this,textlist!!)
        listofchats.adapter=adapter
        loadfreindsinfo()
        sendid.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            var HM=HashMap<String,String>()
            HM.put("uid",myid!!)
            HM.put("text",chattext.text.toString())
            HM.put("imageurl",myimage.toString())
            if(loginkey!=null) {
                val rand = Random()
                val n = rand.nextInt(1000000) + 1
                database.getReference("chats").child(loginkey).child(n.toString()).setValue(HM)

            }

            chattext.text=null
        }

    }
    fun loadfreindsinfo()
    {

        val database = FirebaseDatabase.getInstance()
        database.reference.child("chats").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(myid+otherid)) {
                    loginkey=myid+otherid
                    database.reference.child("chats").child(loginkey).addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {

                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            textlist!!.clear()
                            var td=p0!!.value as HashMap<String,Any>
                            for(key in td.keys)
                            {
                                var post=td[key] as HashMap<String,Any>
                                textlist!!.add(Texts(post["uid"].toString(),post["text"].toString(),post["imageurl"].toString()))

                            }
                            textlist!!.reverse()
                            adapter!!.notifyDataSetChanged()
                        }


                })
                }
                else if (snapshot.hasChild(otherid+myid))
                {
                    loginkey=otherid+myid
                    database.reference.child("chats").child(loginkey).addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {

                        }

                        override fun onDataChange(p0: DataSnapshot?) {
                            textlist!!.clear()
                            var td=p0!!.value as HashMap<String,Any>
                            for(key in td.keys)
                            {
                                var post=td[key] as HashMap<String,Any>
                                textlist!!.add(Texts(post["uid"].toString(),post["text"].toString(),post["imageurl"].toString()))

                            }
                            textlist!!.reverse()
                            adapter!!.notifyDataSetChanged()
                        }


                    })
                }
                else{
                    loginkey=myid+otherid

                     var x=  database.getReference("chats").child(myid+otherid).setValue(null)


                }
            }

        });




    }

}
