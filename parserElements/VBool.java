package parserElements;

public class VBool implements IValue {
    boolean	v;	
    public VBool(boolean v0){
        v=v0;
    }	
    public boolean getBool(){
        return v;
    }

    public void show(){
       System.out.println(v); 
    }
}	