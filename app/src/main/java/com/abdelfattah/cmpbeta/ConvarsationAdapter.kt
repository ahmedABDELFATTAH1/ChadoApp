package com.abdelfattah.cmpbeta

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
class ConvarsationAdapter (val context: Context, val list:ArrayList<Texts>) :
        RecyclerView.Adapter<ConvarsationAdapter .ViewHolder>() {
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.text!!.text=list[p1].Text
        Picasso.get().load(list[p1].imageurl).into(p0.image)

    }

    class ViewHolder : RecyclerView.ViewHolder
    {
        var text: TextView? = null
        var image: CircleImageView?= null
        constructor(v: View):super(v)
        {
            text=v.findViewById(R.id.namee)
            image=v.findViewById(R.id.photourlezz)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ConvarsationAdapter .ViewHolder {

        var myview= LayoutInflater.from(context).inflate(R.layout.sender,null,false)
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