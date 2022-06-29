package com.example.test2.connections
import java.io.*
import java.net.Socket
fun SendFeedBack (newName:String,newPassword:String,newEmail:String,newPhone:String):String {
    val connection=Socket("192.168.56.1",10001)
    val toServer = ObjectOutputStream(connection.getOutputStream())
    toServer.writeObject(1)
    toServer.writeObject(newName)
    toServer.writeObject(newPassword)
    toServer.writeObject(newEmail)
    toServer.writeObject(newPhone)
    val fromServer=ObjectInputStream(connection.getInputStream())
    val a=fromServer.readObject()
    connection.close()
    return a.toString()
}