package CayKhung;

public class MainThread extends Thread{
    public int[][] graph;
    private Boolean[][] checked;
    private int[] parent;
    private int[] child;
    public int x, y;
    
    MainThread(int[][] graph, int x, int y){
        this.x = x;
        this.y = y;
        this.graph = new int[y-x+1][y-x+1];
        this.checked = new Boolean[y-x+1][y-x+1];
        this.parent = new int[y-x+1];
        this.child = new int[y-x+1];

        for(int i = x; i <= y; i ++){
            for(int j = x ; j <= y; j ++){
                this.graph[i-x][j-x] = graph[i][j];
                this.checked[i-x][j-x] = false;
            }
            this.parent[i-x] = -1;
            this.child[i-x] = -1;
        }
    }
    
    private Boolean check(int a, int b){
        Boolean checkChild1 = false;
        Boolean checkChild2 = false;
        Boolean checkParent1 = false;
        Boolean checkParent2 = false;

        for(int i = 0 ; i < this.graph.length; i ++){
            if(this.child[i] == a){
                checkChild1 = true;
            }
            if(this.child[i] == b){
                checkChild2 = true;
            }
            if(this.parent[i] == a){
                checkParent1 = true;
            }
            if(this.parent[i] == b){
                checkParent2 = true;
            }
        }
        if(checkChild1 && checkChild2){
            if(checkParent1 && checkParent2){
                return true;
            }
            return false;
        }
        return true;  
    }
    private int[] pick(){
        int result[] = new int[2];
        int min = Integer.MAX_VALUE;
        for(int i = 0 ; i < this.graph.length; i ++){
            for(int j = 0 ; j < this.graph.length; j ++){
                if(this.graph[i][j] > 0 && this.graph[i][j] < min && this.checked[i][j] == false){
                    min = this.graph[i][j];
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        this.checked[result[0]][result[1]] = true;
        this.checked[result[1]][result[0]] = true;
        return result;
    }
    public void solve(){
        int[] newpick;
        Boolean temp1 = false;
        Boolean temp2 = false;
        int cou = 0;
        for(int count = 0 ; count < this.graph.length - 1; count ++){        	
        	do {
            newpick = this.pick();
        	}while(check(newpick[0],newpick[1]) == false);
            MainThread.count(newpick[0] + this.x + 1, newpick[1] + this.x + 1, this.graph[newpick[0]][newpick[1]]);          
            for(int i = 0 ; i < this.graph.length; i ++){
                if(this.parent[i] == newpick[0] || this.child[i] == newpick[0]){
                    temp1 = true;
                }
                if(this.parent[i] == newpick[1] || this.child[i] == newpick[1]){
                    temp2 = true;
                }
           }
            if(temp1 && !temp2){
                this.parent[cou] = newpick[0];
                this.child[cou] = newpick[1];
                cou ++;
            }
            else if(!temp1 && temp2){
                this.parent[cou] = newpick[1];
                this.child[cou] = newpick[0];
                cou ++;
            }
            else if(!temp1 && !temp2){
                this.parent[cou] = newpick[0];
                this.child[cou] = newpick[1];
                cou++;
                this.parent[cou] = newpick[1];
                this.child[cou] = newpick[0];
                cou++;
            }
        } 
    }

    public void start(){
    	this.solve();
    }
    private static synchronized void count(int node1, int node2, int cost) {
        Main.countThread++;
        Main.result += "|---" + node1 + "---" + node2 + "---" + "cost: " + cost + "---|"+ "\t";

        if (Main.countThread % 10 == 0) {
          Main.result += "\n";
        }
      }
}
