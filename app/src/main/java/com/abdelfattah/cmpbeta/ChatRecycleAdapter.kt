package com.abdelfattah.cmpbeta

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
class ChatRecycleAdapter(val context:Context,val list:ArrayList<chatinfo>) :
        RecyclerView.Adapter<ChatRecycleAdapter.ViewHolder>() {
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.email!!.text=list[p1].email
        p0.name!!.text=list[p1].name
        Picasso.get().load(list[p1].photo).into(p0.image)

    }

    class ViewHolder : RecyclerView.ViewHolder
    {
        var email: TextView? = null
        var name: TextView? = null
        var image: CircleImageView ?= null
        constructor(v:View):super(v)
        {
           name=v.findViewById(R.id.namee)
            email=v.findViewById(R.id.emaile)
            image=v.findViewById(R.id.photourle)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ChatRecycleAdapter.ViewHolder {

        var myview=LayoutInflater.from(context).inflate(R.layout.membersticket,null,false)
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