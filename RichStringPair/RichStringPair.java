/*
 * String のペアに様々なインターフェースを実装する
 */

import java.util.*;

// 「String のペア」のクラス
class StringPair {
    public String fst;
    public String snd;
    StringPair(String fst, String snd) {
        this.fst = fst;
        this.snd = snd;
    }
    String getFirst() {
        return fst;
    }
    String getSecond() {
        return snd;
    }
}

/*
 三つのインターフェースを実装したリッチな StringPair
 */
class RichStringPair extends StringPair
    implements Comparable<RichStringPair>,
               Iterator<String>, Iterable<String> {
    // コンストラクタは親と同じく文字列二つを取る定義
    RichStringPair(String fst, String snd) {
        super(fst, snd);
    }
    // 文字列への変換メソッドのオーバーライド
    public String toString() {
        return "(\"" + getFirst() + "\", \"" + getSecond() + "\")";
    }

    /* 以下、インターフェースを実装するために必要なメソッド等を書くこと */
    // void compare(){
    //     //最初の文字が一緒だったら次の文字を比較
    //     if(fst.compareTo(snd) == 0){
    //         //次の文字を比較する。-1だったら交換する？fstとsnd
    //     }
    // }

    public int compareTo(RichStringPair e){
        return super.fst.compareTo(super.snd);
    }
    

    /* fill here */
    public boolean hasNext(){
        if(compareTo(this) == 1){
            return true; 
        }else if(compareTo(this) == 0){
            String temp;
            temp = this.fst;
            this.fst = this.snd;
            this.snd = temp;
            if(compareTo(this) == 1){
                temp = this.fst;
                this.fst = this.snd;
                this.snd = temp;
                return true; 
            }
            return false;
        }else{
            return false;
        }
        return true;
    }
    public String next(){
        String ret = toString();

        return this;
    }
    public Iterator<String> iterator(){
        return this;
    }    
}

// RichStringPair の動作確認
class RichStringPairCheck {
    public static void main(String [] args) {
        // 拡張forでの使用：２つの要素を生成する 
        RichStringPair pair = new RichStringPair("Hello", "World");
        for(String s : pair) {
            System.out.println("s = " + s);
        }

        // 比較可能なのでソートが出来るという例：
        RichStringPair [] ps = new RichStringPair[] {
            new RichStringPair("Hello", "Java"),
            new RichStringPair("Zero", "One"),
            new RichStringPair("Hello", "World"),
            new RichStringPair("ABC", "World")};
        // Arrays: 配列のソートなどのメソッドを提供するクラス
        Arrays.sort(ps);
        for(RichStringPair p : ps) {
            System.out.println(p);
        }
    }
}
