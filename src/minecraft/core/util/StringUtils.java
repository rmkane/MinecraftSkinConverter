package minecraft.core.util;

public class StringUtils {
	public static String join(String[] path, String str) {
		StringBuffer buff = new StringBuffer();
		
		if (path != null && path.length > 0) {
			for (int i = 0; i < path.length; i++) {
				if (i > 0) {
					buff.append(str);
				}
				buff.append(path[i]);
			}
		}
		
		return buff.toString();
	}
}
