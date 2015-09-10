package core;

import java.awt.Image;
import java.awt.Shape;

public class SectionUtils {
	public static Image cropSection(Image image, Section section) {
		return ImageUtils.cropImage(image, section.getShape(),
				section.getxOffset(), section.getyOffset());
	}

	public static Section parseSection(String data) {
		Section section = new Section();
		String[] shapeInfo = data.split("\\|");
		int[] shapeOffset = TransformationUtils.mapToInt(shapeInfo[1], ",");
		int[] shapePath = TransformationUtils.mapToInt(shapeInfo[2], "[,;]");
		Shape shape = TransformationUtils.pointsToShape(shapeOffset, shapePath);

		section.setName(shapeInfo[0]);
		section.setShape(shape);
		section.setxOffset(shapeOffset[0]);
		section.setyOffset(shapeOffset[1]);
		
		return section;
	}
}
