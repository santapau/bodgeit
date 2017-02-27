package net.continuumsecurity.examples.bodgeit;

import net.continuumsecurity.Config;
import net.continuumsecurity.Credentials;
import net.continuumsecurity.UserPassCredentials;
import net.continuumsecurity.behaviour.ILogin;
import net.continuumsecurity.behaviour.ILogout;
import net.continuumsecurity.behaviour.INavigable;
import net.continuumsecurity.web.WebApplication;

import org.apache.velocity.runtime.RuntimeConstants;
import org.openqa.selenium.By;

import javax.swing.plaf.synth.SynthTextAreaUI;

/**
 * Created by paul on 22/02/17.
 */
public class BodgeitTasksApplication  extends WebApplication implements ILogin,
        ILogout,INavigable {

    @Override
    public void logout() {
        driver.findElement(By.linkText("Logout")).click();
    }

    @Override
    public void login(Credentials credentials) {
        UserPassCredentials creds = new UserPassCredentials(credentials);
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(creds.getUsername());
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(creds.getPassword());
        driver.findElement(By.id("submit")).click();
    }

    @Override
    public void openLoginPage() {
        driver.get(Config.getInstance().getBaseUrl() + "/login.jsp");
        findAndWaitForElement(By.id("username"));
    }

    @Override
    public boolean isLoggedIn() {
        if (driver.getPageSource().contains("Logout")){
            return true;
        }
        return false;
    }

    @Override
    public void navigate() {
        driver.get(Config.getInstance().getBaseUrl());

        driver.findElement(By.linkText("Home")).click();
        findAndWaitForElement(By.tagName("table"));

        driver.findElement(By.linkText("About Us")).click();
        findAndWaitForElement(By.linkText("Scoring page"));

        driver.findElement(By.linkText("Contact Us")).click();
        findAndWaitForElement(By.id("submit"));

        driver.findElement(By.linkText("Your Basket")).click();
        findAndWaitForElement(By.id("update"));

        driver.findElement(By.linkText("Search")).click();
        findAndWaitForElement(By.linkText("Advanced Search"));

    }
}
