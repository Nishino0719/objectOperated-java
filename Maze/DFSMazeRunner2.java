/**
 * コマンドライン引数に指定したテキストファイルから迷路を読み込み、脱出経路を表示する{@code main} をもつ．探索は深さ優先探索で．
 */
class DFSMazeRunner2 {
    public static void main(String [] args) throws Exception {
        if(args.length < 1) {
            System.out.println("java DFSMazeRunner2 mazefile [-g | -w]");
            System.out.println("  -w : the mazefile is of text-mazes with warps");
            System.out.println("  -g : the mazefile is of graph-mazes");
            return;
        }
        boolean graph = false;
        boolean warp = false;
        if(args.length >= 2) {
            if(args[1].equals("-g")) graph = true;
            if(args[1].equals("-w")) warp = true;
        }
        // TextMaze も TextMazeWithWarp も GraphMaze も Maze<Pos> を実装するので、
        // 全てに対して同じ DFSMazeSolver.solve を使うことができる。
        if(!graph) {
            if(!warp) {
                DFSMazeSolver.solve(new TextMaze(args[0]));
            } else {
                DFSMazeSolver.solve(new TextMazeWithWarp(args[0]));
            }
        } else {
            DFSMazeSolver.solve(new GraphMaze(args[0]));
        }
    }
}
