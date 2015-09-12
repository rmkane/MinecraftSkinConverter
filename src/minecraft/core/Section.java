package minecraft.core;

import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;

public class Section {
	private String name;
	private Shape shape;
	private Point offset;
	private Image image;

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

	public Point getOffset() {
		return offset;
	}

	public void setOffset(Point offset) {
		this.offset = offset;
	}

	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
}