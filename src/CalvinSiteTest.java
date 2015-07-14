import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 * As a student
 * I want to work hard
 * So that I learn well and get a good grade
 * @author Ross Acheson
 *
 */

public class CalvinSiteTest {

	static WebDriver driver = new HtmlUnitDriver();
	static WebDriver foxDriver = new FirefoxDriver();
	static WebDriver foxDriver2 = new FirefoxDriver();

	// Start at the home page for Calvin for each test
	@Before
	public void setUp() throws Exception {
		driver.get("http://www.calvin.edu");
	}

	/*
	 * As a user 
	 * I want to see a set of options specific to me on the home page
	 * So that I can find resources that are relevant to me quickly and easily
	 * 
	 * @author Ross Acheson
	 */

	// Given that I am on the main page
	// When I view the header
	// Then I am provided shortcuts for "Students", "Parents", "Alumni",
	// "Faculty & Staff" and "Admitted Students" links
	@Test
	public void testHasShortcutLinks() {

		// Check for the links - if any of
		// these is not found, fail the test

		try {
			driver.findElement(By.linkText("Students"));
			driver.findElement(By.linkText("Parents"));
			driver.findElement(By.linkText("Alumni"));
			driver.findElement(By.linkText("Faculty & Staff"));
			driver.findElement(By.linkText("Admitted Students"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	// Given that I am on the alumni page
	// When I view the page
	// Then I am provided with a link to support the college
	@Test
	public void testHasSupportLink() {

		driver.get("http://www.calvin.edu/alumni/");

		// Check for the link - if not found, fail the test

		try {
			driver.findElement(By.linkText("Support"));
		} catch (NoSuchElementException nseex) {
			fail();
		}
	}

	/*
	 * As a member of the college staff 
	 * I want pictures of me to include an alt text with my name 
	 * So that I can be found via google image searches, and have a bolstered professional online presence
	 * 
	 * @author Ross Acheson
	 */

	// Given that I am on the Provost contact page
	// When I inspect the Provost's picture,
	// Then I see that the alt text contains her name
	@Test
	public void testProvostPicAltText() {

		driver.get("http://www.calvin.edu/admin/provost/contact.html");
		// get the first instance of the "img-right" class (the provost's pic)
		WebElement e = driver.findElement(By.className("img-right"));
		String picAltText = e.getAttribute("alt");
		assertTrue(picAltText.contains("Cheryl Brandsen"));
	}

	// Given that I am on the Administration about page
	// When I inspect the President's picture,
	// Then I see that the alt text contains his name
	@Test
	public void testPresidentPicAltText() {

		driver.get("http://www.calvin.edu/about/administration/");
		// get the first instance of the class containing the president's pic
		WebElement container = driver.findElement(By.className("large-4"));
		// get the first img within WebElement c, which is the President's pic
		WebElement presPic = container.findElement(By.tagName("img"));
		String picAltText = presPic.getAttribute("alt");
		assertTrue(picAltText.contains("Michael K. Le Roy"));
	}

	/*
	 * As a user 
	 * I want to be able to search the Calvin web site 
	 * So that I can find resources related to my area of interest
	 * 
	 * @author Ross Acheson
	 */

	// Given that I am on the main page
	// When I view it
	// Then the search box should be visible
	@Test
	public void testSearchBoxDisplays() {
		WebElement searchBox = driver.findElement(By.id("GoogleSearch"));
		assertTrue(searchBox.isDisplayed());
	}

	// Given that I am on the main page
	// When I type something in the search box and click on the magnifying glass
	// Then I should be directed to the search page
	@Test
	public void testSearchGoesToSearchPage() {
		driver.findElement(By.id("query")).sendKeys("Ghana");
		WebElement submitQuery = driver.findElement(By.name("btnG"));
		submitQuery.click();
		String title = driver.getTitle();
		assertTrue(title.contains("Search"));
	}

	/*
	 * As a user 
	 * I want the site to react to screen size 
	 * So that it is usable and beautiful when accessed from a variety of devices
	 * 
	 * @author Ross Acheson
	 */

	// Given that the screen size is 350 x 550
	// When I view the home page
	// Then I should see the hamburger icon menu in the upper right
	@Test
	public void testHamburgerIconShows() {
		foxDriver.get("http://www.calvin.edu");
		Dimension mobileSize = new Dimension(350, 550);
		foxDriver.manage().window().setSize(mobileSize);
		WebElement hamburgerIcon = foxDriver.findElement(By
				.className("menu-icon"));
		assertTrue(hamburgerIcon.isDisplayed());
	}

	// Given that the screen size is 1080 x 550
	// When I view the home page
	// Then I should /not/ see the hamburger icon menu in the upper right
	@Test
	public void testHamburgerIconDoesNotShow() {
		foxDriver.get("http://www.calvin.edu");
		Dimension desktopSize = new Dimension(1080, 550);
		foxDriver.manage().window().setSize(desktopSize);
		WebElement hamburgerIcon = foxDriver.findElement(By
				.className("menu-icon"));
		assertFalse(hamburgerIcon.isDisplayed());
	}

	/*
	 * As a user 
	 * I want to be able to contact the Web Administrators of the site
	 * So that I can give feedback about the site
	 * 
	 * @author Ross Acheson
	 */

	// Given that I am on the main page
	// When I open the comments box
	// Then I should be able to see and select the form input areas
	@Test
	public void testCanUseCommentsBox() {
		foxDriver.get("http://www.calvin.edu");
		foxDriver.findElement(By.id("habla_sizebutton_a")).click(); // click caret to open box
		WebElement nameInputArea = foxDriver.findElement(By
				.id("habla_name_input"));
		assertTrue(nameInputArea.isDisplayed());
		nameInputArea.click();
		nameInputArea.sendKeys("Nombre");
		// make sure  the focus is still in the input area
		assertTrue(nameInputArea.equals(foxDriver.switchTo().activeElement())); 
	}

	// Given that I am on the main page
	// When I see the comments box
	// Then I should also be able to remove it from view
	@Test
	public void testCanCloseCommentsBox() {
		foxDriver2.get("http://www.calvin.edu");
		Dimension desktopSize = new Dimension(1080, 700);
		foxDriver2.manage().window().setSize(desktopSize);
		WebElement sizeButton = foxDriver2.findElement(By
				.id("habla_sizebutton_a"));
		WebElement closeButton = foxDriver2.findElement(By
				.id("habla_closebutton_a"));
		WebElement commentsBar = foxDriver2
				.findElement(By.id("habla_both_div")); // small clickable area inviting comments

		assertTrue(commentsBar.isDisplayed()); // the minimized comments option should be visible by default
		sizeButton.click(); // maximize comments box
		WebElement commentsArea = foxDriver2.findElement(By
				.id("habla_offline_message_div")); // larger area with form for comments
		assertTrue(commentsArea.isDisplayed());
		sizeButton.click(); // minimize comments box again
		// check if commentsArea is Displayed, if so, fail
		try {
			if (commentsArea.isDisplayed())
				fail();
		} catch (NoSuchElementException e) {
			System.out.println("Comments Area successfully minimized.");
		}
		closeButton.click(); // close comments box
		// check if commentsBar is Displayed, if so, fail
		try {
			if (commentsBar.isDisplayed())
				fail();
		} catch (NoSuchElementException e) {
			System.out.println("Comments bar successfully closed.");
		}
	}

}