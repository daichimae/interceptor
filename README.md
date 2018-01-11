# Interceptors
The interceptor pattern is a software design pattern that allows services to be added transparently to a framework and triggered automatically when certain events occur. The interceptor pattern can be used when it is unreasonable or impossible to anticipate all the services frameworks must offer to users or to rely upon applications to implement all the necessary services themselves. An implementation of this pattern, which involves dispatchers, accessors and mutators to the internal state of a framework, notification of the occurrence of designated events, interactions between objects over a network and many other components, is rather complicated and time-consuming. The client, server and interceptor packages that are included in this project make it easier for Java framework developers to implement the interceptor pattern.

## Components in the Packages
The interceptor packages are developed in Java and uses AspectJ to invoke dispatchers and Java RMI to enable clients to access to dispatchers and for context. The figure below illustrates the structure of key components in this library and the following is the summary of the classes and the interfaces:
* Interceptor - This interface defines the signatures of a callback method that the server invokes automatically via the dispatching mechanism when the corresponding events occur. This interface extends the Remote interface so dispatchers on the server side can invoke the callback method remotely.
* DispatcherRemote - This Remote interface enables clients to register concrete interceptors with dispatchers remotely. On the RMI registry, a _DispatcherRemote_ instance is accessed by the name of the event that the _Dispatcher_ instance is associated with.
* Dispatcher - This class is an implementation of the _DispatcherRemote_ interface. An _Dispatcher_ instance allows clients to register and remove concrete interceptors, and dispatches registered concrete interceptor callbacks when designated events occur. Interceptors are stored in a list and a newly registered interceptor is added at the end of the list.
* ContextRemote - This _Remote_ interface enables clients to access and modify framework’s internal state remotely.
* Context - This class is an implementation of the _ContextRemote_ interface and provides an accessor method to read field values on the framework and a mutator method to set values to fields on the concrete framework. A _Context_ object lets frameworks store field objects and lets clients read and/or modify the values of the fields via the __getValue__ and __setValue__ methods.
* Accessible, Mutable and Interceptible - They define the annotations __@Accessible__, __@Mutable__ and __@Interceptible__, respectively. They have the event element to be filled for future references.
* InterceptorAspect - This aspect catches the invocation of a method annotated with __@Interceptible__, has the framework create a _Context_ object and trigger the appropriate dispatcher.
* InterceptibleFramework - This class provides a context creation and a dispatching mechanism with frameworks. The constructor creates an RMI registry and a _Dispatcher_ object for each method annotated with __@Interceptible__ using reflection and store it in a hashmap. The __createContext__ method takes an event name, creates a field object for each field annotated with __@Accessible__ and/or __@Accessible__ with its event element matching the passed event name, and store it in the _Context_ object.

![alt text](https://github.com/daichimae/interceptor/blob/master/images/diagram.png "Class diagram for the interceptor packages")

## Requirements
Java 8 or higher, AspectJ

## Deployment
### Server Side:
  1. Extend the _InterceptibleFramework_ class.
  2. In the subclasses of _InterceptibleFramework_, annotate methods that you want to let clients intercept with __@Interceptible__, and name this event by setting a String value to the event element.
  3. Annotate class attributes that interceptors can read/modify with __@Accessible__/__@Mutable__, and specify in which events the attributes are accessed by setting a String array to the event element.

### Client Side:
  1. Implement the _Interceptor_ interface. Use the _ContextRemote_ object passed to the callback method to read/modify the attributes on the server.
  2. Register the concrete interceptors with _DispatcherRemote_ objects on the RMI registry that the server creates.
