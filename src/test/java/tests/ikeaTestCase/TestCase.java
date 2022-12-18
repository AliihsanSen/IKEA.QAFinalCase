package tests.ikeaTestCase;

import com.github.javafaker.Faker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import pages.IkeaPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.io.IOException;
import java.util.Set;

public class TestCase {

    IkeaPage ikeaPage = new IkeaPage();
    Actions actions = new Actions(Driver.getDriver());
    Faker faker = new Faker();
    Select select;
    private static Logger logger= LogManager.getLogger(TestCase.class.getName());

    @Test
    public void test01() throws InterruptedException, IOException {

        logger.info("Kullanici, 'https://ikea.com.tr' sayfasina gider");
        Driver.getDriver().get(ConfigReader.getProperty("ikeaUrl"));
        // Bu adimda configuration.properties dosyasi kullanilarak, kodun icerisine dogrudan sitenin url'ini degil
        // configuration.properties dosyasinda sitenin url'inin atandigi deger yazilarak kod daha dinamik hale getirilmistir.

        logger.info("Kullanici, arama kutucuguna aranacak kelimeyi yazar ve aratir");
        ikeaPage.searchBox.sendKeys(ConfigReader.getProperty("productToSearch"), Keys.ENTER);
        // Bu adimda da configuration.properties dosyasi bir ust adimdaki mantik cercevesinde kullanilarak kodun dinamik olmasi amaclanmistir.

        logger.info("Kullanici, donen sonuclarda kac tane aranan urun oldugunu dogrular");
        String result = ikeaPage.resultText.getText();
        String[] resultArr = result.split(" ");
        System.out.println("Arama sonucunda " + resultArr[0] + " tane " + ConfigReader.getProperty("productToSearch") + " bulundu.");
        // Bu adimda, yapilan aramadan sonra gelen sonuc sayfasinda aratilan urunden kac adet bulundugu konsolda yazdirilmistir.
        // Bunun icin gelen sonuc yazisi bir String'e atanmis, sonrasinda split methodu ile String'e atanan text yazisi bosluklardan bolunerek
        // sonuc sayisina ulasilmis ve konsola yazdirilmistir.

        logger.info("Kullanici, ileride lazim olabilecegi icin bulundugu sayfanin window handle degerini bir variable'a atar");
        String firstTabWindowHandleHashCode = Driver.getDriver().getWindowHandle();
        // Bu adimda, kullancinin gelen sonuc sayfasindan urun secmesiyle yeni bit sekme acilacagindan, yeni sekmeye gecmek icin
        // gereken islemleri yapabilmek adina ilk sekmenin window handle degerinin alinmasi gerekmektedir.

        logger.info("Kullanici, donen sonuclardan ikincisine tiklar");
        ikeaPage.secondProduct.click();

        logger.info("Kullanici, islem yapabilmek icin driver'i Set araciligi ile yeni acilan sekmeye tasir");
        Set<String> windowHandleseti = Driver.getDriver().getWindowHandles();
        String secondTabWindowHandleHashCode = "";
        for (String each : windowHandleseti) {
            if (!each.equals(firstTabWindowHandleHashCode)) {
                secondTabWindowHandleHashCode = each;
            }
        }
        // Bu adimda yeni acilan sekmenin window handle degerini bulmak icin, tarayicida bulunan tum window handle degerleri bir Set'e atilmistir
        // Tarayicimizda 2 adet sekmenin acik oldugunu biliyoruz ve bunlardan ilk sekmenin window handle degerini zaten yukaridaki adimda bir String'e atamistik
        // Bilinmeyen window handle degerini bulmak icin bir dongu olusturduk ve bize gelen her window handle degerini elimizde onceden var olan deger ile karsilastirdik
        // Eger gelen window handle degeri elimizdeki deger ile ayni degilse bu deger yeni acilan sekmenin window handle degeri olacagi icin
        // bu yeni window handle degerini ileride kullanmak icin yeni bir String variable'a atadik

        logger.info("Kullanici, driver'i Set araciligi ile buldugu yeni acilan tab'a gecirir");
        Driver.getDriver().switchTo().window(secondTabWindowHandleHashCode);
        // Bu adimda yukarida Set ve loop araciligi ile buldugumuz yeni window handle degeri ile driver'i yeni sekmeye gecirdik.

        logger.info("Kullanici, arattigi urunu sepete ekler");
        ikeaPage.addToBasketButton.click();

        logger.info("Kullanici, sepete gider");
        ikeaPage.viewCart.click();

        logger.info("Kullanici, adres ekleme butonuna tiklar");
        actions.sendKeys(Keys.PAGE_DOWN).perform();
        Thread.sleep(1000);
        ikeaPage.createAddressButton.click();

        logger.info("Kullanici, adres bilgilerini girer");
        ikeaPage.emailBox.sendKeys(faker.internet().emailAddress());
        actions.sendKeys(Keys.TAB).
                sendKeys(faker.name().firstName(), Keys.TAB).
                sendKeys(faker.name().lastName(), Keys.TAB).
                sendKeys(faker.phoneNumber().phoneNumber(), Keys.TAB).
                sendKeys(faker.phoneNumber().cellPhone(), Keys.TAB).perform();
        // Bu adimin bu kisminda Actions class'ini kullanarak her bir bilgi girilecek box'un locate'ini alma zahmetinden kurtulmus oluyoruz.
        // Zira bilgileri girdikten sonra klavyedeki TAB tusu ile bir sonraki box'a locate almadan gecmis oluyoruz.
        // Ayrıca hem girilen bilgilerin her seferinde yenilenmesi gerekebileceginden
        // hem de kisisel verilerin gizliligini ihlal etmemek icin Faker class'i kullanilarak random bilgiler uretilmistir.

        WebElement cityDropDownMenu = ikeaPage.cityDropDownMenu;
        select=new Select(cityDropDownMenu);
        select.selectByVisibleText("Antalya");

        WebElement townDropDownMenu= ikeaPage.townDropDownMenu;
        select=new Select(townDropDownMenu);
        select.selectByVisibleText("Konyaaltı");

        WebElement mahalleDropDownMenu=ikeaPage.mahalleDropDownMenu;
        select=new Select(mahalleDropDownMenu);
        select.selectByVisibleText("Liman mah.");
        // Adimin diger kisminda ise il,ilce ve mahalle secimi icin dropdown menu kullanilmis ve bu sekilde bilgiler secilmistir.

        ikeaPage.fullAddressTextArea.sendKeys(faker.address().fullAddress());

        logger.info("Kullanici, adres bilgilerini kaydeder");
        ikeaPage.addAddressButton.click();

        logger.info("Kullanici, adrese teslim butonuna tiklar");
        ikeaPage.homeDeliveryButton.click();

        logger.info("Kullanici, odeme sayfasina gider");
        ikeaPage.paymentPageButton.click();

        logger.info("Kullanici, odeme sayfasina kadar geldigini dogrular");
        System.out.println(ikeaPage.paymentPageTitleText.getText());
        assert ikeaPage.paymentPageTitleText.isDisplayed();
        // Bu adimda urunun sepete eklenmesinin ve odeme sayfasina gidilmesinin ardindan odeme sayfasina gelindigini
        // sayfanin basliginin konsola yazdirilmasiyla ve bu yazdirilan basligin goruntulenebildigini
        // Assert class'i araciligi ile dogruluypruz.

        logger.info("Kullanici browser'i kapatir");
        Driver.getDriver().quit();
    }
}
