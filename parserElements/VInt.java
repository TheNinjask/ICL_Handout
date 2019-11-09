package parserElements;

public class VInt implements IValue {
    int	v;	
    public VInt(int v0){
        v=v0;
    }	
    public int getval(){
        return	v;
    }

    public void show(){
      System.out.println(v);  
    }	
}	