import java.io.File;
import java.io.IOException;

public class DeleteFiles {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String path = "C:\\Users\\kchinnak\\Desktop\\uploadingdir";
		
		File file = new File(path);
		delete(file);
		

	}
	
	public static void delete(File file) throws IOException{
		
		for(File cFile: file.listFiles()){
			if(!cFile.delete()){
				throw new IOException();
			}
		}
	}

}
