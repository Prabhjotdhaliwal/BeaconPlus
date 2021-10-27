package com.example.beaconplus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    data1: ArrayList<String>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>()
{

    private  var DeviceName1= arrayOf("bluetoothdevice1","bluetoothdevice2","bluetoothdevice3","bluetoothdevice4","bluetoothdevice5")
    private  var DeviceAddress1= arrayOf("Deviceaddress1","Deviceaddress3","Deviceaddress4","Deviceaddress5","bluetoothdevice5")
     private var Images= arrayListOf(R.drawable.ic_baseline_bluetooth_connected_24,R.drawable.ic_baseline_bluetooth_connected_24,R.drawable.ic_baseline_bluetooth_connected_24,R.drawable.ic_baseline_bluetooth_connected_24,R.drawable.ic_baseline_bluetooth_connected_24)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_items,parent,false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {

     //   holder.itemName.text=DeviceName1[position]
      //holder.itemAddress.text=DeviceAddress1[position]
     // holder.itemImage.setImageResource(Images[position])

    }

    override fun getItemCount(): Int {
return DeviceName1.size
    }

  inner  class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView),View.OnClickListener{
//var itemImage:ImageView
//var itemName:TextView
//var itemAddress:TextView

init {
   // itemImage=itemView.findViewById(R.id.DeviceImage)
   // itemName=itemView.findViewById(R.id.DeviceName)
 //   itemAddress=itemView.findViewById(R.id.DeviceAddress)

}



        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

    }
}

