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
import androidx.navigation.Navigation
import com.example.test2.connections.SendFeedBack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Register : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View
        v=inflater.inflate(R.layout.fragment_register, container, false)
        var p:String=""
        val enterName=v.findViewById<EditText>(R.id.etName)
        val enterPassword=v.findViewById<EditText>(R.id.etPassword)
        val enterEMAIL=v.findViewById<EditText>(R.id.etEmail)
        val enterPHONE=v.findViewById<EditText>(R.id.etPhoneNumber)
        val submitBtn=v.findViewById<Button>(R.id.btnSubmit)

        var strname:String=""
        var strpassword:String=""
        var stremail:String=""
        var strphone:String=""
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        enterName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                strname= enterName.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        enterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                strpassword= enterPassword.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        enterEMAIL.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                stremail= enterEMAIL.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        enterPHONE.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                strphone= enterPHONE.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!strphone.matches(emailPattern.toRegex())) {

                }
            }
        })
        submitBtn.setOnClickListener{

            if(strname.equals(""))
                Toast.makeText(activity,"please enter name", Toast.LENGTH_LONG).show()
            else if(strpassword.equals(""))
                Toast.makeText(activity,"please enter password", Toast.LENGTH_LONG).show()
            else if(stremail.equals("")||!stremail.matches(emailPattern.toRegex()))
                Toast.makeText(activity,"please enter correct email", Toast.LENGTH_LONG).show()
            else if(strphone.equals(""))
                Toast.makeText(activity,"please enter phone", Toast.LENGTH_LONG).show()
            else {
                CoroutineScope(Dispatchers.IO).launch {
                    p = SendFeedBack(strname, strpassword, stremail, strphone)
                    withContext(Dispatchers.Main){
                        if(p=="used name")
                            Toast.makeText(activity,"used name", Toast.LENGTH_LONG).show()
                        else if (p=="used email") Toast.makeText(activity,"used email",
                            Toast.LENGTH_LONG).show()
                        else if (p=="used phone") Toast.makeText(activity,"used phone",
                            Toast.LENGTH_LONG).show()
                        else {
                            Toast.makeText(
                                activity, "registered success",
                                Toast.LENGTH_LONG
                            ).show()
                            Navigation.findNavController(v).navigate(R.id.action_register_to_login2)
                        }
                    }
                }
            }
        }



        return v
    }
}