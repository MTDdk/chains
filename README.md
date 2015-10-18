# Chains

This is just a minor tool sprung from the idea, that it should be possible to use lambda expressions in try-with-resource constructs.

The idea originated from the specific wish to use the following construct:
```java
try (resource) {
 ...
} catch (SQLException e -> System.out.println(e.getMessage()));
```

This is of course not far from what actually is currently possible:
```java
try (resource) {
 ...
} catch (SQLException e){ System.out.println(e.getMessage()); }
``` 

However, the this sparkled thoughts of further improvements to the try-with-resources paradigm, so we tried to device a chaining tool that was lambda-first, but also made it possible to catch specific exceptions anywhere in the chain.

Especially the part with *catching anywhere in the chain* can be quite verbose in traditional Java.
See this example:
```java
try (Connection conn = spec.getConnection()) {
    try (ResultSet result = source.createStatement().executeQuery("SELECT * FROM table")) {
        try {
            while(result.next()) {
                String something = result.getString(1);
            }
        } catch (SQLException e) {
            // using the resultset is somehow failing
            // do some fallback
        }
    } catch (SQLException e) {
        // the creation of the statement might have failed
        // do some fallback
    }
} catch (SQLTimeoutException e) {
    // database login timed out
} catch (SQLException e) {
    // a connection to the database could not be established
}
```

The example is of course extremely elaborate, but this form is necessary if you want to be extensively fault tolerant or robust.

## Example usages

The above example can with **Chains** be expressed like this:
```java
Consumer<? super Throwable> excep   = e -> e.printStackTrace(),
                            timeout = e -> e.getMessage();
Try
  .with(() -> spec.getConnection()).exception(timeout, SQLTimeoutException.class).exception(excep, SQLException.class)
  .with((Connection conn) -> conn.createStatement().executeQuery("SELECT * FROM table")).exception(excep)
  .perform((ResultSet result) -> {
      while(result.next()) {
          String something = result.getString(1);
      }
  }).exception(excep)
  .execute();
```

This, of course, shows that it is necessary to still be quite explicit in handling the known exceptions.

However, the tool ensures to close all streams whether exceptions are handled or not. This means that only foreseen exceptions are taken care of, the rest is suppressed.

If you only need to handle a single case of exception, because other exceptions are too unlikely, the tool helps you to be carefree of any other exception, but stills promises to always close all resources.

The same example with only a single exception handling:
```java
Try
  .with(() -> spec.getConnection())
  .with(conn -> conn.createStatement().executeQuery("SELECT * FROM table"))
  .perform((ResultSet result) -> {
      while(result.next()) {
          String something = result.getString(1);
      }
  }).exception(e -> e.printStackTrace())
  .execute();
```
This example only explicitly handles if any exceptions occur when reading from the `ResultSet`.


##Functionality
Only a few method signatures exists in **Chains**, each with a, hopefully, easily understandable purpose.

* `with` - takes a lambda outputting a resource. This resource can either be used in chain with another `with` to generate further resources or chained with `perform`.
* `perform` - if in conjunction with a `with`, takes a resource to be used, or simply takes and executes a lambda of any kind. Can also handle lambdas which calculates results.
* `exception` - the default behaviour is to handle all kinds of Throwables, but it can be used for a specific exception, or even performing the same lambda for multiple, explicit stated, exceptions.
* `execute` - the very last part of the chain builder. Depending on the nature of the previous `perform` this call is either void or returns an `Optional`.


##Caveat
As it is clear in the examples the tool lacks in its current version chaining of exception handling. Whenever an exception occurs it must have an explicit method

In the near future is should be possible to use the tool like in the last example, but letting all exceptions be handled either by their place in the stack (as in the first example), or bubble to the nearest explicit exception handling.