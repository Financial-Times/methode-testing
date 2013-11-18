package com.ft.methodetesting;

import java.io.IOException;

import com.ft.methodeapi.model.EomFile;
import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.io.Resources;

public class MethodeArticle {
	
	public static final String HEADLINE_FROM_TEST_FILE = "Eurozone collapses ...! Kaboom.";
	public static final String MARK_DELETED_TRUE = "<DIFTcomMarkDeleted>True</DIFTcomMarkDeleted>";
	public static final String MARK_DELETED_FALSE = "<DIFTcomMarkDeleted>False</DIFTcomMarkDeleted>";

	private String articleXml;
	private String attributesXml;

	public MethodeArticle(String articleXml, String attributesXml) {
		this.articleXml = articleXml;
		this.attributesXml = attributesXml;
	}

	public String getArticleXml() {
		return articleXml;
	}
	
	public String getAttributesXml() {
		return attributesXml;
	}
	
	public EomFile getEomFile() {
		return new EomFile("","EOM::CompoundStory",
                articleXml.getBytes(Charsets.UTF_8),
                attributesXml
            );
	}
	
	@Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .add("articleXml", articleXml)
                .add("attributesXml", attributesXml)
                .toString();
    }

    public static Builder builder(String articleXml, String attributesXml) {
        Builder builder = new Builder();
        builder.articleXml = articleXml;
        builder.attributesXml = attributesXml;
        return builder;
    }
	
	public static class Builder {

		private String articleXml;
		private String attributesXml;

		public Builder withHeadline(String expectedPublishedArticleHeadline) {
	        attributesXml = attributesXml.replace(HEADLINE_FROM_TEST_FILE,expectedPublishedArticleHeadline);
	        articleXml = articleXml.replace(HEADLINE_FROM_TEST_FILE, expectedPublishedArticleHeadline);
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
			return published().article();
		}

		public MethodeArticle buildDeletedArticle() {
            return deleted().article();
		}

        public MethodeArticle article() {
            return build();
        }

        public MethodeArticle build() {
            return new MethodeArticle(articleXml, attributesXml);
        }

	}
	


}
