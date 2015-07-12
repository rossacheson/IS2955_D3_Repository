import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * As a <role>
 * I want...
 * So that...
 * @author Ross Acheson
 *
 */

//https://github.com/laboon/infsci2955/blob/master/sample_code/RedditTest.java
public class CalvinSiteTest {

	static WebDriver driver = new HtmlUnitDriver();

	// Start at the home page for Calvin for each test
	@Before
	public void setUp() throws Exception {
		driver.get("http://www.calvin.edu");
	}

	/*
	 * As a user I want to see a set of options specific to me on the home page
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
	 * As a member of the college staff I want pictures of me to include an alt
	 * text with my name So that I can be found via google image searches, so as
	 * to have a bolstered professional online presence
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
	 * As a user I want to be able to search the Calvin web site So that I can
	 * find resources related to my area of interest
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
	 * // Given that I am on the main page // When I view the title // Then I
	 * see that it contains the word "reddit"
	 * 
	 * @Test public void testShowsCorrectTitle() {
	 * 
	 * // Simply check that the title contains the word "reddit"
	 * 
	 * String title = driver.getTitle(); assertTrue(title.contains("reddit")); }
	 * 
	 * // Given that I am on the main page // When I view the Remember Me
	 * section // Then I should see that it contains the phrase "remember me"
	 * 
	 * @Test public void testHasRememberMe() {
	 * 
	 * // Check that there is a remember-me element // that contains the text
	 * "remember me" // If it does not exist, or text is incorrect, fail test
	 * 
	 * try { WebElement e = driver.findElement(By.id("remember-me")); String
	 * elementText = e.getText();
	 * assertTrue(elementText.contains("remember me")); } catch
	 * (NoSuchElementException nseex) { fail(); } }
	 * 
	 * // Given that I am on the main page // When I click on the "new" link //
	 * Then I should be redirected to the "new" page
	 * 
	 * @Test public void testSeeNewLinks() {
	 * 
	 * // find the "new" link and click on it // The page you go to should
	 * include "newest submissions" // in the title
	 * 
	 * driver.findElement(By.linkText("new")).click(); String newPageTitle =
	 * driver.getTitle();
	 * assertTrue(newPageTitle.contains("newest submissions")); }
	 * 
	 * // Given that I am on the main page // And I am not logged in // When I
	 * try to login with an valid username and invalid password // Then I am
	 * given the opportunity to reset the password
	 * 
	 * @Test public void testBadPasswordResetLink() {
	 * 
	 * // Enter username "meow", password "meow"
	 * 
	 * driver.findElement(By.name("user")).sendKeys("meow");
	 * driver.findElement(By.name("passwd")).sendKeys("meow");
	 * 
	 * // Look for the submit button (in the login div) and click // to attempt
	 * to login
	 * 
	 * WebElement loginDiv = driver.findElement(By.id("login_login-main"));
	 * 
	 * WebElement submitButton = loginDiv.findElement(By.className("btn"));
	 * submitButton.click();
	 * 
	 * // Check that there is a link to reset password and it is visible
	 * 
	 * try { WebElement resetPw = driver.findElement(By
	 * .linkText("reset password")); assertTrue(resetPw.isDisplayed()); } catch
	 * (NoSuchElementException nseex) { fail(); } }
	 */

}