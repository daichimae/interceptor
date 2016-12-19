package interceptor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that configures and triggers concrete interceptors.
 *
 * @author Daichi Mae
 */
class Dispatcher extends UnicastRemoteObject implements DispatcherRemote {

    private List<Interceptor> interceptors = new ArrayList<>();


    Dispatcher() throws RemoteException {}

    /**
     * Subscribe a concrete interceptor with the server.
     *
     * @param interceptor concrete interceptor to be registered
     * @throws RemoteException
     */
    public void register(Interceptor interceptor) throws RemoteException {
        interceptors.add(interceptor);
    }

    /**
     * Un-subscribe a concrete interceptor with the server.
     *
     * @param interceptor concrete interceptor to be removed
     * @throws RemoteException
     */
    public void remove(Interceptor interceptor) throws RemoteException {
        interceptors.remove(interceptor);
    }

    /**
     * Invoke the callback methods of the interceptors.
     * @param context context that's passed to the interceptors
     */
    void dispatch(Context context) {
        try {
            for (Interceptor interceptor : interceptors) {
                interceptor.callback(context);
            }
        } catch(RemoteException e) {
            e.printStackTrace();
        }
    }
}
