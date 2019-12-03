package parserElements;

import java.util.Map;
import java.util.Map.Entry;

public class VClosure implements IValue {

    String id;
    ASTNode body;
    Env<IValue> env;

    public VClosure(String id, ASTNode body, Env<IValue> env) {
        this.id = id;
        this.body = body;
        this.env = env;
    }

    public IValue getVal(Map<String, IValue> args) {
        Env<IValue> envC = this.env.beginScope();
        for (Entry<String, IValue> entry : args.entrySet()) {
            envC.assoc(entry.getKey(), entry.getValue());
        }
        IValue tmp = body.eval(envC);
        envC.endScope();
        return tmp;
    }

    public void show(){
        //TODO
        System.out.println(id);
    }
}	