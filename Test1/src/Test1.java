import java.util.*;

public class Test1 {
    public static void main(String [] args) throws Exception {
        int strNum = 100;
        int fileNum = 100;
        // FileBuilder.getFile(fileNum, strNum);   //   <-- если раскомментировать, будет генерировать (strNum * fileNum) строк
        Scanner in = new Scanner(System.in);
        System.out.print("Введите критерий удаления строки : ");
        String crit = in.nextLine();      //  <-- если строка содержит подстроку crit, строка удаляется из объединенного файла
        int numDelStr = FileBuilder.combineFile(fileNum, strNum, crit);
        SQLConnector.setTotalSize((fileNum * strNum) - numDelStr);
        SQLConnector.getProgress();    // <-- выводит прогресс импорта строк
        SQLConnector.ConnectAndInsert();     // <-- подключается к таблице БД, импортирует строки и считает сумму и медиану
    }
}
