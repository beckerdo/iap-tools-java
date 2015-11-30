package com.jenkov.iap.write.custom;

import com.jenkov.iap.IonFieldTypes;
import com.jenkov.iap.IonUtil;
import com.jenkov.iap.write.IIonFieldWriter;

import java.lang.reflect.Field;

/**
 * Created by jjenkov on 04-11-2015.
 */
public class IonFieldWriterBoolean implements IIonFieldWriter {

    protected byte[] keyField = null;
    protected IGetterBoolean getter = null;

    public IonFieldWriterBoolean(String fieldName, IGetterBoolean getter) {
        this.keyField = IonUtil.preGenerateKeyField(fieldName);
        this.getter = getter;
    }

    @Override
    public int writeKeyAndValueFields(Object sourceObject, byte[] destination, int destinationOffset, int maxLengthLength) {

        System.arraycopy(this.keyField, 0, destination, destinationOffset, this.keyField.length);
        destinationOffset += this.keyField.length;

        return this.keyField.length + writeValueField(sourceObject, destination, destinationOffset, maxLengthLength);
    }


    @Override
    public int writeValueField(Object sourceObject, byte[] destination, int destinationOffset, int maxLengthLength) {
        boolean value = this.getter.get(sourceObject);

        if(value){
            destination[destinationOffset] = (byte) (255 & ((1 << 4) | IonFieldTypes.BOOLEAN));
        } else {
            destination[destinationOffset] = (byte) (255 & ((2 << 4) | IonFieldTypes.BOOLEAN));
        }

        return 1;    //total length of a boolean field is always 1
    }


 }
