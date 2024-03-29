package com.endmysuffering.easycommands.typechecks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings({"rawtypes"})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(EnumMemberChecks.class)
public @interface EnumMemberCheck {
    public Class<? extends Enum> enumClazz();
    public String[] paramNames();
}
