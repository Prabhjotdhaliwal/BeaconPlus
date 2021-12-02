package com.example.beaconplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.beaconplus.databinding.ActivityMainBinding



class ScanActivity : AppCompatActivity()
{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        val startScan: Button = findViewById(R.id.startScanbtn)
        val stopScan: Button = findViewById(R.id.stopScanbtn)

        startScan.setOnClickListener()
        {

            addFragmentToActivity(StartScanFragment())
        }
stopScan.setOnClickListener()
{

}
    }

    private fun LoadFragment(fragment: Fragment)
    {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
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
}