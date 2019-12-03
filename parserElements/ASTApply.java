package parserElements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTApply implements ASTNode {

    ASTNode func;
    List<String> params;

    public ASTApply(ASTNode func, List<String> params) {
        this.func = func;
        this.params = params;
    }

    public IValue eval(Env<IValue> env) {
        IValue function = func.eval(env);
        if(function instanceof VClosure){
            Map<String, IValue> args = new HashMap<>();
            for (String param : params) {
                args.put(param, env.find(param));
            }
            return ((VClosure) function).getVal(args);
        }
        throw new TypeError("TODO ASTApply error msg!");
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        //TODO 
    }
}