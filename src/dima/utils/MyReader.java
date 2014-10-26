package dima.utils; /**
 * Created by dima on 11.09.14.
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

public class MyReader {

	String inputFile;


	public MyReader(String _inputFile) {
		inputFile = _inputFile;
	}

	public ArrayList<String> readArray() throws IOException {
		//CARE     it can be slow
		ArrayList<String> answer1 = new ArrayList<String>(Files.readAllLines((new File(inputFile)).toPath(), Charset.forName("UTF-8")));
		ArrayList<String> answer = new ArrayList<>();
		for (int i = 0; i < answer1.size(); i++) {
			if (answer1.get(i).length() > 0 && answer1.get(i).charAt(0) == 65279) {
				answer1.get(i).substring(1);
			}
			if (answer1.get(i).length() > 0 && answer1.get(i).charAt(0) != '/') {
				answer.add(answer1.get(i));
			}
		}
		return answer;
	}


	@SuppressWarnings("All lines except the first are ignored.")
	public String readString() throws IOException {
		ArrayList<String> arrayList = readArray();
		if (arrayList != null && arrayList.size() > 0) {
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < arrayList.size(); i++) {
				//System.out.println("_" + arrayList.get(i) + "_");
				if (arrayList.get(i).indexOf(System.lineSeparator()) == -1) {
					s.append(arrayList.get(i));
				} else {
					s.append(arrayList.get(i).substring(0, arrayList.get(i).indexOf(System.lineSeparator())));
					return s.toString();
				}
			}
			return s.toString();
		}
		return null;
	}

}