import java.util.Scanner;
import java.math.BigInteger;

// ベースクラス： BigInteger の加算器
class BISum {
    private BigInteger sum;
    BISum() {
        sum = BigInteger.ZERO;
    }
    void add(BigInteger x) {
        sum = sum.add(x);
    }
    BigInteger getSum() {
        return sum;
    }
}

/*
　BISum を継承し、「加算倍率」の機能を付け加えたクラス BISum2 を定義せよ。
　少なくとも、以下の2つのメソッドの追加が必要：

　・現在の加算倍率を m にセットするメソッド：

　　　void setMultiplier(BigInteger m)
　
　・現在の加算倍率を返すメソッド：

　　　BigInteger getMultiplier()

　上記のほか、必要なコンストラクタやフィールドを入れること。
　更に、親クラスのメソッドの一部をオーバーライドする必要があればすること。

*/

class BISum2 extends BISum{
    /* fill here*/
    private BigInteger sum2;
    private BigInteger multi2;
    BISum2() {
        this.sum2 = BigInteger.ZERO;
        this.multi2 = BigInteger.ONE;
    }
    void add(BigInteger x) {
        this.sum2 = sum2.add(x.multiply(this.multi2));
    }

    void setMultiplier(BigInteger m){
        this.multi2 = m;
    }

    BigInteger getMultiplier(){
        return this.multi2;
    }

    BigInteger getSum(){
        return this.sum2;
    }
    
}

class BISum2Check {
    public static void main(String [] args){
        BISum2 b = new BISum2();
        // 無限ループ
        try(
            Scanner sc = new Scanner(System.in)){
			while(true) {
			    // 古い倍率を表示
			    System.out.println("Previous multiplier = " + b.getMultiplier());
			    // 新たな倍率を入力してもらう
			    System.out.print("Multiplier?: ");
			    if(!sc.hasNextBigInteger()) break;
			    BigInteger m = sc.nextBigInteger();
			    if(m.compareTo(BigInteger.ZERO)==0) break; // 0 なら終わり
			    b.setMultiplier(m);  // 倍率セット
			    while(sc.hasNextBigInteger()) {
			        BigInteger x =  sc.nextBigInteger(); // 入力された整数を読み込む
			        if(x.compareTo(BigInteger.ZERO)==0) break; // 0 が入力されたら終わり
			        // そうでなければその整数を s に与える。
			        b.add(x);
			        // 現在の合計を出力。文字列は + で連結できる。
			        // 文字列に値を + すると、文字列に変換されてから連結される
			        System.out.println("current sum = " + b.getSum());
			    }
            }
        }
    }
}