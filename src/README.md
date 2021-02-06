## オブジェクト指向最終課題

## 行列電卓を作成する。
## レポート用に既存コマンド、新たに作成したコマンドを列挙する。
### 既に既存コマンド

- eye (単位行列の生成)
- zero (ゼロ行列の生成)
- add (行列の加算)
- mat (任意の生成)
- store (行列の保存)

### 作成したコマンド

- anyn (与えられたサイズの行列に任意の数字を新たに生成した行列を返す)
- sub (行列の減算)
- mul (行列の乗算)
- anymul (任意の整数をかける)

## 実行例

- anyn

```bash
>> anyn 2 10
[  10.000   10.000]
[  10.000   10.000]
```

- sub 
 
```bash
>> sub:
..      -10 3
..      2 3
.. 
[  -10.000    -3.000]
[   -2.000    -3.000]
```

- mul
```bash
>> mat:
..      1 1
..      1 1
.. 
[   1.000    1.000]
[   1.000    1.000]
>> mul:
..      2 2
..      1 4
.. 
[   3.000    6.000]
[   3.000    6.000]
```

- anymul
```bash
>> mat:
..      2 2
..      2 2
.. 
[   2.000    2.000]
[   2.000    2.000]
>> anymul 4
[   8.000    8.000]
[   8.000    8.000]
```