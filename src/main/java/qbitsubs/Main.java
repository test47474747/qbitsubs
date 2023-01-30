package qbitsubs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Main {
	public static void main(String[] args) throws RuntimeException, IOException {
		if (args == null || args.length == 0 || args[0].isBlank()) {
			new StringPicker();
		} else {

			Path langPath = Paths.get(JarFilePathUtil.getJarFilePath(Main.class)).getParent().resolve("Languages.txt");
			if (!Files.exists(langPath)) {
				throw new QbitsubsException(
						"Error finding Languages.txt file. Make sure it is in same folder as the jar file.",
						new FileNotFoundException());
			}

			List<String> languagesList = Files.readAllLines(langPath);
			Collections.reverse(languagesList);

			Path path = Paths.get(args[0]);

			// Find subsPath
			Path subsPath = Files
					.find(path, 1,
							(p, a) -> a.isDirectory() && (p.getFileName().toString().equalsIgnoreCase("Subs")
									|| p.getFileName().toString().equalsIgnoreCase("Subtitles")))
					.findAny().orElseThrow(() -> new RuntimeException("No Subs or Subtitles folder found in " + path));

			// Get subfolders
			var subfolders = Files.list(subsPath).filter(Files::isDirectory).toArray(Path[]::new);

			if (subfolders.length == 0) {
				// there are no subfolders in the Subs directory
				var files = Files.list(subsPath).toArray(Path[]::new);
				for (String language : languagesList) {
					Optional<Path> largestSrtFile = Arrays.stream(files)
							.filter(p -> p.getFileName().toString().contains(language))
							.filter(p -> p.getFileName().toString().endsWith(".srt"))
							.max(Comparator.comparingLong(p -> {
								try {
									return Files.size(p);
								} catch (IOException e) {
									e.printStackTrace();
									throw new QbitsubsException("Error finding appropriate subtitle file", e);
								}
							}));

					if (largestSrtFile.isPresent()) {
						Path targetPath = subsPath.getParent().resolve(subsPath.getParent().getFileName() + ".srt");
						Files.copy(largestSrtFile.get(), targetPath, StandardCopyOption.REPLACE_EXISTING);
						System.out.println("Copied " + largestSrtFile.get() + " to " + targetPath);
					} else {
						System.out.println("No english .srt files found in " + subsPath);
						throw new QbitsubsException("No english .srt files found in " + subsPath,
								new FileNotFoundException());
					}
				}
			} else {
				// there are subfolders in the Subs directory
				Arrays.stream(subfolders).forEach(subfolder -> {
					Path[] files = null;
					try {
						files = Files.list(subfolder).toArray(Path[]::new);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					for (String language : languagesList) {
						Optional<Path> largestSrtFile = Arrays.stream(files)
								.filter(p -> p.getFileName().toString().contains(language))
								.filter(p -> p.getFileName().toString().endsWith(".srt"))
								.max(Comparator.comparingLong(p -> {
									try {
										return Files.size(p);
									} catch (IOException e) {
										e.printStackTrace();
										throw new QbitsubsException("Error finding appropriate subtitle file", e);
									}
								}));

						if (largestSrtFile.isPresent()) {
							Path targetPath = subsPath.resolve(largestSrtFile.get().getParent().getFileName() + ".srt");
							try {
								Files.copy(largestSrtFile.get(), targetPath, StandardCopyOption.REPLACE_EXISTING);
							} catch (IOException e) {
								e.printStackTrace();
								throw new QbitsubsException("Error copying subtitle file to target location", e);
							}
							System.out.println("Copied " + largestSrtFile.get() + " to " + targetPath);
						} else {
							System.out.println("No english .srt files found in " + subfolder);
						}
					}
				});
			}
		}
	}
}
