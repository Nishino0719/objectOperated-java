import java.util.*;
import java.io.*;

/**
 * 標準入力から入力された経路が、正しい経路かどうかを判定する．
 *
 * 使い方： java DFSMazeRunner graph-1.txt -g | java GraphMazeAnswerCheck graph-1.txt
 */
public class GraphMazeAnswerCheck {
    public static void main(String [] args) throws Exception {
        // グラフ迷路の定義ファイルを読む
        if(args.length < 1) {
            System.out.println("java GraphMazeAnswerCheck graphfile");
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        HashMap<String, HashSet<String>> neighbors = new HashMap<String, HashSet<String>>();
        for(int i = 0; i < n; i++) {
            String name = br.readLine();
            neighbors.put(name, new HashSet<String>());
        }
        for(int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine()); 
            String n1 = st.nextToken();
            String n2 = st.nextToken();
            neighbors.get(n1).add(n2);
            neighbors.get(n2).add(n1);
        }
        // 標準入力をとる
        br = new BufferedReader(new InputStreamReader(System.in));
        HashSet<String> ns = null;
        String prev = null;
        for(;;) {
            String line = br.readLine();
            if(ns == null) {
                if(!line.equals("S")) {
                    System.out.println("NG: the path must start with S");
                    return;
                }
            } else {
                if(neighbors.get(line) == null) {
                    System.out.println("NG: node " + line + " does not exist");
                    return;
                    
                }
                if(!ns.contains(line)) {
                    System.out.println("NG: no edge exists between " + prev + " and " + line);
                    return;
                }
            }
            ns = neighbors.get(line);
            prev = line;
            if(line.equals("G")) {
                System.out.println("OK");
                return;
            }
        }
    }
}

