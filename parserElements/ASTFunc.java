package parserElements;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTFunc implements ASTNode {

    ASTNode func;
    Map<String, ASTNode> params;

    public ASTFunc(ASTNode func, Map<String, ASTNode> params) {
        this.func = func;
        this.params = params;
    }

    public IValue eval(Env<IValue> env) {
        /*
         * IValue function = func.eval(env); Map<String, IValue> args = new HashMap<>();
         * for (String param : params) { args.put(param, env.find(param)); }
         */
        return new VClosure(params.keySet(), func, env);
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        // TODO
    }

    @Override
    public IType typecheker(Env<IType> env) {
        List<IType> types = new ArrayList<>();
        for (Entry<String, ASTNode> param : params.entrySet()) {
            IType tmp = env.find(param.getKey());
            //if(tmp!=param.getValue().typecheker(env))
            //    throw new TypeError("Illegal type for function");
            types.add(tmp);
        }
        return TFunType.getInstance(types, func.typecheker(env));
    }

}