package interceptor;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An interface that defines the signatures of a callback method that the server
 * invokes automatically via the dispatching mechanism when the corresponding
 * events occur.
 *
 * @author Daichi Mae
 */
public interface Interceptor extends Remote {
    void callback(ContextRemote context) throws RemoteException;
}
