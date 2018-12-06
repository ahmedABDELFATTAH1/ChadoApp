package com.abdelfattah.cmpbeta

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_note2.*
import kotlinx.android.synthetic.main.fragment_note2.view.*
import android.widget.AdapterView.OnItemClickListener
import android.support.v7.widget.RecyclerView

class NoteFragment : Fragment() {

    var listoftexts:ArrayList<Note>?=null
    var adapter:RecycleAdapter?=null
    var dbManager:Dbmanger?=null
    var cursor:Cursor?=null
    var id:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        listoftexts = ArrayList<Note>()
        dbManager= Dbmanger(context!!)
        cursor= dbManager!!.getalldata("Table_ahmedqqqq")
        cursor!!.moveToFirst()
        id =0
        while(id !=cursor!!.count)
        {
            insertdata()
            println("ON Creata SSSSS +$id")
            cursor!!.moveToNext()
            id=id!!+1

        }

    }

    override fun onResume() {

        super.onResume()
        println("ON RESUME SSSSS out  +$id")
        println("ON RESUME  cursor!!.count + "+cursor!!.count.toString() )
        cursor= dbManager!!.getalldata("Table_ahmedqqqq")
        listoftexts!!.clear()
        id=0
        cursor!!.moveToFirst()
        while(id!! < cursor!!.count)
        {
            insertdata()
            cursor!!.moveToNext()
            id=id!!+1
            println("ON RESUME SSSSS +$id")

        }
        adapter!!.notifyDataSetChanged()
    }

    fun insertdata() {
       listoftexts!!.add(Note(cursor!!.getString(1),cursor!!.getString(2),cursor!!.getString(3),cursor!!.getString(4)) )

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val rvv = inflater.inflate(R.layout.fragment_note2, container, false)
        var simplelayoutmanager=LinearLayoutManager(this.context)
        rvv.RV.layoutManager=simplelayoutmanager
        adapter=RecycleAdapter(this.context!!,listoftexts!!)
        rvv.RV.adapter=adapter
        rvv.addid.setOnClickListener {
            val intent = Intent(activity, ADD::class.java)
            startActivity(intent)
        }

        return rvv
    }

    }


