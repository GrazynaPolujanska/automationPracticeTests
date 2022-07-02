import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

//Zad1. Napisz test, który zweryfikuje działanie aplikacji, gdy przy próbie logowania nie podano loginu.
public class Projekt_Grazyna_Polujanska {

    static WebDriver driver = new ChromeDriver();

    @BeforeAll
    static void prepareBrowser(){
        driver.manage().window().maximize();
    }

    @AfterAll
    static void closeBrowser(){
        driver.manage().deleteAllCookies();
        driver.quit();

    }

    @Test
    @DisplayName("errorWhenSetLoginIsEmpty")
    void shouldVerifyErrorWhenLoginIsEmpty() throws InterruptedException {
        driver.get("http://automationpractice.com/index.php");

        WebElement signInButton = driver.findElement(By.className("login"));
        Thread.sleep(2000);
        signInButton.click();
        Thread.sleep(2000);
        WebElement pageTitle = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("AUTHENTICATION", pageTitle.getText());

        WebElement password = driver.findElement(By.id("passwd"));
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));

        password.sendKeys("1234qwerTY");
        submitButton.click();

        WebElement loginErrorMessage = driver.findElement(By.cssSelector("#center_column > div.alert.alert-danger"));
        Assertions.assertEquals("There is 1 error\n" + "An email address required.", loginErrorMessage.getText());

    }

    // Zadanie 1 Wersja druga ze zmieniajacym się haslem

    @Test
    @DisplayName("errorWhenRandomLoginIsEmpty")
    void shouldVerifyErrorWhenLoginIsEmpty2() throws InterruptedException {
        driver.get("http://automationpractice.com/index.php");

        WebElement signInButton = driver.findElement(By.className("login"));
        Thread.sleep(2000);
        signInButton.click();
        Thread.sleep(2000);
        WebElement pageTitle = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("AUTHENTICATION", pageTitle.getText());

        WebElement password = driver.findElement(By.id("passwd"));
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));

        Random random = new Random();
        String randomPassoword = "kociak" + random.nextInt(1000);
        password.sendKeys(randomPassoword);
        submitButton.click();

        WebElement loginErrorMessage = driver.findElement(By.cssSelector("#center_column > div.alert.alert-danger"));
        Assertions.assertEquals("There is 1 error\n" + "An email address required.", loginErrorMessage.getText());

    }
//    Zad2. Napisz test, który zweryfikuje działanie aplikacji, gdy przy próbie logowania nie podano hasła.
    @Test
    @DisplayName("errorWhenRandomPassowordIsEmpty")
    void shouldVerifyErrorWhenPasswordIsEmpty() throws InterruptedException {
        driver.get("http://automationpractice.com/index.php");

        WebElement signInButton = driver.findElement(By.className("login"));
        Thread.sleep(2000);
        signInButton.click();
        Thread.sleep(2000);
        WebElement pageTitle = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("AUTHENTICATION", pageTitle.getText());

        WebElement login = driver.findElement(By.id("email"));
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));

        Random random = new Random();
        String randomLogin = "kocia" + random.nextInt(1000) + "czek@gmail.com";
        login.sendKeys(randomLogin);
        submitButton.click();

        WebElement loginErrorMessage = driver.findElement(By.cssSelector("#center_column > div.alert.alert-danger"));
        Assertions.assertEquals("There is 1 error\n" + "Password is required.", loginErrorMessage.getText());
    }

//    Zad3. Sprawdź, czy strona główna oraz strona logowania zawiera logo i pole wyszukiwania.

    @Test
    @DisplayName("logoAndSearchbarIsDisplayed")
    void shouldVerifyIfLogoAndSearchbarIsDisplayed(){
        driver.get("http://automationpractice.com/index.php");
        WebElement logo= driver.findElement(By.cssSelector("#header_logo > a > img"));
        Assertions.assertTrue(logo.isDisplayed());
        WebElement searchbar = driver.findElement(By.id("search_query_top"));
        Assertions.assertTrue(searchbar.isDisplayed());

    }

//    Zad4. Napisz test sprawdzający przejście ze strony głównej do strony ”Kontakt”

    @Test
    @DisplayName("goingToContactPage")
    void shouldVerifyGoingFromMainPageToContactPage() throws InterruptedException {
        driver.get("http://automationpractice.com/index.php");
        WebElement contactInButton = driver.findElement(By.id("contact-link"));
        Thread.sleep(2000); // bez tego znów wychodził unknown error: unexpected command response
//        próbowałam z wersją wait, ale unknown error, ale sama funkcja Wait chyba jest napisana dobrze :)
//        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("contact-link")));
//      albo  wait.until(ExpectedConditions.elementToBeClickable(contactInButton));
        contactInButton.click();
        WebElement contactPageTitle = driver.findElement(By.cssSelector("#center_column > h1"));
        Assertions.assertEquals("CUSTOMER SERVICE - CONTACT US", contactPageTitle.getText());
    }

//    Zad5. Napisz test sprawdzający przejście ze strony logowania do strony głównej.

    @Test
    void shouldVerifyGoingFromLoginToMainPage() throws InterruptedException {
        driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        Thread.sleep(2000); // ponownie bez tego niestety nie dziala :(
        WebElement houseIcon = driver.findElement(By.className("icon-home"));
        houseIcon.click();
        Assertions.assertEquals("http://automationpractice.com/index.php",driver.getCurrentUrl());
        //dodatek: przejscie ponownie na strone logowania i wrocenie na glowna
        // poprzez klikniecie tym razem w logo zamiast ikone domku
        WebElement signInButton = driver.findElement(By.className("login"));
        Thread.sleep(2000);
        signInButton.click();
        WebElement logo= driver.findElement(By.cssSelector("#header_logo > a > img"));
        logo.click();
        Assertions.assertEquals("http://automationpractice.com/index.php",driver.getCurrentUrl());

    }

//    Zad6. Napisz test, który dodaje produkt do koszyka.
//          Zweryfikuj, czy dodanie powiodło się.
    @Test
    void shouldVerifyIfItemIsAddedToCart() throws InterruptedException {
        driver.get("http://automationpractice.com/index.php");
        WebElement womenButton = driver.findElement(By.cssSelector("#block_top_menu > ul > li:nth-child(1) > a"));
        Thread.sleep(2000); //te wszystkie sleepy są bo niestety bez nich nie dzialalo,
        // wiem, ze sprawdzana jest poprawnosc, a nie działanie, ale jakos zle mi bylo jak nie dzialalo :)
        womenButton.click();
        WebElement itemButton = driver.findElement(By.linkText("Faded Short Sleeve T-shirts"));
        Thread.sleep(2000);
        itemButton.click();
        WebElement addToCartButton = driver.findElement(By.cssSelector("#add_to_cart > button > span"));
        Thread.sleep(2000);
        addToCartButton.click();
        Thread.sleep(2000);
        WebElement proceedToCheckoutButton = driver.findElement(By.cssSelector(".button-medium > span"));
        Thread.sleep(2000);
        proceedToCheckoutButton.click();
        Thread.sleep(2000);
        WebElement itemCounter = driver.findElement(By.className("heading-counter"));
        Assertions.assertEquals("Your shopping cart contains: 1 Product", itemCounter.getText());

    }

//    Zad7. Napisz test, który dodaje produkt do koszyka, a następnie usuwa go.
//    Zweryfikuj, czy usunięcie powiodło się.

    @Test
    void shouldVerifyIfItemIsRemovedFromCart() throws InterruptedException {
        driver.get("http://automationpractice.com/index.php");
        WebElement womenButton = driver.findElement(By.cssSelector("#block_top_menu > ul > li:nth-child(1) > a"));
        Thread.sleep(2000);
        womenButton.click();
        WebElement itemButton = driver.findElement(By.linkText("Faded Short Sleeve T-shirts"));
        Thread.sleep(2000);
        itemButton.click();
        WebElement addToCartButton = driver.findElement(By.cssSelector("#add_to_cart > button > span"));
        Thread.sleep(2000);
        addToCartButton.click();
        Thread.sleep(2000);
        WebElement proceedToCheckoutButton = driver.findElement(By.cssSelector(".button-medium > span"));
        Thread.sleep(2000);
        proceedToCheckoutButton.click();
        Thread.sleep(2000);
        WebElement removeButton = driver.findElement(By.cssSelector("#cart_quantity_down_1_1_0_0 > span"));
        Thread.sleep(2000);
        removeButton.click();
        Thread.sleep(2000);
        WebElement cartIsEmpty = driver.findElement(By.cssSelector("#center_column > p"));
        Assertions.assertTrue(cartIsEmpty.isDisplayed());


    }

    //Zad8.Zrefaktoruj logowanie. Utwórz metodę pomocniczą login(), która przyjmuje dwa parametry: login i hasło.
    // Użyj jej w teście sprawdzającym logowanie.

    static void login(){

        WebElement login= driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("passwd"));
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));

        password.sendKeys("1234qwerty");
        login.sendKeys("testlogowania@yopmail.com");
        submitButton.click();
    }

    @Test
    void shouldVerifyLoggingIn() throws InterruptedException {
        driver.get("http://automationpractice.com/index.php");

        WebElement signInButton = driver.findElement(By.className("login"));
        Thread.sleep(2000);
        // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); --> deprecated, alternatywą może być explicity wait
        signInButton.click();
        Thread.sleep(2000);
        WebElement pageTitle = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("AUTHENTICATION", pageTitle.getText());

        login();

        WebElement loginMessage = driver.findElement(By.className("page-heading"));
        Assertions.assertEquals("MY ACCOUNT", loginMessage.getText());
    }


    // Dodatkowy test: sprawdza czy po kliknieciu w filtr 'polyester' odpowiedni checkbox będzie zaznaczony.

    @Test
    void shouldVerifyIfPolyesterFiltrChecboxIsSelected() throws InterruptedException {
        driver.get("http://automationpractice.com/index.php");
        WebElement womenButton = driver.findElement(By.cssSelector("#block_top_menu > ul > li:nth-child(1) > a"));
        Thread.sleep(2000);
        womenButton.click();
        WebElement polyesterButton = driver.findElement(By.cssSelector("#ul_layered_id_feature_5 > li:nth-child(2) > label > a"));
        polyesterButton.click();
        WebElement polyesterCheckbox = driver.findElement(By.cssSelector("#layered_id_feature_1"));
        Assertions.assertTrue(polyesterCheckbox.isSelected());

    }


}