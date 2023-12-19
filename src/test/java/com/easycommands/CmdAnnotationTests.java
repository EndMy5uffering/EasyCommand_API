package com.easycommands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.junit.Test;

import com.endmysuffering.easycommands.CMDManager;
import com.endmysuffering.easycommands.ExecTest;

import net.md_5.bungee.api.ChatColor;

public class CmdAnnotationTests {
    
    @Test
    public void TestAnnotaionRegisteration(){
        CMDManager manager = new CMDManager();
        DummyManager m1 = new DummyManager();
        manager.registerGuard(DummyAnnotation.class, (args, annotation) -> m1.has(args.getWildCard("key")));

        Map<Class<? extends Annotation>, ExecTest> o = null;
        try {
            o = (Map<Class<? extends Annotation>, ExecTest>)TestUtils.getField(manager, "GuardTests");
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            assertTrue("Could not get field", false);
        }

        assertTrue("No annotation found after registering it", o.get(DummyAnnotation.class) != null);
    }

    @Test
    public void TestTypeCheckBoolean(){
        CMDManager manager = new CMDManager();
        TestPlayer player = new TestPlayer();
        assertTrue("Could not register TypeCheck commands", manager.register(new AnnotationCommands()));

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd1 true"));
        assertTrue("Last args was null", player.lastArgs != null);
        Boolean b = player.lastArgs.getObject(Boolean.class, "w1");
        assertTrue("No boolean object found", b != null);
        assertTrue("Boolean value was false expected true", b);

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd1 false"));
        assertTrue("Last args was null", player.lastArgs != null);
        Boolean b2 = player.lastArgs.getObject(Boolean.class, "w1");
        assertTrue("No boolean object found", b2 != null);
        assertTrue("Boolean value was false expected true", !b2);
    }

    public static void main(String ... args){
        
        CMDManager manager = new CMDManager();
        TestPlayer player = new TestPlayer();
        manager.register(new AnnotationCommands());

        TestUtils.makeCall(manager, player, "/cmd1 true");
    }

    @Test
    public void TestTypeCheckByte(){
        CMDManager manager = new CMDManager();
        TestPlayer player = new TestPlayer();
        assertTrue("Could not register TypeCheck commands", manager.register(new AnnotationCommands()));

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd2 1"));
        assertTrue("Last args was null", player.lastArgs != null);
        Byte b = player.lastArgs.getObject(Byte.class, "w1");
        assertTrue("No byte object found", b != null);
        assertTrue("Byte value was not 1!", b == 1);

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd2 56"));
        assertTrue("Last args was null", player.lastArgs != null);
        Byte b2 = player.lastArgs.getObject(Byte.class, "w1");
        assertTrue("No Byte object found", b2 != null);
        assertTrue("Byte value was not 56", b2 == 56);
    }

    @Test
    public void TestTypeCheckInt(){
        CMDManager manager = new CMDManager();
        TestPlayer player = new TestPlayer();
        assertTrue("Could not register TypeCheck commands", manager.register(new AnnotationCommands()));

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd3 1"));
        assertTrue("Last args was null", player.lastArgs != null);
        Integer b = player.lastArgs.getObject(Integer.class, "w1");
        assertTrue("No Int object found", b != null);
        assertTrue("Int value was not 1!", b == 1);

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd3 56"));
        assertTrue("Last args was null", player.lastArgs != null);
        Integer b2 = player.lastArgs.getObject(Integer.class, "w1");
        assertTrue("No Int object found", b2 != null);
        assertTrue("Int value was not 56", b2 == 56);
    }

    @Test
    public void TestTypeCheckFloat(){
        CMDManager manager = new CMDManager();
        TestPlayer player = new TestPlayer();
        assertTrue("Could not register TypeCheck commands", manager.register(new AnnotationCommands()));

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd4 1.25"));
        assertTrue("Last args was null", player.lastArgs != null);
        Float b = player.lastArgs.getObject(Float.class, "w1");
        assertTrue("No Float object found", b != null);
        assertTrue("Float value was not 1!", b > 1 && b < 2);

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd4 56.15"));
        assertTrue("Last args was null", player.lastArgs != null);
        Float b2 = player.lastArgs.getObject(Float.class, "w1");
        assertTrue("No Float object found", b2 != null);
        assertTrue("Float value was not 56.15", b2 > 56 && b2 < 57);
    }

    @Test
    public void TestTypeCheckLong(){
        CMDManager manager = new CMDManager();
        TestPlayer player = new TestPlayer();
        assertTrue("Could not register TypeCheck commands", manager.register(new AnnotationCommands()));

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd5 125521"));
        assertTrue("Last args was null", player.lastArgs != null);
        Long b = player.lastArgs.getObject(Long.class, "w1");
        assertTrue("No Long object found", b != null);
        assertTrue("Long value was not 1!", b == 125521);

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd5 666"));
        assertTrue("Last args was null", player.lastArgs != null);
        Long b2 = player.lastArgs.getObject(Long.class, "w1");
        assertTrue("No Long object found", b2 != null);
        assertTrue("Long value was not 666", b2 == 666);
    }

    @Test
    public void TestTypeCheckEnum(){
        CMDManager manager = new CMDManager();
        TestPlayer player = new TestPlayer();
        assertTrue("Could not register TypeCheck commands", manager.register(new AnnotationCommands()));

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd6 Test1"));
        assertTrue("Last args was null", player.lastArgs != null);
        TestEnum b = player.lastArgs.getObject(TestEnum.class, "w1");
        assertTrue("No Long object found", b != null);
        assertTrue("Long value was not 1!", b.equals(TestEnum.Test1));

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd6 MOCK"));
        assertTrue("Last args was null", player.lastArgs != null);
        TestEnum b2 = player.lastArgs.getObject(TestEnum.class, "w1");
        assertTrue("No Long object found", b2 != null);
        assertTrue("Long value was not 666", b2.equals(TestEnum.MOCK));

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd6 a"));
        String err = ChatColor.RED + "Some of the provided arguments do not match the required type!";
        assertTrue("Player got no messsage", player.messages.size() > 0);
        assertTrue("Player message was different", player.messages.get(0).equals(err));
    }

    @Test
    public void TestTypeCheckEnum2(){
        CMDManager manager = new CMDManager();
        TestPlayer player = new TestPlayer();
        assertTrue("Could not register TypeCheck commands", manager.register(new AnnotationCommands()));

        assertTrue("Could not call command", TestUtils.makeCall(manager, player, "/cmd7 Test1 MOCK2"));
        assertTrue("Last args was null", player.lastArgs != null);
        TestEnum b = player.lastArgs.getObject(TestEnum.class, "w1");
        TestEnum2 b2 = player.lastArgs.getObject(TestEnum2.class, "w2");
        assertTrue("No Enum object found", b != null && b2 != null);
        assertTrue("Enum value was not Test1!", b.equals(TestEnum.Test1));
        assertTrue("Enum value was not MOCK2!", b2.equals(TestEnum2.MOCK2));
    }

}
