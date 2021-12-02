package com.example.beaconplus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class DeviceConnection:Fragment() {
    var name: String? = ""
    var address: String? = ""
    var rssi: Int? = null
    private lateinit var Devicenametxt: TextView
    private lateinit var Deviceaddresstxt: TextView
    private lateinit var DeviceRssitxt: TextView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.connectdevicefragment, container, false)
        Devicenametxt=view.findViewById(R.id.name1txt)
        Deviceaddresstxt=view.findViewById(R.id.address1txt)
        DeviceRssitxt=view.findViewById(R.id.rssi1txt)

        name = arguments?.getString("devicename")
        address = arguments?.getString("deviceaddress")
        rssi = arguments?.getInt("deviceRssi")

        Devicenametxt.text="Device name: "+name
        Deviceaddresstxt.text="Device address: "+address
        DeviceRssitxt.text="Rssi: "+rssi.toString()+"dBm"
        println("Name$name,address$address,rssi$rssi")

        return view
    }

}