package mailRu;

import java.util.Arrays;
import java.util.List;

import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Story;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.annotations.Managed;
import net.thucydides.junit.runners.ThucydidesRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import mailRu.requirements.Application;
import mailRu.steps.mailActorSteps;

@Story(Application.Search.MailRuLoginForm.class)
@RunWith(ThucydidesRunner.class)
public class MailRuLoginFormTest {

	private String login_name = "psusloparov_test";
	private String error_login_name = "error";
	private String domain_name = "@inbox.ru";
	private String pass_name = "PSUSLOPAROV1test";
	private List<String> domains = Arrays.asList("@mail.ru", "@inbox.ru", "@list.ru", "@bk.ru");

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @ManagedPages(defaultUrl = "http://mail.ru")
    public Pages pages;

    @Steps
    public mailActorSteps mailActor;

    @Test
    public void SimpleMailLoginPositive() {
        mailActor.go_to_url();
        mailActor.check_domains(domains);
        mailActor.login(login_name, domain_name, pass_name);
        mailActor.is_logged_in(true);
        mailActor.logout();
        mailActor.is_logged_out();
    }

    @Test
    public void SimpleMailLoginNegative() {
    	mailActor.go_to_url();
    	mailActor.check_domains(domains);
    	mailActor.login(error_login_name, domain_name, pass_name);
    	mailActor.is_logged_in(false);
    }
} 