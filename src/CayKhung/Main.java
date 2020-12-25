package CayKhung;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class Main{
  private Vector<MainThread> mainThreads;
  private Vector<SubThread> subThreads;
  public static int countThread, countNonThread;
  public static long startTimeThread,startTimeNonThread;
  public static String result = "";
  private static long timeThread;
  private static long timeNonThread;
  private static int N;
  static int[][] GRAPH;
  
  static JFrame jFrame;

  static Thread executeSpeed = new Thread(new Runnable(){
      public void run() {
          boolean flag = false;
          boolean flag2 = false;
          while(true){
              if(Main.countThread == N-1 && flag == false){
                  timeThread = new Date().getTime() - Main.startTimeThread;                
                  flag = true;                
              }
              if(Main.countNonThread == N-1 && flag2 == false){
                  timeNonThread = new Date().getTime() - Main.startTimeNonThread;
                  flag2 = true;
              }
              
              try{
                  Thread.sleep(1);
              }catch (Exception e){
                  e.printStackTrace();
              }
          }
          
      };
  });
    
	  Main() {
	    this.mainThreads = new Vector<>();
	    this.subThreads = new Vector<>();
	    Main.countThread = 0;
	    Main.countNonThread = 0;
	  }

  @SuppressWarnings("deprecation")
public static void main(String[] args) throws Exception {
	  
	  	jFrame = new JFrame();
	    jFrame.setSize(800, 600);
	    JLabel lb4 = new JLabel("Nhap so node trong mot luong:");
	    JLabel lb5 = new JLabel("Du lieu dau vao");
	    JLabel lb6 = new JLabel("No file selected");
	    JLabel lb7 = new JLabel("Du lieu dau ra");

	    JTextField text1 = new JTextField();
	    JButton btn = new JButton("Thuc thi");
	    JButton btn1 = new JButton("Open File");

	    JTextArea inputData = new JTextArea();
	    inputData.setEditable(false);
	    JScrollPane scrollInputData = new JScrollPane(inputData, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    JTextArea outputData = new JTextArea();
	    outputData.setEditable(false);
	    JScrollPane scrollOutputData = new JScrollPane(outputData, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollInputData.setBounds(30, 120, 350, 350);
	    scrollOutputData.setBounds(400, 120, 350, 350);
	    lb4.setBounds(30, 30, 200, 20);
	    lb5.setBounds(160, 100, 200, 20);
	    lb6.setBounds(540, 30, 200, 20);
	    lb7.setBounds(540, 100, 200, 20);
	    btn1.setBounds(400, 30, 250, 20);
	    btn1.resize(100, 20);
	    text1.setBounds(220, 30, 30, 25);

	    btn.setBounds(660, 30, 90, 20);

	    jFrame.add(lb5);
	    jFrame.add(lb7);
	    jFrame.add(lb4);
	    jFrame.add(text1);
	    jFrame.add(btn);
	    jFrame.add(lb6);
	    jFrame.add(btn1);
	    jFrame.add(scrollInputData);
	    jFrame.add(scrollOutputData);
	    jFrame.add(new JPanel());
	    jFrame.setVisible(true);
	    
    Main.executeSpeed.start();
    btn1.addActionListener(new ActionListener() {	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			 JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
	            j.setMultiSelectionEnabled(true); 
	            int r = j.showOpenDialog(null); 	  
	            if (r == JFileChooser.APPROVE_OPTION) { 
	                File files = j.getSelectedFile();	  
	                lb6.setText(""); 
	                lb6.setText(files.getName()); 	                    
	                File myfile = new File(lb6.getText());                   
	                Scanner scan;
						try {
							scan = new Scanner(myfile);
						    int k = 0;
						    if(scan.hasNextLine()) {
						    	
						    	N = Integer.parseInt(scan.nextLine().trim());
						    	GRAPH = new int[N][N];
						    	 while (scan.hasNextLine()) {
				                      String[] row = scan.nextLine().trim().split(" ");
				                   		                    	  
				                      for (int i = 0; i < row.length; i++) {
				                    	 
				                        GRAPH[k][i] = Integer.parseInt(row[i]);
				                      }
				                      k++;
				                    }
						    }		                    
		                    try {
		                        inputData.setText(new String(Files.readAllBytes((new
		                        File(lb6.getText())).toPath())));
		                        } catch (IOException e1) {
		                        // TODO Auto-generated catch block
		                        e1.printStackTrace();
		                        }
		                    
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	                
	            } 
	            // if the user cancelled the operation 
	            else
	                lb6.setText("cancelled operation"); 
		}
	});
    
    
   btn.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(text1.getText() == "") {
			return;
		}
		
		int nodeInThread = Integer.parseInt(text1.getText());
	    Main root = new Main();

	    Main.startTimeThread = new Date().getTime();
	    
	    if (GRAPH.length % nodeInThread == 0) {
	      for (int i = 0; i < GRAPH.length / nodeInThread; i++) {
	        root.mainThreads.add(new MainThread(GRAPH, nodeInThread * i, nodeInThread * i + nodeInThread - 1));
	        int count = 0;
	        for (int j = 2; j <= GRAPH.length; j *= 2) {	          
	        	
	          if ((i + 1) % j == 0) {
	        	  
	            root.subThreads.add(
	                new SubThread(GRAPH, nodeInThread * i - count * nodeInThread, nodeInThread * i + nodeInThread - 1));
	            count++;       
	          }	         
	        }
	      }
	    }
	    try {
	      for (MainThread i : root.mainThreads) {
	        i.start();
	        i.join();
	      }
	      for (SubThread i : root.subThreads) {
	        i.start();
	        i.join();
	      }
	      
	      outputData.setText(Main.result);
	      Main.startTimeNonThread = new Date().getTime();
	      NonThread nonmultiThread = new NonThread(GRAPH, 0, GRAPH.length - 1);
		  nonmultiThread.start();
	    } catch (Exception e1) {
	      e1.printStackTrace();
	    }
	    
	    new TimeChart(timeThread, timeNonThread);
	    System.out.println(timeThread);
	    System.out.println(root.mainThreads.size() + "");
	    System.out.println(root.subThreads.size() + "");
	 }
   });
  }  
}