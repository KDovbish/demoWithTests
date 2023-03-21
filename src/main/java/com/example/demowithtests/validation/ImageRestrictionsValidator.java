package com.example.demowithtests.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Проверка ограничения, устанавливаемого анотацией ImageRestrictions для типа MultipartFile
 */
public class ImageRestrictionsValidator  implements ConstraintValidator<ImageRestrictions, MultipartFile> {

    private int maxwidth;
    private int maxheight;

    @Override
    public void initialize(ImageRestrictions constraintAnnotation) {
        maxwidth = constraintAnnotation.maxwidth();
        maxheight = constraintAnnotation.maxheight();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        try {
            BufferedImage bufferedImage = ImageIO.read( new ByteArrayInputStream(multipartFile.getBytes()) );
            if (bufferedImage.getWidth() > maxwidth || bufferedImage.getHeight() > maxheight) return false;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

}
