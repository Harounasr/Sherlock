package de.ssherlock.system_tests.ui.facelets;

import de.ssherlock.control.notification.Notification;
import de.ssherlock.control.notification.NotificationType;
import de.ssherlock.system_tests.ui.AbstractSeleniumUITest;
import de.ssherlock.system_tests.ui.SeleniumUITestUtils;
import jakarta.faces.application.FacesMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * UI Test for {@code registration.xhtml}.
 *
 * @author Leon Höfling
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistrationUITest extends AbstractSeleniumUITest {

    //To do: Set wait accordingly to email send time / send failure time.
    /**
     * Test for entering valid credentials to the registration form and clicking register.
     */
    @Test
    void testRegistrationSuccess() {
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/registration.xhtml");
        getDriver().findElement(By.id("registrationForm:userName")).sendKeys("NewUsername");
        getDriver().findElement(By.id("registrationForm:firstName")).sendKeys("NewFirstName");
        getDriver().findElement(By.id("registrationForm:lastName")).sendKeys("NewLastName");
        getDriver().findElement(By.id("registrationForm:email")).sendKeys("hoefli11@ads.uni-passau.de");
        getDriver().findElement(By.id("registrationForm:faculty")).sendKeys("Informatik");
        getDriver().findElement(By.id("registrationForm:passWord")).sendKeys("N3wPa22w-rd!");

        SeleniumUITestUtils.enterOnElementWithId(getWait(), "registrationForm:register");

        Notification notification = new Notification("A registration email was sent to: hoefli11@ads.uni-passau.de. Please verify your email.",
                                                     NotificationType.SUCCESS);
        SeleniumUITestUtils.checkNotification(getWait(), notification);
    }

    /**
     * Test for entering valid credentials to the registration form and clicking register with a false email.
     */
    @Test
    void testRegistrationFailed() {
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/registration.xhtml");
        getDriver().findElement(By.id("registrationForm:userName")).sendKeys("NewUsername");
        getDriver().findElement(By.id("registrationForm:firstName")).sendKeys("NewFirstName");
        getDriver().findElement(By.id("registrationForm:lastName")).sendKeys("NewLastName");
        getDriver().findElement(By.id("registrationForm:email")).sendKeys("some.kindOf@email.com");
        getDriver().findElement(By.id("registrationForm:faculty")).sendKeys("Informatik");
        getDriver().findElement(By.id("registrationForm:passWord")).sendKeys("N3wPa22w-rd!");

        SeleniumUITestUtils.enterOnElementWithId(getWait(), "registrationForm:register");

        Notification notification = new Notification("Email could not be sent. Please try again.", NotificationType.ERROR);
        SeleniumUITestUtils.checkNotification(getWait(), notification);
    }

    /**
     * Test for entering invalid credentials to the registration form.
     */
    @Test
    void testRegistrationValidators() {
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/registration.xhtml");
        getDriver().findElement(By.id("registrationForm:userName")).sendKeys("abcd");
        getDriver().findElement(By.id("registrationForm:firstName")).sendKeys("abcd");
        getDriver().findElement(By.id("registrationForm:lastName")).sendKeys("abcd");
        getDriver().findElement(By.id("registrationForm:email")).sendKeys("NewEmail");
        getDriver().findElement(By.id("registrationForm:passWord")).sendKeys("password");

        SeleniumUITestUtils.enterOnElementWithId(getWait(), "registrationForm:register");

        SeleniumUITestUtils.checkFacesMessage(getWait(), new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Username musst be between 5 and 50 long.", null));
        SeleniumUITestUtils.checkFacesMessage(getWait(), new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Name musst be between 5 and 50 long.", null));
        SeleniumUITestUtils.checkFacesMessage(getWait(), new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Name musst be between 5 and 50 long.", null));
        SeleniumUITestUtils.checkFacesMessage(getWait(), new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Email musst be between 5 and 50 long.", null));
        SeleniumUITestUtils.checkFacesMessage(getWait(), new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                          "Password has to be between 8 and 50 long.", null));
    }

    /**
     * Test for clicking on the back to login button.
     * User should be redirected to the login Facelet.
     */
    @Test
    void testBackToLoginClicked() {
        SeleniumUITestUtils.navigateTo(getDriver(), "view/public/registration.xhtml");
        SeleniumUITestUtils.enterOnElementWithId(getWait(), "registrationLogin:backToLogin");
        assertEquals(SeleniumUITestUtils.BASE_URL + "view/public/login.xhtml", getDriver().getCurrentUrl());
    }
}
