package alarMe;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class LoginProcess extends ConnectToDatabase {
	
	protected static WebDriver driver = new ChromeDriver();
			
	public LoginProcess(String username, String user_password, WebDriver driver){
		LoginProcess.username = getUserName();
		LoginProcess.user_password = getUserPassword();
		LoginProcess.driver = driver;
	}
	
	public void init() {
		//System.setProperty("webdriver.chrome.driver", "chromedriver");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Heidi\\tdt4100-2017-master2\\"
							+ "ws\\chromedriver_win32\\chromedriver.exe");
	}
		
	public void chooseNTNU() throws InterruptedException {
        Select objectSelect = new Select(driver.findElement(By.id("org")));
        objectSelect.selectByValue("ntnu.no");
        driver.findElement(By.className("submit")).click();
        Thread.sleep(5000);
    }

    public void chooseNTNUAgain() throws InterruptedException {
        Select schoolSelect = new Select(driver.findElement(By.id("institusjonsvalg:institusjonsMenu")));
        schoolSelect.selectByValue("FSNTNU");
        driver.findElement(By.name("institusjonsvalg:j_idt121")).click();
        Thread.sleep(5000);
        driver.findElement(By.linkText("Pålogging via Feide")).click();
        Thread.sleep(5000);
    }

    public void login() throws InterruptedException {
    	//tror ikke vi trenger å ta inn username og password her da om vi setter det i konstruktøren.
        WebElement usernameField = driver.findElement(By.name("feidename"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys(username);
        passwordField.sendKeys(user_password);
        driver.findElement(By.className("submit")).click();
        Thread.sleep(5000);
    }

}
