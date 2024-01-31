@SuppressWarnings("all")
open module com.algonquincollege.cst8277.labexercise2 {
    requires logback.core;
    requires org.slf4j;
    //Kinda weird, but SLF4J requires java.sql to work, even if there is no Database around!
    requires java.sql;
}