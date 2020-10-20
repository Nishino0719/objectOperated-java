
class StringMethods {
    // 文字列 s の何文字目に p が現れるか
    // 例えば、s の先頭が p なら1文字目なので 1 が結果
    // 現れないなら -1
    int find(String s, String p) {
        int i = s.indexOf(p);
        if(i < 0){
            return -1;
        }else{
            return i + 1;
        }
    }
    // s の中に p がいくつ現れるか（重複OK）
    // 例えば、"HoHoHo Ho" に "HoHo" は重複ありで 2度
    int count(String s, String p) {
        return 0;
    }
    // s の中に p がいくつ現れるか（重複なし）
    // 例えば、"HoHoHo Ho" に "HoHo" は重複無しで 1度
    int countNOV(String s, String p) {
        int count = 0;
        int subString = 0;
        int i;
        i = s.indexOf(p,subString);
        while(i != -1){
            i = s.indexOf(p,subString);
            if(i != -1){
                count++;
            }
            subString = i + p.length();
        }
        return count;
    }
    // s の中の、文字 b と文字 e の間の部分文字列を返す
    // b は s 中の最左、e は b 以降の最左をとる
    // 対象となる b や e が無いなら空文字列を返す
    String between(String s, char b, char e) {
        int left;
        int right;
        left = s.indexOf(b);
        right = s.indexOf(e) + 1;
        if(right > left){
            return s.substring(left,right);
        }else{
            return " ";
        }
    }
    // 配列 ss 内の全文字列を空白 ' ' でつないだ文字列を返す
    String concat(String [] ss) {
        String connect = new String();
        for(int i=0;i<ss.length;i++){
            connect =  connect + ss[i] + ' ';
        }
        return connect;
    }
    // 与えられた文字列をひっくり返した文字列を返す
    String reverse(String s) {
        StringBuilder sb = new StringBuilder(s);
        s = sb.reverse().toString();
        return s;
    }
    public static void main(String [] args) {
        // テスト用のコードはこの main に書いて
        // java StringMethods とすれば実行できる
        // 適宜自分でテストを書いて自分のコードの動作確認をすること
        StringMethods sm = new StringMethods();
        // System.out.println(sm.countNOV("hohoho ho", "hoho"));
        // System.out.println(sm.count("Hello Java World", "Java"));
        // System.out.println(sm.count("Hello Java World", "Python"));

        
        // System.out.println(sm.find("Hello World", "or"));
        // System.out.println(sm.count("HoHoHo Ho", "HoHo"));
        // System.out.println(sm.countNOV("HoHoHo Ho", "HoHo"));
        // System.out.println(sm.between("Hello World", 'e', 'o'));
        // System.out.println(sm.concat(new String [] { "Hello", "World" }));
        // System.out.println(sm.reverse("Hello World"));
        
    }
}
