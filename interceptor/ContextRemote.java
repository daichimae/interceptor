package interceptor;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An interface that specifies the methods that can be invoked remotely by a
 * concrete interceptor.
 *
 * @author Daichi Mae
 */
public interface ContextRemote extends Remote {
    Object getValue(String key) throws RemoteException;
    void setValue(String key, Object value) throws RemoteException;
}
