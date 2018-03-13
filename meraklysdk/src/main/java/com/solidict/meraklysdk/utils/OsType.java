package com.solidict.meraklysdk.utils;

/**
 * Created by emrahkucuk on 30/11/2017.
 */

public enum OsType {
    ANDROID,IOS;

    public int getValue(){
        switch (this){
            case ANDROID:
                return 0;
            case IOS:
                return 1;
        }
        return -1;
    }
}
