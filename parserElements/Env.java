package parserElements;

import java.util.Map;

import compilerElements.CodeBlock;
import compilerElements.FrameComp;
import parserExceptions.TypeError;

import java.util.HashMap;

//singleton-like class maybe? very likely
//it may be not
//it may be better instead of argument
public class Env<T>{

    private Env<T> ancestor;
    private Map<String, T> dic;
    //private static Env = null;

    public Env(){
        ancestor = null;
        dic = new HashMap<>();
    }

    public Env(Env<T> ancestor){
        this.ancestor = ancestor;
        dic = new HashMap<>();
    }

    public T find(String id){
        T val = dic.get(id);
        if(val == null)
            if(ancestor == null)
                throw new TypeError(String.format("Undeclared Variable (%s)!", id));
            else
                return ancestor.find(id);
        return val;
    }

    public T find(String id, FrameComp frame, CodeBlock comp){
        T val = dic.get(id);
        if(val == null)
            if(ancestor == null || frame.getSl() == null)
                throw new TypeError(String.format("Undeclared Variable (%s)!", id));
            else{
                comp.emit(String.format("getfield %s/sl %s;", frame.getId(), frame.getSl().getLabel()));
                return ancestor.find(id, frame.getSl(), comp);
            }
        comp.emit(String.format("getfield %s/%s %s",
            frame.getId(), val, "I")
        );
        return val;
    }

    public void assoc(String id, T val){
        dic.put(id, val);
    }

    public Env<T> beginScope(){
        Env<T> newEnv = new Env<T>(this);
        return newEnv;
    }
    /*
    public static Env beginScope(){
        if(instance == null)
            instance = new Env();
        else
            instance = new Env(instance);
        return instance;
    }

    public static Env endScope(){
        if(ancestor==null)
            return null;//error
        instance = ancestor;
        return instance;
    }
    */
    public Env<T> endScope(){
        return ancestor;
    }

}