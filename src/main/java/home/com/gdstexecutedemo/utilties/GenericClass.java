package home.com.gdstexecutedemo.utilties;

import com.fasterxml.jackson.databind.JavaType;

public class GenericClass<T> {
    private final JavaType type;
    public GenericClass(JavaType type) {
         this.type = type;
    }
    public JavaType getMyType() {
        return this.type;
    }
}	

