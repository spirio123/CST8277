package com.algonquincollege.cst8277.week3.labexercise2;

public class WhatIsNewJava17 {
	
	// Note - Nesting Shape interface/class Rectangle/Circle inside of WhatIsNewJava17 is NOT new ...
	public interface Shape {
	}
	
	/*
	 * Some things that are new in Java 17 are 'invisible' behind-the-scenes:
	 *   macOS Rendering Pipeline - old OpenGL 2.x, new Apple 'Metal'
	 *   Java support on macOS using 'Apple Silicon'
	 *   Strongly encapsulate JDK internal class definitions
	 *   RMI finally removed - after 12 years of being 'marked' for deletion,
	 *                         finally did it
	 */
	
	static class Rectangle implements Shape {
	    final double length;
	    final double width;
	    public Rectangle(double length, double width) {
	        this.length = length;
	        this.width = width;
	    }
	    double length() { return length; }
	    double width() { return width; }
	}

	static class Circle implements Shape {
	    final double radius;
	    public Circle(double radius) {
	        this.radius = radius;
	    }
	    double radius() { return radius; }
	}

	public static double calculatePerimeterOld(Shape shape) throws IllegalArgumentException {
        if (shape instanceof Rectangle) {
            Rectangle s = (Rectangle) shape;
            return 2 * s.length() + 2 * s.width();
        }
        else if (shape instanceof Circle) {
            Circle s = (Circle) shape;
            return 2 * s.radius() * Math.PI;
        }
        else {
            throw new IllegalArgumentException("Unrecognized shape");
        }
    }
	
    public static double getPerimeter(Shape shape) throws IllegalArgumentException {
        if (shape instanceof Rectangle rect) {          // instanceof Rectangle is a 'pattern', rect becomes live variables
            return 2 * rect.length() + 2 * rect.width();
        }
        else if (shape instanceof Circle circ) {
            return 2 * circ.radius() * Math.PI;
        }
        else {
            throw new IllegalArgumentException("Unrecognized shape");
        }
    }
}
