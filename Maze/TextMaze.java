import java.util.*;
import java.io.*;
/**
 * テキストファイルから読み込む長方形の迷路クラス．
 * 位置は int の組 (i, j)．<br>
 * 入力ファイルの形式は次の通り：<br>
 * 　先頭行に高さ h と幅 w が空白区切りである．<br>
 * 　以降の h 行は，それぞれ w 文字の文字列であり迷路の横一行分を表す． <br>
 * 　　* : 障害物（壁）．迷路の周りは障害物で覆われていると仮定して良い．<br>
 * 　　S : スタート．必ずちょうど一つ存在する．<br>
 * 　　G : ゴール．必ずちょうど一つ存在する．<br>
 */
public class TextMaze implements Maze<IntPair> {
    /** 
     * 迷路の高さ．
     */
    int h;
    /** 
     * 迷路の幅．
     */
    int w;
    /** 
     * 迷路自体の配列．
     * とりあえず文字のまま保持するとする．
     */
    char [][] field;
    /**
     * スタート位置．
     */
    IntPair start;
    /**
     * ゴール位置．
     */
    IntPair goal;
    /**
     * getNeighbor 呼び出し回数．
     */
    int cnt;
    /**
     * ファイル名を受け取り，迷路を読み込むコンストラクタ．
     * @param file ファイル名（パス）の文字列．
     */
    TextMaze(String file) throws Exception {
        // ファイルから 1行ずつ読み込む準備
        BufferedReader br = new BufferedReader(new FileReader(file));
        // 1行読み込み、空白区切りにする
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 最初が高さ、次が幅
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        // 迷路盤面を記録する配列作成
        field = new char[h][w];
        // 迷路盤面に、ファイルから文字を読み込む
        for(int i = 0; i < h; i++) {
            String line = br.readLine(); // 1行読み込み
            for(int j = 0; j < w; j++) {
                field[i][j] = line.charAt(j);
                if(field[i][j] == 'S') { // スタート位置の記録
                    start = new IntPair(i, j);
                } else if(field[i][j] == 'G') { // ゴール位置の記録
                    goal = new IntPair(i, j);
                }
            }
        }
        cnt = 0; // カウンタ初期化
    }
    /**
     * 迷路のスタート位置を取得．
     * @return スタート位置．
     */
    public IntPair getStart() {
        return new IntPair(start); // 念の為、コピーして返す
    }
    // 4方向 (1,0), (0,1), (-1,0), (0,-1) 
    private static final int [] di = new int[] {1, 0, -1, 0};
    private static final int [] dj = new int[] {0, 1, 0, -1};
    /**
     * 指定された位置{@code p}から移動可能な位置のリストを返す．
     * @param p 指定位置．
     * @return 指定位置{@code p}から移動可能な位置のリスト．
     */
    public List<IntPair> getNeighbors(IntPair p) {
        cnt++; // getNeightbors の呼び出し回数カウント
        ArrayList<IntPair> ps = new ArrayList<IntPair>();
        if(field[p.i][p.j] == '*') { // 壁の中にいる -> 移動できない
            return ps;
        }
        for(int k = 0; k < 4; k++) { // 4 方向を順に確認
            int ii = p.i + di[k];
            int jj = p.j + dj[k];
            // 隣接位置 (ii, jj) が壁でないなら移動可能位置として ps に追加
            if(ii >= 0 && jj >= 0 && ii < h && jj < w && field[ii][jj] != '*') {
                ps.add(new IntPair(ii, jj));
            }
        }
        return ps;
    }
    /**
     * 指定された位置{@code p}がゴールかどうかを返す．
     * @param p 指定位置．
     * @return 指定位置{@code p}がゴールであれば{@code true}．そうでなければ{@code false}．
     */
    public boolean isGoal(IntPair p) {
        return goal.equals(p);
    }
    /**
     * スタートからゴールに至る位置の並びを受け取り、それらの位置に{@code .}をおいた迷路を出力する．
     * @param ps スタートからゴールに至る位置の並び．スタートとゴールを両端に持つ．
     */
    public void printAnswer(List<IntPair> ps) {
        char [][] lines = new char[h][w];  // 盤面をコピーする
        for(int i = 0; i < h; i++) {
            for(int j = 0; j < w; j++) {
                lines[i][j] = field[i][j];
            }
        }
        for(IntPair p : ps) {  // 移動経路に + を置いていく
            // 空白のところ以外には経路表示をしない
            if(lines[p.i][p.j] != ' ') continue;
            lines[p.i][p.j] = '.';
        }
        for(int i = 0; i < h; i++) { // 移動経路を入れた盤面を出力
            System.out.println(new String(lines[i]));
        }
        System.out.println("# of calling getNeighbors = " + cnt);
    }
}

