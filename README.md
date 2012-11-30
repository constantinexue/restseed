# 什么是RestSeed #

RestSeed是一个RESTful风格的完整的示例项目，以[Guice](https://code.google.com/p/google-guice/), [Jetty](http://www.eclipse.org/jetty/), [Jersey](http://jersey.java.net/), JPA为基础。
包括HTTP层，逻辑层DomainModel，数据层Entity和Repository，以及模型对象映射等内容。

# 为什么是Guice，而不是Spring #

- Spring的示例已经很多了，可以参考[SpringSide](https://github.com/springside/springside4/)。
- Guice比较轻量级，使用方便，全部采用代码进行配置。个人非常喜欢它优雅的设计。

# 具体包含哪些模块示例？ #

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
[Hibernate](http://www.hibernate.org/) /
[guice-persist](https://code.google.com/p/google-guice/wiki/GuicePersist/)
- LOG:
[slf4j](http://www.slf4j.org/) /
[log4j](http://logging.apache.org/log4j/1.2/)
- Other:
[ModelMapper](http://modelmapper.org/) /
- Test:
[JUnit](http://www.junit.org/)
