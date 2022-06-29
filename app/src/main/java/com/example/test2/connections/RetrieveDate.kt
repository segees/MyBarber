package com.example.test2.connections
import java.io.*
import java.net.Socket

fun RetrieveDate(theDate:String):String{
    val connection=Socket("192.168.56.1",10001)
    val toServer = ObjectOutputStream(connection.getOutputStream())
    toServer.writeObject(3)
    toServer.writeObject(theDate)
    val fromServer=ObjectInputStream(connection.getInputStream())
    val a=fromServer.readObject()
    connection.close()
    return a.toString()
}