package test_automatio_eng;

import org.jbehave.core.annotations.BeforeStories;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertThat;
import org.hamcrest.Matchers;

public class liwwa_Autimation_test {

	@Test
	public void statusesRegardingCI() {
		WebDriver newDriver = intilizeDriver();
		newDriver.get("https://github.com");
		newDriver.manage().window().maximize();

		WebDriverWait wait = new WebDriverWait(newDriver, 60);
		// wait until the search textbox appears
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[aria-label='Search GitHub']")));
		System.out.println("TC1-find github Search Textbox");

		// writ in the github search textbox the repo we need to search on which is
		// "python/cpython" and navigate thrgho it
		newDriver.findElement(By.cssSelector("input[aria-label='Search GitHub']")).sendKeys("python/cpython");
		newDriver.findElement(By.cssSelector("input[aria-label='Search GitHub']")).sendKeys(Keys.RETURN);
		System.out.println("TC2-search for repo in github using the search textbox");

		
		// click on the first search result
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//a[@class='v-align-middle'][contains(@href,'python/cpython')]")));
		newDriver.findElement(By.xpath("//a[@class='v-align-middle'][contains(@href,'python/cpython')]")).click();
		System.out.println("TC3-search for the repo we need in the search resultes");

		
		// Check the URL for the repo
		assertThat(newDriver.getCurrentUrl(), Matchers.equalTo("https://github.com/python/cpython"));
		System.out.println("TC4-check the URL for the repo");

		
		// find the file README.rst & click in it
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='README.rst']")));
		newDriver.findElement(By.xpath("//a[@title='README.rst']")).click();
		System.out.println("TC5-find the file we need to open in the repo");
		
		// Check the URL for the file README.rst
				assertThat(newDriver.getCurrentUrl(), Matchers.equalTo("https://github.com/python/cpython/blob/master/README.rst"));
				System.out.println("TC6-check the utl for the file opened file");

		// check the build status
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//a/img[@alt='CPython build status on Travis CI']")));
		String build = newDriver.findElement(By.xpath("//a/img[@alt='CPython build status on Travis CI']"))
				.getAttribute("data-canonical-src");
		assertThat(build, Matchers.equalTo("https://travis-ci.com/python/cpython.svg?branch=master"));
		System.out.println("TC7-check the CI build Status");
		
		// check the Tests status
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//a/img[@alt='CPython build status on GitHub Actions']")));
		String Tests = newDriver.findElement(By.xpath("//a/img[@alt='CPython build status on GitHub Actions']"))
				.getAttribute("src");
		assertThat(Tests, Matchers.equalTo("https://github.com/python/cpython/workflows/Tests/badge.svg"));
		System.out.println("TC8-check the CI Tests Status");

		// check the AzurePipline status
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//a/img[@alt='CPython build status on Azure DevOps']")));
		String AzurePipline = newDriver.findElement(By.xpath("//a/img[@alt='CPython build status on Azure DevOps']"))
				.getAttribute("data-canonical-src");
		assertThat(AzurePipline, Matchers.equalTo(
				"https://dev.azure.com/python/cpython/_apis/build/status/Azure%20Pipelines%20CI?branchName=master"));
		System.out.println("TC9-check the CI azur Devops Status");

		
		// check the codecov status
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//a/img[@alt='CPython code coverage on Codecov']")));
		String codecov = newDriver.findElement(By.xpath("//a/img[@alt='CPython code coverage on Codecov']"))
				.getAttribute("data-canonical-src");
		assertThat(codecov, Matchers.equalTo("https://codecov.io/gh/python/cpython/branch/master/graph/badge.svg"));
		System.out.println("TC.10-check the CI codecov Status");

		
		// check the zulip status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a/img[@alt='Python Zulip chat']")));
		String zulip = newDriver.findElement(By.xpath("//a/img[@alt='Python Zulip chat']"))
				.getAttribute("data-canonical-src");
		assertThat(zulip, Matchers.equalTo("https://img.shields.io/badge/zulip-join_chat-brightgreen.svg"));
		System.out.println("TC.11-check the CI zulip chat Status");

		
		// close chrome driver
		newDriver.close();
		System.out.println("Close Chrome Driver");

		System.out.println("Test Passed");
	}

	@BeforeStories
	static public WebDriver intilizeDriver() {
		WebDriver driver = createChromeDriver();
		return driver;
	}

	protected static ChromeDriver createChromeDriver() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		options.addArguments("--ignore-ssl-errors=yes");
		options.addArguments("--ignore-certificate-errors");

		return new ChromeDriver(options);

	}

}
