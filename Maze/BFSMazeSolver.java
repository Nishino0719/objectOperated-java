import java.util.*;
public class BFSMazeSolver {
    public static <Pos> void solve(Maze<Pos> m) {
        // 「ある位置に、どの位置から来たか？」を記録する連想配列
        HashMap<Pos, Pos> prev = new HashMap<Pos, Pos>();
        // これからチェックすべき位置のキュー
        LinkedList<Pos> que = new LinkedList<Pos>();
        // キューに入れたことのある位置の集まり
        HashSet<Pos> seen = new HashSet<Pos>();
        Pos st = m.getStart(); // スタート位置
        que.add(m.getStart()); // スタート位置をキューに追加
        seen.add(m.getStart()); // スタート位置をキューに入れたと記憶
        while(que.size() > 0) { // キューに残りがある限り回る
            Pos p = que.remove(); // キューの先頭の位置を取り出す
            if(m.isGoal(p)) { // ゴールに辿り着いたか？
                // ゴールに着いたら、そこに至る経路を逆向きにたどる
                ArrayList<Pos> ps = new ArrayList<Pos>();
                for(Pos c = p; c != null; c = prev.get(c)) {
                    ps.add(c);
                }
                Collections.reverse(ps); // 経路を正しい向きに直す
                m.printAnswer(ps); // 答えを出力
                return;
            }
            List<Pos> ns = m.getNeighbors(p); // 隣の位置を取得
            for( Pos n : ns ) { // 移動できる隣の位置 n の全てについて
                if(!seen.contains(n)) { // まだキューに入れてないなら
                    que.add(n);  // キューに入れて、そのうち探索
                    seen.add(n);
                    prev.put(n, p); // n への移動は p からであった、という記録
                }
            }
        }
        System.out.println("impossible");
    }
}