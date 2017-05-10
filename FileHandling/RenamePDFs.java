package FileHandling;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RenamePDFs {
	private static final CopyOption REPLACE_EXISTING = null;

	public static void rename(String newName, String src){
		Path source = Paths.get(src);
		System.out.println(source);
		String newPath =  newName + ".pdf";
			
		try {
		
			Files.move(source, source.resolveSibling(newPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void remove(Path path){
		try {
		    Files.delete(path);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
	}
}
