package interceptor;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * An interface that specifies the methods that can be invoked remotely by the
 * client.
 *
 * @author Daichi Mae
 */
public interface DispatcherRemote extends Remote {
    void register(Interceptor interceptor) throws RemoteException;
    void remove(Interceptor interceptor) throws RemoteException;
}
