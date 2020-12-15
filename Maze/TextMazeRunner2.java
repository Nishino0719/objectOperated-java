
/**
 * コマンドライン引数に指定したテキストファイルから迷路を読み込み、脱出経路を表示する{@code main} をもつ．
 */
class TextMazeRunner2 {
    public static void main(String [] args) throws Exception {
        if(args.length < 1) {
            System.out.println("java TextMazeRunner2 mazefile [-b | -d]");
            System.out.println("  -b : using BFS (default)");
            System.out.println("  -d : using DFS");
            return;
        }
        boolean bfs = true;
        if(args.length >= 2) {
            if(args[1].equals("-d")) bfs = false;
        }
        // MyQueue と MyStack がともに MyCollection<E> を実装するため、
        // BFS と DFS とに同じ MazeSolver.solve で対応できる。
        // （という形に実装せよ）
        TextMaze tm = new TextMaze(args[0]);
        if(bfs) {
            MazeSolver.solve(tm, new MyQueue<IntPair>());
        } else {
            MazeSolver.solve(tm, new MyStack<IntPair>());
        }
    }
}