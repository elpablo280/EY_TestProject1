import java.math.BigDecimal;

public class StringBuilder
{
    static String getString(int strNum)
    {
        // создание StringBuffer для сохранения результата
        int f = strNum;
        StringBuffer[] r = new StringBuffer[strNum];
        while (f-- > 0) {
            r[f] = new StringBuffer();
        }

        String nl = System.lineSeparator();

        for (int j = 0; j < strNum; j++)
        {
            // генерация даты
            int year = 2016 + (int) Math.round(Math.random() * (5));
            int month = 1 + (int) Math.round(Math.random() * (12));
            int dayOfYear = 1 + (int) Math.round(Math.random() * (30));
            r[j].append(year).append(".").append(month).append(".").append(dayOfYear);

            r[j].append("//");

            // генерация строки латинских символов
            String alphabetLat = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

            for (int i = 0; i < 10; i++) {
                int random = (int) (Math.random() * alphabetLat.length());
                char c = alphabetLat.charAt(random);
                r[j].append(c);
            }

            r[j].append("//");

            // генерация строки русских символов
            String alphabetRus = "йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";

            for (int i = 0; i < 10; i++) {
                int random = (int) (Math.random() * alphabetRus.length());
                char c = alphabetRus.charAt(random);
                r[j].append(c);
            }

            r[j].append("//");

            // генерация четного целого числа
            int a = ((int) (Math.random() * 49999999)) * 2;
            String num1 = Integer.toString(a);

            for (int i = 0; i < num1.length(); i++) {
                char c = num1.charAt(i);
                r[j].append(c);
            }

            r[j].append("//");

            // генерация числа с плавающей точкой
            double b = 1 + (Math.random() * 19);
            BigDecimal g = new BigDecimal(b);
            double b1 = g.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
            String num2 = Double.toString(b1);

            for (int i = 0; i < num2.length(); i++) {
                char c = num2.charAt(i);
                r[j].append(c);
            }

            r[j].append("//");
            r[j].append(nl);
        }
        // возвращаем результирующую строку
        java.lang.StringBuilder string = new java.lang.StringBuilder();

        for (int i = 0; i < strNum; i++){
            string.append(r[i]);
        }

        return string.toString();
    }
}
