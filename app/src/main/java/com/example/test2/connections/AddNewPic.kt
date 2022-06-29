package com.example.test2.connections
import java.io.*
import java.net.Socket

fun addNewPic(name:String,price:String,image: String):String{
    val connection=Socket("192.168.56.1",10001)
    val toServer = ObjectOutputStream(connection.getOutputStream())
    toServer.writeObject(12)
    toServer.writeObject(name)
    toServer.writeObject(price)
    toServer.writeObject(image)
    val fromServer=ObjectInputStream(connection.getInputStream())
    val a=fromServer.readObject()
    connection.close()
    return a.toString()
}
