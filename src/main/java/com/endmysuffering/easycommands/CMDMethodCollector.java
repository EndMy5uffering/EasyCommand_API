package com.endmysuffering.easycommands;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import com.endmysuffering.easycommands.annotations.CMDCommand;
import com.endmysuffering.easycommands.typechecks.TypeChecks;

public class CMDMethodCollector {

    interface CMDCallable{
        boolean call(CMDArgs args) throws CMDCommandException;
    }

    private CMDCallable callable;
	private List<CMDPair<Annotation, ExecTest>> executionTests = new ArrayList<>();
    private Map<String, CMDPair<Annotation, TypeChecks.TypeCheck>> wildCardToTypeCheck = new HashMap<>();

	private PermissionCheck permissionCheck;
	private MissingPermissionHandle missingPermissinHandle = null;


    public CMDMethodCollector(CMDListener listener, Method m) throws CMDCommandException{
        Class<?>[] params = m.getParameterTypes();
        if(m.isAnnotationPresent(CMDCommand.class) && params.length == 1 && params[0] == CMDArgs.class){
            this.callable = (args) -> {
                try {
                    return (boolean)m.invoke(listener, args);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new CMDCommandException(e.getMessage());
                }
            };
        }else{
            throw new CMDCommandException("Invalid method! " + m.getName());
        }
    }

    public boolean executionTest(CMDArgs args){
		for(CMDPair<Annotation, ExecTest> test : this.executionTests){
			if(!test.getSecound().test(args, test.getFirst())) return false;
		}
		return true;
	}

    public boolean doTypeChecks(CMDArgs args){
        for(String card : args.getWildcards()){
            CMDPair<Annotation, TypeChecks.TypeCheck> pair = this.wildCardToTypeCheck.get(card);
            if(pair == null) continue;
            if(!pair.isNotNull()) return false;
            if(!pair.getSecound().check(args, card, pair.getFirst())) return false;
        }
        return true;
    }

    public void setGuardTests(List<CMDPair<Annotation, ExecTest>> tests){
        this.executionTests = tests;
    }

    public void addTypeChecks(String wildcard, CMDPair<Annotation, TypeChecks.TypeCheck> typecheck) {
        this.wildCardToTypeCheck.put(wildcard, typecheck);
    }

    public boolean call(CMDArgs args) throws CMDCommandException {
        return callable.call(args);
    }

    public boolean checkPermissions(Player p){
		return hasPermissionCheck() ? this.permissionCheck.check(p) : true;
	}

    public boolean hasPermissionCheck() {
		return this.permissionCheck != null;
	}

    public PermissionCheck getPermissionCheck() {
		return permissionCheck;
	}

	public void setPermissionCheck(PermissionCheck permissionCheck) {
		this.permissionCheck = permissionCheck;
	}

	public MissingPermissionHandle getMissingPermissinHandle() {
		return missingPermissinHandle;
	}

	public void setMissingPermissinHandle(MissingPermissionHandle missingPermissinHandle) {
		this.missingPermissinHandle = missingPermissinHandle;
	}

}
