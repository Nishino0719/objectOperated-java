import java.util.*;
/**
 * 迷路インターフェース．
 *
 * @param <Pos> 迷路の位置の型．
 */
public interface Maze<Pos> {
    /**
     * 迷路のスタート位置を取得．
     * @return スタート位置．
     */
    Pos getStart();
    /**
     * 指定された位置{@code p}から移動可能な位置のリストを返す．
     * @param p 指定位置．
     * @return 指定位置{@code p}から移動可能な位置のリスト．
     */
    List<Pos> getNeighbors(Pos p);
    /**
     * 指定された位置{@code p}がゴールかどうかを返す．
     * @param p 指定位置．
     * @return 指定位置{@code p}がゴールであれば{@code true}．そうでなければ{@code false}．
     */
    boolean isGoal(Pos p);
    /**
     * スタートからゴールに至る位置の並びを受け取り、それを適宜わかりやすいように出力する．
     * @param ps スタートからゴールに至る位置の並び．スタートとゴールを両端に持つ．
     */
    void printAnswer(List<Pos> ps);
}
