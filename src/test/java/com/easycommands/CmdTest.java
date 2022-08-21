package com.easycommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.easycommands.commands.CMDManager;
import com.easycommands.commands.CMDStruct;
import com.easycommands.commands.CMDCommandException;
import com.easycommands.commands.MissingPermissionsException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.junit.Test;

public class CmdTest {
    
    public String DEFAULTPERM = "DEFAULTPERM";
    public String TESTPERM = "Test.Perm";

    private CMDManager getCMD(){
        CMDManager m = new CMDManager();

        assertRegister("cmd", m);
        assertRegister("cmd arg1 arg1 arg1", m);
        assertRegister("cmd arg1 arg1 arg1 <...>", m);
        assertRegister("cmd arg2 arg1 arg1", m);
        assertRegister("cmd arg3 arg1 arg1", m);
        assertRegister("cmd arg3 arg1 permlocked", m);
        assertRegister("cmd arg3 arg2 arg1", m);
        assertRegister("cmd <arg> arg1 arg1", m);
        assertRegister("cmd <arg> arg2 arg1", m);
        assertRegister("cmd <arg> arg1 <arg2>", m);

        m.registerAliase("cmd", "c");
        assertTrue("Could not register tab lookup for: cmd arg1", m.registerTabLookup("cmd <arg>", (a) -> { return List.of("tmp1", "tmp2", "tmp3"); }));

        assertTrue("Could not register permmision check for: cmd", m.registerPermissionCheck("cmd", (player) -> {
            return player.hasPermission(DEFAULTPERM);
        }));

        assertTrue("Could not register permmision check for: cmd arg3 arg1 permlocked", m.registerPermissionCheck("cmd arg3 arg1 permlocked", (player) -> {
            return player.hasPermission(TESTPERM);
        }));

        return m;
    }

    @Test
    public void CMDRegistrationTest(){
        CMDManager m = new CMDManager();

        m.register("cmd", (a)-> { return true; });
        m.register("cmd arg1", (a)-> { return true; });

        try {
            List<CMDStruct> structs = getCMDStructs("cmd", m);
            assertTrue("Function was not added for simple command /cmd" ,structs.get(0).getFunc() != null);
        } catch (CMDCommandException e1) {
            assert(false);
        }
    }

    @Test
    public void CMDSimpleTest(){
        CMDManager m = getCMD();
        TestPlayer player = new TestPlayer();

        player.permissions.add(DEFAULTPERM);

        try {
            assertTrue("Call of base command returned false!", makeCall(m, player, null, "cmd", new String[0]));
        } catch (MissingPermissionsException | CMDCommandException e) {
            assertTrue("Error while calling: " + e.getMessage(), false);
        }
        assertTrue("Some error occured while calling the command!", player.messages.size() == 0);
        if(player.messages.size() > 0){
            for(String s: player.messages) 
                System.out.println("[MESSAGE AT PLAYER END]: " + s);
        }
    }


    @Test
    public void faultyCommandTest(){
        CMDManager m = getCMD();
        TestPlayer player = new TestPlayer();

        player.permissions.add(DEFAULTPERM);

        try {
            assertTrue("Call of base command returned false!", makeCall(m, player, null, "cmd", new String[]{"a" , "b", "c", "d", "e"}));
        } catch (MissingPermissionsException | CMDCommandException e) {
            assertTrue("Error while calling: " + e.getMessage(), false);
        }
        assertTrue("Some error occured while calling the command!", player.messages.size() == 1);
        if(player.messages.size() > 0){
            for(String s: player.messages) 
                System.out.println("[MESSAGE AT PLAYER END]: " + s);
        }
    }

    @Test
    public void lookupTest(){
        CMDManager m = getCMD();
        TestPlayer player = new TestPlayer();

        player.permissions.add(DEFAULTPERM);

        List<String> results = m.onTabComplete(player, null, "cmd", new String[]{""});
        List<String> shouldBe = List.of("arg1", "arg2", "arg3", "tmp1", "tmp2", "tmp3");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());
        
        results = m.onTabComplete(player, null, "cmd", new String[]{"asdf", ""});
        shouldBe = List.of("arg1", "arg2");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());

        results = m.onTabComplete(player, null, "cmd", new String[]{"arg1", ""});
        shouldBe = List.of("arg1");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());

        results = m.onTabComplete(player, null, "cmd", new String[]{"arg3", ""});
        shouldBe = List.of("arg1", "arg2");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());

        results = m.onTabComplete(player, null, "cmd", new String[]{"arg3", "2"});
        shouldBe = List.of("arg2");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());
    }

    @Test
    public void testPermissions(){
        CMDManager m = getCMD();
        TestPlayer player = new TestPlayer();

        player.permissions.add(DEFAULTPERM);

        try {
            makeCall(m, player, null, "cmd", new String[]{"arg3", "arg1", "permlocked"});
            assertTrue("Expected exception got none!", false);
        } catch (MissingPermissionsException | CMDCommandException e) {
            assertTrue("Expected missing permissions error got: " + e.getClass().getSimpleName() + ": " + e.getMessage(), e instanceof MissingPermissionsException);
        }

        player.permissions.add(TESTPERM);

        try {
            assertTrue("Call of permission locked command expected to return true got false", makeCall(m, player, null, "cmd", new String[]{"arg3", "arg1", "permlocked"}));
        } catch (MissingPermissionsException | CMDCommandException e) {
            assertTrue("No error expeced, got: " + e.getClass().getSimpleName() + ": " + e.getMessage(), false);
        }
    }

    @Test
    public void ClassRegistrationTest(){
        CMDManager classRegManager = new CMDManager();
        TestPlayer player = new TestPlayer();
        assertFalse("Registration of 1st class should have faild. Class contains invalid functions!", classRegManager.register(new RegisterClass()));
        assertFalse("Registration of 2nd class should have faild. Class contains invalid functions!", classRegManager.register(new RegisterClass2()));
        assertTrue("Registration of 3ed class should have worked. Class contains only valid functions!", classRegManager.register(new RegisterClass3()));
        assertFalse("Registration of 4th class should have faild. Class contains invalid functions!", classRegManager.register(new RegisterClass4()));

        try {
            assertTrue("Faild to execute command for registered class", makeCall(classRegManager, player, null, "functest", new String[]{"valid"}));
        } catch (MissingPermissionsException | CMDCommandException e) {
            assertTrue(e.getMessage(), false);
        }

        try {
            makeCall(classRegManager, player, null, "functest", new String[]{"valid2"});
            assertTrue("Player was able to access function with permission restriction", false);
        } catch (MissingPermissionsException e) {
            assertTrue(true);
        } catch(CMDCommandException e){
            assertTrue(e.getMessage(), false);
        }

        player.permissions.add("functions.func4");
        try {
            assertTrue("Player was unable to access function with permission", makeCall(classRegManager, player, null, "functest", new String[]{"valid2"}));
        } catch (MissingPermissionsException e) {
            assertTrue(false);
        } catch (CMDCommandException e) {
            assertTrue(e.getMessage(), false);
        }



    }

    @Test
    public void ClassRegistrationTestTabComplete(){
        CMDManager classRegManager = new CMDManager();
        TestPlayer player = new TestPlayer();
        assertTrue("Registration of 3ed class should have worked. Class contains only valid functions!", classRegManager.register(new RegisterClass3()));


        List<String> results = classRegManager.onTabComplete(player, null, "functest", new String[]{""});
        List<String> shouldBe = List.of("valid", "valid2");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());
    }

    
    @Test
    public void suggestWildcrads(){
        CMDManager m1 = new CMDManager();
        m1.suggestWildcardNames(true);
        TestPlayer player = new TestPlayer();
        m1.register("/test <m1> t1", (a) -> {return true;});
        m1.register("/test t1 <m64> t8", (a) -> {return true;});
        m1.register("/test t2 <m32> t99", (a) -> {return true;});
        m1.register("/test t3 <m16> t66", (a) -> {return true;});
        m1.register("/test abc <m128> abc", (a) -> {return true;});
        m1.registerTabLookup("/test abc <m128>", (a) -> { return List.of("a", "b");});

        List<String> results = m1.onTabComplete(player, null, "test", new String[]{""});
        List<String> shouldBe = List.of("<m1>", "t1", "t2", "t3", "abc");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());
 
        results = m1.onTabComplete(player, null, "test", new String[]{"t1", ""});
        shouldBe = List.of("<m64>");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());
      
        results = m1.onTabComplete(player, null, "test", new String[]{"t3", ""});
        shouldBe = List.of("<m16>");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());
 
        results = m1.onTabComplete(player, null, "test", new String[]{"a", ""});
        shouldBe = List.of("t1");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());
    
        results = m1.onTabComplete(player, null, "test", new String[]{"abc", ""});
        shouldBe = List.of("a", "b");
        assertTrue("Not all elements in result: expected:[" + String.join(", ", shouldBe) + "] got: " + "[" + String.join(", ", results) + "]", results.containsAll(shouldBe) && shouldBe.containsAll(results) && results.size() == shouldBe.size());
    
    }

    private void assertRegister(String cmd, CMDManager m){
        assertTrue("Could not register command: " + cmd, m.register(cmd, (a)-> { return true; }));
    }

    private Method getCallMethod(){
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

    private boolean makeCall(CMDManager m, CommandSender sender, Command cmd, String lable, String[] args) throws MissingPermissionsException, CMDCommandException{
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


    private List<CMDStruct> getCMDStructs(String path, CMDManager m) throws CMDCommandException {
        try {
            Method preParse = m.getClass().getDeclaredMethod("preParse", String.class);
            preParse.setAccessible(true);
            String[] parts = (String[]) preParse.invoke(m, path);

            Field f = m.getClass().getDeclaredField("rootsToCMDS");
            f.setAccessible(true);

            Object pathlist = f.get(m);
            Map<String, CMDStruct> paths = (Map<String, CMDStruct>)pathlist;

            return paths.get(parts[0]).getPath(parts);
        }catch(CMDCommandException e){
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    private Object getField(Object instance, String fname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
        Field P0FAliases = instance.getClass().getDeclaredField("aliases");
        P0FAliases.setAccessible(true);
        return P0FAliases.get(instance);
    }

    private Method getFirstMethodWithName(Class<?> clazz, String name){
        for(Method m : clazz.getDeclaredMethods()){
            if(m.getName().equals(name)){
                return m;
            }
        }
        return null;
    }

    private double getExecutionTime(Method method, Object instance, Object... params){
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
