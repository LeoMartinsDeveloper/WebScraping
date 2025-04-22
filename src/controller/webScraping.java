package controller;

import java.io.*;
import java.util.zip.*; //funcao zip do pacote java util
import org.openqa.selenium.WebDriver; //webdriver do selenium, pra controlar o navegador
import org.openqa.selenium.chrome.ChromeDriver; //para controlar o chrome especificamente
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import java.util.HashMap; //para criar mapa
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement; //para indicar onde clicar (elementos da pagina como botoes e etc)
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;

public class webScraping {
    public static void main(String[] args) throws Exception { //metodo main
        dataScrap(); //chamando o metodo datascrap antes de tudo, pra ja executar o mesmo
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("C:\\Users\\Leo\\Desktop\\ArquivosPDFIntuitive.zip")); //criando um objeto utilizando o java util zip, com a funcao de criar um arquivo comprimido a partir dos PDFS baixados
        for (String arq : new String[]{"C:\\copy3_of_Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf", "C:\\copy_of_Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf"}) { //um for criado para localizar os dois pdfs baixados e comecar a comprimi-los em zip
            FileInputStream fis = new FileInputStream(arq);
            zipOut.putNextEntry(new ZipEntry(new File(arq).getName()));
            fis.transferTo(zipOut);
            fis.close();
            zipOut.closeEntry();
        }
        zipOut.close(); //encerrando a compressao
    }
    private static void dataScrap() { //criando o metodo datascrap, que é o metodo principal onde vai de fato ser realizado o scraping
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        HashMap<String, Object> chromePrefs = new HashMap<>(); //setando um mapa para alterar algumas preferencias do chrome
        chromePrefs.put("download.default_directory", "C:\\");
        chromePrefs.put("plugins.always_open_pdf_externally", true);

        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-blink-features=AutomationControlled");

        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", null);
        options.setExperimentalOption("prefs", chromePrefs);

        options.addArguments("window-size=1920,1080"); //para abrir a janela do chrome em determinada resolucao


        WebDriver driver = new ChromeDriver(options);  //interface webdriver que faz parte do selenium, para fazer a funcao dos cliques na pagina
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos");
        WebElement aceitarCookies = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'br-button') and contains(@class, 'btn-accept')]")));
        aceitarCookies.click();
        driver.manage().window().maximize();

        WebElement anexo1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Anexo I.']")));
        anexo1.click();

        WebElement anexo2 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Anexo II.']")));
        anexo2.click();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } //um try pra pausar o programa por 5s









        //link download pdf 1 https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf
        //link download pdf 2 https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos/Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf


    }

}
