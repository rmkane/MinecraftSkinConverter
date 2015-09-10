import java.awt.Image;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import core.ImageUtils;
import core.Section;
import core.SectionUtils;

public class App {
	public static void main(String[] args) {
		Image img = ImageUtils.loadImage("skin1.png");
		String[] data = {
			"head|0,0|0,8;8,8;8,0;23,0;23,8;31,8;31,15;0,15;0,8",
			"body|16,16|16,20;20,20;20,16;35,16;35,20;39,20;39,31;16,31;16,20"
		};
		
		writeSections(img, data, "shapes");
	}
	
	public static void writeSections(Image img, String[] data, String directory) {
		Map<String, Section> sections = getSections(img, data);
		Iterator<Entry<String, Section>> it = sections.entrySet().iterator();
	   
		while (it.hasNext()) {
	    	Entry<String, Section> pair = it.next();
	    	Section section = pair.getValue();
	    	Image out = SectionUtils.cropSection(img, section);
	    	
	    	ImageUtils.writeImage(out, directory, section.getName() + ".png");
	    }
	}
	
	public static Map<String, Section> getSections(Image image, String[] data) {
		Map<String, Section> shapes = new HashMap<String, Section>();
		
		for (String dataItem : data) {
			Section shape = SectionUtils.parseSection(dataItem);
			shapes.put(shape.getName(), shape);
		}
		
		return shapes;
	}
}
