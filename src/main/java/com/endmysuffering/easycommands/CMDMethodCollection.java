package com.endmysuffering.easycommands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CMDMethodCollection {

    interface CMDCallable{
        boolean call(CMDArgs args) throws CMDCommandException;
    }

    private PlayerCommand playerCommand = null;
    private ConsoleCommand consoleCommand = null;
    private CMDCallable callable; 

    public CMDMethodCollection(CMDFunction func){
        this.callable = (args) -> func.func(args);
    }

    public CMDMethodCollection(CMDListener listener, Method m) throws CMDCommandException{
        Class<?>[] params = m.getParameterTypes();
        if(m.isAnnotationPresent(PlayerCommand.class)) 
            playerCommand = m.getAnnotation(PlayerCommand.class);
        if(m.isAnnotationPresent(ConsoleCommand.class)) 
            consoleCommand = m.getAnnotation(ConsoleCommand.class);
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

    public boolean isConsoleCommand(){
        return this.consoleCommand != null;
    }

    public boolean isPlayerCommand(){
        return this.playerCommand != null;
    }

    public PlayerCommand getPlayerCommand(){
        return this.playerCommand;
    }

    public ConsoleCommand getConsoleCommand(){
        return this.consoleCommand;
    }

}
