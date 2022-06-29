package com.example.test2
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.test2.connections.SetAvailableHours
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class AdminUpdateHours : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View=inflater.inflate(R.layout.fragment_admin_update_hours, container, false)
        val starHour=v.findViewById<EditText>(R.id.startHour)
        val endHour=v.findViewById<EditText>(R.id.endHour)
        val updateHoursBtn=v.findViewById<Button>(R.id.updateHourBtn)
        var tempStart:String=""
        var tempEnd:String=""
        var check:String=""
        starHour.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                tempStart= starHour.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        endHour.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                tempEnd= endHour.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        updateHoursBtn.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).async {
                check= SetAvailableHours(tempStart, tempEnd)
                withContext(Dispatchers.Main){
                    Toast.makeText(activity,check, Toast.LENGTH_LONG).show()
                }
            }
        }
        return v
    }
}