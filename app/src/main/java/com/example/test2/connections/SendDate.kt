package com.example.test2.connections
import java.io.*
import java.net.Socket

fun SendDate(theName:String,theDate:String,theHour:String):String{
    val connection=Socket("192.168.56.1",10001)
    val toServer = ObjectOutputStream(connection.getOutputStream())
    toServer.writeObject(4)
    toServer.writeObject(theName)
    toServer.writeObject(theDate)
    toServer.writeObject(theHour)
    val fromServer=ObjectInputStream(connection.getInputStream())
    val a=fromServer.readObject()
    connection.close()
    return a.toString()
}