@SuppressWarnings("all")
open module Demo {
    requires org.slf4j;
    requires logback.core;
	requires java.sql;
	requires java.persistence;
	requires org.hibernate.orm.core;
}