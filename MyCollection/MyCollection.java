/*
 * Generics を用いた、型 E を格納するデータ構造
 */

import java.util.Scanner;
import java.math.BigInteger;

// 「型 E の値を格納するデータ構造」のベースクラス。
// 要素を持っているかの判定、要素の追加、要素の取り出しができる。
// 一部メソッドは実装の意味がないので abstract として本体未定義
// （この実装は完了している）
abstract class MyCollection<E> {
    String name;  // テスト用に名前を持つことにする
    int size;     // 格納している要素数
    MyCollection(String name) { // コンストラクタ：要素数を初期化する
        // メソッド引数とフィールドとで変数名が被ることは許可されている：
        // 　this を付けたらフィールド変数、付けなければ引数のローカル変数を意味する。
        this.name = name;
        size = 0;
    }
    String getName() { return name; } // 名前取得
    boolean isEmpty() { // 要素があるかどうか
        return size == 0;
    }
    abstract void add(E x); // 値を追加する
    abstract E extract();   // 値をひとつ返すとともに、その値を削除
    abstract E peek();      // extract の削除なしバージョン
}

// スタックとキューを実装するためのセル（この実装は完了している）
class MyCell<E> {
    private E val;         // セルは、E型の値をひとつ持つ
    private MyCell<E> nxt; // そして、「次のセル」の参照を持つ
    // val の値を持つセルを作るコンストラクタ。「次のセル」も受け取る。
    MyCell(E val, MyCell<E> nxt) {
        this.val = val;
        this.nxt = nxt;
    }
    void setNext(MyCell<E> nxt) { // 「次のセル」の変更
        this.nxt = nxt;
    }
    E getVal() {       // 格納されている値の取得
        return val;
    }
    MyCell<E> getNext() {  // 「次のセル」を返す
        return nxt;
    }
}

// スタックの実装（半分ほど既に埋めてある）
class MyStack<E> extends MyCollection<E> {
    MyCell<E> top; // スタックの一番上のセル
    MyStack() {
        // まず、親クラスのコンストラクタを呼ぶ
        super("MyStack");
        // スタックが空なので、top = null (セルなし)
        top = null;
    }
    void add(E x) {
        // 値に x を持ち、
        // 「次のセル」として古い top を指すセルを新たな top に。
        top = new MyCell<E>(x, top);
        size++;
    }
    E extract() {
        // top セルの値を return するために取得し、
        // 「次のセル」に記録されている古い top を復元。

        /* fill here */ 
        E x = top.getVal();
        top = top.getNext();
        size--;
        return x;
    }
    E peek() {
        // top セルの値を見れば良い
        /* fill here */ 
        return top.getVal();

    }
}

// キューの実装
class MyQueue<E> extends MyCollection<E> {
    /* fill here */
    MyCell<E> top,bot;
    MyQueue(){
        super("MyQueue");
        top = null;
        bot = null;
    }
    void add(E x) {
        MyCell c = new MyCell<E>(x, null);
        if(size == 0) {
            top = c;
            bot = c;
        }else{
            top.setNext(c);
            top = c;
        }
        size++;
    }
    E extract() {
        /* fill here */ 
        E x = bot.getVal();
        bot = bot.getNext();
        size--;
        if(size == 0) top = null;
        return x;
    }
    E peek() {
        /* fill here */ 
        return bot.getVal();
    }
}


//=========== 以下は、ここまでに定義したクラスを使ってみた例 ============

// キュートスタックに同じ操作をしたときの違いをみる
// String でも BigInteger でもデータ構造としては同じものを使えることに注意
class MyCollectionCheck {
    public static void main(String [] args) {
        Scanner sc = new Scanner(System.in);
        mainBI(sc);
        mainString(sc);
    }
    public static void mainBI(Scanner sc) {
        MyCollection<BigInteger> c1 = new MyQueue<BigInteger>();
        MyCollection<BigInteger> c2 = new MyStack<BigInteger>();
        System.out.println("Input integers (finish by initial '0', remove by initial sign '-'):");
        while(sc.hasNext()) {
            String x = sc.next();
            if(x.charAt(0) == '0') break;
            if(x.charAt(0) != '-') { // add
                c1.add(new BigInteger(x));
                c2.add(new BigInteger(x));
            } else { // remove (extract)
                c1.extract();
                c2.extract();
            }
        }
        System.out.println("Integers in the data structures:");
        for(int i = 0; i < 2; i++) {
            MyCollection<BigInteger> ic = null;
            if(i == 0) ic = c1;
            else       ic = c2;
            System.out.print(" " + ic.getName() + ":");
            // 入力されたものを出力
            while(!ic.isEmpty()) {
                System.out.print(" " + ic.extract());
            }
            System.out.println();
        }
    }
    public static void mainString(Scanner sc) {
        MyCollection<String> c1 = new MyQueue<String>();
        MyCollection<String> c2 = new MyStack<String>();
        System.out.println("Input words (finish by initial '0', remove by initial sign '-'):");
        while(sc.hasNext()) {
            String x = sc.next();
            if(x.charAt(0) == '0') break;
            if(x.charAt(0) != '-') { // add
                c1.add(x);
                c2.add(x);
            } else { // remove (extract)
                c1.extract();
                c2.extract();
            }
        }
        System.out.println("Words in the data structures:");
        for(int i = 0; i < 2; i++) {
            MyCollection<String> ic = null;
            if(i == 0) ic = c1;
            else       ic = c2;
            System.out.print(" " + ic.getName() + ":");
            // 入力されたものを出力
            while(!ic.isEmpty()) {
                System.out.print(" " + ic.extract());
            }
            System.out.println();
        }
    }
}
