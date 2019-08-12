
package org.chelonix.cnab.core;

import java.util.Objects;
import java.util.StringJoiner;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;


/**
 * A bootstrapping image for the CNAB bundle.
 * 
 */
public class InvocationImage {

    /**
     * A cryptographic hash digest of the contents of the image that can be used to validate the image. This may be interpreted differently based on imageType
     * 
     */
    @JsonbProperty("contentDigest")
    private String contentDigest;
    /**
     * A resolvable reference to the image. This may be interpreted differently based on imageType, but the default is to treat this as an OCI image
     * (Required)
     * 
     */
    @JsonbProperty("image")
    private String image;
    /**
     * The type of image. If this is not specified, 'oci' is assumed
     * 
     */
    @JsonbProperty("imageType")
    @JsonbTypeAdapter(ImageType.ImageTypeMapper.class)
    private ImageType imageType = ImageType.OCI;
    /**
     * Key/value pairs that used to specify identifying attributes of invocation images
     * 
     */
    @JsonbProperty("labels")
    private Labels labels;
    /**
     * The media type of the image
     * 
     */
    @JsonbProperty("mediaType")
    private String mediaType;
    /**
     * The image size in bytes
     * 
     */
    @JsonbProperty("size")
    private int size;

    /**
     * No args constructor for use in serialization
     * 
     */
    public InvocationImage() {
    }

    /**
     * 
     * @param contentDigest
     * @param imageType
     * @param labels
     * @param image
     * @param mediaType
     * @param size
     */
    public InvocationImage(String contentDigest, String image, ImageType imageType, Labels labels, String mediaType, int size) {
        super();
        this.contentDigest = contentDigest;
        this.image = image;
        this.imageType = imageType;
        this.labels = labels;
        this.mediaType = mediaType;
        this.size = size;
    }

    /**
     * A cryptographic hash digest of the contents of the image that can be used to validate the image. This may be interpreted differently based on imageType
     * 
     */
    public String getContentDigest() {
        return contentDigest;
    }

    /**
     * A cryptographic hash digest of the contents of the image that can be used to validate the image. This may be interpreted differently based on imageType
     * 
     */
    public void setContentDigest(String contentDigest) {
        this.contentDigest = contentDigest;
    }

    public InvocationImage withContentDigest(String contentDigest) {
        this.contentDigest = contentDigest;
        return this;
    }

    /**
     * A resolvable reference to the image. This may be interpreted differently based on imageType, but the default is to treat this as an OCI image
     * (Required)
     * 
     */
    public String getImage() {
        return image;
    }

    /**
     * A resolvable reference to the image. This may be interpreted differently based on imageType, but the default is to treat this as an OCI image
     * (Required)
     * 
     */
    public void setImage(String image) {
        this.image = image;
    }

    public InvocationImage withImage(String image) {
        this.image = image;
        return this;
    }

    /**
     * The type of image. If this is not specified, 'oci' is assumed
     * 
     */
    public ImageType getImageType() {
        return imageType;
    }

    /**
     * The type of image. If this is not specified, 'oci' is assumed
     * 
     */
    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public InvocationImage withImageType(ImageType imageType) {
        this.imageType = imageType;
        return this;
    }

    /**
     * Key/value pairs that used to specify identifying attributes of invocation images
     * 
     */
    public Labels getLabels() {
        return labels;
    }

    /**
     * Key/value pairs that used to specify identifying attributes of invocation images
     * 
     */
    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public InvocationImage withLabels(Labels labels) {
        this.labels = labels;
        return this;
    }

    /**
     * The media type of the image
     * 
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * The media type of the image
     * 
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public InvocationImage withMediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    /**
     * The image size in bytes
     * 
     */
    public int getSize() {
        return size;
    }

    /**
     * The image size in bytes
     * 
     */
    public void setSize(int size) {
        this.size = size;
    }

    public InvocationImage withSize(int size) {
        this.size = size;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", InvocationImage.class.getSimpleName() + "[", "]")
                .add("contentDigest='" + contentDigest + "'")
                .add("image='" + image + "'")
                .add("imageType='" + imageType + "'")
                .add("labels=" + labels)
                .add("mediaType='" + mediaType + "'")
                .add("size=" + size)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvocationImage that = (InvocationImage) o;
        return size == that.size &&
                Objects.equals(contentDigest, that.contentDigest) &&
                image.equals(that.image) &&
                Objects.equals(imageType, that.imageType) &&
                Objects.equals(labels, that.labels) &&
                Objects.equals(mediaType, that.mediaType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentDigest, image, imageType, labels, mediaType, size);
    }
}
