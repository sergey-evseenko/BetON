package tests.content;

import adapters.ContentAdapter;
import models.Page;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import tests.BaseTest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class ContentTest extends BaseTest {

    Page page;

    @DataProvider(name = "Pages")
    public Object[][] pages() {
        return new Object[][]{
                //"about" page
                {1, "about", "About", 3},
                //"about_us_imprint" page
                {2, "about_us_imprint", "About us / Imprint", 4},
                //"cookies" page
                {4, "cookies", "Cookies", 0},
                //"data_security_policy" page
                {12, "data_security_policy", "GDPR (Data security policy)", 0},
                //"responsibility" page
                {13, "responsibility", "Responsibility", 0}
        };
    }

    @Test(description = "get document by name", dataProvider = "Pages")
    public void getDocumentByName(int id, String name, String title, int numberOfChildPages) {
        page = new ContentAdapter().getDocumentByName(name, true);
        assertEquals(page.getId(), id, "Invalid page Id");
        assertEquals(page.getName(), name, "Invalid page name");
        assertEquals(page.getTitle(), title, "Invalid title");
        assertEquals(page.getChildren().size(), numberOfChildPages, "Invalid number of child pages");
    }

    @Test(description = "validate params: with_child is false")
    public void validateFalseWithChild() {
        page = new ContentAdapter().getDocumentByName("about", false);
        assertNull(page.getChildren(), "Displaying children pages error");
    }

    //TODO: validate params: with_child is null

    @Test(description = "validate params: invalid lang")
    public void validateInvalidLang() {
        response = new ContentAdapter().validateParams("about", "qwerty", true, 200);
        assertEquals(response.getDescription(), "No translation", "validate content param error");
    }

    @Ignore //TODO Fix 500 error
    @Test(description = "validate params: lang is null")
    public void validateNullLang() {
        response = new ContentAdapter().validateParams("about", null, true, 200);
    }

    @Test(description = "validate params: invalid name")
    public void validateInvalidName() {
        response = new ContentAdapter().validateParams("qwerty", "en", true, 404);
        assertEquals(response.getMessage(), "Document with name 'qwerty' is not found in database.", "validate content param error");
    }

    @Test(description = "validate params: name is null")
    public void validateNullName() {
        response = new ContentAdapter().validateParams(null, "en", true, 400);
        assertEquals(response.getMessage(), "Required String parameter 'name' is not present", "validate content param error");
    }


}
