package search;
import search.*;
import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class driver {
	static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String args[]) throws IOException{
	String docsFile = "docs.txt";
	String noiseWords = "noisewords.txt";
	LittleSearchEngine google = new LittleSearchEngine();
	google.makeIndex(docsFile, noiseWords);
	String kw1 = "andy";
	String kw2 = "worlds";

	ArrayList<String> list = google.top5search(kw1, kw2);
	if(list == null){
	System.out.println("Test??");
	return;
	}
	System.out.println("\n Results");
	for(String item : list){
	System.out.println(item);
	}

	}
}
