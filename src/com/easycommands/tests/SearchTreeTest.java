package com.easycommands.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.easycommands.commands.SearchTree;

class SearchTreeTest {

	TestI[] tests = {() -> "This is test 1",
			() -> "This is test 2",
			() -> "This is test 3"};
	
	String[] paths = {"main test test1",
			"main test test2",
			"main test1 test1"};
	
	@Test
	void test() {
		SearchTree<TestI> tree = new SearchTree<>();
		
		for(int i = 0; i < tests.length; i++)
			tree.addElement(paths[i], tests[i]);

		boolean noNull = true;
		int k = 0;
		for(int i = 0; i < tests.length; i++) {
			TestI out = tree.getElement(paths[i]);
			if(out != null) {
				noNull = false;
				assertTrue(noNull, "Object " + i + " was null!");
			}
		}
		
		if(!noNull) return;
		
		for(int i = 0; i < tests.length; i++) {
			TestI out = tree.getElement(paths[i]);
			assertTrue(out.exe().equals(tests[i].exe()));
		}
		
	}

	interface TestI{
		public String exe();
	}
	
}
