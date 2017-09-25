import java.io.*;
import java.util.*;

public class Dictionary {
	private String name, wordFilePath, meaningFilePath;
	private TreeMap<String, String> dict;
	private boolean isEdited;
	public Dictionary(){
		dict = new TreeMap<>();
		isEdited = false;
	}
	
	public Dictionary(String name, String wordFilePath, String meaningFilePath){
		this.name = name;
		this.wordFilePath = wordFilePath;
		this.meaningFilePath = meaningFilePath;
		dict = new TreeMap<>();
		isEdited = false;
		importData();
	}
	
	public String getName(){
		return name;
	}
	
	public int size(){
		return dict.size();
	}
	
	public boolean isEmpty(){
		return dict.isEmpty();
	}
	
	public boolean isEdited(){
		return isEdited;
	}
	
	public ArrayList<String> getWordList(){
		return new ArrayList<>(dict.keySet());
	}
	
	//add word and the position of its meaning to the TreeMap
	private void addWordFromFile(String str){
		String arr[] = str.split("\t");//str = word + '\t' + position + '\t' + length
		String posAndLen = arr[1] + "\t" + arr[2];
		String word = arr[0].trim().toLowerCase();
		dict.put(word, posAndLen);
	}
	
	//Read all data from file index to get word and its position in file meaning
	public void importData(){
		dict.clear();
		try{
			FileInputStream fis = null;
			BufferedReader br = null;
			fis = new FileInputStream(this.wordFilePath);
			br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			String line = br.readLine();
			while (line != null){
				addWordFromFile(line);
				line = br.readLine();
			}
			fis.close();
			br.close();
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	//convert base 64 to base 10
	private int base64ToBase10(String b64){
		String base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		int b10 = 0;
		int len = b64.length();
		for (int i = 0; i < len; ++i){
			//for each character in String b64, get its position in String base64
			int temp = base64.indexOf(b64.charAt(i));
			b10 += temp * (int)Math.pow(64, len - i - 1);
		}
		return b10;
	}
	
	//convert base 10 to base 64
	private String base10ToBase64(int b10){
		String base64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		String b64 = "";
		int remainder;
		if (b10 == 0)
			b64 = "A";
		while (b10 != 0){
			remainder = b10%64;
			b64 = base64.charAt(remainder) + b64;
			b10 = b10/64;
		}
		return b64;
	}
	
	//get a text from file meaning
	//when knowing its position and the number of character
	private String getMeaning(String posAndLen){
		String arr[] = posAndLen.split("\t");
		int pos = base64ToBase10(arr[0]);
		int len = base64ToBase10(arr[1]);
		StringBuilder str = new StringBuilder();
		try{
			FileInputStream fis = null;
			BufferedReader br = null;
			fis = new FileInputStream(meaningFilePath);
			br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			br.skip(pos);
			for (int i = 0; i < len; ++i)
				str.append((char)br.read());
			fis.close();
			br.close();
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		return str.toString();
	}
	
	//return the meaning of the word
	public String search(String word){
		word = word.trim().toLowerCase();
		String posAndLen = dict.get(word);
		if (posAndLen == null)
			return null;
		return getMeaning(posAndLen);
	}
	
	//remove word from TreeMap dict
	public String remove(String word){
		word = word.trim().toLowerCase();
		if (dict.remove(word) != null){
			isEdited = true;
			return word;
		}
		return null;
	}
	
	//add new word to this Dictionary
	public String addNewWord(String word, String meaning){
		word = word.trim().toLowerCase();
		int newWordPosition = 0;
		int newWordLength = meaning.length();
		try{
			//Write the meaning to the bottom of file meaning
			File file = new File(meaningFilePath);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file, true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			bw.append(meaning + "\n\n");
			
			//get the number of characters in the file
			//It is the position of the meaning
			newWordPosition = countCharsInFile(meaningFilePath);
			String posAndLen = base10ToBase64(newWordPosition) + "\t" + base10ToBase64(newWordLength);
			//add the pair to TreeMap dict
			dict.put(word, posAndLen);
			isEdited = true;
			//System.out.println("added a new word");
			bw.close();
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		return word;
	}
	
	//export all data to file
	public void exportData(){
		if (!isEdited)
			return;
		try{
			File file = new File(wordFilePath);
			//create a new file if it is not existed
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file, false);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			//write all pair of TreeMap dict to the file
			for (String treeKey : dict.keySet()){
				bw.append(treeKey + "\t" + dict.get(treeKey) + "\n"); 
			}
			//System.out.println("exported data to file");
			bw.close();
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	private int countCharsInFile(String filePath){
		int count = 0;
		try{
			FileInputStream fis = new FileInputStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			while (br.ready()){
				String line = br.readLine();
				count += line.length() + 1;//+1 because of '\n'
			}
			br.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
		return count;
	}
	
//	public static void main(String [] args){
//		String wordFilePath = ".\\data\\anh_viet.index";
//		String meaningFilePath = ".\\data\\anh_viet.dict";
//		Dictionary d = new Dictionary("EV", wordFilePath, meaningFilePath);
//		try{
//			FileInputStream fis = new FileInputStream(meaningFilePath);
//			BufferedReader br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
//			FileOutputStream fos = new FileOutputStream(wordFilePath, false);
//			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
//			int pos = 0;
//			int len = 0;
//			br.skip(1);
//			while (br.ready()){
//				StringBuilder str = new StringBuilder();
//				pos += len;
//				len = 0;
//				char c = (char)br.read();
//				len ++;
//				while (!(c == '/' || c == '\n')){
//					str.append(c);
//					len ++;
//					c = (char)br.read();
//				}
//				len ++;
//				System.out.println(str.toString());
//				while (str.charAt(str.length() - 1) == ' ')
//					str.deleteCharAt(str.length() - 1);
//				while ((char)br.read() != '@' && br.ready())
//					len ++;
//				
//				bw.write(str.toString());
//				bw.write("\t" + d.base10ToBase64(pos) + "\t" + d.base10ToBase64(len) + "\n");
//			}
//			br.close();
//			bw.close();
//		}catch(FileNotFoundException e){
//			//
//		}catch(IOException e){
//			//
//		}
//	}
}

