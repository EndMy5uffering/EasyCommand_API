package com.easycommands;

import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.junit.Test;

import com.endmysuffering.easycommands.CMDManager;
import com.endmysuffering.easycommands.ExecTest;

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
        assertTrue("No annotation found after registering it", false);
    }

}
