/*
 * Copyright (c) 2014, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.es.ui.integration.test.store;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.es.integration.common.utils.ESIntegrationUITest;
import org.wso2.es.ui.integration.util.ESWebDriver;
import static org.testng.Assert.*;


public class ESStoreLoginLogoutTestCase extends ESIntegrationUITest {
    private ESWebDriver driver;
    private String baseUrl;

    private String currentUserName;
    private String currentUserPwd;

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        super.init();
        currentUserName = userInfo.getUserName();
        currentUserPwd = userInfo.getPassword();
        driver = new ESWebDriver();
        baseUrl = getWebAppURL();
    }

    @Test(groups = "wso2.es.store", description = "Test Store Login")
    public void testESStoreLogin() throws Exception {
        driver.get(baseUrl + "/store");
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(currentUserName);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(currentUserPwd);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        assertTrue(isElementPresent(By.linkText("My Items")), "My Items link missing");
        assertTrue(isElementPresent(By.linkText(currentUserName)), "Logged in user not shown");
    }

    @Test(groups = "wso2.es.store", description = "Test Store Logout",
            dependsOnMethods = "testESStoreLogin")
    public void testESStoreLogout() throws Exception {
        driver.get(baseUrl + "/store");
        driver.findElement(By.linkText(currentUserName)).click();
        driver.findElement(By.linkText("Sign out")).click();
        assertTrue(isElementPresent(By.linkText("Sign in")), "Sign in link missing");
        assertEquals("Register", driver.findElement(By.id("btn-register")).getText(),
                "Register button missing");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
