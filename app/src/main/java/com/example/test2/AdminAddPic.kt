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
import com.example.test2.connections.addNewPic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class AdminAddPic : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var check:String=""
        val v:View=inflater.inflate(R.layout.fragment_admin_add_pic, container, false)
        val addName=v.findViewById<EditText>(R.id.addName)
        val addPrice=v.findViewById<EditText>(R.id.addPrice)
        val addImage=v.findViewById<EditText>(R.id.addImage)
        val addAllBtn=v.findViewById<Button>(R.id.addBtn)
        var tempName:String=""
        var tempPrice:String=""
        var tempImage:String=""
        addName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                tempName= addName.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        addPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                tempPrice= addPrice.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        addImage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                tempImage= addImage.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        addAllBtn.setOnClickListener() {
            CoroutineScope(Dispatchers.IO).async {
                check= addNewPic(tempName, tempPrice,tempImage)
                withContext(Dispatchers.Main){
                    Toast.makeText(activity,check, Toast.LENGTH_LONG).show()
                }
            }
        }
        return v
    }
}