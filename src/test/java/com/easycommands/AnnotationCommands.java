package com.easycommands;

import com.endmysuffering.easycommands.CMDArgs;
import com.endmysuffering.easycommands.CMDListener;
import com.endmysuffering.easycommands.annotations.CMDCommand;
import com.endmysuffering.easycommands.typechecks.BooleanTypeCheck;
import com.endmysuffering.easycommands.typechecks.ByteTypeCheck;
import com.endmysuffering.easycommands.typechecks.EnumMemberCheck;
import com.endmysuffering.easycommands.typechecks.FloatTypeCheck;
import com.endmysuffering.easycommands.typechecks.IntTypeCheck;
import com.endmysuffering.easycommands.typechecks.LongTypeCheck;

public class AnnotationCommands implements CMDListener{

    @CMDCommand(cmd = "/cmd1 <w1>")
    @BooleanTypeCheck(paramNames = "w1")
    public boolean boolFunc1(CMDArgs args){
        TestPlayer player = (TestPlayer)args.getSender();
        player.lastArgs = args;
        return true;
    }

    @CMDCommand(cmd = "/cmd2 <w1>")
    @ByteTypeCheck(paramNames = "w1")
    public boolean byteFunc1(CMDArgs args){
        TestPlayer player = (TestPlayer)args.getSender();
        player.lastArgs = args;
        return true;
    }

    @CMDCommand(cmd = "/cmd3 <w1>")
    @IntTypeCheck(paramNames = "w1")
    public boolean intFunc1(CMDArgs args){
        TestPlayer player = (TestPlayer)args.getSender();
        player.lastArgs = args;
        return true;
    }

    @CMDCommand(cmd = "/cmd4 <w1>")
    @FloatTypeCheck(paramNames = "w1")
    public boolean floatFunc1(CMDArgs args){
        TestPlayer player = (TestPlayer)args.getSender();
        player.lastArgs = args;
        return true;
    }
    
    @CMDCommand(cmd = "/cmd5 <w1>")
    @LongTypeCheck(paramNames = "w1")
    public boolean longFunc1(CMDArgs args){
        TestPlayer player = (TestPlayer)args.getSender();
        player.lastArgs = args;
        return true;
    }

    @CMDCommand(cmd = "/cmd6 <w1>")
    @EnumMemberCheck(paramNames = "w1", enumClazz = TestEnum.class)
    public boolean enumFunc1(CMDArgs args){
        TestPlayer player = (TestPlayer)args.getSender();
        player.lastArgs = args;
        return true;
    }
}
