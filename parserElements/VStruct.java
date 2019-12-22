package parserElements;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class VStruct implements IValue {
    
    private Map<String, IValue> dictionary;	
    
    public VStruct(){
        dictionary = new HashMap<>();
    }

    public VStruct(VStruct struct1, VStruct struct2){
        dictionary = new HashMap<>(struct1.getKeysValues());
        dictionary.putAll(struct2.getKeysValues());
    }
    
    public IValue getValue(String key){
        return dictionary.get(key);
    }	
    
    public void setKeyValue(String key, IValue value){
        dictionary.put(key, value);
    }

    public Map<String, IValue> getKeysValues(){
        return dictionary;
    }

    public void show(){
        System.out.println("{\n");
        for (Entry<String, IValue> elem : dictionary.entrySet()) {
            System.out.print(
                String.format("\t%s = ", elem.getKey())
            );
            elem.getValue().show();
            System.out.println();
        }
        System.out.println("}");
    }	
}	