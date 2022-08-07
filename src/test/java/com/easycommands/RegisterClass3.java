package com.easycommands;

import com.easycommands.commands.CMDArgs;
import com.easycommands.commands.CMDCommand;
import com.easycommands.commands.Permission;

public class RegisterClass3 implements CMDListener{
    
    @CMDCommand(cmd = "/functest valid")
    public boolean func3(CMDArgs args){
        return true;
    }

    @CMDCommand(cmd = "/functest valid2")
    @Permission(Permissions = {"functions.func4"})
    public boolean func4(CMDArgs args){
        System.out.println("This is function 4 of the Register class 3");
        return true;
    }

}
