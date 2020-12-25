package CayKhung;

public class SubThread extends Thread {
	  private int[][] graph;
	  private Boolean[][] checked;
	  private int x;
	  private int y;

	  SubThread(int[][] graph, int x, int y) {
		this.checked = new Boolean[y-x+1][y-x+1];
	    this.x = x;
	    this.y = y;
	    this.graph = new int[y - x + 1][y - x + 1];
	    for (int i = x; i <= y; i++) {
	      for (int j = x; j <= y; j++) {
	        this.graph[i - x][j - x] = graph[i - (y - x + 1)][j];
	        this.checked[i-x][j-x] = false;
	      }
	    }

	  }

	  @Override
	  public void run() {
		    int min = Integer.MAX_VALUE;
		    int result[] = new int[2];
		    for (int i = 0; i < this.graph.length; i++) {
		      for (int j = 0; j < this.graph.length; j++) {		    	  
		        if (this.graph[i][j] < min && this.graph[i][j] > 0 && this.checked[i][j] == false) {
		        	min = this.graph[i][j];
		        	result[0] = i;
		        	result[1] = j;
		        }
		      }
		    }
		    this.checked[result[0]][result[1]] = true;
	        this.checked[result[1]][result[0]] = true;
		    SubThread.count(result[0] + x - (y - x + 1), result[1] + x , graph[result[0]][result[1]]);
	  }
	  
	  private static synchronized void count(int node1, int node2, int cost) {
		  Main.countThread++;
		  Main.result += "|---" + node1 + "---" + node2 + "---" + "cost: " + cost + "---|" + "\t";
		    if (Main.countThread % 10 == 0) {
		      Main.result += "\n";
		    }
	  }
	  
}