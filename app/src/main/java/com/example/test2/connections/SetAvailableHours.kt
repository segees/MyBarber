package com.example.test2.connections
import java.io.*
import java.net.Socket

fun SetAvailableHours(starHour:String,endHour:String):String{
    val connection=Socket("192.168.56.1",10001)
    val toServer = ObjectOutputStream(connection.getOutputStream())
    toServer.writeObject(14)
    toServer.writeObject(starHour)
    toServer.writeObject(endHour)
    val fromServer=ObjectInputStream(connection.getInputStream())
    val a=fromServer.readObject()
    connection.close()
    return a.toString()
}