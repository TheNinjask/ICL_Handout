package parserElements;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import parserExceptions.TypeError;

public class VClosure implements IValue {

    Set<String> ids;
    ASTNode body;
    Env<IValue> env;

    public VClosure(Set<String> ids, ASTNode body, Env<IValue> env) {
        this.ids = ids;
        this.body = body;
        this.env = env;
    }

    public IValue getVal(List<IValue> args) {
        Env<IValue> envC = this.env.beginScope();
        Iterator<String> it = ids.iterator();
        for ( IValue entry : args) {
            if(!it.hasNext()){
                throw new TypeError("Too many arguments!");
            }
            envC.assoc(it.next(), entry);
        }
        if(it.hasNext()){
            throw new TypeError("Too few arguments!");
        }
        IValue tmp = body.eval(envC);
        envC.endScope();
        return tmp;
    }

    public void show(){
        //TODO
    }
}	