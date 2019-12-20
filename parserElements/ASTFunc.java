package parserElements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;

public class ASTFunc implements ASTNode {

    ASTNode func;
    Set<String> params;

    public ASTFunc(ASTNode func, Set<String> params) {
        this.func = func;
        this.params = params;
    }

    public IValue eval(Env<IValue> env) {
        /*IValue function = func.eval(env);
        Map<String, IValue> args = new HashMap<>();
        for (String param : params) {
            args.put(param, env.find(param));
        }*/
        return new VClosure(params, func, env);
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        //TODO 
    }

    @Override
    public IType typecheker(Env<IType> env) {
        List<IType> types = new ArrayList<>();
        for (String param : params) {
            types.add(env.find(param));
        }
        return TFunType.getInstance(types);
    }

}