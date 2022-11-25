package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class HepsiBuradaLoginPage {

    public HepsiBuradaLoginPage() {
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "(//span[@class='sf-OldMyAccount-d0xCHLV38UCH5cD9mOXq'])[1]")
    public WebElement logInOrSignInButton;
    @FindBy(xpath = "//a[@id='login']")
    public WebElement popUpLogInButton;
    @FindBy(xpath = "//input[@id='txtUserName']")
    public WebElement emailOrPhoneNumberBox;
    @FindBy(xpath = "//button[@id='btnLogin']")
    public WebElement emailLogInButton;
    @FindBy(xpath = "//input[@id='txtPassword']")
    public WebElement passwordBox;
    @FindBy(xpath = "//button[@id='btnEmailSelect']")
    public WebElement passwordLogInButton;
}