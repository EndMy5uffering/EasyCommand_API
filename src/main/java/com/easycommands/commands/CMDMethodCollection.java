package com.easycommands.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.easycommands.commands.CMDCommandException.ErrorReason;

public class CMDMethodCollection {
    public CMDFunction cmdFunction;
    public Method method;

    public CMDMethodCollection(Object func) throws CMDCommandException{
        if(func instanceof CMDFunction){
            cmdFunction = (CMDFunction) func;
        }else if(func instanceof Method){
            Method m = (Method) func;
            Class<?>[] argList = m.getParameterTypes();
            if(m.getReturnType() == boolean.class && argList.length == 1 && argList[0].getClass().equals(CMDArgs.class.getClass()))
                this.method = m;
            else
                throw new CMDCommandException("Give function was invalid(" + m.getName() + ")", ErrorReason.OTHER);
        }
    }

    public boolean call(CMDArgs args) throws CMDCommandException {
        if(this.cmdFunction != null) return this.cmdFunction.func(args);
        if(this.method != null){
            try {
                return (boolean) this.method.invoke(args);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new CMDCommandException(e.getMessage(), ErrorReason.COMMAND_FUNCTION_INVOCATION_EXCEPTION);
            }
        }

        throw new CMDCommandException(ErrorReason.FUNCTION_WAS_NULL);
            
    }
}
