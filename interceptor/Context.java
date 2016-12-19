package interceptor;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

/**
 * This class defines accessor and mutator methods that allow a concrete
 * interceptor to access and modify a server's internal state.
 *
 * @author Daichi Mae
 */
class Context extends UnicastRemoteObject implements ContextRemote {

    // the server instance that created this context
    private InterceptibleFramework interceptibleFramework;

    private HashMap<String, Field> accessibles = new HashMap<>();
    private HashMap<String, Field> mutables = new HashMap<>();


    Context(InterceptibleFramework interceptibleFramework) throws RemoteException {
        this.interceptibleFramework = interceptibleFramework;
    }

    /**
     * Accessor
     *
     * @param key the name of a field to query
     * @return value of a field
     * @throws RemoteException
     */
    public Object getValue(String key) throws RemoteException {
        Object o = null;
        try {
            o = accessibles.get(key).get(interceptibleFramework);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

    /**
     * Mutator
     *
     * @param key the name of a field to change the value
     * @param value new value to be set
     * @throws RemoteException
     */
    public void setValue(String key, Object value) throws RemoteException {
        try {
            mutables.get(key).set(interceptibleFramework, value);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a field that a concrete interceptor can access.
     *
     * @param key the name of a field
     * @param value the value of a field
     */
    void putAccessible(String key, Field value) {
        accessibles.put(key, value);
    }

    /**
     * Add a field that a concrete interceptor can change.
     *
     * @param key the name of a field
     * @param value the value of a field
     */
    void putMutable(String key, Field value) {
        mutables.put(key, value);
    }
}
