package alarMe;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class Exams extends LoginProcess {

    private HashMap<String, String> exams = new HashMap<String, String>();
  
    
    public Exams(){
    	super(username, user_password, driver);
    }

    
    public HashMap<String, String> getExams() {
        return exams;
    }
    
    public void setExams() throws InterruptedException{
    	//STUDWEB
        driver.get("https://idp.feide.no/simplesaml/module.php/feide/login.php?asLen=169&AuthState=_"
                + "d3cf8da4fdb8785ba65151ba2683aca1150fe3bfc2%3Ahttps%3A%2F%2Fidp.feide.no%2Fsimplesaml%"
                + "2Fsaml2%2Fidp%2FSSOService.php%3Fspentityid%3Dhttps%253A%252F%252Ffsweb.no"
                + "%252Fstudentweb%26cookieTime%3D1487768961");
        Thread.sleep(5000);

        //Choose NTNU as the institution
        //chooseNTNU();

        //innlogging med brukerinput
        //login();

        //need to choose NTNU as the institution one more time
        chooseNTNUAgain();

        //adds the exam dates to this.exams as <subject code subject name, date>
        List<WebElement> datesAndSubjects = driver.findElements(By.className("infoLinje"));
        for (WebElement element : datesAndSubjects){
            int index = datesAndSubjects.indexOf(element);
        	if (index == 0 || index % 3 == 0) {
                String date = element.getText();
                String courseCode = datesAndSubjects.get(index + 1).getText();
                String courseName = datesAndSubjects.get(index + 2).getText();
                this.exams.put(courseCode + " " + courseName, date);
            }
            

        }

    }
    
    
    //Code to add courses and exam dates to Database
    public void addExamsToDatabase() throws SQLException {
        	   
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
     	    setConnection();
     	    Set<String> keys = exams.keySet();
    		for (Iterator<String> i = keys.iterator(); i.hasNext(); ){
    			String key = (String) i.next();
    			String [] list= key.split(" ",2);
    			String value =  exams.get(key);
    			Date utilDate = new SimpleDateFormat("dd.MM.yyyy").parse(value);		
    			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
    			PreparedStatement p = (PreparedStatement) connection.prepareStatement("INSERT INTO Exam(coursecode, coursename, exam_date, student_id) VALUES(?,?,?,?)");
    			p.setString(1,list[0]);	
    			p.setString(2,list[1]);
    	        p.setDate(3, sqlDate);
    	        p.setInt(4, student_id);
    	        p.executeUpdate();
    	      
    		}
    	       		
    	}catch(Exception e){
    			System.out.println( "error:" + e);
    	       
    	   }
    	}
    		
    


}
