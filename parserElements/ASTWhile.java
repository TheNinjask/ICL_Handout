package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTWhile implements ASTNode {

    ASTNode condition;
    ASTNode body;

    public ASTWhile(ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }

    public IValue eval(Env<IValue> env) {
        IValue iCond = condition.eval(env);
        String error = iCond.getClass().getSimpleName();
        if(!(iCond instanceof VBool))
            throw new TypeError(String.format("Illegal arguments (%s) to if condition", error));
        while(((VBool)iCond).getBool()){
            body.eval(env);
            iCond = condition.eval(env);
            if(!(iCond instanceof VBool))
                throw new TypeError(String.format("Illegal arguments (%s) to if condition", error));
        }
        return new VBool(false);
    }

    @Override
    public void compile(Env<FrameComp> env, Env<IType> type, CodeBlock comp) {
        //phrase below might be wrong
        //this might be wrong due to what happens with numerous cycles
        String whily = comp.genLabel();
        String exit = comp.genLabel();
        typecheker(type);
        comp.emit(String.format("%s:", whily));
        comp.emit("sipush 1");
        condition.compile(env, type, comp);
        comp.emit(String.format("if_icmpne %s", exit));
        body.compile(env, type, comp); //??
        comp.emit(String.format("goto %s", whily));
        comp.emit(String.format("%s:", exit));
        comp.emit("sipush 0");
    }

    @Override
    public IType typecheker(Env<IType> env) {
        IType condType = condition.typecheker(env);
        if(condType.equals(TBool.getInstance())){
            /*IType bodyType =*/ body.typecheker(env);
            return TBool.getInstance();
        }
        throw new TypeError("Illegal Type to while condition");
    }
}