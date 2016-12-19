package interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class represents a concrete framework in the interceptor design pattern.
 * It can be subclassed and in a subclass the methods that the application can
 * intercept are annotated with @interceptible. Also, the attributes that
 * the application can read/write are annotated with @Accessible/@Mutable.
 *
 * @author Daichi Mae
 */
public class InterceptibleFramework {

    private HashMap<String, Dispatcher> dispatchers = new HashMap<>();

    // mapping from method signatures to event names
    private HashMap<String, String> events = new HashMap<>();

    /**
     * Constructor
     *
     * For each method annotated with @Interceptible, create a dispatcher.
     */
    public InterceptibleFramework() {
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch(RemoteException e) {
            e.printStackTrace();
        }

        for(Method method : this.getClass().getDeclaredMethods()) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            for(Annotation annotation : annotations){
                if(annotation instanceof Interceptible) {
                    Interceptible interceptible = (Interceptible) annotation;
                    // associate a method signature with an event
                    events.put(method.toString(), interceptible.event());
                    try {
                        Dispatcher dispatcher = new Dispatcher();
                        // associate an event with a dispatcher
                        dispatchers.put(interceptible.event(), dispatcher);
                        // bind a dispatcher to the corresponding event name in the register
                        registry.rebind(interceptible.event(), dispatcher);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    } // end constructor

    /**
     * Accessor
     *
     * @return hashmap of method signatures and event names
     */
    HashMap<String, String> getEvents() {
        return events;
    }

    /**
     * Create a context.
     *
     * @param event the name of an event
     * @return a context object
     * @throws RemoteException
     */
    Context createContext(String event) throws RemoteException {
        System.out.println("Creating a context on event " + event);
        Context context = new Context(this);

        // look for fields that are associated with the event
        for(Field field : this.getClass().getDeclaredFields()) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            for(Annotation annotation : annotations) {
                if(annotation instanceof Accessible) {
                    Accessible accessible = (Accessible) annotation;
                    //if(accessible.event().equals(event)) {
                    if(Arrays.asList(accessible.event()).contains(event)) {
                        field.setAccessible(true);
                        context.putAccessible(field.getName(), field);
                    }
                }
                if(annotation instanceof Mutable) {
                    Mutable accessible = (Mutable) annotation;
                    if(Arrays.asList(accessible.event()).contains(event)) {
                        field.setAccessible(true);
                        context.putMutable(field.getName(), field);
                    }
                }
            }
        } // end outer for
        return context;
    }

    /**
     * Invoke a dispatcher.
     *
     * @param event the name of an event
     */
    void invokeDispatcher(String event, Context context) {
        System.out.println("Invoking dispatcher " + event);
        dispatchers.get(event).dispatch(context);
    }
}
