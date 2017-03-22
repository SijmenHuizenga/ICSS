/*
 * Alle code zoals aangeleverd door ICA HAN is eigendom van ICA HAN. Deze code is te zien in het eerste commit van deze repository. Alle wijzigingen en uitbreidingen ná het eerste commit vallen onder de MIT Licentie:
 *
 * Copyright 2017 Sijmen Huizenga
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.han.ica.icss.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class Value extends ASTNode {

    public final Type getType(){
        ArrayList<ASTNode> visitedNodes = new ArrayList<>();
        return getType(visitedNodes);
    }

    public final boolean containsReferenceTo(ConstantReference ref){
        ArrayList<ASTNode> visitedNodes = new ArrayList<>();
        return containsReferenceTo(ref, visitedNodes);
    }

    /**
     * Get the type of this value.
     * @param vistitedNodes the list of visited nodes to overcome recursion problems
     * @return the type of this value.
     */
    protected abstract Type getType(List<ASTNode> vistitedNodes);

    /**
     * Check if this value contains somewhere a reference to the given reference.
     * @param vistitedNodes the list of visited nodes to overcome recursion problems
     * @return true when the reference is found or if a recursion problem occered, else false
     */
    protected abstract boolean containsReferenceTo(ConstantReference ref, List<ASTNode> vistitedNodes);


    public enum Type {
        PIXEL,
        PERCENTAGE,
        COLOR,
        MIXED,  // the result of a calculation existing of mulitiple types
        UNKNOWN // not (yet) known because the value is not yet found.
    }

    public static <T> boolean containsreal(List<T> list, T o){
        for(T t : list)
            if(t == o)
                return true;
        return false;
    }

}
