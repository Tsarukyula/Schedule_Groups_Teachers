package org.ait.schedule.tests;

import org.ait.schedule.pages.GroupsPage;
import org.ait.schedule.pages.TeachersPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ScheduleWriterTest extends TestBase{

    @Test
    public void schoolScheduleAllGroupsAndTeachersTest() {
        GroupsPage groupsPage = new GroupsPage(driver);
        TeachersPage teachersPage = new TeachersPage(driver);

        getAllGroups(groupsPage);
        getAllTeachers(teachersPage);
    }

    public void getAllGroups(GroupsPage groupsPage) {
        List<String> groupNames = Arrays.asList("27E", "30E", "31.1M", "31.2M", "33E",
                "34.1M", "34.2M", "34.3M", "34M-JP-1", "34M-JP-2", "34M-QAP",
                "35E", "35E-JP", "36E", "37.1M", "37.2M", "38E", "39.1E",
                "39.2E", "40.1M", "40.2M", "41E", "42.1EL", "42.2ELL",
                "42.3ELL", "43M", "44E", "45.1EL", "45.2ELL");

        try (FileWriter writer = new FileWriter("schedule.txt", true)) {
            writer.write("Schedule = {\n groups: [\n");
            for (int i = 0; i < groupNames.size(); i++) {
                String groupName = groupNames.get(i);
                groupsPage.navigateTo();
                List<WebElement> groupElements = groupsPage.getGroupElements(groupName);

                for (WebElement groupElement : groupElements) {
                    try {
                        String groupNameText = groupElement.getText();
                        groupElement.click();
                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'AIT-TR (Berlin) - " + groupName + "')]")));
                        String groupUrl = driver.getCurrentUrl();

                        writer.write("{\n\"name\": \"" + groupNameText + "\",\n");
                        writer.write("\"src\": \"" + groupUrl + "\"\n}");

                        if (i != groupNames.size() - 1) {
                            writer.write(","); // Не последний элемент в списке
                        }
                        writer.write("\n"); // Последний элемент в списке

                        driver.navigate().back();
                        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(text(), '" + groupName + "')]")));
                    } catch (NoSuchElementException e) {
                        System.out.println("Элемент с группой " + groupName + " не найден на странице.");
                        continue;
                    }
                }
            }
            writer.write("],\n");
            System.out.println("Группы добавлены в файл schedule.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllTeachers(TeachersPage teachersPage) {
        List<String> teacherNames = Arrays.asList("Alisher Khamidov", "Nadezhda Zolotykh", "Andrey Belov", "Marsel Sidikov",
                "Irina Baker", "Vladimir Leonov", "Julia Smirnova", "Pavel Bulich", "Daniil Tkachenko", "Alexey Rudakov",
                "Kristina Tomash", "Leonid Kleimann", "Evgeny Grazhdansky", "Andrej Reutow", "Dmitrii Kuvaldin",
                "Sergey Razin", "Igor Eidelman", "Andrey Bakhtinov", "Stanislav Perminov", "Andrey Pomelov",
                "Gleb Zavertayev", "Igor Zimmermann", "Edward Fish", "Sergey Iryupin", "IlyasM", "Sergey Bugaenko",
                "Mikhail Smokotnin", "Nelli Efremyan", "Emin Karapetyan", "Artyom Bakharev", "Karlen Agababyan",
                "Hayk Inants", "Ilya Tkachov", "Ekaterina Zavertyaeva", "Vitaliy Vinnik");

        teachersPage.navigateTo();

        try (FileWriter writer = new FileWriter("schedule.txt", true)) {
            writer.write("teachers: [\n");
            for (int i = 0; i < teacherNames.size(); i++) {
                List<WebElement> teacherElements = teachersPage.getTeacherElements(teacherNames.get(i));

                for (WebElement teacherElement : teacherElements) {
                    try {
                        String teacherNameText = teacherElement.getText();
                        teacherElement.click();
                        String teacherUrl = driver.getCurrentUrl();

                        writer.write("{\n\"name\": \"" + teacherNameText + "\",\n");
                        writer.write("\"src\": \"" + teacherUrl + "\"\n}");

                        if (i < teacherNames.size() - 1) {
                            writer.write(",\n"); // Не последний элемент в списке
                        } else {
                            writer.write("\n"); // Последний элемент в списке
                        }

                        driver.navigate().back();
                    } catch (NoSuchElementException e) {
                        System.out.println("Элемент с преподавателем " + teacherNames.get(i) + " не найден на странице.");
                        continue;
                    }
                }
            }
            writer.write("]\n}");
            System.out.println("Преподаватели добавлены в файл schedule.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
