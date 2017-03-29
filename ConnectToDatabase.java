package alarMe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ConnectToDatabase {
		
	protected Connection connection;
	private String db = "sql11163131";
	protected static String username;
	protected static String user_password;
	protected static int student_id;

	public void setConnection(){
        try {
        	connection = null;
            String server = "sql11.freemysqlhosting.net";
            String database_username = "sql11163131";
            String database_password = "wi4gXfVvT3";
            String connectionString = "jdbc:mysql://" + server + "/" + db + "?user=" + database_username + "&password=" + database_password;
            connection = DriverManager.getConnection(connectionString);
            
        } catch (Exception e){
            e.printStackTrace();}
  }

	
	public boolean checkForNewUser(){
		setConnection();
		Statement stm = null;
		try{
			int rows_database_old = 26;
			stm = connection.createStatement();
			ResultSet result = stm.executeQuery("select count(*) from Student"); 
	        result.next();
	        int rows_database = result.getInt(1); 
			//siden vi tenker at det bare blir lagt til én bruker om gangen
			if(rows_database != 0 && rows_database > rows_database_old){
				return true;
			}
			return false;
			
		}catch(Exception e){
			System.out.println("Her skjedde det noe feil: " + e);
		
		}finally {
	        if (stm != null) { try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	    }
	}
	return false;
			
}

	
	public ArrayList<String> newUser(){
		ArrayList<String> user = new ArrayList<String>();
		if (checkForNewUser() == true){
			setConnection();
			Statement stm = null;
			try{
				int student_id = 68;
				stm = connection.createStatement();
				ResultSet rs = stm.executeQuery("select * from Student");
				while(rs.next()){
					student_id += 1;
					username = rs.getString("username");
					user_password = rs.getString("user_password");	
				}
				user.add(username);
				user.add(user_password);
				ConnectToDatabase.student_id = student_id;
			
			}catch(Exception e){
				System.out.println("Her skjedde det noe feil: " + e);
				
			}
		} else{
			System.out.println("Ingen ny bruker er lagd");
		}
	
	return (user);
	}
	
	public String getUserName(){
		ArrayList<String> user = newUser();
		username = user.get(0);
		return username;
	}
	
	public String getUserPassword(){
		ArrayList<String> user = newUser();
		user_password = user.get(1);
		return user_password;
				
	}
	
	public int getStudentId(){
		return student_id;
	}
	

}