import java.util.Map;
import java.util.HashMap;

//singleton-like class maybe? very likely
//it may be not
//it may be better instead of argument
public class Env{

    private Env ancestor;
    private Map<String, IValue> dic;
    //private static Env = null;

    public Env(){
        ancestor = null;
        dic = new HashMap<>();
    }

    public Env(Env ancestor){
        this.ancestor = ancestor;
        dic = new HashMap<>();
    }

    public IValue find(String id){
        IValue val = dic.get(id);
        if(val == null)
            if(ancestor == null)
                throw new TypeError("Undeclared Variable!");
            else
                return ancestor.find(id);
        return val;
    }

    public void assoc(String id, IValue val){
        dic.put(id, val);
    }

    public Env beginScope(){
        Env newEnv = new Env(this);
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
    public Env endScope(){
        return ancestor;
    }

}