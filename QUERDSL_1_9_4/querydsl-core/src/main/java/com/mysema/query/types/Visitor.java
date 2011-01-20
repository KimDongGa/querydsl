/*
 * Copyright (c) 2010 Mysema Ltd.
 * All rights reserved.
 *
 */
package com.mysema.query.types;

/**
 * Visitor defines a Visitor signature for {@link Expr} instances.
 *
 * @author tiwe
 * @version $Id$
 */
public interface Visitor {

    /**
     * @param expr
     */
    void visit(Constant<?> expr);

    /**
     * @param expr
     */
    void visit(Custom<?> expr);

    /**
     * @param expr
     */
    void visit(FactoryExpression<?> expr);

    /**
     * @param expr
     */
    void visit(Operation<?> expr);

    /**
     * @param expr
     */
    void visit(Path<?> expr);

    /**
     * @param query
     */
    void visit(SubQueryExpression<?> query);

    /**
     * @param param
     */
    void visit(Param<?> param);

}