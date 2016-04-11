package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class ObjectInfo {
	private Dimension dim;
	private Color color;
	private Point position;

	public ObjectInfo(Dimension dim, Color color, Point position) {
		super();
		this.dim = dim;
		this.color = color;
		this.position = position;
	}

	public Rectangle getRectangle() {
		return new Rectangle(this.position.x, this.position.y, this.dim.width,
				this.dim.height);
	}

	public Dimension getDim() {
		return dim;
	}

	public void setDim(Dimension dim) {
		this.dim = dim;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
}
