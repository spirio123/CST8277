/*****************************************************************************
 * File:  ExampleController.java
 * Course materials CST 8277
 * @author Teddy Yap
 * @author (original) Prof. Mike Norman
 *
 */
package com.algonquincollege.cst8277.example.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
* Example JSF Controller
*/
@Named
@SessionScoped
public class ExampleController implements Serializable {
    public static final String CUTE_MESSAGE = "I'm a little tea-pot, short and stout";

    private static final long serialVersionUID = 1L;

    // Member Variables
    protected String msg;

    public ExampleController() {
    }

    @PostConstruct
    public void setup() {
        setCuteMessage(CUTE_MESSAGE);
    }

    public String getCuteMessage() {
        return msg;
    }

    public void setCuteMessage(String cuteMessage) {
        this.msg = cuteMessage;
    }

}