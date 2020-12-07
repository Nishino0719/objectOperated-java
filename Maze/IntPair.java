/**
 * int の組のクラス．
 */
public class IntPair {
    /**
     * 第一要素
     */
    int i;
    /**
     * 第二要素
     */
    int j;
    /**
     * 各要素を受け取るコンストラクタ．
     * @param i 第一要素．
     * @param j 第二要素．
     */
    IntPair(int i, int j) {
        this.i = i;
        this.j = j;
    }
    /**
     * コピーコンストラクタ．
     * @param p コピー元の {@code IntPair}．
     */
    IntPair(IntPair p) {
        this.i = p.i;
        this.j = p.j;
    }
    /**
     * 指定されたオブジェクトとの同値性判定．
     * 指定されたオブジェクトが {@code IntPair} であり，かつ，第一要素と第二要素がともに等しければ同じである．
     * @param o 指定されたオブジェクト．
     * @return 指定されたオブジェクトと同じなら {@code true}，そうでなければ {@code false}．
     */
    public boolean equals(Object o) {
        if(o == null) return false;  // 相手が null なら false
        if(o instanceof IntPair) {   // o が IntPair のインスタンスなら、
            IntPair ip = (IntPair)o; // o を IntPair のインスタンスとみなして
            return ip.i == i && ip.j == j; // 比較
        }
        return false;
    }
    /**
     * このオブジェクトのハッシュ値を返す．
     * {@code this.equals(o) == true} のとき，{@code o.hashCode() == this.hashCode()} である．
     * とりあえず Fibonacci hashing (The Art of Computer Programming, Vol. 3, Sect. 6.4) で第二要素を飛ばす．
     * @return このオブジェクトのハッシュ値．
     */
    public int hashCode() {
        return i + j * 0x9e3779b9; // 2654435769;
    }
    /**
     * この組の文字列表現を返す．
     * @return この組の文字列表現．
     */
    public String toString() {
        return "(" + i + "," + j + ")";
    }
}
