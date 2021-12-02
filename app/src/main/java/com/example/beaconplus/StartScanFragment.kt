package com.example.beaconplus

import androidx.fragment.app.Fragment
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.bluetooth.BluetoothAdapter
import android.os.Handler
import android.widget.*

class StartScanFragment:Fragment(R.layout.startscanfragment) {
val frg_TAG=1
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newScannedDeviceList: ArrayList<AvailableDevices>
    private val scanResults = mutableListOf<ScanResult>()
    //Set up Bluetooth
    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    private var scanning = false
    private val handler = Handler()
    private val SCAN_PERIOD: Long = 10000

    private lateinit var communicator: Communicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        newRecyclerView = view.findViewById(R.id.recyclerView)

        // Set Recyclerview
        newRecyclerView.layoutManager = LinearLayoutManager(view.context)
        newRecyclerView.setHasFixedSize(true)
        scanBLEdevices()

    }
    private fun onListItemClick(position: Int) {
        Toast.makeText(getActivity(),position,Toast.LENGTH_SHORT).show();

    }
    private fun scanBLEdevices() {
        if (!scanning) { // Stops scanning after a pre-defined scan period.
            handler.postDelayed({
                scanning = false
                bluetoothLeScanner.stopScan(leScanCallback)
                newRecyclerView.adapter?.notifyDataSetChanged();

            }, SCAN_PERIOD)
            scanning = true
            bluetoothLeScanner.startScan(leScanCallback)
        } else {
            scanning = false
            bluetoothLeScanner.stopScan(leScanCallback)
            newRecyclerView.adapter?.notifyDataSetChanged();

        }
    }

    //   private val leDeviceListAdapter = LeDeviceListAdapter()
    // Device scan callback.
    private val leScanCallback: ScanCallback = object : ScanCallback()

    {

        override fun onScanResult(callbackType: Int, result: ScanResult)
        {
            super.onScanResult(callbackType, result)
            newScannedDeviceList = arrayListOf<AvailableDevices>()
            DevicesTv.setText("Scanned Devices")
            val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
            if (indexQuery != -1)
            {
                // A scan result already exists with the same address
                scanResults[indexQuery] = result
                newRecyclerView.adapter?.notifyItemChanged(indexQuery)
            }
            else
            {
                with(result.device)
                {
                    println("Found BLE device! Name: ${name ?: "Unnamed"},address: $address")
                    scanResults.add(result)
                    val adapter=ScannedDeviceAdapter(scanResults)
                    newRecyclerView.adapter=adapter
                    adapter.notifyDataSetChanged()
                    adapter.setOnItemClickListener(object :ScannedDeviceAdapter.onItemClickListener
                    {
                        override fun onItemClick(position: Int) {
Toast.makeText(activity,"You have clicked $position",Toast.LENGTH_SHORT).show()

                            communicator=activity as Communicator
if (result.device.name==null)

{
    communicator.transferData("Unnamed",result.device.address,result.rssi)

}
else
{
    communicator.transferData(result.device.name,result.device.address,result.rssi)

}

                        }
                    })
                  //  newRecyclerView.adapter = ScannedDeviceAdapter(scanResults)

                }
            }

        }
    }

}
