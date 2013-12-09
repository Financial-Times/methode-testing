package com.ft.methodetesting;

import java.io.IOException;

import com.ft.methodeapi.model.EomFile;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * ReferenceArticlesTest
 *
 * @author Simon.Gibbs
 */
public class ReferenceArticlesTest {

    @Test
    public void shouldMakeKitchenSinkArticleAvailable() {
        assertThat(ReferenceArticles.publishedKitchenSinkArticle(), notNullValue());
    }

    @Test
    public void shouldMakeKitchenDeletedSinkArticleAvailable() {
        assertThat(ReferenceArticles.unpublishedKitchenSinkArticle(), notNullValue());
    }

    @Test
    public void shouldMakeExpectedBodyAvailable() {
        assertThat(ReferenceArticles.expectedKitchenSinkTransformedXml(), notNullValue());
    }

    @Test
    public void shouldNotBorkMultibyteCharacters() {
        EomFile result = ReferenceArticles.publishedKitchenSinkArticle().build().getEomFile();

        assertThat(result.getAttributes(),containsString("Lead headline \u00a342m for S&amp;P\u2019s \u201cup 79%\u201d"));

    }
    
    @Test
    public void shouldReplaceTokenStringsAndCanonicalize() {
    	String expectedKitchenSinkTransformedXml = ReferenceArticles.expectedKitchenSinkTransformedXml();
    	String expectedBodyWithTokenReplaced = ReferenceArticles.replaceToken(expectedKitchenSinkTransformedXml, "\\[(READ-API-TOKEN)\\]", "localhost:9090");
    	String canonicalized = ReferenceArticles.replaceToken(expectedBodyWithTokenReplaced, "<br/>", "<br></br>");
    	
    	String bodyFromFile = "";
        try {
            bodyFromFile = Resources.toString(ReferenceArticlesTest.class.getResource("expectedTransformedBodyWithTokenReplaced.txt"), Charsets.UTF_8);

            if (System.getProperty("line.separator").equals("\r\n")) {
                bodyFromFile = bodyFromFile.replace("\r", "");
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error reading from content in JAR file",e);
        }
        
        assertEquals(bodyFromFile, canonicalized);
    }
    
    
    
    @Test
    public void shouldNotReplaceNonTokenStrings() {
    	String originalString = "abcdefghijklmnopqrstuvwxyz1234567890";
		String tokenReplacedString = ReferenceArticles.replaceToken(originalString, "\\[(READ-API-TOKEN)\\]", "test");
        assertEquals(originalString, tokenReplacedString);
    }
}
