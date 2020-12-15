import java.util.*;
/**
 * DFSMazeSolver と BFSMazeSolver の solve の，差異を吸収するためのインターフェース．
 * 端的には，差異の数だけメソッドが必要であるということ．
 * @param <E> 要素の型．
 */
interface Adapter<E> {
    /**
     * 新しい、空のインスタンスを準備するメソッド．
     */
    void init();
    // TODO: 必要なだけメソッドを追加する
    void addValue(E value);
    int size();
    E getValue();
}

/**
 * DFSMazeSolver と BFSMazeSolver の共通コード部分．
 * 差異は，Adapter<E> で吸収する．
 */
class MazeSolver {
    /**
     * DFSMazeSolver と BFSMazeSolver の solve を共通化したコード．
     * 差異の部分は Adapter<E> のメソッドに置き換えて，その具体的なコードは Adapter<E> を継承する各クラスに記述する．
     * なお，メソッドの最初の方で　Adapter<Pos> の新しいインスタンスを生成しているため，引数に Adapter<Pos> collection_instance を受け取るのは無駄に思うかもしれない．
     * しかし，Java ではある型の新しいインスタンスを作るためにはその型の別のインスタンスが手元にある必要がある（もしくはその型の Class オブジェクトがほしい）．そのため，余計な引数を受け取る実装になっている．
     */
    public static <Pos> void solve(Maze<Pos> m, Adapter<Pos> collection) {
        // 「ある位置に、どの位置から来たか？」を記録する連想配列
        HashMap<Pos, Pos> prev = new HashMap<Pos, Pos>();
        // これからチェックすべき位置を貯める構造を内部に用意させる
        collection.init();

        // TODO: あとは BFSMazeSolver.solve と DFSMazeSolver.solve のコードの続きを、
        // その差異の部分を Adapter<Pos> のメソッド呼び出しに置き換えつつ記述
        HashSet<Pos> seen = new HashSet<Pos>();
        Pos st = m.getStart();
        collection.addValue(m.getStart());
        seen.add(m.getStart());
        while(collection.size() > 0){
            Pos p = collection.getValue();
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
            List<Pos> ns = m.getNeighbors(p);
            for(Pos n: ns){
                if(!seen.contains(n)){
                    collection.addValue(n);
                    seen.add(n);
                    prev.put(n,p);
                }
            }
        }
        System.out.println("impossible");
    }
}




/**
 * DFSMazeSolver の差異部分を書き下したもの．
 * 差異吸収のためのインターフェース Adapter<E> を実装する．
 * つまり，各メソッドの中身は，DFSMazeSolver の solve のコードの一部である．
 */
class MyStack<E> implements Adapter<E> {
    Stack<E> st;
    MyStack() {
        st = null; // まだ準備できてない
    }
    public void init() {
        st = new Stack<E>(); // 空の Stack を準備
    }

    // 必要なだけメソッドを実装
    public void addValue(E value){
        st.push(value);
    }

    public int size(){
        return st.size();
    }

    public E getValue(){
        return st.pop();
    }
    
}

/**
 * BFSMazeSolver の差異部分を書き下したもの．
 * 差異吸収のためのインターフェース Adapter<E> を実装する．
 * つまり，各メソッドの中身は，BFSMazeSolver の solve のコードの一部である．
 */
class MyQueue<E> implements Adapter<E> {
    LinkedList<E> que;
    MyQueue() {
        que = null; // 準備できてない状態
    }
    public void init() {
        que = new LinkedList<E>(); // 空の LinkedList を準備
    }

    // 必要なだけメソッドを実装
    public void addValue(E value){
        que.add(value);
    }

    public int size(){
        return que.size();
    }

    public E getValue(){
        return que.remove();
    }
    
}