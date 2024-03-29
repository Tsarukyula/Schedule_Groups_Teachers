package org.ait.schedule.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TeachersPage {
    private WebDriver driver;

    public TeachersPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo() {
        driver.get("https://primetimetable.com/publish/?id=e189aba9-787f-463e-849d-7a372dfd4788&v=1&time=6&skin=3#id=e189aba9-787f-463e-849d-7a372dfd4788&view=1");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='viewIndicator class']")));
        WebElement targetElement = driver.findElement(By.xpath("//div[@class='viewIndicator class']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(targetElement).perform();
        WebElement clickableElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='switcherTab teacher hint--rounded hint--bounce hint--bottom']")));
        actions.moveToElement(clickableElement).perform();
        clickableElement.click();
    }

    public List<WebElement> getTeacherElements(String teacherName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(text(), '" + teacherName + "')]")));
        return driver.findElements(By.xpath("//div[contains(text(), '" + teacherName + "')]"));
    }
}
