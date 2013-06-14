# What's RestSeed? #

RestSeed is a [RESTful](https://jax-rs-spec.java.net/) complete seed project based on [Guice](https://code.google.com/p/google-guice/), [Jetty](http://www.eclipse.org/jetty/), [Jersey](http://jersey.java.net/) and JPA.
It includes HTTP layer, domain models, entities and repositories, and mappers.

# What's the reason I chose Guice, not Spring? #

- There are many Spring examples on the web. e.g. [SpringSide](https://github.com/springside/springside4/)
- Guice is light weight, easy to use and based on coding configuration. It's so graceful!

# What're the components contained in this project? #

- Google:
[guice](https://code.google.com/p/google-guice/) /
[gson](https://code.google.com/p/google-gson/) /
[guava](https://code.google.com/p/guava-libraries/)
- Jetty:
[jetty](http://www.eclipse.org/jetty/)
- Jersey:
[jersey-server & jersey-multipart](http://jersey.java.net/) /
[guice-servlet](https://code.google.com/p/google-guice/wiki/Servlets)
- Apache:
[commons-lang3](http://commons.apache.org/lang/) /
[commons-io](http://commons.apache.org/io/)
- Joda:
[joda-time](http://joda-time.sourceforge.net/)
- JPA:
[hibernate](http://www.hibernate.org/) /
[guice-persist](https://code.google.com/p/google-guice/wiki/GuicePersist/)
- LOG:
[slf4j](http://www.slf4j.org/) /
[log4j](http://logging.apache.org/log4j/1.2/)
- Other:
[ModelMapper](http://modelmapper.org/) / [Reflections](https://code.google.com/p/reflections/)
- Test:
[JUnit](http://www.junit.org/)

# Projects

- parent: basic maven definition 
- common: shared object for RESTful API definition
- server: RESTful API implementation
- client: RESTful client implmentation

Author: @ConstantineXue at Sina Weibo