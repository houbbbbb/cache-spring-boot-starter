package com.github.houbbbbb.cachespringbootstarter.aspects;

import com.github.houbbbbb.cachespringbootstarter.annotations.MapCacheAnnotation;
import com.github.houbbbbb.cachespringbootstarter.utils.PrintUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Aspect
public class MapCacheAspect {
    private static final Map<String, Object> MAP = new HashMap<>();

    @Pointcut("@annotation(com.github.houbbbbb.cachespringbootstarter.annotations.MapCacheAnnotation)")
    private void mapCacheCut(){}

    @Around("mapCacheCut()")
    public Object mapCacheAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        MapCacheAnnotation annotation = method.getAnnotation(MapCacheAnnotation.class);

        // 获取注解参数值
        String name = annotation.name();
        String key = annotation.key();
        boolean value = annotation.value();
        // name前缀
        String namePrefix = name.concat("_");

        // 缓存处理
        Object o = null;
        if(value) {
            String finalKey = parse(joinPoint, method, key);
            String finalName = namePrefix.concat(finalKey);
            if(MAP.containsKey(finalName)) {
                o = MAP.get(finalName);
                PrintUtils.println("cache get", finalName);
            } else {
                o = joinPoint.proceed();
                MAP.put(finalName, o);
                PrintUtils.println("cache put", finalName);
            }
        } else {
            Set<String> keySets = MAP.keySet();
            for (String pr: keySets) {
                if(pr.startsWith(namePrefix)) {
                    MAP.remove(pr);
                }
            }
//            MAP.remove(finalName);
            o = joinPoint.proceed();
            PrintUtils.println("mapCache remove prefix", name);
        }

        return o;
    }

    private String parse(ProceedingJoinPoint joinPoint, Method method, String key) {
        // 创建解析表达式
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        // 创建上下文
        EvaluationContext context = new StandardEvaluationContext();
        Object[] params = joinPoint.getArgs();
        String[] paraNames = new DefaultParameterNameDiscoverer().getParameterNames(method);
        int len = paraNames.length;
        for (int i = 0; i < len; i++) {
            context.setVariable(paraNames[i], params[i]);
        }
        // 解析
        return expression.getValue(context).toString();
    }
}
