package com.easycommands.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.easycommands.CMDListener;

public class CMDMethodCollection {

    interface CMDCallable{
        boolean call(CMDArgs args) throws CMDCommandException;
    }

    private CMDCallable callable; 

    public CMDMethodCollection(CMDFunction func){
        this.callable = (args) -> func.func(args);
    }

    public CMDMethodCollection(CMDListener listener, Method m) throws CMDCommandException{
        Class<?>[] params = m.getParameterTypes();
        if(m.isAnnotationPresent(CMDCommand.class) && params.length == 1 && params[0] == CMDArgs.class){
            this.callable = (args) -> {
                try {
                    return (boolean)m.invoke(listener, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new CMDCommandException(e.getMessage());
                }
            };
        }else{
            throw new CMDCommandException("Invalid method! " + m.getName());
        }
    }

    public boolean call(CMDArgs args) throws CMDCommandException {
        return callable.call(args);
    }


}
