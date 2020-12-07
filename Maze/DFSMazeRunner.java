/**
 * コマンドライン引数に指定したテキストファイルから迷路を読み込み、脱出経路を表示する{@code main} をもつ．探索は深さ優先探索で．
 */
class DFSMazeRunner {
    public static void main(String [] args) throws Exception {
        if(args.length < 1) {
            System.out.println("java DFSMazeRunner mazefile [-g | -w]");
            System.out.println("  -g : the mazefile is of graph-mazes");
            return;
        }
        boolean graph = false;
        if(args.length >= 2) {
            if(args[1].equals("-g")) graph = true;
        }
        // TextMaze も GraphMaze も Maze<Pos> を実装するので、
        // 全てに対して同じ DFSMazeSolver.solve を使うことができる。
        if(!graph) {
            DFSMazeSolver.solve(new TextMaze(args[0]));
        } else {
            DFSMazeSolver.solve(new GraphMaze(args[0]));
        }
    }
}
