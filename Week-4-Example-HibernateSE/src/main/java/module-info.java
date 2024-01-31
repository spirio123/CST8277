@SuppressWarnings("all")
open module HibernateSE {
    requires org.slf4j;
    requires logback.core;
	requires java.sql;
	requires java.persistence;
	requires org.hibernate.orm.core;
	requires java.naming;
}