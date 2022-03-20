package com.easycommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.easycommands.commands.CMDManager;
import com.easycommands.commands.CMDStruct;
import com.easycommands.commands.EasyCommandError;
import com.easycommands.commands.MissingPermissions;

import org.junit.Test;

public class TestFile {

    @Test
    public void TestRegister() throws EasyCommandError{
        CMDManager m = new CMDManager();
        m.register("command sub1 sub11 sub111", (sender, cmd, str, args, wildcards) -> {
            System.out.println("[DEFAULT TEST] command sub1 sub11 sub111");
            return true;
        });

        m.register("command sub1 <W1> sub111", (sender, cmd, str, args, wildcards) -> {
            System.out.println("[DEFAULT TEST] command sub1 <W1> sub111 [WITH: " + args[1] + "]");
            return true;
        });

        try {
            assert(!m.call(new TestPlayer(), null, "command", new String[0]));
            assert(m.call(new TestPlayer(), null, "command", new String[]{"sub1", "sub11", "sub111"}));
            assert(m.call(new TestPlayer(), null, "command", new String[]{"sub1", "tmp1", "sub111"}));
            
            try {
                m.call(new TestPlayer(), null, "command", new String[]{"sub1", "tmp2", "sub11"});
            } catch (Exception e) {
                assertTrue(e instanceof EasyCommandError);
            }
        } catch (MissingPermissions e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestRegisterVarArgs() throws EasyCommandError{
        CMDManager m = new CMDManager();
        m.register("command <...>", (sender, cmd, str, args, wildcards) -> {
            String astring = "";
            for(String s : args){
                astring += s + "; ";
            }
            System.out.println("[VARARG TEST] command <...> [WITH: " + astring + "]");
            return true;
        });

        try {
            assert(!m.call(new TestPlayer(), null, "command", new String[0]));
            assert(m.call(new TestPlayer(), null, "command", new String[]{"sub1", "sub11", "sub111"}));
            assert(m.call(new TestPlayer(), null, "command", new String[]{"sub1", "tmp", "sub111"}));
            assert(m.call(new TestPlayer(), null, "command", new String[]{"sub1", "tmp", "sub11"}));
        } catch (MissingPermissions e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestRegisterVarArgsEdgeCases() throws EasyCommandError{
        CMDManager m = new CMDManager();
        assert(m.register("command sub1 <...>", (sender, cmd, str, args, wildcards) -> {
            String astring = "";
            for(String s : args){
                astring += s + "; ";
            }
            System.out.println("[EDGE TEST] command sub1 <...> [WITH: " + astring + "]");
            return true;
        }));
        System.out.println("[EDGE TEST] registered first command");
        assert(m.register("command sub2 sub21 sub211", (sender, cmd, str, args, wildcards) -> {
            System.out.println("[EDGE TEST] command sub2 sub21 sub211");
            return true;
        }));
        System.out.println("[EDGE TEST] registered secound command");

        assert(!m.register("command sub1 <...> sub11", (sender, cmd, str, args, wildcards) -> {
            System.out.println("[EDGE TEST] THIS SHOULD NOT RUN!");
            return true;
        }));
        System.out.println("[EDGE TEST] registered third command");

        try {
            assert(m.call(new TestPlayer(), null, "command", new String[]{"sub1", "sub11", "sub11"}));
            assert(m.call(new TestPlayer(), null, "command", new String[]{"sub1", "tmp", "sub111"}));
            assert(m.call(new TestPlayer(), null, "command", new String[]{"sub1", "tmp", "sub11"}));
            assert(m.call(new TestPlayer(), null, "command", new String[]{"sub2", "sub21", "sub211"}));
        } catch (MissingPermissions e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestWildCards() throws EasyCommandError {
        CMDManager m = new CMDManager();
        assertTrue(m.register("command <CARD1> <CARD2> <CARD3>", (sender, cmd, str, args, wildcards) -> {
            assertEquals("arg1", wildcards.get("CARD1"));
            assertEquals("arg2", wildcards.get("CARD2"));
            assertEquals("arg3", wildcards.get("CARD3"));
            return true;
        }));

        assertTrue(m.register("command <CARD1>", (sender, cmd, str, args, wildcards) -> {
            assertEquals("arg1", wildcards.get("CARD1"));
            return true;
        }));

        assertTrue(!m.register("command <CARD2>", (sender, cmd, str, args, wildcards) -> {
            assertEquals("arg1", wildcards.get("CARD1"));
            return true;
        }));

        try {
            assertTrue(m.call(new TestPlayer(), null, "command", new String[]{"arg1", "arg2", "arg3"}));
            assertTrue(m.call(new TestPlayer(), null, "command", new String[]{"arg1"}));
            assertTrue(!m.call(new TestPlayer(), null, "command", new String[]{"arg1", "arg2"}));
        } catch (MissingPermissions e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestWildCards2() {
        CMDManager m = new CMDManager();

        m.register("tg <arg1>", (sender, cmd, str, args, wildcards) -> {
            assertEquals(wildcards.get("arg1"), "test");
            return true;
        });

        assertTrue(m.onCommand(new TestPlayer(), null, "tg", new String[]{"test"}));
    }

    @Test
    public void TestLookups(){
        CMDManager m = new CMDManager();

        m.register("cmd arg1 arg11", null);
        m.register("cmd arg2 arg21", null);
        m.register("cmd arg3 arg31", null);
        m.register("cmd <A> arg31", null);

        m.addTabLookup("cmd <A>", () -> {
            return new ArrayList<String>(Arrays.asList("VARG"));
        });
        ArrayList<String> shouldbe = new ArrayList<String>(Arrays.asList("arg1", "arg2", "arg3", "VARG"));
        ArrayList<String> got = m.tabComplete(new TestPlayer(), null, "cmd", new String[]{""});
        assertTrue(shouldbe.size() == got.size() && shouldbe.containsAll(got) && got.containsAll(shouldbe));

    }

    private List<CMDStruct> getCMDStructs(String path, CMDManager m) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, ClassNotFoundException, InvocationTargetException, EasyCommandError{
        Method preParse = m.getClass().getDeclaredMethod("preParse", String.class);
        preParse.setAccessible(true);
        String[] parts = (String[]) preParse.invoke(m, path);

        Field f = m.getClass().getDeclaredField("rootsToCMDS");
        f.setAccessible(true);

        Object pathlist = f.get(m);
        Map<String, CMDStruct> paths = (Map<String, CMDStruct>)pathlist;

        return paths.get(parts[0]).getPath(parts);
    }

    private Object getField(Object instance, String fname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
        Field P0FAliases = instance.getClass().getDeclaredField("aliases");
        P0FAliases.setAccessible(true);
        return P0FAliases.get(instance);
    }

    @Test
    public void TestAliases() throws MissingPermissions, EasyCommandError{
        CMDManager m = new CMDManager();

        m.register("cmd arg1 arg11 arg3", (a,b,c,d,e) -> { return true;});
        m.registerAliase("cmd", "c");

        assertTrue(m.call(new TestPlayer(), null, "c", new String[]{"arg1", "arg11", "arg3"}));
    }
}
