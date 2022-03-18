package com.easycommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import com.easycommands.commands.CMDManager;
import com.easycommands.commands.MissingPermissions;

import org.junit.Test;

public class TestFile {

    @Test
    public void TestRegister(){
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
            assert(!m.call(null, null, "command", new String[0]));
            assert(m.call(null, null, "command", new String[]{"sub1", "sub11", "sub111"}));
            assert(m.call(null, null, "command", new String[]{"sub1", "tmp1", "sub111"}));
            assert(!m.call(null, null, "command", new String[]{"sub1", "tmp2", "sub11"}));
        } catch (MissingPermissions e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestRegisterVarArgs(){
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
            assert(!m.call(null, null, "command", new String[0]));
            assert(m.call(null, null, "command", new String[]{"sub1", "sub11", "sub111"}));
            assert(m.call(null, null, "command", new String[]{"sub1", "tmp", "sub111"}));
            assert(m.call(null, null, "command", new String[]{"sub1", "tmp", "sub11"}));
        } catch (MissingPermissions e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestRegisterVarArgsEdgeCases(){
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
            assert(m.call(null, null, "command", new String[]{"sub1", "sub11", "sub11"}));
            assert(m.call(null, null, "command", new String[]{"sub1", "tmp", "sub111"}));
            assert(m.call(null, null, "command", new String[]{"sub1", "tmp", "sub11"}));
            assert(m.call(null, null, "command", new String[]{"sub2", "sub21", "sub211"}));
        } catch (MissingPermissions e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestWildCards(){
        CMDManager m = new CMDManager();
        assert(m.register("command <CARD1> <CARD2> <CARD3>", (sender, cmd, str, args, wildcards) -> {
            assertEquals("arg1", wildcards.get("CARD1"));
            assertEquals("arg2", wildcards.get("CARD2"));
            assertEquals("arg3", wildcards.get("CARD3"));
            return true;
        }));

        assert(m.register("command <CARD1>", (sender, cmd, str, args, wildcards) -> {
            assertEquals("arg1", wildcards.get("CARD1"));
            return true;
        }));

        assert(!m.register("command <CARD2>", (sender, cmd, str, args, wildcards) -> {
            assertEquals("arg1", wildcards.get("CARD1"));
            return true;
        }));

        try {
            assert(m.call(null, null, "command", new String[]{"arg1", "arg2", "arg3"}));
            assert(m.call(null, null, "command", new String[]{"arg1"}));
            assert(!m.call(null, null, "command", new String[]{"arg1", "arg2"}));
        } catch (MissingPermissions e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestLookups(){
        CMDManager m = new CMDManager();

        m.register("cmd arg1 arg11", null);
        m.register("cmd arg2 arg21", null);
        m.register("cmd arg3 arg31", null);
        m.register("cmd <A> arg31", null);

        m.addLookup("cmd <A>", () -> {
            return new ArrayList<String>(Arrays.asList("VARG"));
        });
        ArrayList<String> shouldbe = new ArrayList<String>(Arrays.asList("arg1", "arg2", "arg3", "VARG"));
        ArrayList<String> got = m.tabComplete(null, null, "cmd", new String[]{""});
        assertTrue(shouldbe.size() == got.size() && shouldbe.containsAll(got) && got.containsAll(shouldbe));

    }
}
