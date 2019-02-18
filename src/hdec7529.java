
// DHRUV NAIK	cs610 7529	prp

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.IOException;
import java.util.NoSuchElementException;

class DecNode7529 {
	
	private int valueChar;
	private DecNode7529 leftNode;
	private DecNode7529 rightNode;
	private boolean leafNode;
	
	public DecNode7529(int valueChar, boolean leafNode) {
		
		this.valueChar = valueChar;
		this.leafNode = leafNode;
	}
	
	public void setLeftNode(DecNode7529 leftNode) {
		
		this.leftNode = leftNode;
	}
	
	public void setRightNode(DecNode7529 rightNode) {
		
		this.rightNode = rightNode;
	}
	
	public int getValue() {
		
		return valueChar;
	}
	
	public DecNode7529 getLeftNode() {
		
		return leftNode;
	}
	
	public DecNode7529 getRightNode() {
		
		return rightNode;
	}
	
	public boolean isLeaf() {
		
		return leafNode;
	}
}

class DecBinaryTree7529 {
	
	private DecNode7529 rootNode;
	
	public DecBinaryTree7529(){
		this.rootNode = new DecNode7529(-1,false);
	}
	
	public DecNode7529 getRootNode() {
		
		return rootNode;
	}
}

public class hdec7529 {
	
	static ArrayList<Integer> finalCharList = new ArrayList<Integer>();
	static String orgFile;
	
	//Creating binary tree to retrieve node values
	private static void createBinaryTree(String huffmanCodeFile, DecBinaryTree7529 decodedBinaryTree) {
		
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(huffmanCodeFile))) {
			
			String line;
			
			
			while ((line = bufferedReader.readLine()) != null) {
				
				DecNode7529 tempNode = decodedBinaryTree.getRootNode();
				
				String[] list = line.split(" ");
				int valueChar = Integer.parseInt(list[0]);
				String huffmanCode = list[1];
				
				for(int i=0; i<huffmanCode.length()-1; i++) {
					
					DecNode7529 node = new DecNode7529(-1,false);
					
					if(huffmanCode.charAt(i) == '0' && tempNode.getLeftNode() != null)
						tempNode=tempNode.getLeftNode();
					
					else if(huffmanCode.charAt(i) == '1' && tempNode.getRightNode() != null)
						tempNode=tempNode.getRightNode();
					
					else if(huffmanCode.charAt(i) == '0' && tempNode.getLeftNode() == null) {
						
						tempNode.setLeftNode(node);
						tempNode=node;
					}
					
					else if(huffmanCode.charAt(i) == '1' && tempNode.getRightNode() == null) {
						
						tempNode.setRightNode(node);
						tempNode=tempNode.getRightNode();
					}
				}
				
				DecNode7529 leaf = new DecNode7529(valueChar,true);
				
				if(huffmanCode.charAt(huffmanCode.length()-1) == '0')
					tempNode.setLeftNode(leaf);
				
				else
					tempNode.setRightNode(leaf);
			}
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}	
	}
	
	//Converting encoded data into original data and storing it into finalCharList array
	private static void decodeHufFile(String file, DecBinaryTree7529 decodedBinaryTree) {
		
		String[] fileName = file.split("\\.");
		
		String temp = fileName[fileName.length-1];
		
		if(!temp.equals("huf"))
			throw new NoSuchElementException("File is not type of .huf.");
		
		orgFile = file.replaceAll(".huf", "");
		ArrayList<Integer> charList = new ArrayList<Integer>();
		
		try(InputStream inputStream = new FileInputStream(file)) {
			
			DecNode7529 tempNode = decodedBinaryTree.getRootNode();
			int readBytes;
			
			while ((readBytes = inputStream.read()) != -1) {
                
				String binaryString = Integer.toBinaryString(readBytes);
				
				if(binaryString.length() < 8) {
					
					int a = 8 - binaryString.length();
					String toBeAddedString = "";
					
					for (int i=0; i<a; i++)
						toBeAddedString += "0";
					
					binaryString = toBeAddedString + binaryString;
				}
				
				for(int i=0; i<binaryString.length(); i++) {
					
					if(binaryString.charAt(i) == '0')
						tempNode = tempNode.getLeftNode();
					
					else
						tempNode = tempNode.getRightNode();
					
					if(tempNode.isLeaf()) {
						
						charList.add(tempNode.getValue());
						tempNode = decodedBinaryTree.getRootNode();
					}
				}
            }
		}
		
		catch (FileNotFoundException e) {
			 
			e.printStackTrace();
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		finalCharList = charList;
		File fileToDelete = new File(file);
		fileToDelete.delete();
	}

	//Adding decoded data into original file
	private static void writeToOrgFile() {
		
		try (FileOutputStream fileOutputStream = new FileOutputStream(orgFile)) {
			
			char contentChar;
			byte contentByte;
			
			for(int i : finalCharList) {
				
				contentChar= (char)i;
				contentByte = (byte)contentChar;
				fileOutputStream.write(contentByte);
			}
			
			fileOutputStream.close();
		} 
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		String huffmanCodeFile = "huffmancode7529.txt";
		String file = args[0];
		
		DecBinaryTree7529 decodedBinaryTree = new DecBinaryTree7529();
		createBinaryTree(huffmanCodeFile,decodedBinaryTree);
		decodeHufFile(file,decodedBinaryTree);
		writeToOrgFile();
		
		System.out.println("Decoding is completed and original file is available.");
	}
}