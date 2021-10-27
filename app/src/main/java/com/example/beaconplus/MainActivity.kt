package com.example.beaconplus

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import androidx.appcompat.app.AppCompatActivity.RESULT_OK as RESULT_OK1
lateinit var bluetoothAdapter:BluetoothAdapter
lateinit var bltImage:ImageView
lateinit var DevicesTv:TextView
private val REQUEST_REQUEST_ENABLE_BT:Int = 1
private val REQUEST_CODE_DISCOVERABLE_BT =2

class MainActivity : AppCompatActivity() {
  //  private var layoutManager:RecyclerView.LayoutManager?= null
   // private  var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>?=null

    private  lateinit var newRecyclerView:RecyclerView
    private lateinit var newDeviceList:ArrayList<Device>
    lateinit var imageId:Array<Int>
    lateinit var DeviceName:Array<String>
    lateinit var DeviceAddress:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val TurnOnbutton: Button = findViewById(R.id.turnonbtn)
        val TurnOffBltBtn: Button = findViewById(R.id.turnoffbtn)
        val DiscoverDeviceBtn: Button = findViewById(R.id.discoverbtn)
        val showPairedDevices: Button = findViewById(R.id.showdevicesbtn)
        DevicesTv= findViewById(R.id.DevicesTv)
        bltImage = findViewById(R.id.bltImg)
        newRecyclerView=findViewById(R.id.recyclerView)

        //Set up Bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) == null) {
            //Device doesn't support Bluetooth
            Toast.makeText(this, "Your Device doesn't support Bluetooth ", Toast.LENGTH_SHORT)
                .show()
        }







//Data for RecyclerView
   imageId= arrayOf(R.drawable.ic_baseline_bluetooth_24,
       R.drawable.ic_baseline_bluetooth_24,
      )
        //R.drawable.ic_baseline_bluetooth_24,
       // R.drawable.ic_baseline_bluetooth_24,
       // R.drawable.ic_baseline_bluetooth_24
       // DeviceName= arrayOf("Device1","Device2","Device3","Device4","Device5")
       // DeviceAddress= arrayOf("DeviceAddress1","DeviceAddress2","DeviceAddress3","DeviceAddress4","DeviceAddress5")

       // Set Recyclerview
        newRecyclerView.layoutManager=LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newDeviceList= arrayListOf<Device>()
      //  getDeviceData()






//Turn On Bluetooth
        TurnOnbutton.setOnClickListener {
            if (bluetoothAdapter?.isEnabled==true)
            {
                Toast.makeText(this, "Bluetooth is already turned on", Toast.LENGTH_SHORT).show()

            }
        else
            {
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

          discoverBltDevices()

        }

        showPairedDevices.setOnClickListener()
        {
            Toast.makeText(this, "Paired Devices", Toast.LENGTH_LONG).show()
            getpairedDevices()

        }
    }


    private fun getDeviceData()
    {
        for (i in imageId.indices)
        {
            val Device = Device( DeviceName[i], DeviceAddress[i])
            newDeviceList.add(Device)
        }
        newRecyclerView.adapter=MyAdapter(newDeviceList)

    }
    private fun showBltDialog()
    {

        if (bluetoothAdapter?.isEnabled == false)
        {
            val enableblthIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableblthIntent, REQUEST_REQUEST_ENABLE_BT)

            //  bltImage.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24)

        }
    }
    private fun turnOffBlt() {
        if(!bluetoothAdapter.isEnabled)
        {
            Toast.makeText(this, "Bluetooth is already Off", Toast.LENGTH_LONG).show()

        }
        else
        {
            bluetoothAdapter.disable()
            bltImage.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)
            Toast.makeText(this, "Turning OFF Bluetooth", Toast.LENGTH_LONG).show()

        }

    }

    private fun discoverBltDevices() {
   //Discover Bluetooth Devices
        if(bluetoothAdapter.isDiscovering)
        {
            if(!bluetoothAdapter.isDiscovering)
            {
                Toast.makeText(this, "Making Your Device Discoverable", Toast.LENGTH_LONG).show()
val  discoverIntent= Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(discoverIntent,REQUEST_CODE_DISCOVERABLE_BT)
            }
        }
    //Create a BroadcastReceiver for ActionFound
      /*  val receiver=object :BroadcastReceiver()
        {
            override fun onReceive(context: Context?, intent: Intent) {
                val action: String? =intent.action
                when(action)
                {
                    BluetoothDevice.ACTION_FOUND -> {
                        //a device is found found ,  Now ge this device,object and infor from the intent\
                        val  device: BluetoothDevice? =intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        val deviceName= device?.name
                        val deviceHardwareAddress =device?.address // Physical address of the device *MAC address
                        System.out.println(device)
                        System.out.println(deviceName)
                        System.out.println(deviceHardwareAddress)
                    } } }
        }

        //To Register for broadcasts when a device is discoverable
        val filter= IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver,filter)
        Toast.makeText(this, "Searching for Bluetooth devices", Toast.LENGTH_LONG).show()
   */
    }

    //Paired Devices
    private fun getpairedDevices()
    {
        if(bluetoothAdapter.isEnabled) {
             DevicesTv.setText("Paired Devices")
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
            pairedDevices?.forEach { device ->

                val deviceName = device.name //name of the bluetooth device
                val deviceHardwareAddress = device.address //Mac address of the bluetooth device

                  System.out.println(device)
                  System.out.println(deviceName)
                  System.out.println(deviceHardwareAddress)

                //DeviceName= arrayOf(deviceName)
              //  DeviceAddress= arrayOf(deviceHardwareAddress)


                    val Device = Device(deviceName,deviceHardwareAddress)
                    newDeviceList.add(Device)

            }
            newRecyclerView.adapter=MyAdapter(newDeviceList)

        }
            else
            {
                Toast.makeText(this, " First Turn On Bluetooth", Toast.LENGTH_LONG).show()




}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when(requestCode) {
            REQUEST_REQUEST_ENABLE_BT ->
                if (resultCode == Activity.RESULT_OK) {
                    bltImage.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24)
                    Toast.makeText(this, "Bluetooth is On", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Couldn't turnd on Bluetooth", Toast.LENGTH_SHORT).show()
                }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}




    /* override fun onClick(v: View?) {
         when(v?.id)
         {
             R.id.turnonbtn->
             {               Toast.makeText(this,"Bluetooth turned on",Toast.LENGTH_LONG).show()
             }

           //  R.id.turnOffBtn-> println("Two")
            // R.id.DiscoverBtn-> println("Three")
            // R.id.ShowPairedBtn-> println("Four")
             else
             -> print("Nothing is clicked")
         }    }  */



//  //Set up image according to enable oo disable bluetooth
//                    if (bluetoothAdapter?.isEnabled==true)
//                    {
//                        bltImage.setImageResource(R.drawable.ic_baseline_bluetooth_connected_24)
//                    }
//                    bltImage.setImageResource(R.drawable.ic_baseline_bluetooth_disabled_24)






