
// DHRUV NAIK	cs610 7529	prp

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Node7529 {
	
	private int valueChar;
	private int freqChar;
	private Node7529 leftNode;
	private Node7529 rightNode;
	
	public Node7529 (int valueChar, int freqChar, Node7529 leftNode, Node7529 rightNode) {
		
		this.valueChar = valueChar;
		this.freqChar = freqChar;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}
	
	public int getValueChar() {
		
		return valueChar;
	}
	
	public int getFreqChar() {
		
		return freqChar;
	}
	
	public Node7529 getLeftNode() {
		
		return leftNode;
	}
	
	public Node7529 getRightNode() {
		
		return rightNode;
	}
}

class BinaryHeap7529 {
	
	private int heapSize;
	private Node7529[] binaryNodeArray;
	
	public BinaryHeap7529(int num) {
		
		heapSize = 0;
		binaryNodeArray = new Node7529[num];
	}
	
	public boolean isFull() {
		
		if (heapSize == binaryNodeArray.length)
			return true;
		
		else
			return false;
	}
	
	public boolean isEmpty() {
		
		if (heapSize == 0)
			return true;
		
		else
			return false;
	}
	
	public int getHeapSize() {
		
		return heapSize;
	}
	
	public Node7529[] getBinaryHeapArray() {
		
		return binaryNodeArray;
	}
	
	public int shiftToParent(int num) {
		
		return num/2;
	}
	
	public int shiftToLeftChild(int num) {
		
		return num*2;
	}
	
	public int shiftToRightChild(int num) {
		
		return (num*2) + 1;
	}
	
	public Node7529 extractNode() throws Exception {
		
		if (isEmpty())
			throw new Exception("The heap is empty right now.");
			
		Node7529 node = binaryNodeArray[1];
		binaryNodeArray[1] = binaryNodeArray[heapSize];
		heapSize--;
		heapify(1);
			
		return node;
	}
	
	public void insertIntoHeap(Node7529 node) throws Exception {
		
		if (isFull())
			throw new Exception("The heap is full right now.");
				
		heapSize++;
		int num = heapSize;
			
		while (num>1 && binaryNodeArray[shiftToParent(num)].getFreqChar() > node.getFreqChar()) {
				
			binaryNodeArray[num] = binaryNodeArray[shiftToParent(num)];
			num = shiftToParent(num);			
		}
		
		binaryNodeArray[num] = node;
	}
	
	public void heapify(int num) {
		
		int leftChild = shiftToLeftChild(num);
		int rightChild = shiftToRightChild(num);
		int temp;
		
		if(rightChild <= heapSize) {
			
			if(binaryNodeArray[leftChild].getFreqChar() < binaryNodeArray[rightChild].getFreqChar())
				temp = leftChild;
			
			else
				temp = rightChild;
			
			if(binaryNodeArray[num].getFreqChar() > binaryNodeArray[temp].getFreqChar()) {
				
				Node7529 tempNode;
				tempNode = binaryNodeArray[temp];
				binaryNodeArray[temp] = binaryNodeArray[num];
				binaryNodeArray[num] = tempNode; 

				heapify(temp);
			}
		}
	}
}

public class henc7529 {

	static ArrayList<Integer> binaryCharList = new ArrayList<>();
	static Integer[] frequencyArray = new Integer[512];
	static String[] huffmanCode = new String[512];
	static String fileName;
	static int charCount;
	    
	//Extracting the file and saving the bytes in binaryCharList and their frequencies in frequencyArray
	public static void extractFile() {
		
		File file = new File(fileName);
	    
	    try (FileInputStream fileInputStream = new FileInputStream(file)) {
	    	
	    	int charInt;

	    	while((charInt = fileInputStream.read()) != -1) {
	    		
	    		binaryCharList.add(charInt);
	    		charCount++;
	    		
	    		if (frequencyArray[charInt] == null)
	    			frequencyArray[charInt]=1;
	    		
	    		else
	    			frequencyArray[charInt] = frequencyArray[charInt] + 1;
	    	}
	    }
	    
	    catch (FileNotFoundException e) {
			
	    	e.printStackTrace();
		}
	    
	    catch (IOException e) {
			
	    	e.printStackTrace();
		}
	}
	
	//Creating a binary heap based on the frequencyArray
	public static Node7529 createHeap() throws Exception {
		
    	BinaryHeap7529 binaryHeap = new BinaryHeap7529(charCount);
    	int tempChar;
    	
    	for (int i=0; i<frequencyArray.length; i++) {
    		
    		if (frequencyArray[i] != null) {
    		
	    		Node7529 temp = new Node7529(i, frequencyArray[i], null, null);
	    		binaryHeap.insertIntoHeap(temp);
    		}
    	}
    	
    	System.out.println("Frequency array is completed.");
    	
    	tempChar=-1;
    	
    	while (binaryHeap.getHeapSize() > 1) {
    		
    		try {
    			
				Node7529 min1 = binaryHeap.extractNode();
				Node7529 min2 = binaryHeap.extractNode();
				
				int freq = min1.getFreqChar() + min2.getFreqChar();
				binaryHeap.insertIntoHeap(new Node7529(tempChar, freq, min1, min2));
				tempChar--;
			}
    		
    		catch (Exception e) {

				e.printStackTrace();
			}
    	}
    	
    	Node7529 rootNode = binaryHeap.getBinaryHeapArray()[1];
    	return rootNode;
	}
	
	//Retrieving huffman codes for all nodes in binary heap
	public static void huffmanEncoding(Node7529 node, String path) {
		
		if (node.getRightNode() == null && node.getLeftNode() == null) {
			
			int temp = node.getValueChar();
			huffmanCode[temp] = path;
		}
		
		if (node.getLeftNode() != null)
			huffmanEncoding(node.getLeftNode(), path+"0");
		
		if (node.getRightNode() != null)
			huffmanEncoding(node.getRightNode(), path+"1");
	}
	
	//Saving all the codes into huffmancode7529.txt file
	private static void huffmanCodeToFile() {
		
		String file = "huffmancode7529.txt";
		
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
			
			String content = Integer.toString(0) + " " + huffmanCode[0];
			bufferedWriter.write(content);
			
			for(int i=1; i<huffmanCode.length; i++) {
				
				if(huffmanCode[i] != null) {
					
					content = "\n" + Integer.toString(i) + " " + huffmanCode[i];
					bufferedWriter.write(content);
				}
			}
			
			bufferedWriter.close();
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	//Converting original file into .huf file after data encoding
	public static void hufFileConversion() {
		
		String hufFileName = fileName + ".huf";
		File file = new File(fileName);
		
		try (FileOutputStream fileOutputStream  = new FileOutputStream(new File(hufFileName))) {

			String buffer ="";
			
			for(int i=0; i<binaryCharList.size(); i++) {
				
				String charCode = huffmanCode[binaryCharList.get(i)];
				String code = buffer + charCode;

				int length = code.length();
				int temp = length-length%8;

				if(length<8)
					buffer = code;
				
				else {
					
					int x=0;
					int y=8;
					
					while(y <= temp) {
						
						byte binaryBlock = (byte)Integer.parseInt(code.substring(x,y),2);
						x=y;
						y = y+8;
						fileOutputStream.write(binaryBlock);
					}

					buffer = code.substring(temp, length);
				}
			}

			fileOutputStream.close();
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		file.delete();
	}
	
	public static void main(String[] args) throws Exception {
		
		fileName = args[0];
		
		extractFile();
		System.out.println("File extraction is completed.");
		Node7529 root = createHeap();
		System.out.println("Heap creation is completed.");
		huffmanEncoding(root,"");
		System.out.println("Huffman encoding is completed.");		
		huffmanCodeToFile();
		System.out.println("Huffman text file is completed. Huffman file creation is in process.");
		hufFileConversion();
		System.out.println(".huf file is completed.");
	}
}