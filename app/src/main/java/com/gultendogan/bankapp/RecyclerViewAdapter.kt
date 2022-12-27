package com.gultendogan.bankapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_row.view.*

class RecyclerViewAdapter(val shareList : ArrayList<Share>) : RecyclerView.Adapter<RecyclerViewAdapter.ShareHolder>(){

    class ShareHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShareHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row,parent,false)
        return ShareHolder(view)
    }

    override fun onBindViewHolder(holder: ShareHolder, position: Int) {
        holder.itemView.recycler_row_kullanici.text = shareList[position].kullanici
        holder.itemView.recycler_row_alici.text = shareList[position].alici
        holder.itemView.recycler_row_miktar.text = shareList[position].miktar + "$"
    }

    override fun getItemCount(): Int {
        return shareList.size
    }


}