/*
 * Copyright (c) 2008 Mysema Ltd.
 * All rights reserved.
 * 
 */
package com.mysema.query.grammar;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.map.LazyMap;

import com.mysema.query.grammar.Types.*;

/**
 * Visitor provides a dispatching Visitor for Expr instances
 * 
 * @author tiwe
 * @version $Id$
 */
public abstract class Visitor<T extends Visitor<T>> {
    
    private static final Package PACKAGE = Visitor.class.getPackage();

    private final Map<Class<?>, Method> methodMap = LazyMap.decorate(new HashMap<Class<?>,Method>(), 
            new Transformer<Class<?>,Method>(){

        public Method transform(Class<?> cl) {
            try {
                while (!cl.getPackage().equals(PACKAGE)){
                    cl = cl.getSuperclass();
                }                     
                Method method = null;
                Class<?> sigClass = Visitor.this.getClass();
                while (method == null && !sigClass.equals(Visitor.class)) {
                    try {
                        method = sigClass.getDeclaredMethod("visit", cl);
                    } catch (NoSuchMethodException nsme) {
                        sigClass = sigClass.getSuperclass();
                    }
                }
                if (method != null) {
                    method.setAccessible(true);
                } else {
                    throw new IllegalArgumentException("No method found for " + cl.getName());
                }
                return method;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    });

    @SuppressWarnings("unchecked")
    public final T handle(Expr<?> expr) {
        assert expr != null;
        try {
            methodMap.get(expr.getClass()).invoke(this, expr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (T) this;
    }

    protected abstract void visit(AliasEntity<?> expr);
    
    protected abstract void visit(AliasEntityCollection<?> expr);
    
    protected abstract void visit(AliasNoEntity<?> expr);

    protected abstract void visit(AliasSimple expr);

    protected abstract void visit(AliasToPath expr);

    protected abstract void visit(ConstantExpr<?> expr);

    protected abstract void visit(Operation<?, ?> expr);

    protected abstract void visit(OperationBoolean expr);
    
    protected abstract void visit(OperationComparable<?,?> expr);
    
    protected abstract void visit(OperationNumber<?,?> expr);
    
    protected abstract void visit(OperationString expr);

    protected abstract void visit(Path<?> expr);

    protected abstract void visit(PathBoolean expr);

    protected abstract void visit(PathComparable<?> expr);
    
    protected abstract void visit(PathComponentCollection<?> expr);
    
    protected abstract void visit(PathComponentMap<?,?> expr);

    protected abstract void visit(PathEntity<?> expr);

    protected abstract void visit(PathEntityCollection<?> expr);
    
    protected abstract void visit(PathEntityMap<?,?> expr);

    protected abstract void visit(PathEntityRenamable<?> expr);
    
    protected abstract void visit(PathNoEntity<?> expr);
    
    protected abstract void visit(PathNoEntitySimple<?> expr);

    protected abstract void visit(PathString expr);
    
}
