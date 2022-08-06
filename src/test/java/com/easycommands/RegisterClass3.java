package com.easycommands;

import com.easycommands.commands.CMDArgs;
import com.easycommands.commands.CMDCommand;

public class RegisterClass3 {
    
    @CMDCommand(cmd = "/functest valid")
    public boolean func3(CMDArgs args){
        return true;
    }

}
