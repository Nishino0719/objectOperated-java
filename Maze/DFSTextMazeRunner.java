/**
 * コマンドライン引数に指定したテキストファイルから迷路を読み込み、脱出経路を表示する{@code main} をもつ．探索は深さ優先探索で．
 */
class DFSTextMazeRunner {
    public static void main(String [] args) throws Exception {
        if(args.length < 1) {
            System.out.println("java DFSTextMazeRunner mazefile");
            return;
        }
        TextMaze tm = new TextMaze(args[0]);
        DFSMazeSolver.solve(tm);
    }
}
