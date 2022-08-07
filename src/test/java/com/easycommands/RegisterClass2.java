package com.easycommands;

import com.easycommands.commands.CMDCommand;

public class RegisterClass2 implements CMDListener{

    @CMDCommand(cmd = "/functest arg1 arg2 arg3")
    public boolean func2(){
        return false;
    }
    
}
