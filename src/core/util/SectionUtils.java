package core.util;

import java.awt.Image;
import java.awt.Point;

import core.Section;

public class SectionUtils {
	public static Image cropSection(Image image, Section section) {
		return ImageUtils.cropImage(image, section.getShape(),
				section.getOffset().x, section.getOffset().y);
	}

	// Grammar for parsing data using Backus-Naur Form
	// ========================================================================
	// <maj-div>       = '|'
	// <min-div>       = ';'
	// <sub-div>       = ','
	// <divider>       = <maj-div> | <min-div> | <sub-div>
	// <word>          = <char> { <char> }
	// <number>        = <digit> { <number> }
	// <name>          = <word> { <word> | <number> }
	// <x-offset>      = <number>
	// <y-offset>      = <number>
	// <offset>        = <x-offset> , <sub-div> , <y-offset> 
	// <x-coordinate>  = <number>
	// <y-coordinate>  = <number>
	// <point>         = <x-coordinate> , <sub-div> , <y-coordinate>
	// <points>        = <point> { <minor-div> , <point> }
	// <section>       = <name> , <maj-div> , <offset> , <maj-div> , <points>
	public static Section parseSection(String data, String suffix) {
		Section section = new Section();
		String[] shapeInfo = data.split("\\|");
		int[] shapeOffset = ConversionUtils.mapToInt(shapeInfo[1], ",");
		int[] shapePath = ConversionUtils.mapToInt(shapeInfo[2], "[,;]");

		section.setName(String.format("%s-%s", shapeInfo[0], suffix));
		section.setShape(ConversionUtils.pointsToShape(shapeOffset, shapePath));
		section.setOffset(new Point(shapeOffset[0], shapeOffset[1]));
		
		return section;
	}
	
	public static void writeSection(Section section, String directory) {
	    ImageUtils.writeImage(section.getImage(), directory, section.getName() + ".png");
	}
}
