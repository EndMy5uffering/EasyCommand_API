package com.endmysuffering.easycommands;

public class CMDPair<K, V> {
    
    private K first;
    private V secound;

    public CMDPair(K first, V secound){
        this.first = first;
        this.secound = secound;
    }

    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public V getSecound() {
        return secound;
    }

    public void setSecound(V secound) {
        this.secound = secound;
    }
    
}
