package fr.capverszero.recunit;

import com.google.common.base.Objects;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA.
 * User: nicolas
 * Date: 25/11/13
 * Time: 08:55
 * To change this template use File | Settings | File Templates.
 */
public class Record {
    
	@Getter
	@Setter
	private List<Object> arguments;
    
    @Getter
    @Setter
    private Object result;

    @Setter
    @Getter
    private String methodName;

    @Setter
    @Getter
    private String ID;
    
    public Record(){
    
    }
    
    public Record(String ID,  String methodName){
    	this.ID = ID;
    	this.methodName = methodName;
    }
   
    /**
     * 
     * @return postfox for the key
     */
    public String toPostfixKey(){
    	  return Objects.toStringHelper(this)
               //   .add("arguments", arguments)
                  .add("methodName", methodName)
                  .toString();
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("arguments", arguments)
                .add("result", result)
                .add("methodName", methodName)
                .toString();
    }
}
