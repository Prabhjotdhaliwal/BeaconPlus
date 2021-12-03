package com.example.beaconplus

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class ScanActivity : AppCompatActivity(),Communicator
{


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        val startScan: Button = findViewById(R.id.startScanbtn)
        val stopScan: Button = findViewById(R.id.stopScanbtn)


        newRecyclerView = findViewById(R.id.recyclerView)

        // Set Recyclerview
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        startScan.setOnClickListener()
        {

            scanBLEdevices()
        }
stopScan.setOnClickListener()
{

}
    }

    private fun onListItemClick(position: Int) {
        Toast.makeText(this,position, Toast.LENGTH_SHORT).show();

    }
    private fun LoadFragment(fragment: Fragment)
    {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
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
                            Toast.makeText(this@ScanActivity,"You have clicked $position",Toast.LENGTH_SHORT).show()
                           // addFragmentToActivity(DeviceConnection())
                        }
                    })
                    //  newRecyclerView.adapter = ScannedDeviceAdapter(scanResults)

                }
            }

        }
    }


override fun onBackPressed() {
        super.onBackPressed()
    }
    private fun addFragmentToActivity(fragment: Fragment?){

        if (fragment == null) return
        val fm = supportFragmentManager
        val tr = fm.beginTransaction()
        tr.add(R.id.fragment_container, fragment)
        tr.attach(fragment)
        tr.commit()


    }

    override fun transferData(DeviceName: String, DeviceAddress: String, DeviceRssi: Int) {
        val bundle=Bundle()
        bundle.putString("devicename",DeviceName)
        bundle.putString("deviceaddress",DeviceAddress)
        bundle.putInt("deviceRssi",DeviceRssi)
        val transaction=this.supportFragmentManager.beginTransaction()
        val ConnectionFragment=DeviceConnection()
        ConnectionFragment.arguments=bundle
        transaction.replace(R.id.fragment_container,ConnectionFragment)
        transaction.commit()

    }
}