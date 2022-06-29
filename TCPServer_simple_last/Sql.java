import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;

public class Sql {

	private Connection connect;

	public String return_query(String name, String password)  {
		String str = "";
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		try{
			statement = connect.prepareStatement("SELECT name FROM mysql1.testest where name = '"+name+"'");
			statement2 = connect.prepareStatement("SELECT password FROM mysql1.testest where name= '"+name+"' and password = '"+password+"'");
			ResultSet result = statement.executeQuery();
			ResultSet result2 = statement2.executeQuery();
			while (result.next())
				str =str+ result.getString(1);
			if (str.equals("")) return "username or password are not correct";
			else str="";
			while (result2.next())
				str =str+ result2.getString(1);
			if (str.equals("")) return "username or password are not correct";
			if (str.equals("admin")) return "admin";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public String select_query(String name, String password, String email, String phone)  {
		String str = "";
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		PreparedStatement statement3 = null;
		try{
			statement = connect.prepareStatement("SELECT name FROM mysql1.testest where name = '"+name+"'");
			statement2 = connect.prepareStatement("SELECT * FROM mysql1.testest where email = '"+email+"'");
			statement3 = connect.prepareStatement("SELECT * FROM mysql1.testest where phone = '"+phone+"'");
			ResultSet result = statement.executeQuery();
			ResultSet result2 = statement2.executeQuery();
			ResultSet result3 = statement3.executeQuery();
			while (result.next())
				str += result.getString(1);
			if (!str.equals("")) return "used name";
			while (result2.next())
				str += result2.getString(1);
			if (!str.equals("")) return "used email";
			while (result3.next())
				str += result3.getString(1);
			if (!str.equals("")) return "used phone";
			insert_statement(name,password,email,phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public void insert_statement(String name, String password, String email, String phone) {
		String sqlInsert = "insert into mysql1.testest (name,password,email,phone) values (?,?,?,?)";
		try {
			PreparedStatement pst = connect.prepareStatement(sqlInsert);
			pst.setString(1, name);
			pst.setString(2, password);
			pst.setString(3, email);
			pst.setString(4, phone);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addNewPic(String name, String price, String image) {

		String id="SELECT id FROM blogpost order by id desc limit 1";
		String sqlInsert = "insert into mysql1.blogpost (id,title,body,image) values (?,?,?,?)";
		String str="";
		try {
			PreparedStatement pst2 = connect.prepareStatement(id);
			ResultSet result2 = pst2.executeQuery();
			while (result2.next())
				str =str+ result2.getString(1);
			int j=Integer.parseInt(str);
			j++;
			PreparedStatement pst = connect.prepareStatement(sqlInsert);
			pst.setString(1, String.valueOf(j));
			pst.setString(2, name);
			pst.setString(3, price);
			pst.setString(4, image);
			pst.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String returnData(String name){
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		String str="";
		try {
			statement = connect.prepareStatement("SELECT date  FROM timecheck where name = '"+name+"'");
			statement2 = connect.prepareStatement("SELECT hour  FROM timecheck where name = '"+name+"'");
			ResultSet result = statement.executeQuery();
			ResultSet result2 = statement2.executeQuery();
			while (result.next())
				str = str + result.getString(1) + "! ";
			while (result2.next())
				str =str+ result2.getString(1);
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	public String theBlogCount(){
		PreparedStatement statement = null;
		String str="";
		try {
			statement = connect.prepareStatement("SELECT COUNT(*)  FROM blogpost");
			ResultSet result = statement.executeQuery();
			while (result.next())
				str =str+ result.getString(1);
			if (str.equals("")) return "0";
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	public String adminCount(){
		PreparedStatement statement = null;
		String str="";
		try {
			statement = connect.prepareStatement("SELECT COUNT(*)  FROM timecheck");
			ResultSet result = statement.executeQuery();
			while (result.next())
				str =str+ result.getString(1);
			if (str.equals("")) return "0";
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}


	public String adminData(){
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		PreparedStatement statement3 = null;
		String str="";
		try {
			statement = connect.prepareStatement("SELECT name  FROM timecheck");
			ResultSet result = statement.executeQuery();
			statement2 = connect.prepareStatement("SELECT date  FROM timecheck");
			ResultSet result2 = statement2.executeQuery();
			statement3 = connect.prepareStatement("SELECT hour  FROM timecheck");
			ResultSet result3 = statement3.executeQuery();
			while (result.next())
				str =str+ result.getString(1)+"! ";
			while (result2.next())
				str =str+ result2.getString(1)+"! ";
			while (result3.next())
				if(!result3.isLast()) {
					str = str + result3.getString(1) + "! ";
				}
				else {
					str =str+ result3.getString(1);
				}
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	public String deletePics(String name){
		PreparedStatement statement = null;
		String str="";
		try {
			statement = connect.prepareStatement("DELETE FROM blogpost WHERE title = '"+name+"'");
			statement.execute();
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	public String deleteData(String name){
		PreparedStatement statement = null;
		String str="";
		try {
			statement = connect.prepareStatement("DELETE FROM timecheck WHERE name = '"+name+"'");
			statement.execute();
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	public String theBlogPost(){
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		PreparedStatement statement3 = null;
		String str="";
		try {
			statement = connect.prepareStatement("SELECT title FROM blogpost");
			statement2 = connect.prepareStatement("SELECT body FROM blogpost");
			statement3 = connect.prepareStatement("SELECT image FROM blogpost");
			ResultSet result = statement.executeQuery();
			ResultSet result2 = statement2.executeQuery();
			ResultSet result3 = statement3.executeQuery();
			while (result.next())
				str =str+ result.getString(1)+"! ";
			while (result2.next())
				str =str+ result2.getString(1)+"! ";
			while (result3.next())
				str =str+ result3.getString(1)+"! ";
			return str;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}

	public String availableHours()  {
		String str = "";
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		try{
			statement = connect.prepareStatement("SELECT starthour FROM mysql1.available");
			statement2 = connect.prepareStatement("SELECT endhour FROM mysql1.available");
			ResultSet result = statement.executeQuery();
			ResultSet result2 = statement2.executeQuery();
			while (result.next()) {
				str += result.getString(1)+" ";
			}
			while (result2.next()) {
				str += result2.getString(1);
			}
			return str;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}


	public String setAvailableHours(String start,String end)  {
		String str = "";
		PreparedStatement statement = null;
		try{
			statement = connect.prepareStatement("UPDATE mysql1.available SET starthour= '"+start+"',endhour= '"+end+"'" );
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public String select_date(String date)  {
		String str = "";
		PreparedStatement statement = null;
		try{
			statement = connect.prepareStatement("SELECT Hour FROM mysql1.timecheck where Date = '"+date+"'");
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				str += result.getString(1)+" ";
			}
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public String submit_date(String name,String date,String hour){
		String sqlInsert = "insert into mysql1.timecheck (name,date,hour) values (?,?,?)";

		try {
			PreparedStatement pst = connect.prepareStatement(sqlInsert);
			pst.setString(1, name);
			pst.setString(2, date);
			pst.setString(3, hour);
			pst.execute();
		} catch (SQLException e) {e.printStackTrace();}
		return "success";
	}

	public  void connection()
	{
		try {Class.forName("com.mysql.jdbc.Driver");System.out.println("Works");
		} catch (ClassNotFoundException e) {e.printStackTrace();}
	}

	public  void ConectingToSQL ()
	{
		connection();
		String host = "jdbc:mysql://localhost:3306/mysql1?autoReconnect=true&useSSL=false";
		String username = "root";
		String password = "1111";
		try {
			 connect = (Connection) DriverManager.getConnection(host, username, password);
		System.out.println("work");
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	
}
