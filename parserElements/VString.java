package parserElements;

public class VString implements IValue {
    String v;	
    public VString(String v0){
        v=v0;
    }	
    public String getval(){
        return v;
    }

    public void show(){
      System.out.println(v);  
    }	
}	