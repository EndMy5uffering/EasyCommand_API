package com.easycommands;

import com.endmysuffering.easycommands.CMDArgs;
import com.endmysuffering.easycommands.CMDListener;
import com.endmysuffering.easycommands.annotations.CMDCommand;
import com.endmysuffering.easycommands.annotations.Permission;

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
