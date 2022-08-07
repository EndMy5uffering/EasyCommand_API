package com.easycommands;

import com.easycommands.commands.CMDArgs;
import com.easycommands.commands.CMDCommand;

public class RegisterClass4 implements CMDListener{

    @CMDCommand(cmd = "/functest")
    public boolean func1(){
        return false;
    }

    @CMDCommand(cmd = "/functest arg1")
    public boolean func2(CMDArgs args){
        return false;
    }
}
