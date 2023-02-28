package com.easycommands;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.endmysuffering.easycommands.CMDCommandException;
import com.endmysuffering.easycommands.CMDManager;
import com.endmysuffering.easycommands.CMDNode;
import com.endmysuffering.easycommands.MissingPermissionsException;

public class TestUtils {
    public static Method getCallMethod(){
        try {
            Method call = CMDManager.class.getDeclaredMethod("call", CommandSender.class , Command.class , String.class , String[].class );
            call.setAccessible(true);
            return call;
        } catch (NoSuchMethodException e) {
            assertTrue("No method 'call' found", false);
        } catch (SecurityException e) {
            assertTrue("Could not access the call function", false);
        }
        return null;
    }

    public static boolean makeCall(CMDManager m, CommandSender sender, Command cmd, String lable, String[] args) throws MissingPermissionsException, CMDCommandException{
        try {
            Method call = getCallMethod();
            if(call == null) {
                assertTrue("Call was null!", false);
                return false;
            }
            call.setAccessible(true);
            return (boolean) call.invoke(m, sender, cmd, lable, args);
        } catch(InvocationTargetException e){
            if(e.getTargetException() instanceof MissingPermissionsException)
                throw (MissingPermissionsException)e.getTargetException();
            if(e.getTargetException() instanceof CMDCommandException)
                throw (CMDCommandException)e.getTargetException();
            assertTrue("Error in call function! Message: " + e.getTargetException().getMessage(), false);
            return false;
        }catch (IllegalAccessException | IllegalArgumentException e) {
            assertTrue("Could not access call method: " + e.getClass().getName(), false);
            return false;
        }
    }

    public static boolean makeCall(CMDManager m, CommandSender sender, String cmd) {
        String[] split = cmd.split(" ");
        String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, split.length-1);
        try {
            return makeCall(m, sender, null, split[0].replace("/", ""), args);
        } catch (MissingPermissionsException | CMDCommandException e) {
            return false;
        }
    }


    public static List<CMDNode> getCMDStructs(String path, CMDManager m) throws CMDCommandException {
        try {
            Method preParse = m.getClass().getDeclaredMethod("preParse", String.class);
            preParse.setAccessible(true);
            String[] parts = (String[]) preParse.invoke(m, path);

            Field f = m.getClass().getDeclaredField("rootsToCMDS");
            f.setAccessible(true);

            Object pathlist = f.get(m);
            Map<String, CMDNode> paths = (Map<String, CMDNode>)pathlist;

            return paths.get(parts[0]).getPath(parts);
        }catch(CMDCommandException e){
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public static Object getField(Object instance, String fname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
        Field P0FAliases = instance.getClass().getDeclaredField(fname);
        P0FAliases.setAccessible(true);
        return P0FAliases.get(instance);
    }

    public static Method getFirstMethodWithName(Class<?> clazz, String name){
        for(Method m : clazz.getDeclaredMethods()){
            if(m.getName().equals(name)){
                return m;
            }
        }
        return null;
    }

    public static double getExecutionTime(Method method, Object instance, Object... params){
        try {
            method.setAccessible(true);
            long start = System.nanoTime();
            method.invoke(instance, params);
            return (System.nanoTime() - start)/1000.0D;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            return -1;
        }
    }
}
