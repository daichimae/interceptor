package interceptor;

import java.rmi.RemoteException;

/**
 * An aspect that invokes the context creation method and notifies the
 * appropriate dispatcher upon an interceptible event.
 *
 * @author Daichi Mae
 */
public aspect InterceptorAspect {

    // catch the invocation of an interceptible method
    pointcut interceptibleEvent(): call(@Interceptible * *(..));

     // create a context and invoke a dispatcher.
    before() : interceptibleEvent() {
        System.out.println("An interceptible event triggered.");
        InterceptibleFramework interceptibleFramework
                = (InterceptibleFramework) thisJoinPoint.getTarget();
        // SIGNATURE FORMATS MIGHT BE DIFFERENT
        String event = interceptibleFramework.getEvents().get(
                thisJoinPointStaticPart.getSignature().toLongString());
        try {
            Context context = interceptibleFramework.createContext(event);
            interceptibleFramework.invokeDispatcher(event, context);
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }
}
