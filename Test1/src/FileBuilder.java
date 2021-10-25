import java.io.*;

public class FileBuilder
{
    static void getFile(int fileNum, int strNum) throws Exception {
        FileWriter[] nFile = new FileWriter[fileNum];
        for (int i = 0; i < fileNum; i++)
        {
            nFile[i] = new FileWriter("file" + (i + 1) + ".txt");
            nFile[i].write(StringBuilder.getString(strNum));
            nFile[i].close();
        }
    }

    static int combineFile(int fileNum, int strNum, String crit) {
        int numDelStr = 0;
        try {
            // количество удаленных строк
            FileWriter ResFile = new FileWriter("ResultingFile.txt");
            for (int i = 0; i < fileNum; i++)
            {
                File file = new File("file" + (i + 1) + ".txt");
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);
                String [][] line = new String[fileNum][strNum];
                for (int j = 0; j < strNum; j++)
                {
                    line[i][j] = reader.readLine();

                    // проверка на критерий удаления строки
                    if (!line[i][j].contains(crit))
                    {
                        ResFile.write(line[i][j]);
                        ResFile.write(System.getProperty("line.separator"));
                    }
                    else
                    {
                        numDelStr++;
                    }
                }
            }
            ResFile.close();
            System.out.println("Количество удаленных строк : " + numDelStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numDelStr;
    }
}
