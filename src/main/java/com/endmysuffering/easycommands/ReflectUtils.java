package com.endmysuffering.easycommands;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectUtils {

    public static String getMethodSignature(Method m){
        return m.getDeclaringClass().getName() + 
        "." + m.getName() + "(" + 
        String.join(",", List.of(m.getParameters()).stream().map(e -> e.getName()).collect(Collectors.toList())) + 
        ") -> " + m.getReturnType().getName();
    }
    
}
