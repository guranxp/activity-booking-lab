package com.guranxp.spring.v1.domain;

import java.lang.reflect.Method;

public abstract class Aggregate {

    public void on(Event event) {
        try {
            Method handler = findHandlerMethod(event);
            handler.setAccessible(true);
            handler.invoke(this, event);
        } catch (EventHandlerNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Throwable e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to invoke event handler", e);
        }
    }

    private Method findHandlerMethod(Event event) {
        for (final Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(EventSourcingHandler.class)) {
                final Class<?>[] params = method.getParameterTypes();
                if (params.length == 1 && params[0].isAssignableFrom(event.getClass())) {
                    return method;
                }
            }
        }
        throw new EventHandlerNotFoundException(event.getClass(), this.getClass());
    }
}
