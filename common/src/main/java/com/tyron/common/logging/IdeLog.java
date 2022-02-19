package com.tyron.common.logging;

import androidx.annotation.NonNull;

import java.util.logging.Logger;

public class IdeLog {

    @NonNull
    public static Logger getLogger() {
        return Logger.getGlobal();
    }

    @NonNull
    public static Logger getCurrentLogger(@NonNull Object clazz) {
        Logger logger = Logger.getLogger(clazz.getClass()
                                                 .getSimpleName());
        if (logger.getParent() != getLogger()) {
            logger.setParent(getLogger());
        }
        return logger;
    }
}
