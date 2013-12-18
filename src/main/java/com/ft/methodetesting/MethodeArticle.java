package com.ft.methodetesting;

import com.ft.methodeapi.model.EomFile;
import com.ft.methodetesting.xml.Xml;
import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class MethodeArticle {
	
	public static final String HEADLINE_FROM_TEST_FILE = "Lead headline \u00a342m for S&amp;P\u2019s \u201cup 79%\u201d";
	public static final String MARK_DELETED_TRUE = "<DIFTcomMarkDeleted>True</DIFTcomMarkDeleted>";
	public static final String MARK_DELETED_FALSE = "<DIFTcomMarkDeleted>False</DIFTcomMarkDeleted>";
	public static final String WEB_REVISE = "Stories/WebRevise";
	public static final String WEB_READY = "Stories/WebReady";

	private String articleXml;
	private String attributesXml;
	private String workflowStatus;

	public MethodeArticle(String articleXml, String attributesXml, String workflowStatus) {
		this.articleXml = articleXml;
		this.attributesXml = attributesXml;
		this.workflowStatus = workflowStatus;
	}

	public String getArticleXml() {
		return articleXml;
	}
	
	public String getAttributesXml() {
		return attributesXml;
	}

	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public EomFile getEomFile() {
		return new EomFile("","EOM::CompoundStory",
                articleXml.getBytes(Charsets.UTF_8),
                attributesXml, workflowStatus
            );
	}
	
	@Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("articleXml", articleXml)
                .add("attributesXml", attributesXml)
				.add("workflowStatus", workflowStatus)
                .toString();
    }

    public static Builder builder(String articleXml, String attributesXml, String workflowStatus) {
        Builder builder = new Builder();
        builder.articleXml = articleXml;
        builder.attributesXml = attributesXml;
		builder.workflowStatus = workflowStatus;
        return builder;
    }
	
	public static class Builder {

		private String articleXml;
		private String attributesXml;
		private String workflowStatus;

        private Builder() { }

		public Builder withHeadline(String expectedPublishedArticleHeadline) {
	        attributesXml = attributesXml.replace(HEADLINE_FROM_TEST_FILE,expectedPublishedArticleHeadline);
	        articleXml = articleXml.replace(HEADLINE_FROM_TEST_FILE, expectedPublishedArticleHeadline);
			return this;
		}

		public Builder withWorkflowStatus(String workflowStatus) {
			this.workflowStatus = workflowStatus;
			return this;
		}

        public Builder published() {
            Preconditions.checkArgument(!this.attributesXml.contains(MARK_DELETED_TRUE),"already deleted");
            return this;
        }

        public Builder deleted() {
            attributesXml = attributesXml.replace(MARK_DELETED_FALSE, MARK_DELETED_TRUE);
            return this;
        }
		
		public MethodeArticle buildPublishedArticle() {
			return published().build();
		}

		public MethodeArticle buildDeletedArticle() {
            return deleted().build();
		}

        public MethodeArticle build() {
            Xml.assertParseable(articleXml);
            Xml.assertParseable(attributesXml);
            return new MethodeArticle(articleXml, attributesXml, workflowStatus);
        }

	}
	


}
