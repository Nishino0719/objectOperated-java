import java.util.Scanner;

public class ScannerRational {


	public static void main(String args[]) {
		Rational x, y;

		Scanner scanner = new Scanner(System.in); // 標準入力からの読み込み

		System.out.println("演算子(+,-,*,/)　有理数(x)　有理数(y)");
		while (scanner.hasNext()) {
			String input = scanner.nextLine(); // 行単位の読み込み
			String rat[] = input.split(" "); // 空白で行を分割

			if (rat.length == 3) { // ２つの値
				String num1[] = rat[1].split("/");
				String num2[] = rat[2].split("/");

				try {
					if (num1.length == 1) { // 整数
						x = new Rational(Integer.parseInt(num1[0]));
					} else if (num1.length == 2) { // 分数
						x = new Rational(Integer.parseInt(num1[0]), Integer.parseInt(num1[1]));
					} else {
						System.out.println("x:有理数に変換できません");
						continue;
					}
//				} catch (NumberFormatException e) {
//					System.out.println("数値の形式が正しくありません: x = " + rat[1]);
//					continue;
//				}

				//y {
					if (num2.length == 1) { // 整数
						y = new Rational(Integer.parseInt(num2[0]));

					} else if (num2.length == 2) { // 分数
						y = new Rational(Integer.parseInt(num2[0]), Integer.parseInt(num2[1]));

					} else {
						System.out.println("y:有理数に変換できません");
						continue;
					}
				} catch (NumberFormatException e) {
					System.out.println("数値の形式が正しくありません:  ");
					continue;
				}


				if (rat[0].equals("+")){

					System.out.println(rat[1] + " + " + rat[2] + " = " + Rational.add(x, y));
				}
				//引き算
				if (rat[0].equals("-")) {
					System.out.println(rat[1] + " - " + rat[2] + " = " + Rational.sub(x, y));
				}
				//掛け算
				if (rat[0].equals("*")) {
					System.out.println(rat[1] + " * " + rat[1] + " = " + Rational.mul(x, y));
				}
				//割り算
				if (rat[0].equals("/")) {
					System.out.println(rat[1] + " / " + rat[2] + " = " + Rational.div(x, y));
				}
			} else {
				System.out.print("引数の数が正しくありません");

				System.out.println("INPUT: " + input);
			}
		}
		scanner.close();
	}
}

class Rational {
	int den;//分母
	int mol;//分子

	public Rational(int x, int y) {

		this.mol = x;
		this.den = y;

		correct(this);
	}

	//整数を有理数に変換する
	public Rational(int x) {
		this(x, 1);
	}

	//最小公約数を求める
	int gcd(int a, int b) {
		if (b == 0)
			return a;

		return gcd(b, a % b);
	}

	Rational correct(Rational r) {

		//約分をする
		int n;

		n = gcd(r.mol, r.den);
		r.den /= n;
		r.mol /= n;

		//分母が0または負の時に処理をする
		if (r.den <= 0) {
			if (r.den == 0) {//分母が0の時終了する
				System.out.println("分母が0です");

				r.mol = 0;
				r.den = 1;
				return r;

			}
			//分母が負の時分母と分子の正負を入れ替える
			r.den *= -1;
			r.mol *= -1;
		}
		return r;
	}

	//有理数の和を求める
	public Rational add(Rational r) {

		this.mol = this.mol * r.den + r.mol * this.den;
		this.den = this.den * r.den;

		return correct(this);
	}

	//整数が来た時の和を求める
	public Rational add(int x) {
		Rational rat = new Rational(x);

		return this.add(rat);
	}

	//演算子的利用
	public static Rational add(Rational a, Rational b) {
		Rational rat = new Rational(a.mol, a.den);

		return rat.add(b);
	}

	//有理数の差を求める
	public Rational sub(Rational r) {
		this.mol = this.mol * r.den - r.mol * this.den;
		this.den = this.den * r.den;

		return correct(this);
	}

	//整数が来た時の差を求める
	public Rational sub(int x) {
		Rational rat = new Rational(x);

		return this.sub(rat);
	}

	//演算子的利用
	public static Rational sub(Rational a, Rational b) {
		Rational rat = new Rational(a.mol, a.den);

		return rat.sub(b);
	}

	//有理数の積
	public Rational mul(Rational r) {
		this.mol = this.mol * r.mol;
		this.den = this.den * r.den;

		return correct(this);
	}

	//整数が来た時の積を求める
	public Rational mul(int x) {
		Rational rat = new Rational(x);

		return this.mul(rat);
	}

	//演算子的利用
	public static Rational mul(Rational a, Rational b) {
		Rational rat = new Rational(a.mol, a.den);

		return rat.mul(b);
	}

	//有理数の商
	public Rational div(Rational r) {
		this.mol = this.mol * r.den;
		this.den = this.den * r.mol;

		return correct(this);
	}

	//整数が来た時の商を求める
	public Rational div(int x) {
		Rational rat = new Rational(x);

		return this.div(rat);
	}

	//演算子的利用
	public static Rational div(Rational a, Rational b) {
		Rational rat = new Rational(a.mol, a.den);

		return rat.div(b);
	}

	//有理数の出力表示
	public String toString() {
		if (this.den == 1) {
			return String.valueOf(this.mol);//int型をstringに変えるときstring.valueofを使う
		}
		return this.mol + "/" + this.den;//なかにstring型があるときはstring.valueofを使わなくてもよい
	}

	//インスタンスが０か否か判定
	public boolean Zero() {
		return (this.mol == 0);
	}

	public boolean lessThan(Rational r) {
		int x;
		x = this.mol * r.den - r.mol * this.den;

		return (x < 0);
	}

}
