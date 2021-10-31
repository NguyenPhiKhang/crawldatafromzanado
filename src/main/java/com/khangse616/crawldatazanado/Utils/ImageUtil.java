package com.khangse616.crawldatazanado.Utils;

import com.khangse616.crawldatazanado.repositories.DataImageRepository;
import org.apache.commons.lang3.RandomStringUtils;

public class ImageUtil {
    public static String fileName(DataImageRepository imageRepository, String typeImage) {
        String nameImage = "";
        do {
            nameImage = RandomStringUtils.randomAlphanumeric(20).concat(".").concat(typeImage.toLowerCase());
        } while (imageRepository.existsDataImageById(nameImage));
        return nameImage;
    }
}
