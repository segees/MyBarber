import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.HashMap;

public class socketHandler extends Thread {
	Socket incoming;
	Sql sql;
	socketHandler(Socket _in , Sql sql) {incoming=_in;this.sql = sql;}
	public void run()
	{
		try
		{
            ObjectInputStream fromClient=new ObjectInputStream(incoming.getInputStream());
			ObjectOutputStream toClient=new ObjectOutputStream(incoming.getOutputStream());
			String tcp,name,password,email,phone,date,start,end,hour,price,image;
			int choice = (int) fromClient.readObject();
			switch (choice){
				case 1:
					name =  (String) fromClient.readObject();
					password = (String) fromClient.readObject();
					email = (String) fromClient.readObject();
					phone = (String) fromClient.readObject();
					tcp=sql.select_query(name,password,email,phone);
					toClient.writeObject(tcp);
				case 2:
					name =  (String) fromClient.readObject();
					password = (String) fromClient.readObject();
					tcp=sql.return_query(name,password);
					toClient.writeObject(tcp);
				case 3:
					date = (String) fromClient.readObject();
					tcp=sql.select_date(date);
					toClient.writeObject(tcp);
				case 4:
					name = (String) fromClient.readObject();
					date = (String) fromClient.readObject();
					hour = (String) fromClient.readObject();
					tcp=sql.submit_date(name,date,hour);
					toClient.writeObject(tcp);
				case 5:
					tcp=sql.theBlogCount();
					toClient.writeObject(tcp);
				case 6:
					tcp=sql.theBlogPost();
					toClient.writeObject(tcp);
				case 7:
					name = (String) fromClient.readObject();
					tcp = sql.returnData(name);
					toClient.writeObject(tcp);
				case 8:
					name = (String) fromClient.readObject();
					tcp = sql.deleteData(name);
					toClient.writeObject(tcp);
				case 9:
					tcp = sql.adminData();
					toClient.writeObject(tcp);
				case 10:
					tcp = sql.adminCount();
					toClient.writeObject(tcp);
				case 11:
					date = (String) fromClient.readObject();
					tcp = sql.deletePics(date);
					toClient.writeObject(tcp);
				case 12:
					name = (String) fromClient.readObject();
					price = (String) fromClient.readObject();
					image = (String) fromClient.readObject();
					sql.addNewPic(name,price,image);
					toClient.writeObject("tcp");
				case 13:
					tcp=sql.availableHours();
					toClient.writeObject(tcp);
				case 14:
					start = (String) fromClient.readObject();
					end = (String) fromClient.readObject();
					sql.setAvailableHours(start,end);
					toClient.writeObject("tcp");
			}
		}
		catch(IOException e)
		{e.getMessage();} catch (ClassNotFoundException e) {e.printStackTrace();}
		try {incoming.close();} catch (IOException e) {e.printStackTrace();}
	}
}
