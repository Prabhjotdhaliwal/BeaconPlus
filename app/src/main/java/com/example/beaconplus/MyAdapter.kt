package com.example.beaconplus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class MyAdapter(private val DeviceList:ArrayList<PairedDevice>):
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_items,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem=DeviceList[position]
     //   holder.DeviceImage.setImageResource(currentItem.DeviceImage)
        holder.DeviceName.text=currentItem.DeviceName
        holder.DeviceAddress.text=currentItem.Deviceaddress
    }

    override fun getItemCount(): Int
    {
      return DeviceList.size
    }


    class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)
    {
       // val  DeviceImage:ImageView=itemView.findViewById(R.id.deviceimg11)
        val DeviceName:TextView=itemView.findViewById(R.id.deviceName11)
        val DeviceAddress:TextView=itemView.findViewById(R.id.deviceAddress11)
    }
}