package parserElements;

public class VCell implements IValue {
    
    IValue value;	
    
    public VCell(IValue	v0){
        value=v0;
    }
    
    public IValue get()
    {
        return	value;
    }	
    
    public void set(IValue v0){
        value=v0;
    }

    public void show(){
        value.show();
    }	
}	