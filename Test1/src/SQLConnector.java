import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.List;

public class SQLConnector implements Runnable {

    private static final String url = "jdbc:mysql://localhost:3306/world";
    private static final String user = "root";
    private static final String password = "1234";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs1;
    private static ResultSet rs2;

    private static long currentID;
    private static long totalSize;

    public static void setTotalSize(int TotalSize)
    {
        totalSize = TotalSize;
    }

    public static void getProgress()
    {
        new Thread(new SQLConnector()).start();
    }

    @Override
    public void run() {

        for ( int k = 0; k < totalSize; k++)
        {
            long currentProgress = totalSize - currentID;
            if (currentProgress == 1)
            {
                System.out.println("Импорт завершен!");
                return;
            }
            System.out.println("Импортировано " + currentID + "; осталось " + currentProgress + " из " + totalSize);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    static void ConnectAndInsert() {

        // запросы на создание таблицы и рассчет суммы и медианы
        String query0 = "drop table if exists `world`.`strings`;";

        String query1 = "CREATE TABLE `strings` (`id` int(20) NOT NULL,`randomdate` varchar(20) NOT NULL,`latinstring` varchar(20) NOT NULL,`russtring` varchar(20) NOT NULL,`intnumber` varchar(20) NOT NULL,`floatnumber` varchar(20) NOT NULL, PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=cp1251;";

        String query = "INSERT INTO strings(id, randomdate, latinstring, russtring, intnumber, floatnumber) VALUES (?,?,?,?,?,?)";

        String queryMed = """
                SELECT AVG(dd.floatnumber/2) as median_val
                FROM (
                SELECT d.floatnumber, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum
                  FROM strings d, (SELECT @rownum:=0) r
                  WHERE d.floatnumber is NOT NULL
                  -- put some where clause here
                  ORDER BY d.floatnumber
                ) as dd
                WHERE dd.row_number IN ( FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2) );""";

        try {
            con = DriverManager.getConnection(url, user, password);

            stmt = con.createStatement();

            // считываем данные с объединенного файла
            File file = new File("ResultingFile.txt");

            int rs11 = stmt.executeUpdate(query0);
            int rs21 = stmt.executeUpdate(query1);

            List< String > lines = Files.readAllLines(file.toPath());

            String line;
            String[] chunks;
            int id = 0;
            String randomDate;
            String latinString;
            String rusString;
            int intNum;
            float floatNum;
            PreparedStatement statement = con.prepareStatement(query);

            for (int k = 0; k < totalSize; k++)
            {
                line = lines.get(k);
                chunks = line.split("//");

                currentID = id++;
                randomDate = chunks[0];
                latinString = chunks[1];
                rusString = chunks[2];
                intNum = Integer.parseInt(chunks[3]);
                floatNum = Float.parseFloat(chunks[4]);

                statement.setInt(1, id);
                statement.setString(2, randomDate);
                statement.setString(3, latinString);
                statement.setString(4, rusString);
                statement.setInt(5, intNum);
                statement.setFloat(6, floatNum);
                statement.execute();

            }

            String sqlS="Select Sum(intnumber) as sumprice from strings";
            PreparedStatement pst1 = con.prepareStatement(sqlS);
            rs1 = pst1.executeQuery();
            if(rs1.next()) {
                String sum = rs1.getString("sumprice");
                System.out.println("Сумма всех целых чисел : " + sum);
            }

            PreparedStatement pst2 = con.prepareStatement(queryMed);
            rs2 = pst2.executeQuery();
            if(rs2.next()) {
                String sum = rs2.getString("median_val");
                System.out.println("Медиана всех дробных чисел : " + sum);
            }

        } catch (SQLException | IOException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs1.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs2.close(); } catch(SQLException se) { /*can't do anything */ }

        }
    }
}