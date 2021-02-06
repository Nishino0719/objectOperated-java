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
     * 与えられた行列と自身の減算結果の行列を新たに生成して返す. 
     * @param mat 減算する行列
     * @return 行列減算 {@code this} + {@code mat} の結果となる行列. 
     *         サイズ違いなどで計算不可能な場合には {@code null}. 
     */
    Matrix sub(Matrix mat) {
        // 計算できないときには null を返す. 
        if(mat == null || sizeMismatch(mat)) return null;
        // あとは単純な加算
        Matrix ret = new Matrix(m, n);

        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                ret.vals[i][j] = this.vals[i][j] - mat.vals[i][j];
            }
        }
        return ret;
    }
    /**
     * 与えられた行列と自身の乗算結果の行列を新たに生成して返す. 
     * @param mat 乗算する行列
     * @return 行列乗算 {@code this} + {@code mat} の結果となる行列. 
     *         サイズ違いなどで計算不可能な場合には {@code null}. 
     */
    Matrix mul(Matrix mat) {
        // 計算できないときには null を返す. 
        if(mat == null || n != mat.m ) return null;
        // あとは単純な加算
        //:poop:
        Matrix ret = new Matrix(m, n);
        for(int i = 0; i < m; i++) {
            for(int k=0; k< mat.n ;k++){
                double c = 0;
                for(int j = 0; j < n; j++) {
                    c +=  this.vals[i][j] * mat.vals[j][k];
                }
                ret.vals[i][k] = c;
            }
        }
        return ret;
    }
    /**
     * 与えられた整数と自身の乗算結果の行列を新たに生成して返す. 
     * @param mat 乗算する行列
     * @return 行列乗算 {@code this} + {@code a} の結果となる行列. 
     *         サイズ違いなどで計算不可能な場合には {@code null}. 
     */
    Matrix anymul(int a) {
        Matrix ret = new Matrix(m, n);

        for(int i = 0; i < m; i++) {
            for(int j=0; j< n ;j++){
                ret.vals[i][j] = a * this.vals[i][j];
            }
        }
        return ret;
    }
    /**
     * 与えられた整数と自身の除算結果の行列を新たに生成して返す. 
     * @param mat 除算する行列
     * @return 行列除算 {@code this} + {@code a} の結果となる行列. 
     *         サイズ違いなどで計算不可能な場合には {@code null}. 
     */
    Matrix div(Matrix mat) {
        Matrix invMat = new Matrix(m, n);
        Matrix ret = new Matrix(m, n);
        //まずは逆行列を求める.
        invMat = invMat.inv(mat);
        if(ret.m == m && ret.n == n){
            ret = invMat.mul(this);
            return ret;
        }else{
            System.out.println(m + "×" + n+ "行列と"+ret.m+"×"+ret.n+"行列は乗算できません。");
            return null;
        }
    }

    /**
     * 与えられた行列の逆行列の行列を結果として返す. 
     * @param mat 逆行列する行列
     * @return 逆行列行列となる行列. 
     *         サイズ違いなどで計算不可能な場合には {@code null}. 
     */
    Matrix inv(Matrix mat) {
        if(mat.m != mat.n){
            System.out.println("入力された行列は正方行列でないので逆行列を持ちません.");
            return null;
        }
        if(mat.n != m){
            System.out.println(m +"×"+n+"行列と"+mat.m+"×"+mat.n+"行列は除算することができません.");
            return null;
        }
        //掃き出し法を用いる.
        Matrix inv = new Matrix(mat.m, mat.n);
        //単位行列の作成
        for(int i=0;i<mat.m;i++){
            for(int j = 0;j< mat.n;j++){
                if(i == j){
                    inv.vals[i][j] = 1;
                }else{
                    inv.vals[i][j] = 0;
                }
            }
        }
        //掃き出し法
        for(int i = 0;i < mat.m; i++){
            double buf = 1 / mat.vals[i][i];
            for(int j = 0;j < mat.n;j++){
                mat.vals[i][j] *= buf;
                inv.vals[i][j] *= buf;
            }
            for(int j = 0;j < mat.n;j++){
                if(i != j){
                    buf = mat.vals[j][i];
                    for(int k=0;k < mat.n;k++){
                    mat.vals[j][k] -= mat.vals[i][k] * buf;
                    inv.vals[j][k] -= inv.vals[i][k] * buf;
                    }
                }
           }
        }
        return inv;
    }
    /**
     * 現在の行列を転置行列を新たに生成して返す. 
     * @param mat 乗算する行列
     * @return 行列乗算 {@code this} + {@code a} の結果となる行列. 
     *         サイズ違いなどで計算不可能な場合には {@code null}. 
     */
    Matrix trans() {
        Matrix ret = new Matrix(n, m);
        for(int i = 0; i < m; i++) {
            for(int j=0; j< n ;j++){
                ret.vals[j][i] = this.vals[i][j];
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
     * 与えられたサイズの行列に任意の数字を新たに生成した行列を返す. 
     * @param n 生成するスカラーのサイズ
     * @return {@code n}×{@code} の任意の行列
     */
    public static Matrix anyn(int n,int a) {
        Matrix ret = new Matrix(n, n); // これは nxn のゼロ行列
        for(int i = 0;i<n;i++){
            for(int j= 0; j<n;j++){
                ret.vals[i][j] = a;
            }
        }
        return ret;
    }
    /**
     * 与えられたサイズの行列に任意の数字を新たに生成した行列を返す. 
     * @param n 生成する行列のサイズ
     * @return {@code n}×{@code} の任意の行列
     */
    public static void help(ArrayList<String> commands,String option) {

        boolean isExist = false;
        if(option.equals("all")){
            System.out.println("コマンド一覧");
            System.out.println("コマンドの詳細が知りたい場合は >>help コマンド名");
            System.out.println(commands);
            isExist = true;
        }else{
            for(String command: commands){
                if(command.equals(option)){
                    System.out.println(option +"コマンドは以下の通りです。");
                    System.out.println("________________________________");
                    try{
                        File file = new File("help/" + option + ".txt");
                        FileReader filereader = new FileReader(file);
                        int ch;
                        while((ch = filereader.read()) != -1){
                            System.out.print((char)ch);
                        }
                        
                        filereader.close();
                    }catch(FileNotFoundException e){
                        System.out.println(e);
                    }catch(IOException e){
                        System.out.println(e);
                    }
                    isExist = true;
                    System.out.println("");
                    System.out.println("________________________________");
                    break;
                }
            }
        }
        if(!isExist){
            System.out.println("そのようなコマンドは存在しません。");
            System.out.println(">>help allでコマンドの一覧表示");
        }
        System.out.println(">>現在の行列");

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
 * 任意の整数が入った行列を現在の「結果」にする「コマンド」. 
 * {@code anyn} の後に整数値が並ぶ 1行の「ブロック」を受け付け, その整数値のサイズの単位行列を「結果」として返す. 
 * 例えば, 次のような「ブロック」を入力として受け付ける（2x2 の単位行列になる）. 
 * <p><blockquote><pre>{@code
 * anyn 2 10
 * }</pre></blockquote><p>
 */
class anynMatrix implements Command<Matrix> {
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix r) {
        try {
            if(block.size() == 1){
                if(ts.length == 3 && "anyn".equals(ts[0])) {
                    // 単位行列の実際の生成は Matrix クラスにまかせる
                    if(Integer.parseInt(ts[1]) <= 1){
                        System.out.println(Integer.parseInt(ts[1]));
                        return null;
                    }else{
                        return Matrix.anyn(Integer.parseInt(ts[1]),Integer.parseInt(ts[2]));
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
/**
 * コマンドの一覧を[閲覧]するコマンド
 * {@code help} 
 * 例えば, 次のような「ブロック」を入力として受け付ける. 
 * <p><blockquote><pre>{@code
 * help
 * }</pre></blockquote><p>
 */
class CommandsHelp implements Command<Matrix> {
    private ArrayList<String> helpCommands;
    /**
     * 変数の情報を保持する {@code Arraylist} オブジェクトを受け取るコンストラクタ. 
     * @param commands 変数の情報を保持するオブジェクト. 
     */
    CommandsHelp(ArrayList<String> commands) {
        helpCommands = commands;
    }

    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix r) {
        try {
            if(block.size() == 1){
                if(ts.length == 2 && "help".equals(ts[0])) {
                    // コマンドの表示は Matrix クラスにまかせる
                    Matrix.help(helpCommands,ts[1]);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return r;
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
class MatrixSub extends CommandWithMemory<Matrix> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ. 
     * @param mem 変数の情報を保持するオブジェクト. 
     */
    MatrixSub(Memory<Matrix> mem) {
        super(mem); // 親のコンストラクタをそのまま呼ぶだけ
    }
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix res) {
        // 行列の値を直接書く場合
        if(block.size() > 1 && ts.length == 1 && "sub".equals(ts[0])){
            // 実際の読み込みと加算は Matrix クラスに任せる
            Matrix v = Matrix.read(block);
            return res.sub(v);
        }
        // 行列を保存した変数が指定された場合
        if(block.size() == 1 && ts.length == 2 && "sub".equals(ts[0])) {
            // 変数の値をメモリから取得
            Matrix v = mem.get(ts[1]);
            return res.sub(v); // 実際の加算は Matrix クラス任せ
        }
        return null;
    }
}
class MatrixMul extends CommandWithMemory<Matrix> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ. 
     * @param mem 変数の情報を保持するオブジェクト. 
     */
    MatrixMul(Memory<Matrix> mem) {
        super(mem); // 親のコンストラクタをそのまま呼ぶだけ
    }
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix res) {
        // 行列の値を直接書く場合
        if(block.size() > 1 && ts.length == 1 && "mul".equals(ts[0])){
            // 実際の読み込みと加算は Matrix クラスに任せる
            Matrix v = Matrix.read(block);
            return res.mul(v);
        }
        // 行列を保存した変数が指定された場合
        if(block.size() == 1 && ts.length == 2 && "mul".equals(ts[0])) {
            // 変数の値をメモリから取得
            Matrix v = mem.get(ts[1]);
            return res.mul(v); // 実際の加算は Matrix クラス任せ
        }
        return null;
    }
}
class MatrixAnyMul implements Command<Matrix> {
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix res) {
        try {
            if(block.size() == 1 && ts.length == 2 && "anymul".equals(ts[0])){
                return res.anymul(Integer.parseInt(ts[1]));
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("anymul 任意の整数値　で入力してください");
            return null;
        }
        return null;
    }
}
class MatrixDiv extends CommandWithMemory<Matrix> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ. 
     * @param mem 変数の情報を保持するオブジェクト. 
     */
    MatrixDiv(Memory<Matrix> mem) {
        super(mem); // 親のコンストラクタをそのまま呼ぶだけ
    }
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix res) {
        // 行列の値を直接書く場合
        if(block.size() > 1 && ts.length == 1 && "div".equals(ts[0])){
            // 実際の読み込みと加算は Matrix クラスに任せる
            Matrix v = Matrix.read(block);
            return res.div(v);
        }
        // 行列を保存した変数が指定された場合
        if(block.size() == 1 && ts.length == 2 && "div".equals(ts[0])) {
            // 変数の値をメモリから取得
            Matrix v = mem.get(ts[1]);
            return res.div(v); // 実際の加算は Matrix クラス任せ
        }
        return null;
    }
}
class MatrixInv extends CommandWithMemory<Matrix> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ. 
     * @param mem 変数の情報を保持するオブジェクト. 
     */
    MatrixInv(Memory<Matrix> mem) {
        super(mem); // 親のコンストラクタをそのまま呼ぶだけ
    }
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix res) {
        // 行列の値を直接書く場合
        if(block.size() > 1 && ts.length == 1 && "inv".equals(ts[0])){
            // 実際の読み込みと加算は Matrix クラスに任せる
            Matrix v = Matrix.read(block);
            return res.inv(v);
        }
        // 行列を保存した変数が指定された場合
        if(block.size() == 1 && ts.length == 2 && "inv".equals(ts[0])) {
            // 変数の値をメモリから取得
            Matrix v = mem.get(ts[1]);
            return res.inv(v); // 実際の加算は Matrix クラス任せ
        }
        return null;
    }
}
//現在の行列の転置行列を求める
class MatrixTrans implements Command<Matrix> {
    public Matrix tryExec(final String [] ts, final List<String> block, final Matrix res) {
        try {
            if(block.size() == 1 && ts.length == 1 && "trans".equals(ts[0])){
                return res.trans();
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
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
        ArrayList<String> commands = new ArrayList<String>();
        comms.add(new EmptyCommand<Matrix>());
        comms.add(new MatrixValue());
        commands.add("mat");
        comms.add(new IdentityMatrix());
        commands.add("eye");
        comms.add(new ZeroMatrix());
        commands.add("zero");
        comms.add(new MatrixAdd(mem));
        commands.add("add");
        comms.add(new MatrixSub(mem));
        commands.add("sub");
        comms.add(new MatrixMul(mem));
        commands.add("mul");
        comms.add(new MatrixDiv(mem));
        commands.add("div");
        comms.add(new MatrixInv(mem));
        commands.add("inv");
        comms.add(new anynMatrix());
        commands.add("anyn");
        comms.add(new MatrixAnyMul());
        commands.add("anymul");
        comms.add(new MatrixTrans());
        commands.add("trans");
        comms.add(new LoadStore<Matrix>(mem));
        commands.add("store");
        comms.add(new CommandsHelp(commands));
        commands.add("help");
        comms.add(mem);
        // 入力は標準入力から
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 電卓の生成と実行
        Calculator<Matrix> c = new Calculator<Matrix>(br, comms);
        // 初期値は 2x2 のゼロ行列
        c.run(new Matrix(2,2));
    }
}