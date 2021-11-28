package cz.czechitas.selenium;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestyPrihlasovaniNaKurzy {

    public static final String WEB_URL = "https://cz-test-jedna.herokuapp.com/";
    WebDriver prohlizec;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Java-Training\\Selenium\\geckodriver.exe");
        prohlizec = new FirefoxDriver();
        prohlizec.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void existujiciUcetPrihlaseniDoAplikace() {
        prohlizec.navigate().to(WEB_URL + "prihlaseni");

        vyplnEmail("svatava.valaskova@centrum.cz");
        vyplnHeslo("P6ppDJj448Rxdxk");
        klikniNaPrihlasit();

        overPrihlaseni();

    }

    @Test
    public void vybratKurzPrihlaseniPrihlaseniDitete() {
        prohlizec.navigate().to(WEB_URL + "11-trimesicni-kurzy-webu");

        vytvoreniPrihlasky();

        vyplnEmail("svatava.valaskova@centrum.cz");
        vyplnHeslo("P6ppDJj448Rxdxk");
        klikniNaPrihlasit();

        vyberteTerminKurzWebu();
        vyplnJmeno("Veronika");
        vyplnPrijmeni("Valášková");
        vyplnDatumNarozeni("21.11.2014");
        platbaPrevodem();
        SouhlasSPodminkami();

        vytvoritPrihlasku();

        overeniVSeznamuPrihlasek();

    }

    @Test
    public void prihlaseniVybratKurzPrihlasitDite() {
        prohlizec.navigate().to(WEB_URL + "prihlaseni");

        vyplnEmail("svatava.valaskova@centrum.cz");
        vyplnHeslo("P6ppDJj448Rxdxk");
        klikniNaPrihlasit();

        prohlizec.navigate().to(WEB_URL + "1-digitalni-akademie-testovani");

        vytvoreniPrihlasky();
        vyberteTerminDaTestovani();
        vyplnJmeno("Veronika");
        vyplnPrijmeni("Valášková");
        vyplnDatumNarozeni("21.11.2014");
        platbaHotove();
        SouhlasSPodminkami();

        vytvoritPrihlasku();

        overeniVSeznamuPrihlasek();
    }

    @Test
    public void prihlaseniKurzuOdhlaseniKurzuOvereni() {
        prohlizec.navigate().to(WEB_URL + "prihlaseni");

        vyplnEmail("svatava.valaskova@centrum.cz");
        vyplnHeslo("P6ppDJj448Rxdxk");
        klikniNaPrihlasit();

        prohlizec.navigate().to(WEB_URL + "1-digitalni-akademie-testovani");

        vytvoreniPrihlasky();
        vyberteTerminDaTestovani();
        vyplnJmeno("Veronika");
        vyplnPrijmeni("Valášková");
        vyplnDatumNarozeni("21.11.2014");
        platbaHotove();
        SouhlasSPodminkami();

        vytvoritPrihlasku();

        odhlaseniZaka();

        WebElement zakBylOdhlasen = prohlizec.findElement(By.xpath("//div[@class='toast-message' and text()='Žák Veronika Valášková byl úspěšně odhlášen']"));
        Assertions.assertNotNull(zakBylOdhlasen);

    }

    public void odhlaseniZaka() {
        WebElement odhlaseni = prohlizec.findElement(By.xpath("//a[@class='btn btn-sm btn-danger' and text()='Odhlášení účasti']"));
        odhlaseni.click();
        WebElement nemoc = prohlizec.findElement(By.xpath("//label[@class='custom-control-label' and text()='Nemoc']"));
        nemoc.click();
        WebElement odhlasitZaka = prohlizec.findElement(By.xpath("//input[@class='btn btn-primary']"));
        odhlasitZaka.click();
    }


    public void klikniNaPrihlasit() {
        WebElement prihlaseni = prohlizec.findElement(By.xpath("//button[@class='btn btn-primary']"));
        prihlaseni.click();
    }

    public void overPrihlaseni() {
        WebElement prihlasen = prohlizec.findElement(By.xpath("//span[text()='Přihlášen']"));
        Assertions.assertNotNull(prihlasen);
    }

    public void vyplnPole(String xPath, String hodnota) {
        WebElement inputElement = prohlizec.findElement(By.id(xPath));
        inputElement.sendKeys(hodnota);
    }

    public void vyplnHeslo(String heslo) {
        vyplnPole("password", heslo);
    }

    public void vyplnEmail(String email) {
        vyplnPole("email", email);
    }

    public void vyplnJmeno(String jmeno) {
        vyplnPole("forename", jmeno);
    }

    public void vyplnPrijmeni(String prijmeni) {
        vyplnPole("surname", prijmeni);
    }

    public void vyplnDatumNarozeni(String narozeni) {
        vyplnPole("birthday", narozeni);
    }

    public void vyberteTerminKurzWebu() {
        WebElement termin = prohlizec.findElement(By.className("filter-option-inner-inner"));
        termin.click();
        WebElement termin2 = prohlizec.findElement(By.id("bs-select-1-2"));
        termin2.click();
    }

    public void vyberteTerminDaTestovani() {
        WebElement termin = prohlizec.findElement(By.className("filter-option-inner-inner"));
        termin.click();
        WebElement termin2 = prohlizec.findElement(By.id("bs-select-1-0"));
        termin2.click();
    }

    public void platbaHotove() {
        WebElement hotove = prohlizec.findElement(By.xpath("//label[@class ='custom-control-label' and text()='Hotově']"));
        hotove.click();
    }

    public void platbaPrevodem() {
        WebElement prevodem = prohlizec.findElement(By.xpath("//label[@class ='custom-control-label' and text()='Bankovní převod']"));
        prevodem.click();
    }

    public void SouhlasSPodminkami() {
        WebElement souhlas = prohlizec.findElement(By.xpath("//label[@class ='custom-control-label' and text()='Souhlasím s všeobecnými podmínkami a zpracováním osobních údajů.']"));
        souhlas.click();
    }

    public void vytvoritPrihlasku() {
        WebElement vytvorPrihlasku = prohlizec.findElement(By.xpath("//input[@class='btn btn-primary mt-3']"));
        vytvorPrihlasku.click();
    }


    public void vytvoreniPrihlasky() {
        WebElement vytvoritPrihlasku = prohlizec.findElement(By.xpath("//a[@class='btn btn-sm align-self-center btn-primary']"));
        vytvoritPrihlasku.click();
    }

    public void overeniVSeznamuPrihlasek() {
        prohlizec.navigate().to(WEB_URL + "zaci");

        List<WebElement> seznamPrihlasek = prohlizec.findElements(By.xpath("//td[@class='dtr-control sorting_1'and text()='Veronika Valášková']"));
        WebElement ocekavaneJmenoZaka = seznamPrihlasek.get(0);
        String zak = ocekavaneJmenoZaka.getText();
        Assertions.assertEquals("Veronika Valášková", zak);
    }

    @AfterEach
    public void tearDown() {
        prohlizec.quit();
    }
}
