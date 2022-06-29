package com.example.test2
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.test2.connections.DeleteAppData
import com.example.test2.connections.ReceiveAppData
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main


class Menu : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myPreference=MyPreference(requireContext())
        val v:View=inflater.inflate(R.layout.fragment_menu, container, false)
        val newAppBtn=v.findViewById<Button>(R.id.newAppBtn)
        val updateAppBtn=v.findViewById<Button>(R.id.updateAppBtn)
        val navBtn=v.findViewById<Button>(R.id.navBtn)
        val nameText=v.findViewById<TextView>(R.id.appName)
        val dateText=v.findViewById<TextView>(R.id.appDate)
        val dataLayout=v.findViewById<FrameLayout>(R.id.theOneApp)
        var check:String=""
        val myPref:String=myPreference.getLoginC()
        CoroutineScope(Dispatchers.IO).launch {
            check = ReceiveAppData(myPref)
            withContext(Main) {
               if(check!=""){
                   check=check.replace("!","")
                   newAppBtn.visibility=View.GONE
                   updateAppBtn.visibility=View.VISIBLE
                   dateText.text=check
                   nameText.text=myPref
                   dataLayout.visibility=View.VISIBLE
               }
                else {
                   newAppBtn.visibility = View.VISIBLE
                   updateAppBtn.visibility=View.GONE
                   dataLayout.visibility=View.GONE
               }
            }
        }
        newAppBtn.setOnClickListener{
            Navigation.findNavController(v).navigate(R.id.action_menu_to_newApp)
        }
        updateAppBtn.setOnClickListener{
            CoroutineScope(Dispatchers.Default).async {
                DeleteAppData(myPref)
                withContext(Dispatchers.Main) {
                    newAppBtn.visibility = View.VISIBLE
                    updateAppBtn.visibility=View.GONE
                    dataLayout.visibility=View.GONE
                }
            }
        }
        navBtn.setOnClickListener {
            activity?.let {
                val intent = Intent(it, MapsActivity::class.java)
                it.startActivity(intent)
            }
        }
        return v
    }
}