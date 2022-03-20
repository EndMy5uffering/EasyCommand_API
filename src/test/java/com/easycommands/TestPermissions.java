package com.easycommands;

import static org.junit.Assert.assertTrue;

import com.easycommands.commands.CMDManager;
import com.easycommands.commands.MissingPermissions;

import org.junit.Test;

public class TestPermissions {
    
    @Test
    public void TestCommandPermissions(){
        CMDManager m = new CMDManager();

        m.register("cmd arg1 arg2 arg3", (a,b,c,d,e) -> { return true;});
        m.addPermissionCheck("cmd arg1 arg2 arg3", (p) -> {
            return p.hasPermission("LOL");
        }, (mp) -> {
            mp.setMessage("NoPermissions");
            mp.setReturnStatus(true);
            mp.getPlayer().sendMessage(mp.getMessage());
            return mp;
        });

        assertTrue(m.onCommand(new TestPlayer(), null, "cmd", new String[]{"arg1", "arg2", "arg3"}));
     }

}
