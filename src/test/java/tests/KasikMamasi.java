package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.EBebekPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.ScreenShot;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertTrue;

public class KasikMamasi {

    Actions actions = new Actions(Driver.getDriver());
    EBebekPage EBebekPage = new EBebekPage();
    SoftAssert softAssert = new SoftAssert();
    ScreenShot screenShot = new ScreenShot();
    private static Logger logger = LogManager.getLogger(KasikMamasi.class.getName());

    @Test
    public void test02() throws InterruptedException, IOException {

        logger.info("Kullanici, https://www.hepsiburada.com/ sitesini ziyaret eder");
        Driver.getDriver().get(ConfigReader.getProperty("hepsiburadaUrl"));

        logger.info("Kullanici, sayfada çıkan cookie'leri kabul eder");
        EBebekPage.acceptCookiesButton.click();

        logger.info("Kullanici, testin herhangi bir aşamasında lazim olabileceginden bulundugu sayfanin 'windowhandle' degerini alir");
        String firstTabWindowHandleHashCode = Driver.getDriver().getWindowHandle();
        Thread.sleep(500);

        logger.info("Kullanici, herhangi bir kısıtlama olmadan istedigi urunu aratir ve 'enter' tusuna basar");
        EBebekPage.searchBox.sendKeys(ConfigReader.getProperty("productToSearch2"), Keys.ENTER);
        Thread.sleep(500);

        logger.info("Kullanici, açilan sayfayi daha iyi gorebilmek icin sayfayi biraz asagi kaydirir");
        actions.sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(500);

        logger.info("Kullanici, arama sonucunda ekrana gele urun listesinden (veya tek bir sonuc da donmus olabilir) urun secer");
        EBebekPage.secondProduct.click();

        logger.info("Kullanici, açilan sayfadaki urunun yeni bir sekmede acildigini gorur, bu nedenle yeni sekmede islem yapabilmek icin 'windowhandles' degerlerini alip bir set'e atar");
        Set<String> windowHandleseti = Driver.getDriver().getWindowHandles();
        String secondTabWindowHandleHashCode = "";
        for (String each : windowHandleseti) {
            if (!each.equals(firstTabWindowHandleHashCode)) {
                secondTabWindowHandleHashCode = each;
            }
        }

        logger.info("Kullanici, daha onceden almis olduğu Set araciligi ile buldugu yeni sekmenin 'windowhandle' degeri kullanarak driveri yeni sekmeye gecirir");
        Driver.getDriver().switchTo().window(secondTabWindowHandleHashCode);

        logger.info("Kullanici, yeni sekmede acilan urunun basligini testin daha sonraki asamalarinda kullanacagi icin String bir variable'a atar");
        String secondProductName = EBebekPage.secondProductName.getText();

        logger.info("Kullanici, açilan sayfadaki urunu ilk saticidan sepete ekler");
        EBebekPage.addToCartButton.click();

        logger.info("Kullanici, urun eklendikten sonra sayfada cikan bilgilendirme pop-up'ini kapatir");
        EBebekPage.popUpXButton.click();

        logger.info("Kullanici, ikinci saticidan ayni urunu eklemek icin sayfayi asagiya kaydirir");
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).perform();
        Thread.sleep(600);

        logger.info("Kullanici, ayni urunu sitedeki mevcut ikinci saticidan da sepete ekler");
        EBebekPage.otherSellerAddToCartButton.click();

        logger.info("Kullanici, ekledigi urunlerin dogrulugunu test edebilmek icin sepetim sayfasina gider");
        EBebekPage.popUpViewCartButton.click();

        logger.info("Kullanici, açılan sayfanin 'Sepetim' sayfasi oldugunu dogrular");
        softAssert.assertTrue(EBebekPage.cartPageText.isDisplayed());

        logger.info("Kullanici, sepete ekledigi ilk urunun " + EBebekPage.firstSeller.getText() + " saticisindan oldugunu dogrular");
        softAssert.assertTrue(EBebekPage.firstProductInfoBox.getText().contains(EBebekPage.firstSeller.getText()));

        logger.info("Kullanici, sepete ekledigi urunun dogru urun oldugunu daha onceden aldigi urun adi ile karsilastirarak dogrulama yapar");
        softAssert.assertTrue(EBebekPage.firstProductInfoBox.getText().contains(secondProductName));

        logger.info("Kullanici, sepete ekledigi diger urunun " + EBebekPage.secondSeller.getText() + " saticisindan oldugunu dogrular");
        softAssert.assertTrue(EBebekPage.secondProductInfoBox.getText().contains(EBebekPage.secondSeller.getText()));

        logger.info("Kullanici, sepete ekledigi urunun eklemek istedigi dogru urun oldugunu daha onceden aldigi urun adi ile karsilastirarak dogrulama yapar");
        softAssert.assertTrue(EBebekPage.secondProductInfoBox.getText().contains(secondProductName));
        softAssert.assertAll();

        logger.info("Kullanici, testin basında sepete ekledigi urunlerin ekran goruntuleri sayesinde manuel olarak da dogrular");
        screenShot.getScreenshot("Sepetim sayfasi ");

        logger.warn("Kullanici, testin başından sonuna kadar acilan tum sayfalari kapatarak test islemine son verir");
        Driver.getDriver().quit();

    }
}