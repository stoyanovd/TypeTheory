package com.utils;

/**
 * Created by dima on 10.09.14.
 */

import java.io.*;
import java.util.StringTokenizer;

public class FastScanner {
	BufferedReader br;
	StringTokenizer st;

	public FastScanner(File f) throws FileNotFoundException {
		br = new BufferedReader(new FileReader(f));
	}

	public String next() {
		while (st == null || !st.hasMoreTokens()) {
			try {
				if (br.ready()) {
					st = new StringTokenizer(br.readLine());
				} else {
					return null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return st.nextToken();
	}

	public int nextInt() {
		return Integer.parseInt(next());

	}
}