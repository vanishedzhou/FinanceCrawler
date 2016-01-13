package tongji.zzy;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestSelenium {

	public static void main(String[] args) {

	}

	@Test
	public void test() {
		System.getProperties().setProperty("webdriver.chrome.driver",
				"C:\\Users\\IBM_ADMIN\\Downloads\\chromedriver.exe");
		WebDriver webDriver = new ChromeDriver();
		webDriver.get("http://huaban.com/");
		WebElement webElement = webDriver.findElement(By.xpath("/html"));
		System.out.println(webElement.getAttribute("outerHTML"));
		webDriver.close();
	}

}
