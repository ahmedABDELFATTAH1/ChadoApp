package com.abdelfattah.cmpbeta

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.ticket.view.*
import android.widget.Toast
import android.widget.CompoundButton




class RecycleAdapter(val context:Context,val list:ArrayList<Note>) :
        RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {
    var dbManager:Dbmanger?=null
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0. mTitle!!.text=list[p1].notetitle
        p0. mDesc!!.text=list[p1].notedescription
        p0. mDate!!.text=list[p1].Date
        if(list[p1]!!.done=="TRUE")
        {
            p0.check!!.isChecked=true
        }
        else{
            p0.check!!.isChecked=false
        }
        p0.check!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                dbManager!!.updatecheckbox(list!![p1].notetitle,"TRUE")
                list!![p1].done="TRUE"
            }
            else{
                dbManager!!.updatecheckbox(list!![p1].notetitle,"FALSE")
                list!![p1].done="FALSE"

            }
        };




        p0.delete!!.setOnClickListener {
            dbManager!!.delete(list!![p1].notetitle)
            list!!.removeAt(p1)
            notifyDataSetChanged()
        }

        p0.edit!!.setOnClickListener {
            val intent = Intent(context, ADD::class.java)
            intent.putExtra("NUMBER",p1)
            intent.putExtra("OLDTITLE",list!![p1].notetitle)
            intent.putExtra("OLDDIS",list!![p1].notedescription)
            intent.putExtra("DONE",list!![p1].done)
            startActivity(context,intent,null)
            notifyDataSetChanged()

        }

    }

    class ViewHolder : RecyclerView.ViewHolder
    {
        var mTitle: TextView? = null
        var mDesc: TextView? = null
        var mDate: TextView? = null
        var delete: ImageButton ?= null
        var edit: ImageButton ?= null
        var check:CheckBox?=null
        constructor(v:View):super(v)
        {
            mTitle=v.findViewById(R.id.titletext)
            mDesc=v.findViewById(R.id.desctext)
            mDate=v.findViewById(R.id.Dataid)
            delete=v.findViewById(R.id.Deleteidbtn)
            edit=v.findViewById(R.id.Editidbtn)
            check=v.findViewById(R.id.donecheckBox)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int):  RecycleAdapter.ViewHolder {

        dbManager= Dbmanger(context!!)
        var myview=LayoutInflater.from(context).inflate(R.layout.ticket,null,false)
        return ViewHolder(myview)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun getItemCount(): Int
    {
        return list.size
    }
}