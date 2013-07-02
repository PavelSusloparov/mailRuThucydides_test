package mailRu.pages;

import net.thucydides.core.annotations.DefaultUrl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import net.thucydides.core.pages.PageObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

@DefaultUrl("http://mail.ru")
public class MailRuPage extends PageObject {

	@FindBy(id="mailbox__login")
	private WebElement mailboxLogin;

	@FindBy(id="mailbox__password")
	private WebElement mailboxPassword;
	
	@FindBy(id="mailbox__auth__button")
	private WebElement mailboxAuthButton;

	@FindBy(id="HeaderBtnSentMsg")
	private WebElement headerButtonSentMsg;

	@FindBy(id="q")
	private WebElement searchTerms;

	@FindBy(id="PH_logoutLink")
	private WebElement logoutButton;
	
	public MailRuPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Check domains. It has to compare with included domains list. 
	 * 
	 * @param domains  List of domain, which have to be in drop down box.
	 */
	/**
	 * Check domains. It has to compare with included domains list. 
	 * 
	 * @param domains  List of domain, which have to be in drop down box.
	 */
	public void check_mail_domains(List<String> domains) {
		Select droplist = new Select(getDriver().findElement(By.id("mailbox__login__domain")));
	    // Check domains from domains list.
	    int current_len = 1;
	    int comparator = 1;
	    List<WebElement> domain_dropdown = droplist.getOptions();
	    while (current_len < domains.size()) {
	       for (int domains_index = 1; domains_index <= domain_dropdown.size(); domains_index++){
	        	if (domains.get(domains_index).equals(domain_dropdown.get(current_len).getText())){
	        		comparator++;
	        		break;
	        	}
	        }
	        current_len++;
	        }
	        Assert.assertEquals(comparator, domain_dropdown.size());
		}
	
	/**
	 * Login to mail.ru system. 
	 * 
	 * @param login_name  Login without domain.
	 * @param domain_name  Domain name.
	 * @param pass_name  Password.
	 */
	public void mail_login(String login_name, String domain_name, String pass_name) {
		// Enter the login string login_name
        element(mailboxLogin).type(login_name);

        // Choose domain from drop down list
        Select droplist = new Select(getDriver().findElement(By.id("mailbox__login__domain")));
        droplist.selectByVisibleText(domain_name);
        
        // Enter the password string pass_name
        element(mailboxPassword).type(pass_name);

        // Click on login button
        element(mailboxAuthButton).click();
        
        //Wait 3 seconds for page loading.
        getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	/**
	 * Check that we login to the mail system.domains.
	 * 
	 * @param equal. Show our expectation. Do we need login in system or not.
	 */
	public void check_inside_mail_system(Boolean equal){
		try{
			Assert.assertNotNull(element(headerButtonSentMsg));
		}
		catch (org.openqa.selenium.NoSuchElementException e){
			if (equal){
				throw e;
			}
		}
	}
	
	/**
	 * Check that we logout from the mail system.domains.
	 * Focus should be on the main http://mail.ru page.
	 */
	public void check_outside_mail_system(){
		element(searchTerms).getAttribute("q");
	}

	/**
	 * Logout from mail.ru system.
	 */
	public void mail_logout(){
		element(logoutButton).click();
	}

	public void enter_keywords(String keyword) {
        element(searchTerms).type(keyword);
	}
}


