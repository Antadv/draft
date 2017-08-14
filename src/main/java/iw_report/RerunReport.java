package iw_report;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 * Created by liubingguang on 2017/7/28.
 */
public class RerunReport {

    public static void main(String[] args) {
        rerunReport();
    }

    public static void loopDate() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;
        try {
            startDate = format.parse("2017-06-22");
            endDate = new Date();
        } catch (ParseException e) {
            throw new RuntimeException("时间格式化错误");
        }

        Calendar calendar = Calendar.getInstance();
        while (startDate.before(endDate)) {
            System.out.println(format.format(startDate));
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, 1);
            startDate = calendar.getTime();
        }
    }

    public static void rerunReport() {
        String url = "http://reportgen.superjia.com/auto_execute.json";
        Map<String, String> param = new HashMap<>();
        param.put("ids", "1127,1113,1121,1103,1107,1109,1125,1119,1115,1105,1123,1126,1112,1120,1102,1106,1108,1124,1118,1114,1104,1122");
        //param.put("JSESSIONID", "2775E5EA1A50998D9FDAA55BE8210817");
        //param.put("username", "reportAdmin");
        //param.put("password", "iwjw_report_28");

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        Date endDate;
        try {
            startDate = format.parse("2017-06-24");
            endDate = new Date();
        } catch (ParseException e) {
            throw new RuntimeException("时间格式化错误");
        }

        long startTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        while (startDate.before(endDate)) {
            try {
                param.put("date", format.format(startDate));

                System.out.println("start: date " + param.get("date"));

                calendar.setTime(startDate);
                calendar.add(Calendar.DATE, 1);
                startDate = calendar.getTime();

                String result = Jsoup.connect(url)
                        .ignoreContentType(true)
                        .cookie("JSESSIONID", "2775E5EA1A50998D9FDAA55BE8210817")
                        .data(param).timeout(1000 * 3600)
                        .get()
                        .text();
                System.out.println(result);

            } catch (IOException e) {
                System.out.println("====超时了");
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("=============执行完成！");
        System.out.println("=============耗时" + (endTime - startTime));
    }
}
