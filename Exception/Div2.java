/*
 * 例外を指定通りに投げるメソッドを作る演習課題
 */

// この演習課題で投げられる例外の定義
class NonPositiveException extends Exception { }
class OddNumberException extends Exception { }

// 実装すべきクラス
class Div2 {
    // 正の偶数を受け取って、その 1/2 を返すメソッド
    // 正の数でなければ NonPositiveException を投げ、
    // 正だけど偶数でなければ OddNumberException を投げる
    int div2(int x) /* fill here */ 
}

// Div2 のチェック
class Div2Check {
    public static void main(String [] args) {
        Div2 d = new Div2();
        int [] is = {4, 0, -3, 3}; // 配列の初期化構文を使っている
        for(int i : is) {
            System.out.print("div2(" + i + ") ");
            try {
                int res = d.div2(i);
                System.out.println("= " + res);
            } catch(NonPositiveException e) {
                System.out.println(" :: NonPositiveException");
            } catch(OddNumberException e) {
                System.out.println(" :: OddNumberException");
            }
        }
    }
}