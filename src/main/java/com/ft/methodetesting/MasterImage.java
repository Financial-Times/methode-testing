package com.ft.methodetesting;

import com.ft.methodeapi.model.EomFile;
import com.ft.methodetesting.util.FileUtil;
import org.apache.commons.io.IOUtils;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MasterImage {

    public static final String EXPECTED_CAPTION = "Fruits of the soul";
    public static final String EXPECTED_ALT_TEXT = "Picture with fruits";
    public static final String EXPECTED_METHODE_ORIGINATING_URI = "http://api.ft.com/system/FTCOM-METHODE";
    private static final String TEST_IMAGE_UUID = UUID.randomUUID().toString();
    private static final String MASTER_IMAGE_SAMPLE_IMAGE = "images/Master_2048x1152/mi-sample-image.jpg";
    private static final String MASTER_IMAGE_ATTRIBUTES_XML = "images/Master_2048x1152/mi-sample-attributes.xml";
    private static final String MASTER_IMAGE_SYSTEM_ATTRIBUTES_XML = "images/Master_2048x1152/mi-sample-system-attributes.xml";
    private static final String MASTER_IMAGE_USAGE_TICKETS_XML = "images/Master_2048x1152/mi-sample-usage-tickets.xml";
    protected byte[] imageBinary;
    protected String imagePath, attributes, systemAttributes, usageTickets;

    public EomFile buildImage() throws Exception {
        return buildImageWithUuid(TEST_IMAGE_UUID);
    }

    public EomFile buildImageWithUuid(String uuid) throws Exception {
        imagePath = MASTER_IMAGE_SAMPLE_IMAGE;
        attributes = String.format(FileUtil.loadFile(MASTER_IMAGE_ATTRIBUTES_XML), EXPECTED_CAPTION, EXPECTED_ALT_TEXT);
        systemAttributes = FileUtil.loadFile(MASTER_IMAGE_SYSTEM_ATTRIBUTES_XML);
        usageTickets = FileUtil.loadFile(MASTER_IMAGE_USAGE_TICKETS_XML);
        imageBinary = loadImage();
        return new EomFile(uuid, "Image", imageBinary, attributes, "", systemAttributes, usageTickets, null);
    }

    protected byte[] loadImage() throws Exception {
        return IOUtils.toByteArray(MasterImage.class.getResourceAsStream("/" + imagePath));
    }

}
