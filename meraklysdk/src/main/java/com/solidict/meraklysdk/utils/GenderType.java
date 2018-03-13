package com.solidict.meraklysdk.utils;

/**
 * Created by muazekici on 13.03.2018.
 */

public enum GenderType {

    MAN,WOMAN;

    public int getValue(){
        switch (this){
            case MAN:
                return 0;
            case WOMAN:
                return 1;
        }
        return -1;
    }
}
