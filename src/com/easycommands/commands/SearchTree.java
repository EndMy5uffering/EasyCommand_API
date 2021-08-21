package com.easycommands.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchTree<T> {

	private HashMap<String, TreeNode<T>> roots = new HashMap<>();
		
	public void addElement(String path, T element) {
		String[] args = path.split(" ");
		
		if(roots.get(args[0]) == null) {
			roots.put(args[0], new TreeNode<T>(args[0], null));
		}

		roots.get(args[0]).addElement(path, element);
	}
	
	public T getElement(String path) {
		String[] args = path.split(" ");
		String[] nargs = new String[args.length -1];
		for(int i = 1; i < args.length; i++) {
			nargs[i-1] = args[i];
		}
		return getElement(args[0], nargs);
	}
	
	public T getElement(String root, String[] args) {
		String path = root;
		if(args != null) {
			for(int i = 0; i < args.length; i++) {
				path += " " + args[i];
			}
		}
		if(roots.get(root) != null) {
			TreeNode<T> node = roots.get(root).getElement(path);
			return node != null ? node.getElement() : null;
		}
		return null;
	}
	
	public List<TreeNode<T>> getNext(String root, String[] args){
		String path = root;
		if(args != null) {
			for(int i = 0; i < args.length; i++) {
				path += " " + args[i];
			}
		}
		if(roots.get(root) != null) {
			TreeNode<T> node = roots.get(root).getElement(path);
			return node != null ? node.getNext() : null;
		}
		return null;
	}

	class TreeNode<S> {

		private String value;
		private S element;
		private List<TreeNode<S>> next = new ArrayList<>();
		
		public TreeNode(String value, S element) {
			this.value = value;
			this.element = element;
		}

		public String getValue() {
			return value;
		}

		public S getElement() {
			return element;
		}
		
		public boolean isLeave() {
			return next.isEmpty();
		}
		
		public boolean addElement(String path, S element) {
			String[] s = path.split(" ");
			return addChild(s, 0, element);
		}
		
		private boolean addChild(String[] args, int count, S element) {
			if(count >= args.length-1) {
				 this.element = element;
				 return true;
			} 
			if(!args[count].equalsIgnoreCase(this.value)) return false;
			if(!hasNextElement(args[count+1])) this.next.add(new TreeNode<S>(args[count+1], null));
			return getNextElement(args[count + 1]).addChild(args, count + 1, element);
		}
		
		public boolean hasElement() {
			return this.element != null;
		}
		
		private boolean hasNextElement(String value) {
			for(TreeNode<S> n : next) {
				if(n.getValue().equalsIgnoreCase(value)) {
					return true;
				}
			}
			return false;
		}
		
		private TreeNode<S> getNextElement(String value) {
			for(TreeNode<S> n : next) {
				if(n.getValue().equalsIgnoreCase(value)) {
					return n;
				}
			}
			return null;
		}
		
		public TreeNode<S> getElement(String[] path) {
			return getElement(path, 0);
		}
		
		public TreeNode<S> getElement(String path) {
			return getElement(path.split(" "));
		}
		
		private TreeNode<S> getElement(String[] args, int count) {
			if(count >= args.length) return null;
			if(!args[count].equalsIgnoreCase(this.value)) return null;
			if(args[count].equalsIgnoreCase(this.value) && count >= args.length - 1) return this;
			return hasNextElement(args[count+1]) ? getNextElement(args[count + 1]).getElement(args, count + 1) : null;
		}
		
		public List<TreeNode<S>> getNext(){
			return this.next;
		}
		
	}
	
}
