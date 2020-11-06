// 既存のライブラリ（クラス）を使うためのインポート宣言
// import java.util.*; でもよい
import java.util.Scanner;

// Sum に、「加算倍率」を付け加えたもの
class Sum2 {
    // 現在の合計を保持するフィールド。
    // private で、このインスタンスだけが読み書きができるよう制限。
    private int sum;
    // 現在の加算倍率を保持するフィールド
        /* fill here */
    private int multi;
    // コンストラクタ：インスタンスの初期化を行う特殊なメソッド
    Sum2() {
        this.sum = 0;  // 自分(this)の持つフィールド sum を 0 に初期化
        /* fill here */
        this.multi = 1;
    }
    // 受け取った x に現在の倍率を掛けてを現在の合計に足し込むメソッド
    void add(int x) {
        /* fill here */
    	this.sum += x * this.multi;
    }
    // [from, to] の区間の整数を全て足し込むメソッド
    void addFromTo(int from, int to) {
        // for文による繰り返し
        for(int i = from; i <= to; i++) {
            this.add(i);  // 自身の他のメソッドを呼ぶことも可能
        }
    }
    // 現在の倍率を与えられた m に設定するメソッド
    void setMultiplier(int m) {
        /* fill here */
    	this.multi = m;
    }
    // 現在の倍率を返すメソッド
    int getMultiplier() {
        /* fill here */
    	return this.multi;
    }
    // 現在の合計（sum）を返すメソッド
    int getSum() {
        return this.sum;
    }
}

// Sum2 の動作確認用クラス
class Sum2Check {
    // いわゆる main 関数。
    public static void main(String [] args) {
        Sum2 s = new Sum2(); // Sum クラスのインスタンス生成
        try (// ターミナルから数値を読むための Scanner のインスタンス生成
		Scanner sc = new Scanner(System.in)) {
			// 無限ループ
			while(true) {
			    // 古い倍率を表示
			    System.out.println("Previous multiplier = " + s.getMultiplier());
			    // 新たな倍率を入力してもらう
			    System.out.print("Multiplier?: ");
			    if(!sc.hasNextInt()) break;
			    int m = sc.nextInt();
			    if(m == 0) break; // 0 なら終わり
			    s.setMultiplier(m);  // 倍率セット
			    while(sc.hasNextInt()) {
			        int x = sc.nextInt(); // 入力された整数を読み込む
			        if(x == 0) break; // 0 が入力されたら終わり
			        // そうでなければその整数を s に与える。
			        s.add(x);
			        // 現在の合計を出力。文字列は + で連結できる。
			        // 文字列に値を + すると、文字列に変換されてから連結される
			        System.out.println("current sum = " + s.getSum());
			    }
			}
		}
        // 最後に、最後の倍率のまま 1 から 10 まで足してみる
        s.addFromTo(1, 10);
        // 合計が「55 * 最後の倍率」だけ増えているはずである
        System.out.println("sum = " + s.getSum());
    }
}