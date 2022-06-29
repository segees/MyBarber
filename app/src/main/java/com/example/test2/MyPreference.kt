package com.example.test2
import android.content.Context

class MyPreference(context:Context) {
    val prefName="sharedName"
    val prefPass="sharedPass"
    val preference=context.getSharedPreferences(prefName,Context.MODE_PRIVATE)

    fun getLoginC():String{
        return preference.getString(prefName,"").toString()
    }
    fun setLoginC(count:String){
        val editor=preference.edit()
        editor.putString(prefName,count)
        editor.apply()
    }
    fun getPassC():String{
        return preference.getString(prefPass,"").toString()
    }
    fun setPassC(count:String){
        val editor=preference.edit()
        editor.putString(prefPass,count)
        editor.apply()
    }
    fun deletePref(){
        val editor=preference.edit()
        editor.putString(prefPass,"")
        editor.putString(prefName,"")
        editor.apply()
    }
}