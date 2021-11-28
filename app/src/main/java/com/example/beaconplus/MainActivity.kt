package com.example.beaconplus

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.bluetooth.le.ScanRecord





lateinit var bltImage:ImageView
lateinit var DevicesTv:TextView
private val REQUEST_REQUEST_ENABLE_BT:Int = 1
private val REQUEST_CODE_DISCOVERABLE_BT =2
private val LOCATION_PERMISSION_REQUEST_CODE=3

class MainActivity : AppCompatActivity() {
    //  private var layoutManager:RecyclerView.LayoutManager?= null
    // private  var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>?=null


    //Set up Bluetooth
    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    private var scanning = false
    private val handler = Handler()
    private val SCAN_PERIOD: Long = 10000

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newPairedDeviceList: ArrayList<PairedDevice>
    private lateinit var newScannedDeviceList: ArrayList<AvailableDevices>
    private lateinit var DiscoverdDeviceList: ArrayList<String>
    private val ScannedDevices: MutableList<String> = ArrayList()
    private val scanResults = mutableListOf<ScanResult>()

    lateinit var imageId: Array<Int>
    lateinit var DeviceName: Array<String>
    lateinit var DeviceAddress: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//Bluetooth Buttons
        val TurnOnbutton: Button = findViewById(R.id.turnonbtn)
        val TurnOffBltBtn: Button = findViewById(R.id.turnoffbtn)
        val DiscoverDeviceBtn: Button = findViewById(R.id.discoverbtn)
        val ScanDevices: Button = findViewById(R.id.scanDevice)
        val showPairedDevices: Button = findViewById(R.id.showpaireddevicesbtn)


        val pgsBar: ProgressBar  = findViewById(R.id.pBar);
pgsBar.setVisibility(GONE)
        DevicesTv = findViewById(R.id.DevicesTv)
        bltImage = findViewById(R.id.bltImg)
        newRecyclerView = findViewById(R.id.recyclerView)


        if (packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) == null) {
            //Device doesn't support Bluetooth
            Toast.makeText(this, "Your Device doesn't support Bluetooth ", Toast.LENGTH_SHORT)
                .show()
        }


//Check location permissions

        requestLocationPermission()

        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        // registerReceiver(receiver, filter)

//Data for RecyclerView
        imageId = arrayOf(
            R.drawable.ic_baseline_bluetooth_24,
            R.drawable.ic_baseline_bluetooth_24,
        )
        //R.drawable.ic_baseline_bluetooth_24,
        // R.drawable.ic_baseline_bluetooth_24,
        // R.drawable.ic_baseline_bluetooth_24
        // DeviceName= arrayOf("Device1","Device2","Device3","Device4","Device5")
        // DeviceAddress= arrayOf("DeviceAddress1","DeviceAddress2","DeviceAddress3","DeviceAddress4","DeviceAddress5")

        // Set Recyclerview
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        //  getDeviceData()


//Turn On Bluetooth
        TurnOnbutton.setOnClickListener {
            if (bluetoothAdapter?.isEnabled == true) {
                Toast.makeText(this, "Bluetooth is already turned on", Toast.LENGTH_SHORT).show()

            } else {
                showBltDialog()

            }


            //to check bluetooth is enabled or disabled on your device

            /*
*/

        }

        //tunoffBluetooth
        TurnOffBltBtn.setOnClickListener()
        {
            turnOffBlt()
        }


        //Discover Devices
        DiscoverDeviceBtn.setOnClickListener()
        {
            makeDiscoverable()


        }

        ScanDevices.setOnClickListener()
        {

           scanBLEdevices()


        //Second way to discover bluetooth devices


         /*  val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            registerReceiver(receiver, filter)
            bluetoothAdapter.startDiscovery()
*/
            //to up to an another activity
            // val intent = Intent(this, DevicesActivity::class.java)
            //  startActivity(intent)
        }

        showPairedDevices.setOnClickListener()
        {
            Toast.makeText(this, "Paired Devices", Toast.LENGTH_LONG).show()
            getpairedDevices()

        }


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

         // leDeviceListAdapter.addDevice(result.device)
         //  leDeviceListAdapter.notifyDataSetChanged()

         DevicesTv.setText("Scanned Devices")
         val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
         if (indexQuery != -1)
         {
             // A scan result already exists with the same address
             scanResults[indexQuery] = result
             //   scanResultAdapter.notifyItemChanged(indexQuery)
         }
         else
         {
             with(result.device)
             {
                 var rssi = result.rssi
                 println(rssi)
                 println("Found BLE device! Name: ${name ?: "Unnamed"},address: $address")
                 scanResults.add(result)

                 var devicename1=result.device.name
                 var address=result.device.address
                 var rssii=result.rssi
                 if (result.device.name==null)
                 {
                     devicename1="Unnamed"
                 }

          /*      for(i in scanResults)
                 {
                    val Device = AvailableDevices(address,devicename1,rssii)
                     newScannedDeviceList.add(Device)

               }
*/
               //  println(newScannedDeviceList)
               val Device = AvailableDevices(address,devicename1,rssii)
                    newScannedDeviceList.add(Device)

             newRecyclerView.adapter = ScannedDeviceAdapter(scanResults)

                // newRecyclerView.adapter = RecyclerAdapter(newScannedDeviceList)
                 newRecyclerView.adapter?.notifyDataSetChanged()

             }
         }

     }
 }
    private val startScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            newScannedDeviceList = arrayListOf<AvailableDevices>()

            val device = result.device
            val name=result.device.name
            val mScanRecord = result.scanRecord
            val address = device.address
            val content = mScanRecord!!.bytes
            val mRssi = result.rssi
            val mRssi1 = Integer.toString(mRssi)
          //  val ContentMessage: String = ByteArrayToHexString(content)
            if (address == null || address.trim { it <= ' ' }.length == 0) {
               // mResultAdapter.addbth(address,name, mRssi1,)
                val Device = AvailableDevices(address,name,mRssi)
                newScannedDeviceList.add(Device)
                newRecyclerView.adapter = RecyclerAdapter(newScannedDeviceList)
                 newRecyclerView.adapter?.notifyDataSetChanged()
                //public void addbth(String mac,String rssi, String content)
                //mResultAdapter.notifyDataSetChanged()
                return
            }
        }
    }
     private fun requestLocationPermission()
     {
         // Check Location permissions
         if (ContextCompat.checkSelfPermission(
                 this@MainActivity,
                 Manifest.permission.ACCESS_FINE_LOCATION
             ) !==
             PackageManager.PERMISSION_GRANTED
         ) {
             if (ActivityCompat.shouldShowRequestPermissionRationale(
                     this@MainActivity,
                     Manifest.permission.ACCESS_FINE_LOCATION
                 )
             ) {
                 ActivityCompat.requestPermissions(
                     this@MainActivity,
                     arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                 )
             } else {
                 ActivityCompat.requestPermissions(
                     this@MainActivity,
                     arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
                 )
             }

         }
     }



     /*  private fun scanBLEdevices()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            requestLocationPermission()
        } else {
            scanResults.clear()
          //  bleScanner.startScan(null, scanSettings, scanCallback)
            isScanning = true
        }
    }

    private fun startBleScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            requestLocationPermission()
        } else {
            scanResults.clear()
            //bleScanner.startScan(null, scanSettings, scanCallback)
            isScanning = true
        }
    }

    private fun stopBleScan() {
        bleScanner.stopScan(scanCallback)
        isScanning = false
    }

    private  val scanCallback:ScanCallback by lazy {


        object :ScanCallback()
        {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                //  super.onScanResult(callbackType, result)
                println("onScanResult")

                val bluetoothDevice=result?.device
                if (bluetoothDevice!=null)
                {

                    println("Founder devices")
                    println("Devicename ${bluetoothDevice.name} Device Address ${bluetoothDevice.uuids}")
                }
            }
        }
    }*/
     override fun onResume() {
         super.onResume()
         if (!bluetoothAdapter.isEnabled) {
             showBltDialog()
         }
     }

     //Activity method
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

         when (requestCode) {
             REQUEST_REQUEST_ENABLE_BT ->
                 if (resultCode == Activity.RESULT_OK) {
                     showBltDialog()
                 } else {
                     Toast.makeText(this, "Couldn't turnd on Bluetooth", Toast.LENGTH_SHORT).show()
                 }

         }
         super.onActivityResult(requestCode, resultCode, data)
     }


     override fun onRequestPermissionsResult(
         requestCode: Int, permissions: Array<String>,
         grantResults: IntArray
     ) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         when (requestCode) {
             1 -> {
                 if (grantResults.isNotEmpty() && grantResults[0] ==
                     PackageManager.PERMISSION_GRANTED
                 ) {
                     if ((ContextCompat.checkSelfPermission(
                             this@MainActivity,
                             Manifest.permission.ACCESS_FINE_LOCATION
                         ) ===
                                 PackageManager.PERMISSION_GRANTED)
                     ) {
                         Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                     }
                 } else {
                     Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                 }
                 return
             }
         }

     }

     private fun locationPermissionRequest() {
         TODO("Not yet implemented")
     }

     /*
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog

            dialogBuilder.setMessage("Starting from Android M (6.0), the system requires apps to be granted \" +\n" +
                    "                    \"location access in order to scan for BLE devices").setTitle("Location permission required")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id -> finish()
                })
                // negative button text and action
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("AlertDialogExample")
            // show alert dialog
            alert.show()
        }
    }*/


     /*  private fun scanBLEdevices() {
        val scanFilter = ScanFilter.Builder().build()

        val scanFilters: MutableList<ScanFilter> = mutableListOf()
        scanFilters.add(scanFilter)

        val scanSettings =
            ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build()
 bluetoothAdapter.bluetoothLeScanner.startScan(scanFilters,scanSettings,bleScanCallbacl)
    }

private  val bleScanCallbacl:ScanCallback by lazy {


        object :ScanCallback()
        {
            override fun onScanResult(callbackType: Int, result: ScanResult?) {
              //  super.onScanResult(callbackType, result)
           println("onScanResult")

                val bluetoothDevice=result?.device
                if (bluetoothDevice!=null)
                {

                    println("Founder devices")
                    println("Devicename ${bluetoothDevice.name} Device Address ${bluetoothDevice.uuids}")
                }
            }
        }
        }

*/
     override fun onStart() {
         super.onStart()
     }

     /*  private fun getDeviceData() {
        for (i in imageId.indices) {
            val Device = Device(DeviceName[i], DeviceAddress[i])
            newPairedDeviceList.add(Device)
        }
        newRecyclerView.adapter = MyAdapter(newPairedDeviceList)

    }*/

     private fun showBltDialog() {

         if (bluetoothAdapter?.isEnabled == false) {
             val enableblthIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
             startActivityForResult(enableblthIntent, REQUEST_REQUEST_ENABLE_BT)

             //  bltImage.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24)

         }
     }

     private fun turnOffBlt() {
         if (!bluetoothAdapter.isEnabled) {
             Toast.makeText(this, "Bluetooth is already Off", Toast.LENGTH_LONG).show()

         } else {
             bluetoothAdapter.disable()
             bltImage.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)
             Toast.makeText(this, "Turning OFF Bluetooth", Toast.LENGTH_LONG).show()

         }

     }


     private fun makeDiscoverable() {
         //Discover Bluetooth Devices (Unpaired devices)
         if (bluetoothAdapter.isEnabled) {
             if (!bluetoothAdapter.isDiscovering) {
                 bluetoothAdapter.startDiscovery()

                 Toast.makeText(this, "Making Your Device Discoverable", Toast.LENGTH_LONG).show()
                 val discoverIntent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                 discoverIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
                 startActivityForResult(discoverIntent, REQUEST_CODE_DISCOVERABLE_BT)

             }
         } else
             if (bluetoothAdapter.disable()) {
                 Toast.makeText(this, "Bluetooth must be turned On", Toast.LENGTH_LONG).show()

             }
     }
     //  Create a BroadcastReceiver for ActionFound
     val receiver = object : BroadcastReceiver() {
         override fun onReceive(context: Context?, intent: Intent) {
             val action: String? = intent.action
             when (action) {
                 BluetoothDevice.ACTION_FOUND -> {
                     //a device is  found ,  Now get this device,object and info from the intent
                     // bluetoothAdapter.startDiscovery()
                     val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                     val deviceName = device?.name
                     val deviceHardwareAddress = device?.address // Physical address of the device *MAC address
                     Toast.makeText(this@MainActivity,"Found device",Toast.LENGTH_LONG).show()
                     System.out.println(device)
                     System.out.println(deviceName)
                     System.out.println(deviceHardwareAddress)
                 }
             }
         }
     }

     override fun onDestroy()
     {
         super.onDestroy()
         // Don't forget to unregister the ACTION_FOUND receiver.
        //  unregisterReceiver(receiver)
     }

     //Paired Devices
     private fun getpairedDevices()
     {
         newPairedDeviceList = arrayListOf<PairedDevice>()

         if (bluetoothAdapter.isEnabled)
         {
             DevicesTv.setText("Paired Devices")
             val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
             pairedDevices?.forEach { device ->

                 var deviceName = device.name //name of the bluetooth device
                 val deviceHardwareAddress = device.address //Mac address of the bluetooth device

                 System.out.println(device)
                 System.out.println(deviceName)
                 System.out.println(deviceHardwareAddress)
                 if (device.name==null)
                 {
                     deviceName="Unnamed"
                 }
                 val Device = PairedDevice(deviceName, deviceHardwareAddress)
                 newPairedDeviceList.add(Device)
             }
             newRecyclerView.adapter?.notifyDataSetChanged()
             newRecyclerView.adapter = MyAdapter(newPairedDeviceList)
         }
         else
         {
             Toast.makeText(this, " First Turn On Bluetooth", Toast.LENGTH_LONG).show()
         }
     }
 }
