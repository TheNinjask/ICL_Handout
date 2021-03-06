package parserElements;

import java.util.ArrayList;
import java.util.List;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTApply implements ASTNode {

    List<ASTNode> args;

    ASTNode body;

    public ASTApply(List<ASTNode> args, ASTNode body) {
        this.args = args;
        this.body = body;
    }

    public IValue eval(Env<IValue> env) {
        IValue func = body.eval(env);
        List<IValue> tmp = new ArrayList<>();
        for (ASTNode arg : args) {
            tmp.add(arg.eval(env));
        }
        if(func instanceof VClosure){
            return ((VClosure)func).getVal(tmp);
        }
        throw new TypeError("TODO ASTApply Error");
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        //TODO
    }

    @Override
    public IType typecheker(Env<IType> env) {
        List <IType> types = new ArrayList<>();
        for (ASTNode type : args) {
            types.add(type.typecheker(env));
        }
        return TFunType.getInstance(types, body.typecheker(env));
    }

}