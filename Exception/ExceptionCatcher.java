/*
 * 例外を指定通りにキャッチするメソッドを作る演習課題
 */

// 課題で使うインターフェースの定義
interface ExceptionThrower {
    // RuntimeException のサブクラスを投げるので、throws 宣言はない。
    void run();
    String getName(); // 表示用
}

// 実装すべきクラス
class ExceptionCatcher {
    // et.run() の投げる Exception に応じた整数値を返すメソッド。
    // 具体的な値は次のとおり：
    //   0 : 例外が投げられなかった 
    //   1 : NullPointerException 
    //   2 : NegativeArraySizeException 
    //   3 : ArithmeticException
    //   4 : ArrayIndexOutOfBoundsException 
    int catcher(ExceptionThrower et) {
        /* fill here */
        try {
            et.run();
            return 0;
        } catch (NullPointerException e) {
            //TODO: handle exception
            return 1;
        } catch (NegativeArraySizeException e) {
            //TODO: handle exception
            return 2;
        } catch (ArithmeticException e) {
            //TODO: handle exception
            return 3;
        } catch (ArrayIndexOutOfBoundsException e) {
            //TODO: handle exception
            return 4;
        }
        /* fill here */
    }
}

// ExcpetionCacher のチェック
class ExceptionCatcherCheck {
    public static void main(String [] args) {
        // それぞれの例外を投げる ExceptionThrower の配列
        ExceptionThrower [] ets = new ExceptionThrower []{
            new ExceptionThrower() {
                public void run() { int x = 1/(4-4); }
                public String getName() { return "Arithmetic"; }
            },
            new ExceptionThrower() {
                public void run() { int [] xs = new int[-1]; }
                public String getName() { return "NegativeSize"; }
            },
            new ExceptionThrower() {
                public void run() { int x = new int[]{1}[2]; }
                public String getName() { return "ArrayIndex"; }
            },
            new ExceptionThrower() {
                public void run() { String s = null; s.equals(s); }
                public String getName() { return "NullPo"; }
            },
            new ExceptionThrower() {
                public void run() { /* do nothing */ }
                public String getName() { return "Safe"; }
            },
        };
        // ExceptionCatcher に一つずつ入れてみる
        ExceptionCatcher ec = new ExceptionCatcher();
        for(ExceptionThrower et : ets) {
            int res = ec.catcher(et);
            System.out.println("catcher(" + et.getName() + ") = " + res);
        }
    }
}