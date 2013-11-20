package com.ft.methodetesting;

import com.ft.methodeapi.model.EomFile;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
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

}
