package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class IkeaPage {
    public IkeaPage() {

        PageFactory.initElements(Driver.getDriver(), this);

    }

    @FindBy(xpath = "//input[@class='search-field__input']")
    public WebElement searchBox;

    @FindBy(xpath = "//p[@class='search-summary__message']")
    public WebElement resultText;

    @FindBy(xpath = "(//img[@src='https://cdn.ikea.com.tr/urunler/500_500/PN000090.jpg'])[1]")
    public WebElement secondProduct;

    @FindBy(xpath = "(//span[@class='btn-basket-text'])[1]")
    public WebElement addToBasketButton;

    @FindBy(xpath = "//a[@class='shopping-basket']")
    public WebElement viewCart;

    @FindBy(xpath = "(//i[@class='icon-plus'])[2]")
    public WebElement createAddressButton;

    @FindBy(xpath = "//input[@id='ctl00_ContentPlaceHolder1_ctrlAddressEditForm_txtEditEmail']")
    public WebElement emailBox;

    @FindBy(xpath = "//a[@class='btn btn-primary submit-btn']")
    public WebElement addAddressButton;

    @FindBy(xpath = "//select[@name='ctl00$ContentPlaceHolder1$ctrlAddressEditForm$ddlCities']")
    public WebElement cityDropDownMenu;

    @FindBy(xpath = "//select[@name='ctl00$ContentPlaceHolder1$ctrlAddressEditForm$ddlTowns']")
    public WebElement townDropDownMenu;

    @FindBy(xpath = "//select[@name='ctl00$ContentPlaceHolder1$ctrlAddressEditForm$ddlDistricts']")
    public WebElement mahalleDropDownMenu;

    @FindBy(xpath = "//textarea[@name='ctl00$ContentPlaceHolder1$ctrlAddressEditForm$txtEditDetail']")
    public WebElement fullAddressTextArea;

    @FindBy(xpath = "(//div[@class='checkbox home-address-delivery'])[1]")
    public WebElement homeDeliveryButton;

    @FindBy(xpath = "//a[@id='ctl00_ContentPlaceHolder1_btnContinue']")
    public WebElement paymentPageButton;

    @FindBy(xpath = "//*[text()='Sipariş Özeti ve Ödeme']")
    public WebElement paymentPageTitleText;

}
