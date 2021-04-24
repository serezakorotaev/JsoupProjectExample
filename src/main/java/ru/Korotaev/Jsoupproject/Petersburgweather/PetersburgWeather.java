package ru.Korotaev.Jsoupproject.Petersburgweather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Korotaev.Jsoupproject.Interfaces.GetPage;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PetersburgWeather implements GetPage {
    private static final String allInformationFromTable = "table[class=wt]";
    private static final String informationAboutAllDays = "tr[class=wth]";
    private static final String informationAboutTemperature = "tr[valign=top]";
    private static final String informationAboutDay = "th[id=dt]";

    @Override
    public Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url) , 5000);
    }

    //22.04 Суббота погода сегодня
    //22.04
    private static final Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}");

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string");
    }

    static int checkingWhatTimeIsIt(Elements values , int iterationCount) {
        Element valueLn = values.get(3);
        boolean isMorning = valueLn.text().contains("Утро");
        boolean isDay = valueLn.text().contains("День");
        boolean isEvening = valueLn.text().contains("Вечер");
        boolean isNight = valueLn.text().contains("Ночь");

        if (isMorning) {
            iterationCount = 3;
        } else if (isDay) {
            iterationCount = 2;
        } else if (isEvening) {
            iterationCount = 1;
        } else if (isNight) {
            iterationCount = 0;
        }
        return iterationCount;
    }

    private static int printForValues(Elements values , int index) {
        int iterationCount = 4;

        if (index == 0) {
            iterationCount = checkingWhatTimeIsIt(values , iterationCount);
            //для каждого случай (утро, день, вечер, ночь надо сделать собственный цикл. вохможно через switch для подсчета индекса и итератора
        }
        for (int i = 0; i < iterationCount; i++) {
            Element valueLine = values.get(index + i);
            for (Element td : valueLine.select("td")) {
                System.out.print(td.text() + "       ");
            }
            System.out.println();
        }
//        }
        return iterationCount;
    }

    public static void main(String[] args) throws Exception {
        PetersburgWeather petersburgWeather = new PetersburgWeather();
        Document page = petersburgWeather.getPage("https://pogoda.spb.ru");
        Element tableWth = page.select(allInformationFromTable).first();
        Elements names = tableWth.select(informationAboutAllDays);
        Elements values = tableWth.select(informationAboutTemperature);
        int index = 0;
        for (Element name : names) {
            String dateString = name.select(informationAboutDay).text();//для нахождения нужной информации используем регулярные выражения
            String date = getDateFromString(dateString);
            System.out.println(date + "    Явление   Температура   Давл    Влажность   Ветер");
            int iterationCount = printForValues(values , index);
            index = index + iterationCount;
        }

    }
}
