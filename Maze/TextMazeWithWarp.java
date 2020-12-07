import java.util.*;
import java.io.*;
/**
 * テキストファイルから読み込む長方形の迷路クラス（ワープあり）．
 * 位置は int の組 (i, j)．<br>
 * 入力ファイルの形式は次の通り：<br>
 * 　先頭行に高さ h と幅 w が空白区切りである．<br>
 * 　以降の h 行は，それぞれ w 文字の文字列であり迷路の横一行分を表す． <br>
 * 　　* : 障害物（壁）．迷路の周りは障害物で覆われていると仮定して良い．<br>
 * 　　S : スタート．必ずちょうど一つ存在する．<br>
 * 　　G : ゴール．必ずちょうど一つ存在する．<br>
 *    [A-E] : ワープ入口．各文字高々一つ．対応するワープ出口（小文字）に飛ぶ．出口は必ず存在する．<br>
 *    [a-e] : ワープ出口．各文字高々一つ．<br>
 */
class TextMazeWithWarp extends TextMaze {
    /**
     * ワープ出口の情報を保持する連想配列．
     */
    private HashMap<Character,IntPair> warpOuts;
    /**
     * ファイル名を受け取り，迷路を読み込むコンストラクタ．
     * @param file ファイル名（パス）の文字列．
     */
    TextMazeWithWarp(String file) throws Exception {
        // TextMaze のコンストラクタを動かし、ファイル内容を field に読み込む
        super(file);
        // ワープ出口の情報をいれる連想配列のインスタンスを生成
        warpOuts = new HashMap<Character,IntPair>();

        // TODO: ここらへんをうめよ
        // 迷路盤面上を走査し、ワープ出口の情報を warpOuts に集める
        for(...) {
            for(...) {
                if(...) {
                    warpOuts.put(..., ...);
                }
            }
        }
    }
    /**
     * 指定された位置{@code p}から移動可能な位置のリストを返す．
     * @param p 指定位置．
     * @return 指定位置{@code p}から移動可能な位置のリスト．
     */
    public List<IntPair> getNeighbors(IntPair p) {
        // TODO: ここらへんを埋めよ
        // 「p がワープ入口であるならば、p をワープ出口の座標に変更しておく」をする
        if(...) {
            p = ...
        }

        // あとの処理は親クラスの処理と同じ
        return super.getNeighbors(p);
    }
}

