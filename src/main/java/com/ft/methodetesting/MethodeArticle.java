package com.ft.methodetesting;

import java.io.IOException;

import com.ft.methodeapi.model.EomFile;
import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.io.Resources;

public class MethodeArticle {
	
	public static final String HEADLINE_FROM_TEST_FILE = "Eurozone collapses ...! Kaboom.";
	public static final String MARK_DELETED_TRUE = "<DIFTcomMarkDeleted>True</DIFTcomMarkDeleted>";
	public static final String MARK_DELETED_FALSE = "<DIFTcomMarkDeleted>False</DIFTcomMarkDeleted>";
	
	private static final String exampleArticleXmlTemplate = readFromFile("examplePublishedArticleData.txt");
	private static final String exampleAttributesXml = readFromFile("examplePublishedArticleAttributes.txt");

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
	
	public static class Builder {

		String articleXml = exampleArticleXmlTemplate;
		String attributesXml = exampleAttributesXml;

		public Builder withHeadline(String expectedPublishedArticleHeadline) {
	        attributesXml = attributesXml.replace(HEADLINE_FROM_TEST_FILE,expectedPublishedArticleHeadline);
	        articleXml = articleXml.replace(HEADLINE_FROM_TEST_FILE, expectedPublishedArticleHeadline);
			return this;
		}
		
		public MethodeArticle buildPublishedArticle() {
			return new MethodeArticle(articleXml, attributesXml);
		}

		public MethodeArticle buildDeletedArticle() {
			attributesXml = attributesXml.replace(MARK_DELETED_FALSE, MARK_DELETED_TRUE);
			return new MethodeArticle(articleXml, attributesXml);
		}

	}
	
	private static String readFromFile(String resourceName) {
		String bodyFromFile = "";
		try {
			bodyFromFile = Resources.toString(Resources.getResource(resourceName), Charsets.UTF_8);
		
			// because what we get back from the API uses UNIX line encodings, but when working locally on Windows, the expected file will have \r\n
			if (System.getProperty("line.separator").equals("\r\n")) {
				bodyFromFile = bodyFromFile.replace("\r", "");
			}
		} catch (IOException e) {
			throw new RuntimeException("Unexpected error reading from content in JAR file",e);
		}
		return bodyFromFile;
	}

}
