/*
 * 「結果」が行列である電卓. 
 * 行列クラスを定義し, とりあえず演算としては加算や単位行列等を定義している. 
 * コンパイル & 実行：
 * javac Calculator.java IntCalc.java MemoCalc.java MatrixCalc.java
 * java MatrixCalc
 */

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * 電卓の「結果」として使う行列を表すクラス. 
 * 行列の要素は {@code double} の2次元配列で保持する. 
 * 加算や単位行列生成などの演算や, 行列を文字列から読み込む機能を提供する. 
 */
class Matrix {
    /**
     * 行列の行数. 
     */
    final int m;
    /**
     * 行列の列数. 
     */
    final int n;
    /**
     * 行列の要素. 
     * 並びは自然な並びで： {@code vals[i][j]} が (i, j) 要素. 
     */
    double [][] vals;
    /**
     * {@code m}×{@code n} のゼロ行列を作るコンストラクタ. 
     * @param m 行数 
     * @param n 列数 
     */
    Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        vals = new double[m][n];
    }
    /**
     * 与えられた行列をコピーするコンストラクタ. 
     * @param mat コピー元の行列. 
     */
    Matrix(Matrix mat) {
        this(mat.m, mat.n);
        copy(mat.vals);
    }
    /**
     * 与えられた2次元配列の内容を自身の要素としてコピーする. 
     * 次元は矛盾しないとする（与えられた2次元配列の方が大きければ良い）. 
     * @param vals コピー元の2次元配列. 
     */
    void copy(double [][] vals) {
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                this.vals[i][j] = vals[i][j];
            }
        }
    }
    /**
     * 与えら得た行列がサイズ違いで自身に加減算できないときに {@code true} を返す. 
     * @param mat 行列. 
     * @return {@code mat} と自身のサイズが同じでないときに {@code true}. 
     */
    boolean sizeMismatch(Matrix mat) {
        return (mat.m != m) || (mat.n != n);
    }
    /**
     * 与えられた行列と自身の加算結果の行列を新たに生成して返す. 
     * @param mat 加算する行列
     * @return 行列加算 {@code this} + {@code mat} の結果となる行列. 
     *         サイズ違いなどで計算不可能な場合には {@code null}. 
     */
    Matrix add(Matrix mat) {
        // 計算できないときには null を返す. 
        if(mat == null || sizeMismatch(mat)) return null;
        // あとは単純な加算
        Matrix ret = new Matrix(m, n);
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                ret.vals[i][j] = this.vals[i][j] + mat.vals[i][j];
            }
        }
        return ret;
    }
    /**
     * 与えられたサイズの単位行列を新たに生成して返す. 
     * @param n 生成する行列のサイズ
     * @return {@code n}×{@code} の単位行列
     */
    public static Matrix eye(int n) {
        Matrix ret = new Matrix(n, n); // これは nxn のゼロ行列
        for(int i = 0; i < n; i++) {
            ret.vals[i][i] = 1;  // 対角に 1 を入れる
        }
        return ret;
    }
    /**
     * 「ブロック」から与えられたサイズの単位行列を新たに生成して返す. 
     * @param block 電卓から受け取る「ブロック」.
     * @return {@code n}×{@code} の単位行列. 行のサイズの食い違いなどで生成に失敗したら {@code null}
     */
    public static Matrix read(final List<String> block) {
        try {
            int m = block.size() - 1;  // 一行目は行列の中身ではないので無視して行数を決める
            int n = -1;                // 列数は, 最初の行を見て決める
            Matrix ret = null;
            for(int i = 0; i < m; i++) {
                StringTokenizer st = new StringTokenizer(block.get(i+1));
                ArrayList<String> vs = new ArrayList<String>();
                while(st.hasMoreTokens()) vs.add(st.nextToken());
                if(n < 0) {  // 最初の行でサイズ確定 → 決定したサイズの行列をここで生成
                    n = vs.size();
                    ret = new Matrix(m, n);
                } else if(n != vs.size()) {// 行の間でサイズの食い違いがあったら null
                    return null;
                }
                // 要素をコピー
                int j = 0;
                for(String s : vs) {
                    ret.vals[i][j++] = Double.parseDouble(s);
                }
            }
            return ret;
        } catch(Exception e) { // なにか変な例外が生じた際にも生成失敗
        }
        return null;
    }
    /**
     * 行列を表す文字列を返す. 
     * 例えば次のような文字列となる. 
     * <p><blockquote><pre>{@code
     * [   2.000    3.000    4.000]
     * [   5.000    6.000    7.000]
     * }</pre></blockquote><p>
     * （各行の開始と終わりに [ と ] が置かれ, 各要素は固定幅（8.3f）で表示. 
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < m; i++) {
            sb.append("[");
            for(int j = 0; j < n; j++) {
                if(j > 0) sb.append(" ");
                sb.append(String.format("%1$8.3f", vals[i][j]));
            }
            sb.append("]");
            if(i < m - 1) sb.append("\n");
        }
        return sb.toString();
    }
}

/**
 * 行列加算を入力して現在の「結果」をその行列にする「コマンド」. 
 * <p><blockquote><pre>{@code
 * mat :
 *  TAB  a_11 ... a_1m
 *  TAB  a_21 ... a_2m
 *    ...
 *  TAB  a_n1 ... a_nm
 * }</pre></blockquote><p>
 * という, 1行目が {@code mat} である複数行「ブロック」を受け付け, 入力された行列を「結果」として返す. 
 * 行列の入力は, 各行の要素を TAB 始まりの各行に空白区切りで並べて入力する. 
 * 例えば, 次のような「ブロック」を入力として受け付ける（2行目以降は TAB を先頭に入力すること）. 
 * <p><blockquote><pre>{@code
 * mat :
 *      2 3 4
 *      5 6 7
 * }</pre></blockquote><p>
 */
class MatrixValue implements Command<Matrix> {
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix r) {
        if(block.size() <= 1) return null;
        if(ts.length == 1 && "mat".equals(ts[0])) {
            // 実際の読み込みは Matrix クラスに任せる
            return Matrix.read(block);
        }
        return null;
    }
}

/**
 * 単位行列を現在の「結果」にする「コマンド」. 
 * {@code eye} の後に整数値が並ぶ 1行の「ブロック」を受け付け, その整数値のサイズの単位行列を「結果」として返す. 
 * 例えば, 次のような「ブロック」を入力として受け付ける（2x2 の単位行列になる）. 
 * <p><blockquote><pre>{@code
 * eye 2
 * }</pre></blockquote><p>
 */
class IdentityMatrix implements Command<Matrix> {
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix r) {
        if(block.size() != 1) return null;
        if(ts.length == 2 && "eye".equals(ts[0])) {
            // 単位行列の実際の生成は Matrix クラスにまかせる
            return Matrix.eye(Integer.parseInt(ts[1]));
        }
        return null;
    }
}

/**
 * ゼロ行列を現在の「結果」にする「コマンド」. 
 * <p><blockquote><pre>{@code
 * zero n
 * }</pre></blockquote><p>
 * のような 1行「ブロック」を受け付け, その整数値 {@code n} のサイズのゼロ行列を「結果」として返す. 
 * 例えば, 次のような「ブロック」を入力として受け付ける（2x2 のゼロ行列になる）. 
 * <p><blockquote><pre>{@code
 * zero 2
 * }</pre></blockquote><p>
 */
class ZeroMatrix implements Command<Matrix> {
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix r) {
        if(block.size() != 1) return null;
        if(ts.length == 2 && "zero".equals(ts[0])) {
            int n = Integer.parseInt(ts[1]);
            return new Matrix(n, n); // コンストラクタで生成した状態がゼロ行列なので
        }
        return null;
    }
}

/**
 * 行列加算の「コマンド」. 
 * <p><blockquote><pre>{@code
 * add :
 *  TAB  a_11 ... a_1m
 *  TAB  a_21 ... a_2m
 *    ...
 *  TAB  a_n1 ... a_nm
 * }</pre></blockquote><p>
 * という, 1行目が {@code add} である複数行「ブロック」を受け付け, 入力された行列を足した「結果」を返す. 
 * 行列の入力は, 各行の要素を TAB 始まりの各行に空白区切りで並べて入力する. 
 * 例えば, 次のような「ブロック」を入力として受け付ける（2行目以降は TAB を先頭に入力すること）. 
 * <p><blockquote><pre>{@code
 * add :
 *      1 0 1
 *      0 1 0
 * }</pre></blockquote><p>
 * <br />
 * もしくは, {@code add} の後ろに変数名を書いた 1行の「ブロック」を受け付け, 
 * 変数に保存された行列を足した「結果」を返す.  
 */
class MatrixAdd extends CommandWithMemory<Matrix> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ. 
     * @param mem 変数の情報を保持するオブジェクト. 
     */
    MatrixAdd(Memory<Matrix> mem) {
        super(mem); // 親のコンストラクタをそのまま呼ぶだけ
    }
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix res) {
        // 行列の値を直接書く場合
        if(block.size() > 1 && ts.length == 1 && "add".equals(ts[0])){
            // 実際の読み込みと加算は Matrix クラスに任せる
            Matrix v = Matrix.read(block);
            return res.add(v);
        }
        // 行列を保存した変数が指定された場合
        if(block.size() == 1 && ts.length == 2 && "add".equals(ts[0])) {
            // 変数の値をメモリから取得
            Matrix v = mem.get(ts[1]);
            return res.add(v); // 実際の加算は Matrix クラス任せ
        }
        return null;
    }
}

/**
 * 行列電卓を作成して動作させるクラス. 
 * 例えば, ターミナルで次のような実行ができる. 
 * <p><blockquote><pre>{@code
 * $ javac Calculator.java IntCalc.java MemoCalc.java MatrixCalc.java
 * $ java MatrixCalc
 * [   0.000    0.000]
 * [   0.000    0.000]
 * >> mat :
 * ..      2 3 4
 * ..      5 6 7
 * ..
 * [   2.000    3.000    4.000]
 * [   5.000    6.000    7.000]
 * >> store x
 * [   2.000    3.000    4.000]
 * [   5.000    6.000    7.000]
 * >> add :
 * ..      1 0 1
 * ..      0 1 0
 * ..
 * [   3.000    3.000    5.000]
 * [   5.000    7.000    7.000]
 * >> add x
 * [   5.000    6.000    9.000]
 * [  10.000   13.000   14.000]
 * >> eye 2
 * [   1.000    0.000]
 * [   0.000    1.000]
 * >>
 * }</pre></blockquote><p>
 * これは, 最初に「結果」が 2x2 のゼロ行列で電卓が動き始め, 
 * まずは最初のプロンプトの後ろで {@code mat : } と打って複数行の「ブロック」の入力を始め, 
 * 続く TAB 始まりの 2行に 2x3 行列の各行の要素を書き, 続く空行で「ブロック」の入力を終え, 
 * その「ブロック」に対して {@code MatrixValue} が実行されて「結果」がその行列になり, 
 * 続いて {@code store x} と入力し, 
 * {@code LoadStore} が動作してその行列が変数 x に保存され, 
 * 続いて {@code add :} からの数行で同様に 2x3 行列を入力し, 
 * それに対して {@code MatrixAdd} が動作して「結果」が行列の和になり, 
 * さらに {@cdoe add x} と入力し, 
 * それに対して {@code MatrixAdd} が動作して変数 x に保存した行列が加算され, 
 * 最後に {@code eye 2} と入力し, 
 * それに対して {@code IdentityMatrix} が動いて「結果」が 2x2 の単位行列となった. 
 */
class MatrixCalc {
    /**
     * 電卓を作って実行する. 
     */
    public static void main(String [] args) throws Exception {
        // 行列を記憶する変数のための Memory インスタンス
        Memory<Matrix> mem = new Memory<Matrix>();
        // コマンドリストの作成
        ArrayList<Command<Matrix>> comms = new ArrayList<Command<Matrix>>();
        comms.add(new EmptyCommand<Matrix>());
        comms.add(new MatrixValue());
        comms.add(new IdentityMatrix());
        comms.add(new ZeroMatrix());
        comms.add(new MatrixAdd(mem));
        comms.add(new LoadStore<Matrix>(mem));
        comms.add(mem);
        // 入力は標準入力から
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 電卓の生成と実行
        Calculator<Matrix> c = new Calculator<Matrix>(br, comms);
        // 初期値は 2x2 のゼロ行列
        c.run(new Matrix(2,2));
    }
}