package parserElements;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

public class ASTId implements ASTNode {

    String val;

    public ASTId(String val) {
        this.val = val;
    }

    public IValue eval(Env<IValue> env) {
        return env.find(val);
    }

    @Override
    public void compile(Env<FrameComp> env, CodeBlock comp) {
        comp.emit("aload 4");
        FrameComp top = env.find(val);// , comp.getCurrentFrame(), comp);
        FrameComp bot = comp.getCurrentFrame();
        while(!top.getId().equals(bot.getId())){
            comp.emit(String.format("getfield %s/sl %s;", bot.getId(), bot.getSl().getLabel()));
            bot = bot.getSl();
            if(bot==null)
                throw new TypeError(String.format("Undeclared Variable (%s)!", FrameComp.class.getSimpleName()));
        }
        comp.emit(String.format("getfield %s/%s %s",
            bot.getId(), bot.translateVar(val), "I")
        );
    }
}