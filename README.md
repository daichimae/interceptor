# interceptor
A package that implements the interceptor design pattern using reflection, RMI and AspectJ.

Requirements:
Java 8 or higher
AspectJ

Usage:
 Server Side
  1. Extend the InterceptibleFramework class.
  2. In the subclasses of InterceptibleFramework, annotate methods that you want to let clients intercept with @Interceptible, and name this event by setting a String value to the event element.
  3. Annotate class attributes that interceptors can read/modify with @Accessible/@Mutable, and specify in which events the attributes are accessed by setting a String array to the event element.

 Client Side
  1. Implement the Interceptor interface. Use the ContextRemote object passed to the callback method to read/modify the attributes on the server.
  2. Register the concrete interceptors with DispatcherRemote objects on the RMI registry that the server creates.
