package com.shieldpointcapital.lrms.domain.converter;

import com.shieldpointcapital.lrms.security.AesEncryption;

import jakarta.persistence.*;
@Converter
public class EncryptedStringConverter
    implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String plainText) {
        // encrypt before saving to database
        return AesEncryption.encrypt(plainText);
    }

    @Override
    public String convertToEntityAttribute(String encrypted) {
        // decrypt after reading from database
        return AesEncryption.decrypt(encrypted);
    }
}
