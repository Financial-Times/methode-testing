package com.ft.methodetesting;

import org.junit.Test;

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
        assertThat(ReferenceArticles.expectedKichenSinkTransformedXml(), notNullValue());
    }

}
