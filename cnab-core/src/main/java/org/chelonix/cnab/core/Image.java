
package org.chelonix.cnab.core;

import java.util.Objects;
import java.util.StringJoiner;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;

/**
 * An application image for this CNAB bundle
 * 
 */
public class Image {

    /**
     * A cryptographic hash digest of the contents of the image that can be used to validate the image. This may be interpreted differently based on imageType
     *
     */
    @JsonbProperty("contentDigest")
    private String contentDigest;

    /**
     * A description of the purpose of this image
     *
     */
    @JsonbProperty("description")
    private String description;

    /**
     * A resolvable reference to the image. This may be interpreted differently based on imageType, but the default is to treat this as an OCI image
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

    public Image() { }

    @Override
    public String toString() {
        return new StringJoiner(", ", Image.class.getSimpleName() + "[", "]")
                .add("contentDigest='" + contentDigest + "'")
                .add("description='" + description + "'")
                .add("image='" + image + "'")
                .add("imageType=" + imageType)
                .add("labels=" + labels)
                .add("mediaType='" + mediaType + "'")
                .add("size=" + size)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image1 = (Image) o;
        return size == image1.size &&
                Objects.equals(contentDigest, image1.contentDigest) &&
                Objects.equals(description, image1.description) &&
                image.equals(image1.image) &&
                imageType == image1.imageType &&
                Objects.equals(labels, image1.labels) &&
                Objects.equals(mediaType, image1.mediaType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentDigest, description, image, imageType, labels, mediaType, size);
    }
}
