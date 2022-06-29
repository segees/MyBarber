package com.example.test2
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class AdminFragmentMenu : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View=inflater.inflate(R.layout.fragment_admin_menu, container, false)
        val updateAppAdminBtn=v.findViewById<Button>(R.id.adminUpdateApp)
        val updatePicsAdminBtn=v.findViewById<Button>(R.id.adminUpdatePic)
        val addPicAdminBtn=v.findViewById<Button>(R.id.adminAddPic)
        val updateHours=v.findViewById<Button>(R.id.adminUpdateHour)
        updateAppAdminBtn.setOnClickListener{
            Navigation.findNavController(v).navigate(R.id.action_adminFragmentMenu_to_adminAppUpdateMenu)
        }
        updatePicsAdminBtn.setOnClickListener{
            Navigation.findNavController(v).navigate(R.id.action_adminFragmentMenu_to_adminPicsUpdate)
        }
        addPicAdminBtn.setOnClickListener{
            Navigation.findNavController(v).navigate(R.id.action_adminFragmentMenu_to_adminAddPic3)
        }
        updateHours.setOnClickListener{
            Navigation.findNavController(v).navigate(R.id.action_adminFragmentMenu_to_adminUpdateHours)
        }
        return v
    }
}