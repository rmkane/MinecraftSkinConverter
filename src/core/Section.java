package core;

import java.awt.Shape;

public class Section {
	private String name;
	private Shape shape;
	private int xOffset;
	private int yOffset;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}
}