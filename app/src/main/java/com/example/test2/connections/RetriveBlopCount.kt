package com.example.test2.connections
import java.io.*
import java.net.Socket

fun RetrieveBlopCount():String{
    val connection=Socket("192.168.56.1",10001)
    val toServer = ObjectOutputStream(connection.getOutputStream())
    toServer.writeObject(5)
    val fromServer=ObjectInputStream(connection.getInputStream())
    val a=fromServer.readObject()
    connection.close()
    return a.toString()
}