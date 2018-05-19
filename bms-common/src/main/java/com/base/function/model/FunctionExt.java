package com.base.function.model;

import java.util.List;

/**
 * @author BoSongsh
 * @create 2018-01-14 14:39
 **/
public class FunctionExt extends Function {

    List<FunctionExt> functions;

    public List<FunctionExt> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionExt> functions) {
        this.functions = functions;
    }
}
