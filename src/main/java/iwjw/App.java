package iwjw;

import proxy.MinUser;
import proxy.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws ParseException {
        dateFormat();
    }

    public static void lastMonthLastDay() throws ParseException {
        String month = "2017-01";
        DateFormat format = new SimpleDateFormat("yyyy-MM");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");


        Date date = format.parse(month);
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));

        //cale.setTime(date);
        //int lastDay = cale.getActualMaximum(cale.get(Calendar.MONTH));
        //System.out.println(lastDay);
        //String dateStr = format2.format(cale.getTime());
        System.out.println(format2.format(cale.getTime()));
    }

    public static void regexDate() {
        String month = "2017-03-23-2017-03-24";
        String week = "2017-03-23";
        Pattern pattern = Pattern.compile("(\\d{4}-\\d{2}-\\d{2})-(\\d{4}-\\d{2}-\\d{2})");
        Matcher matcher = pattern.matcher(month);
        Matcher matcher2=  pattern.matcher(week);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }

        while (matcher2.find()) {
            System.out.println(matcher2.group(0));
            System.out.println(matcher2.group(1));
            System.out.println(matcher2.group(2));
        }
    }

    public static void listRemove() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("1");
        list.add("1");
        for (String str : list) {
            if (str.equals("1")) {
                list.remove(str);
            }
        }

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String ss = iterator.next();
            if (ss.equals("1")) {
                list.remove(ss);
            }
        }
    }

    // 获得当前周- 周一的日期
    private static String getCurrentMonday(int i) {
        int mondayPlus;
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.DATE, - i);
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            mondayPlus = -6;
        } else {
            mondayPlus = 2 - dayOfWeek;
        }

        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(cd.getTime());
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(monday);
    }

    // 获得当前周- 周日  的日期
    private static String getPreviousSunday(int i) {
        int mondayPlus;
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.DATE, -i);
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            mondayPlus = -6;
        } else {
            mondayPlus = 2 - dayOfWeek;
        }

        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(cd.getTime());
        currentDate.add(GregorianCalendar.DATE, mondayPlus +6);
        Date sunday = currentDate.getTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(sunday);
    }

    public static void divide() {
        long s = 234562323;
        System.out.println(Math.round(s/100));
    }

    public static void getConfigurationByEnv() {
        Properties properties = new Properties();
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream("../conf/jdbc.properties");
        try {
            properties.load(inputStream);
            System.out.println(properties.getProperty("username"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testIntegerEqByte() {
        Byte a = new Byte("20");
        Integer b = new Integer("20");
        System.out.println(a.equals(b));

        System.out.println(Integer.valueOf(a).equals(b));
    }

    public static void getObjFromListByBinarySearch() {
        long startTime = System.currentTimeMillis();

        List<Long> list = new ArrayList<Long>();
        long id = 1;
        while (id < 20000) {
            list.add(id);
            id++;
        }
        Long[] ids = new Long[list.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = list.get(i);
        }
        long needId = Arrays.binarySearch(ids, 1800L);
        System.out.println(list.get(Long.valueOf(needId).intValue()));

        long endTime = System.currentTimeMillis();
        System.out.println("BinarySearch时间：" + (endTime - startTime));
    }

    public static void getObjFromListByLoop() {
        long startTime = System.currentTimeMillis();

        List<Long> list = new ArrayList<Long>();
        long id = 1;
        while (id < 20000) {
            list.add(id);
            id++;
        }
        Long need = new Long("1800");
        for (int i = 0; i < list.size(); i++) {
            if (need.equals(list.get(i))) {
                need = list.get(i);
            }
        }
        System.out.println(need);

        long endTime = System.currentTimeMillis();
        System.out.println("Loop时间：" + (endTime - startTime));
    }

    public static void castObject() {
        User user = new User();
        MinUser minUser = (MinUser) user;
        System.out.println(minUser);

        //System.out.println(user == minUser);
    }

    public static void dateFormat() throws ParseException {
        String dateStr = "2017-05-23";
        DateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = format.parse(dateStr);
        dateStr = format.format(date);
        System.out.println(dateStr);
    }

    @Test
    public void StringValueOfTest() {
        Object a = null;
        System.out.println(StringUtils.isBlank(String.valueOf(a)));
    }
}


