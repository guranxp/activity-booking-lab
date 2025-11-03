package com.guranxp.spring.v1.domain;

import java.lang.reflect.Method;
import java.util.List;

public abstract class Aggregate <T extends Aggregate<T>> {

    public void on(Event event) {
        try {
            Method handler = findHandlerMethod(event);
            handler.setAccessible(true);
            handler.invoke(this, event);
        } catch (EventHandlerNotFoundException e) {
            System.out.println(e.getMessage());
            throw e;
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

    public T apply(List<Event> eventStream) {
        try {
            @SuppressWarnings("unchecked")
            T result = (T) this.getClass().getDeclaredConstructor().newInstance();
            for (Event event : eventStream) {
                result.on(event);
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to apply event stream", e);
        }
    }

    @SuppressWarnings("unchecked")
    public <C extends Command<T>> List<Event> handle(C command) {
        try {
            Method handler = findCommandHandlerMethod(command);
            handler.setAccessible(true);
            return (List<Event>) handler.invoke(this, command);
        } catch (CommandHandlerNotFoundException e) {
            System.out.println(e.getMessage());
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException("Failed to invoke command handler", e);
        }
    }

    private <C extends Command<T>> Method findCommandHandlerMethod(C command) {
        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(CommandHandler.class)) {
                Class<?>[] params = method.getParameterTypes();
                if (params.length == 1 && params[0].isAssignableFrom(command.getClass())) {
                    return method;
                }
            }
        }
        throw new CommandHandlerNotFoundException(command.getClass(), this.getClass());
    }

}
