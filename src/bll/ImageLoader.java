package bll;

import utility.ImageFormat;

public class ImageLoader {

    public ImageFormat extractFormat(String name) {
        ImageFormat imageFormat = null;
        int index = name.lastIndexOf('.');
        String format = "";
        if (index > 0 && index < name.length() - 1) {
            format = name.substring(index + 1).toLowerCase(); // Converting to lowercase for case-insensitive comparison
        }
        ImageFormat[] imageFormats = ImageFormat.values();
        for (ImageFormat elem : imageFormats) {
            if (elem.getValue().equalsIgnoreCase(format)) {
                imageFormat = elem;
                break; // No need to continue looping if a match is found
            }
        }
        return imageFormat; // Returns null if no match is found
    }
}
