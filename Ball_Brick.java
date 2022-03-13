import java.util.Scanner;
public class Ball_Brick{
    public static String list = "";
    public static void print(String[][] a,int n,int bc){
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                System.out.print(a[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("Ball count is "+bc+".");
    }
    public static void travel(String[][] a,int n,int bc){
        for(int i=n-2;i>=0;i--){
            if (!a[i][bc].equals("W") && !a[i][bc].equals(" ")){
                String str = a[i][bc];
                if(str.length() == 1 && Character.isDigit(str.charAt(0))){
                    int N = Integer.parseInt((a[i][bc]));
                    a[i][bc] =  Integer.toString(N-1);
                    if(a[i][bc].equals("0")){
                        a[i][bc] = " ";
                    }
                    break;
                }else if(a[i][bc].equals("DE")){
                    for (int k=1;k<n-1;k++){
                        a[i][k] = " ";
                    }
                    break;
                }else if(a[i][bc].equals("DS")){
                    for (int k=i-1;k<=i+1;k++){
                        for (int j=bc-1;j<=bc+1;j++){
                            a[k][j] = " ";
                        }
                    }
                    break;
                }else if(a[i][bc].equals("B")){
                    a[i][bc] = " ";
                    if(a[n-1][bc+1].equals("_")){
                        a[n-1][bc-1] = "_";
                    }else{
                        a[n-1][bc+1] = "_";
                    }
                    list +=bc+1;
                    break;
                }
            }
        }
    }
    public static boolean check(String[][] a,int n){
        for (int i=1;i<n-1;i++){
            for(int j=1;j<n-1;j++){
                if(!a[i][j].equals(" ")){
                    return false;
                }
            }
        }
        return true;
    }
    public static void change(String[][] a,int n,int pos,int prev){
        if(list.contains(Integer.toString(pos))){
            a[n-1][pos] = "O";
            a[n-1][prev] = "_";
        }else if(list.contains(Integer.toString(prev))){
            a[n-1][prev] = "_";
            a[n-1][pos] = "O";
        }else{
            a[n-1][prev] = "G";
            a[n-1][pos] = "O";
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("ENTER THE SIZE OF THE NxN MATRIX:");
        int n = sc.nextInt();
        String[][] game = new String[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i==0 || j==0 || j == n-1 || i == n-1){
                    if(i==n-1 && j!=0 && j!= n-1){
                            game[i][j] = "G";
                    }else{
                        game[i][j] = "W";
                    }
                }else{
                    game[i][j] = " ";
                }
            }
        }
        while(true){
            System.out.print("Enter the brick's position and the brick type :");
            int a = sc.nextInt();
            int b= sc.nextInt();
            game[a][b] = sc.next();
            System.out.print("Do you want to continue(Y or N)?");
            char z = sc.next().charAt(0);
            if(z != 'Y' && z != 'y'){
                break;
            }
        }
        System.out.print("Enter ball count : ");
        int bc = sc.nextInt();
        int pos = n/2;
        game[n-1][pos] = "O";
        print(game,n,bc);
        while(true){
            System.out.print("Enter the direction in which the ball need to traverse : ");
            String choice = sc.next();
            switch (choice) {
                case "ST" -> travel(game, n, pos);
                case "LD" -> {
                    pos--;
                    if (game[n - 2][pos].equals("W")) {
                        bc--;
                        pos = n / 2;
                    } else {
                        travel(game, n, pos);
                        if(!list.contains(Integer.toString(pos))){
                            if(!game[n-1][pos].equals("_")){
                                bc--;
                            }
                        }
                        change(game, n, pos,pos+1);
                    }
                }
                case "RD" -> {
                    pos++;
                    if (game[n - 2][pos].equals("W")) {
                        bc--;
                        pos = n / 2;
                    } else {
                        travel(game, n, pos);
                        if(!list.contains(Integer.toString(pos))){
                            if(!game[n-1][pos].equals("_")){
                                bc--;
                            }
                        }
                        change(game, n, pos,pos-1);
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + choice);
            }
            print(game,n,bc);
            if(bc == 0){
                System.out.println("you lose GAME OVER..!!");
                break;
            }else if(check(game,n)){
                System.out.println("You win HURRAY..!!");
                break;
            }
        }
    }
}
