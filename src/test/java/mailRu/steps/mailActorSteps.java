package mailRu.steps;

import java.util.List;

import mailRu.pages.MailRuPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.ScenarioSteps;

public class mailActorSteps extends ScenarioSteps {

    /**
	 * Class is provide Steps for mailRuLoginForm.
	 */
	private static final long serialVersionUID = 1L;

	MailRuPage MailRuPage;

    public mailActorSteps(Pages pages) {
        super(pages);
    }

    @Step
	public void go_to_url(){
    	MailRuPage.open();
	}

    @Step
	public void check_domains(List<String> domains){
    	MailRuPage.check_mail_domains(domains);
	}

    @Step
    public void login(String login_name, String domain_name, String pass_name) {
    	MailRuPage.mail_login(login_name, domain_name, pass_name);
    }

    @Step
    public void is_logged_in(Boolean equal){
    	MailRuPage.check_inside_mail_system(equal);
    }

    @Step
    public void is_logged_out(){
    	MailRuPage.check_outside_mail_system();
    }

    @Step
    public void logout(){
    	MailRuPage.mail_logout();
    }
}