package parserElements;

public class VCell implements IValue {
    
    IValue content;	
    
    public VCell(IValue content){
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