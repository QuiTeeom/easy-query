package com.quitee.easyquery.tracer;

import com.quitee.easyquery.ast.AstNode;

import java.util.*;
import java.util.function.Function;

/**
 * @author quitee
 * @date 2022/10/22
 */

public class AstTracer {
    private static final List<AstTraceFilter> EMPTY = new ArrayList<>();

    private AstTracer(){}

    public static AstTracer getInstance(){
        return new AstTracer();
    }


    Map<List<AstTraceFilter>,List<Function<AstNode,Boolean>>> callbacks = new LinkedHashMap<>();

    public AstTracer withCallBack(List<AstTraceFilter> filters,List<Function<AstNode,Boolean>> newCallbacks){
        List<Function<AstNode,Boolean>> oldCallbacks = callbacks.computeIfAbsent(filters,f->new ArrayList<>());
        oldCallbacks.addAll(newCallbacks);
        return this;
    }

    public AstTracer withCallBack(Function<AstNode,Boolean> newCallback){
        return withCallBack(Collections.singletonList(newCallback));
    }

    public AstTracer withCallBack(AstTraceFilter filter,Function<AstNode,Boolean> newCallback){
        return withCallBack(Collections.singletonList(filter), Collections.singletonList(newCallback));
    }

    public AstTracer withCallBack(List<Function<AstNode,Boolean>> newCallbacks){
        List<Function<AstNode,Boolean>> oldCallbacks = callbacks.computeIfAbsent(EMPTY,f->new ArrayList<>());
        oldCallbacks.addAll(newCallbacks);
        return this;
    }


    public void VLR(AstNode root,AstTraceContext ctx){
        if (root==null){
            return;
        }
        if (ctx.stop){
            return;
        }
        callback(root,ctx);
        if (ctx.stop){
            return;
        }
        AstNode left = root.getLeft();
        VLR(left,ctx);
        if (ctx.stop){
            return;
        }
        AstNode right = root.getRight();
        VLR(right,ctx);
    }

    public void VLR(AstNode root){
        if (callbacks.isEmpty()){
            return;
        }
        VLR(root,new AstTraceContext());
    }

    public void LDR(AstNode root,AstTraceContext ctx){
        if (root==null){
            return;
        }
        if (ctx.stop){
            return;
        }
        AstNode left = root.getLeft();
        LDR(left,ctx);
        if (ctx.stop){
            return;
        }

        callback(root,ctx);
        if (ctx.stop){
            return;
        }

        AstNode right = root.getRight();
        LDR(right,ctx);
    }

    public void LDR(AstNode root){
        if (callbacks.isEmpty()){
            return;
        }
        LDR(root,new AstTraceContext());
    }

    public void LRD(AstNode root,AstTraceContext ctx){
        if (root==null){
            return;
        }
        if (ctx.stop){
            return;
        }
        AstNode left = root.getLeft();
        LRD(left,ctx);
        if (ctx.stop){
            return;
        }

        AstNode right = root.getRight();
        LRD(right,ctx);
        if (ctx.stop){
            return;
        }

        callback(root,ctx);
    }

    public void LRD(AstNode root){
        if (callbacks.isEmpty()){
            return;
        }
        LRD(root,new AstTraceContext());
    }

    private void callback(AstNode node,AstTraceContext ctx){

        callbacks.forEach((filters,cbs)->{
            for (AstTraceFilter filter:filters){
                if (!filter.filter(node,ctx)){
                    return;
                }
            }
            for(Function<AstNode,Boolean> cb:cbs){
                boolean stop = cb.apply(node);
                if (stop){
                    ctx.stop = true;
                    return;
                }
            }
        });
    }
}
