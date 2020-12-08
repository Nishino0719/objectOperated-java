import java.util.*;
import java.io.*;
/**
 * 一般のグラフで与えられる迷路クラス．
 * 位置はノード名である String．<br>
 * 入力ファイルの形式は次の通り：<br>
 * 　先頭行にノード数 n とエッジ数 m が空白区切りである．<br>
 * 　続く n 行は，それぞれ空白の入っていない文字列で，ノードの名前を表す．重複はない．<br>
 * 　特別なノード名は次の通り：<br>
 * 　　スタートのノード名は "S"．必ず含まれる．<br>
 * 　　ゴールのノード名は "G"．必ず含まれる．<br>
 * 　更に続く m 行に，エッジの情報が与えられる．<br>
 * 　エッジの情報は，その辺がつなぐ2つのノードの名前を空白区切りにしたもの．
 */
class GraphMaze implements Maze<String> {
    /** 
     * ノード数
     */
    private int n;
    /** 
     * エッジ数
     */
    private int m;
    /** 
     * 迷路自体の情報 ＝ 各ノードがつながるノードのリスト．
     */
    private HashMap<String, List<String>> neighbors;
    /**
     * getNeighbor 呼び出し回数．
     */
    private int cnt;
    /**
     * スタートノード名の定数．
     */
    private final static String startNode = "S";
    /**
     * ゴールノード名の定数．
     */
    private final static String goalNode = "G";
    /**
     * ファイル名を受け取り，迷路を読み込むコンストラクタ．
     * @param file ファイル名（パス）の文字列．
     */
    GraphMaze(String file) throws Exception {
        // ファイルから 1行ずつ読み込む準備
        BufferedReader br = new BufferedReader(new FileReader(file));
        // 1行読み込み、空白区切りにする準備をする
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 最初がノード数, 次がエッジ数
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        // ノード名の登録
        neighbors = new HashMap<String, List<String>>();
        for(int i = 0; i < n; i++) {
            String name = br.readLine(); // ノード名
            neighbors.put(name, new ArrayList<String>()); // 空の隣接リスト追加
        }
        // エッジの登録
        for(int i = 0; i < m; i++) {
            // 1行読み込み、空白区切りにする準備をする
            st = new StringTokenizer(br.readLine()); 
            String n1 = st.nextToken(); // 片方のノード
            String n2 = st.nextToken(); // 他方のノード
            neighbors.get(n1).add(n2);  // n1 の隣接ノードリストに n2 を追加
            neighbors.get(n2).add(n1);  // n2 の隣接ノードリストに n1 を追加
        }
        cnt = 0; // カウンタ初期化
    }

    // TODO: 必要なメソッドを適切に実装せよ
    public String getStart(){
        return startNode;
    }

    public List<String> getNeighbors(String p){
        cnt++;
        ArrayList<String> ps = new ArrayList<String>();    
        for(String str : neighbors.get(p)){
            ps.add(str);
        }
        return ps;
    }

    public boolean isGoal(String p){
        return p.equals(goalNode);        
    }
    
    /**
     * スタートからゴールに至る位置の並びを受け取り，それをそのまま出力する．
     * @param ps スタートからゴールに至る位置の並び．スタートとゴールを両端に持つ．
     */
    public void printAnswer(List<String> ps) {
        for(String s : ps) {
            System.out.println(s);
        }
        System.out.println("# of calling getNeighbors = " + cnt);
    }
}

