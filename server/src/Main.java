import java.util.*;

public class Main {
    public static void main(String[] args) {
        try{
            // 標準入力
            Scanner sc = new Scanner(System.in);
            // カードサイズ
            int cardSize = Integer.parseInt(sc.nextLine());
            // ビンゴカード用の配列
            String[][] bingoCard = new String[cardSize][cardSize];
            // 入力内容のエラー数
            int errorCnt = 0;

            // ビンゴカード作成
            for(int i = 0; i < cardSize; i++){
                // 1行分の単語をスペース区切りでビンゴカードにセット
                String[] str = (sc.nextLine()).split("\\s+");

                // 入力サイズがカードサイズと不一致の場合エラーカウント
                if(str.length != cardSize){
                    errorCnt++;
                } else {
                    for(int j = 0; j < cardSize; j++){
                        bingoCard[i][j] = str[j];
                    }
                }
            }

            // 単語リスト作成
            // 単語リストサイズ
            int wordSize = Integer.parseInt(sc.nextLine());
            // 入力された単語を格納するlist
            List<String> wordList = new ArrayList<String>();

            // 入力された単語をlistに追加
            for(int i = 0; i < wordSize; i++){
                String word = sc.nextLine();

                // 空が入力されたらエラーカウント
                if(word.isEmpty()){
                    errorCnt++;
                } else {
                    wordList.add(word);
                }
            }

            // 入力内容にエラーがある場合、処理を終了する
            if(errorCnt > 0){
                System.out.println("no");
                return;
            }

            // ビンゴ判定処理実行
            int bingoCnt = bingoCheck(cardSize, bingoCard, wordList);

            // 結果出力。勝利条件を満たすかどうか（bingoCnt > 0）
            System.out.println(bingoCnt > 0 ? "yes" : "no");

        } catch(Exception e) {
            // 例外が発生した場合は、noと出力し処理を終了する
            System.out.println("no");
            return;
        }
    }

    // ビンゴ判定処理
    private static int bingoCheck(int cardSize, String[][] bingoCard, List<String> wordList){
        try{           
            // ビンゴ数
            int bingoCnt = 0;

            // 単語チェック用配列
            int[][] checked = new int[cardSize][cardSize];
            // 選ばれた単語がビンゴカードの中にあった場合、その単語を1と印をつける
            for(int i = 0; i < bingoCard.length; i++){
                for(String word : wordList){
                    int num = Arrays.asList(bingoCard[i]).indexOf(word);

                    if(num != -1) {
                        checked[i][num] = 1;
                    }
                }
            }

            // 横列判定
            // 1行の全てが1だったらビンゴカウント
            for(int i = 0; i < cardSize; i++){
                if (Arrays.stream(checked[i]).allMatch(num -> num == 1)) {
                    bingoCnt++;
                }
            }

            // 縦列判定
            // 縦列用配列
            int[][] columns = new int[cardSize][cardSize];
            // 単語チェック用配列から縦列のみの配列を作成
            for(int i = 0; i < cardSize; i++){
                for(int j = 0; j < cardSize; j++){
                    columns[i][j] = checked[j][i];
                }
            }
            // 1列の全てが1だったらビンゴカウント
            for(int i = 0; i < cardSize; i++){
                if (Arrays.stream(columns[i]).allMatch(num -> num == 1)) {
                    bingoCnt++;
                }
            }

            // 斜め列判定
            // 右斜めのカウント
            int rightCnt = 0;
            // 左斜めのカウント
            int leftCnt = 0;
            // 縦横のindexが一致する場所(右斜め)または、縦横のカードサイズ - 1(左斜め)が全て1だったらビンゴカウント
            for(int i = 0; i < cardSize; i++){
                // 1マスずつ確認
                if(checked[i][i] == 1){
                    rightCnt++;
                }
                if(checked[i][cardSize - i - 1] == 1){
                    leftCnt++;
                }
            }
            if(rightCnt == cardSize || leftCnt == cardSize){
                bingoCnt++;
            }

            // ビンゴカウントを返す
            return bingoCnt;

        } catch(Exception e) {
            // 例外が発生した場合は、-1を返す
            return -1;
        }
    }
}