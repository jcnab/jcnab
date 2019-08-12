package org.chelonix.cnab.core;

import javax.json.bind.adapter.JsonbAdapter;

public enum ImageType {

    OCI("oci"), DOCKER("docker");

    private String type;

    ImageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static class ImageTypeMapper implements JsonbAdapter<ImageType, String> {
        @Override
        public String adaptToJson(ImageType imageType) throws Exception {
            return imageType.type;
        }

        @Override
        public ImageType adaptFromJson(String s) throws Exception {
            return ImageType.valueOf(s.toUpperCase());
        }
    }
}
