package parserElements;

public class VCell implements IValue {
    IValue v;	
    public VCell(IValue	v0){
        v=v0;
    }
    
    public IValue get()
    {
        return	v;
    }	
    
    public void set(IValue v0){
        v=v0;
    }

    public void show(){
        
    }	
}	