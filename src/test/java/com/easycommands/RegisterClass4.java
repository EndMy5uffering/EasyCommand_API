package com.easycommands;

import com.endmysuffering.easycommands.CMDArgs;
import com.endmysuffering.easycommands.CMDListener;
import com.endmysuffering.easycommands.annotations.CMDCommand;

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
