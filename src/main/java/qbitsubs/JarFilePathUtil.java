package qbitsubs;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class JarFilePathUtil {

	public static <T> String getJarFilePath(Class<T> clazz) {
		try {
			return byGetProtectionDomain(clazz);
		} catch (Exception e) {
			// cannot get jar file path using byGetProtectionDomain
			// Exception handling omitted
		}
		return byGetResource(clazz);
	}

	private static <T> String byGetResource(Class<T> clazz) {
		URL classResource = clazz.getResource(clazz.getSimpleName() + ".class");
		if (classResource == null) {
			throw new RuntimeException("class resource is null");
		}
		String url = classResource.toString();
		if (url.startsWith("jar:file:")) {
			// extract 'file:......jarName.jar' part from the url string
			String path = url.replaceAll("^jar:(file:.*[.]jar)!/.*", "$1");
			try {
				return Paths.get(new URL(path).toURI()).toString();
			} catch (Exception e) {
				throw new RuntimeException("Invalid Jar File URL String");
			}
		}
		throw new RuntimeException("Invalid Jar File URL String");
	}

	private static <T> String byGetProtectionDomain(Class<T> clazz) throws URISyntaxException {
		URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
		return Paths.get(url.toURI()).toString();
	}
}
