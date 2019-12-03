package parserElements;

public class VRef implements IValue {
    
    IValue content;	
    
    public VRef(IValue content){
        this.content = content;
    }
    
    public IValue get(){
        return content;
    }	
    
    public void set(IValue newContent){
        this.content = newContent;
    }

    public void show(){
        content.show();
    }	
}	