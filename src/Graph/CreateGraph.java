package Graph;


import java.io.File;
import java.io.FileWriter;
import java.util.Random;


public class CreateGraph{
	private static final int N = 16384;
    public static void main(String[] args) throws Exception{
        Random rd = new Random();
        FileWriter fileWriter = new FileWriter(new File("Graph-16384.txt"));
        fileWriter.write(N + "\n");
        int[][] array = new int[N][N];
        for(int i = 0 ; i < N; i ++){
            for(int j = 0 ; j < N; j ++){
                array[i][j] = 0;
            }
        }
        for(int i = 0 ; i < N; i ++){
            for(int j = 0 ; j < N; j ++){
                if(i == j){
                    array[i][j] = 0;
                }
                else if(array[j][i] != 0){
                    array[i][j] = array[j][i];
                }else {
                    array[i][j] = rd.nextInt(100); 
                }
                fileWriter.write(array[i][j] + " ");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }
    
}

