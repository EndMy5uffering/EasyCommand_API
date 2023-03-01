package com.endmysuffering.easycommands.typechecks;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.checkerframework.checker.units.qual.A;

import com.endmysuffering.easycommands.CMDArgs;
import com.endmysuffering.easycommands.CMDCommandException;
import com.endmysuffering.easycommands.CMDPair;

public class TypeChecks {
    public interface TypeCheck{
        /**
         * Checks if provided wildcard is of a speciffic type.
         * If the wildcard can be cast to the given type it will be put in the object buffer in the CMDArgs.
         * Object in the object buffer can be retreaved by args.getObject(Obj.class, "widlcard name")
         * @param args Command args passed to the command function
         * @param wildcard the wildcard to be tested
         * @param annotation the provided annotation
         * @return true if the wildcard in question is of a spceiffic type
        */
        public boolean check(CMDArgs args, String wildcard, Annotation annotation);
    }

    private static final Set<Class<?>> typeCheckAnnotations = Set.of(
        BooleanTypeCheck.class,
        ByteTypeCheck.class,
        EnumMemberCheck.class,
        FloatTypeCheck.class,
        IntTypeCheck.class,
        LongTypeCheck.class,
        EnumMemberChecks.class
    );

    public static Map<Class<?>, TypeCheck> annotationToTypeCheck = new HashMap<>();

    static{
        annotationToTypeCheck.put(IntTypeCheck.class, (args, wildcard, annotation) -> {
            try {
                Integer i = Integer.parseInt(args.getWildCard(wildcard)); 
                args.put(wildcard, i);
                return true;        
            } catch (Exception e) {
                return false;
            }
        });
        annotationToTypeCheck.put(ByteTypeCheck.class, (args, wildcard, annotation) -> {
            try {
                Byte i = Byte.parseByte(args.getWildCard(wildcard)); 
                args.put(wildcard, i);
                return true;        
            } catch (Exception e) {
                return false;
            }
        });
        annotationToTypeCheck.put(EnumMemberCheck.class, (args, wildcard, annotation) -> {
            if(annotation instanceof EnumMemberCheck emc){
                try {
                    Enum<?> i = of(emc.enumClazz(), args.getWildCard(wildcard)); 
                    args.put(wildcard, i);
                    return true;        
                } catch (Exception e) {
                    return false;
                }
            }
            return false;
        });
        annotationToTypeCheck.put(FloatTypeCheck.class, (args, wildcard, annotation) -> {
            try {
                Float i = Float.parseFloat(args.getWildCard(wildcard)); 
                args.put(wildcard, i);
                return true;        
            } catch (Exception e) {
                return false;
            }
        });
        annotationToTypeCheck.put(LongTypeCheck.class, (args, wildcard, annotation) -> {
            try {
                Long i = Long.parseLong(args.getWildCard(wildcard)); 
                args.put(wildcard, i);
                return true;        
            } catch (Exception e) {
                return false;
            }
        });
        annotationToTypeCheck.put(BooleanTypeCheck.class, (args, wildcard, annotation) -> {
            try {
                Boolean i = Boolean.parseBoolean(args.getWildCard(wildcard)); 
                args.put(wildcard, i);
                return true;        
            } catch (Exception e) {
                return false;
            }
        });
    }

    private static <E extends Enum<E>> E of(Class<E> clazz, String name) {
        E value = Enum.valueOf(clazz, name);
        return value;
    }

    public static List<CMDPair<String, CMDPair<Annotation, TypeChecks.TypeCheck>>> getTypeChecks(Annotation a) throws CMDCommandException{
        List<CMDPair<String, CMDPair<Annotation, TypeChecks.TypeCheck>>> result = new ArrayList<>();
        
        if(a instanceof IntTypeCheck itc){
            for(String wc : itc.paramNames()){
                CMDPair<Annotation, TypeChecks.TypeCheck> checkPair = 
                new CMDPair<>(a, annotationToTypeCheck.get(itc.annotationType()));
                CMDPair<String, CMDPair<Annotation, TypeChecks.TypeCheck>> resultPair = 
                new CMDPair<>(wc, checkPair);
                if(!resultPair.isNotNull() && !checkPair.isNotNull()) return new ArrayList<>();
                result.add(resultPair);
            }
        }
        if(a instanceof BooleanTypeCheck itc){
            for(String wc : itc.paramNames()){
                CMDPair<Annotation, TypeChecks.TypeCheck> checkPair = 
                new CMDPair<>(a, annotationToTypeCheck.get(itc.annotationType()));
                CMDPair<String, CMDPair<Annotation, TypeChecks.TypeCheck>> resultPair = 
                new CMDPair<>(wc, checkPair);
                if(!resultPair.isNotNull() && !checkPair.isNotNull()) return new ArrayList<>();
                result.add(resultPair);
            }
        }
        if(a instanceof ByteTypeCheck itc){
            for(String wc : itc.paramNames()){
                CMDPair<Annotation, TypeChecks.TypeCheck> checkPair = 
                new CMDPair<>(a, annotationToTypeCheck.get(itc.annotationType()));
                CMDPair<String, CMDPair<Annotation, TypeChecks.TypeCheck>> resultPair = 
                new CMDPair<>(wc, checkPair);
                if(!resultPair.isNotNull() && !checkPair.isNotNull()) return new ArrayList<>();
                result.add(resultPair);
            }
        }
        if(a instanceof FloatTypeCheck itc){
            for(String wc : itc.paramNames()){
                CMDPair<Annotation, TypeChecks.TypeCheck> checkPair = 
                new CMDPair<>(a, annotationToTypeCheck.get(itc.annotationType()));
                CMDPair<String, CMDPair<Annotation, TypeChecks.TypeCheck>> resultPair = 
                new CMDPair<>(wc, checkPair);
                if(!resultPair.isNotNull() && !checkPair.isNotNull()) return new ArrayList<>();
                result.add(resultPair);
            }
        }
        if(a instanceof LongTypeCheck itc){
            for(String wc : itc.paramNames()){
                CMDPair<Annotation, TypeChecks.TypeCheck> checkPair = 
                new CMDPair<>(a, annotationToTypeCheck.get(itc.annotationType()));
                CMDPair<String, CMDPair<Annotation, TypeChecks.TypeCheck>> resultPair = 
                new CMDPair<>(wc, checkPair);
                if(!resultPair.isNotNull() && !checkPair.isNotNull()) return new ArrayList<>();
                result.add(resultPair);
            }
        }
        if(a instanceof EnumMemberCheck itc){
            for(String wc : itc.paramNames()){
                CMDPair<Annotation, TypeChecks.TypeCheck> checkPair = 
                new CMDPair<>(a, annotationToTypeCheck.get(itc.annotationType()));
                CMDPair<String, CMDPair<Annotation, TypeChecks.TypeCheck>> resultPair = 
                new CMDPair<>(wc, checkPair);
                if(!resultPair.isNotNull() && !checkPair.isNotNull()) return new ArrayList<>();
                result.add(resultPair);
            }
        }
        if(a instanceof EnumMemberChecks itc){
            for(EnumMemberCheck emc : itc.value()){
                for(String wc : emc.paramNames()){
                    CMDPair<Annotation, TypeChecks.TypeCheck> checkPair = 
                    new CMDPair<>(emc, annotationToTypeCheck.get(emc.annotationType()));
                    CMDPair<String, CMDPair<Annotation, TypeChecks.TypeCheck>> resultPair = 
                    new CMDPair<>(wc, checkPair);
                    if(!resultPair.isNotNull() && !checkPair.isNotNull()) return new ArrayList<>();
                    result.add(resultPair);
                }
            }
        }

        return result;
    }
}
