package dima.utils;
/**
 * Created  by dima  on 11.09.14.
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

public class MyReader {

	private String inputFile;


	public MyReader(String _inputFile) {
		inputFile = _inputFile;
	}

	public ArrayList<String> readArray() throws IOException {
		//CARE     it can be slow
		ArrayList<String> answer1 = new ArrayList<>(Files.readAllLines((new File(inputFile)).toPath(), Charset.forName("UTF-8")));
		ArrayList<String> answer = new ArrayList<>();
		for (int i = 0; i < answer1.size(); i++) {
			if (answer1.get(i).length() > 0 && answer1.get(i).charAt(0) == 65279) {
				answer1.set(i, answer1.get(i).substring(1));
			}
			if (answer1.get(i).length() > 0 && answer1.get(i).charAt(0) != '/') {
				answer.add(answer1.get(i));
			}
		}
		return answer;
	}


	//All lines except the first are ignored.
	public String readString() throws IOException {
		ArrayList<String> arrayList = readArray();
		if (arrayList != null && arrayList.size() > 0) {
			StringBuilder s = new StringBuilder();
			for (String anArrayList : arrayList) {
				if (!anArrayList.contains(System.lineSeparator())) {
					s.append(anArrayList);
				} else {
					s.append(anArrayList.substring(0, anArrayList.indexOf(System.lineSeparator())));
					return s.toString();
				}
			}
			return s.toString();
		}
		return null;
	}

}