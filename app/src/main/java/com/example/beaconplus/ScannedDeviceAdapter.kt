package com.example.beaconplus

import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScannedDeviceAdapter(
    private val items: MutableList<ScanResult>
) : RecyclerView.Adapter<ScannedDeviceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScannedDeviceAdapter.ViewHolder
    {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_items,parent,false)
        return ScannedDeviceAdapter.ViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
       /// holder.DeviceName.text=item.DeviceName
      //  holder.DeviceAddress.text=item.Deviceaddress
      //  holder.DeviceRssi.text= item.DeviceRssi.toString()

       holder.bind(item)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
        val DeviceName: TextView =itemView.findViewById(R.id.deviceName11)
        val DeviceAddress: TextView =itemView.findViewById(R.id.deviceAddress11)
        val DeviceRssi: TextView =itemView.findViewById(R.id.deviceAddress11)


        fun bind(result: ScanResult)
        {
//
            val DeviceName: TextView =itemView.findViewById(R.id.deviceName11)
            val DeviceAddress: TextView =itemView.findViewById(R.id.deviceAddress11)
            val DeviceRssi: TextView =itemView.findViewById(R.id.devicerssi1)
           val rssi= result.rssi
           DeviceRssi.text=rssi.toString()
           DeviceName.text = result.device.name ?: "Unnamed"
            DeviceAddress.text = result.device.address
            }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

    }

    }


