package com.ft.methodetesting;

import org.hamcrest.CoreMatchers;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

/**
 * MethodeArticleBuilderTest
 *
 * @author Simon.Gibbs
 */
public class MethodeArticleBuilderTest {

    public static final String EDITED_HEADLINE = "Changed";

    @Test
    public void builtArticleShouldContainExampleHeadlineByDefault() {
        assertThat(ReferenceArticles.publishedKitchenSinkArticle().build().getArticleXml(),containsString(MethodeArticle.HEADLINE_FROM_TEST_FILE));
    }

    @Test
    public void builtArticleAttributesShouldContainExampleHeadlineByDefault() {
        assertThat(ReferenceArticles.publishedKitchenSinkArticle().build().getAttributesXml(),containsString(MethodeArticle.HEADLINE_FROM_TEST_FILE));
    }

    @Test
    public void builtArticleShouldNotContainExampleHeadlineWhenChanged() {
        String articleXml = ReferenceArticles.publishedKitchenSinkArticle().withHeadline(EDITED_HEADLINE).build().getArticleXml();
        assertThat(articleXml,not(containsString(MethodeArticle.HEADLINE_FROM_TEST_FILE)));
        assertThat(articleXml,containsString(EDITED_HEADLINE));
    }

    @Test
    public void builtArticleAttributesShouldNotContainExampleHeadlineWhenChanged() {
        String attributesXml = ReferenceArticles.publishedKitchenSinkArticle().withHeadline(EDITED_HEADLINE).build().getAttributesXml();
        assertThat(attributesXml,not(containsString(MethodeArticle.HEADLINE_FROM_TEST_FILE)));
        assertThat(attributesXml,containsString(EDITED_HEADLINE));
    }

	@Test
	public void builtArticleShouldHaveCorrectWorkflowStatus() {
		assertThat(ReferenceArticles.publishedKitchenSinkArticle().build().getWorkflowStatus(), containsString(MethodeArticle.WEB_READY));
	}

	@Test
	public void builtArticleShouldHaveChangedWorkflowStatus() {
		String workflowStatus = ReferenceArticles.publishedKitchenSinkArticle().withWorkflowStatus(MethodeArticle.WEB_REVISE).build().getWorkflowStatus();
		assertThat(workflowStatus, is(MethodeArticle.WEB_REVISE));
	}

	@Test
	public void builtArticleShouldHaveChangedSource() {
		String newSource = "Reuters";
		String newSourceXml = String.format("<Source title=\"%s\"><SourceCode>%s</SourceCode><SourceDescriptor>%s</SourceDescriptor>", newSource, newSource, newSource);

		String attributesXml = ReferenceArticles.publishedKitchenSinkArticle().withSource(newSource).build().getAttributesXml();
		assertThat(attributesXml, CoreMatchers.containsString(newSourceXml));
	}

}
