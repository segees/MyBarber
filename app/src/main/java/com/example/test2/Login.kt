package com.example.test2
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.test2.connections.RetrieveFeedTask
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class Login : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View
        val myPreference=MyPreference(requireContext())
        var loginCount=myPreference.getLoginC()
        var passCount=myPreference.getPassC()
        v=inflater.inflate(R.layout.fragment_login, container, false)
        val enterName=v.findViewById<EditText>(R.id.etLoginUsername)
        val enterPassword=v.findViewById<EditText>(R.id.etLoginPassword)
        val login=v.findViewById<Button>(R.id.btnSubmit)
        val register=v.findViewById<Button>(R.id.btnRegister)
        var name:String = enterName.text.toString()
        var password:String = enterPassword.text.toString()
        var check:String=""
        if(loginCount!="") {
            enterName.setText(loginCount)
            name=loginCount
            enterPassword.setText(passCount)
            password=passCount
        }
        enterName.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                name= enterName.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        enterPassword.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                password= enterPassword.text.toString() }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        login.setOnClickListener() {
            CoroutineScope(IO).async {
                check= RetrieveFeedTask(name, password)
                withContext(Dispatchers.Main){
                    if(check=="username or password are not correct")
                        Toast.makeText(activity,check, Toast.LENGTH_LONG).show()
                    else if(check=="admin") {
                        myPreference.setLoginC(name)
                        myPreference.setPassC(password)
                        Navigation.findNavController(v).navigate(R.id.action_login2_to_adminFragmentMenu)
                    }
                        else {
                        myPreference.setLoginC(name)
                        myPreference.setPassC(password)
                        Navigation.findNavController(v).navigate(R.id.action_login2_to_menu)
                    }
                }
            }
        }
        register.setOnClickListener{
            Navigation.findNavController(v).navigate(R.id.action_login2_to_register)
        }
        return v
    }

}