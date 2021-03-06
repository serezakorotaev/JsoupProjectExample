package ru.Korotaev.Jsoupproject.Izhevskweather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Korotaev.Jsoupproject.Interfaces.GetPage;

import java.io.IOException;
import java.net.URL;

public class IzhevskWeather implements GetPage {
    private static final String allInformationFromTable = "table[class=forecastTable]";
    private static final String informationAboutDays = "tr[class=forecastDate]";
    private static final String cssQuery = "td[colspan=2]";
    private static final String centigradeTemperature = "div[class=t_0]";

    @Override
    public Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url) , 5000);
    }

    private static int checkingWhatTimeIsIt(Elements time , int iterator) {
        Element time1 = time.get(0);
        if (time1.text().contains("10")) {
            iterator = 3;
        } else if (time1.text().contains("16")) {
            iterator = 2;
        } else if (time1.text().contains("22")) {
            iterator = 1;
        }
        return iterator;
    }

    private static void methodWithCyclesForAddedUsefulInformationFromTable(Elements time , Elements temperatureInDate ,
                                                                           Elements allSunriseAndSunset , int iterator , int index) {
        for (int i = 0; i < iterator; i++) {
            Element time2 = time.get(index + i);
            System.out.print(time2.text() + " ");
        }
        System.out.println();
        for (int i = 0; i < iterator; i++) {
            Element temperature = temperatureInDate.get(index + i);
            System.out.print(temperature.text() + " ");
        }
        System.out.println();
        for (int i = 0; i < iterator; i++) {
            Element sunriseAndSunset = allSunriseAndSunset.get(index + i);
            System.out.print(sunriseAndSunset.text() + " ");
        }
    }

    private static int printHowCountOursInTodayDay(Elements areaTime , int index) {
        int iterator = 4;
        Element areaTimeOneDay = areaTime.get(1);
        Elements time = areaTimeOneDay.select(cssQuery);
        //Temperature
        Element allTemperatureInTable = areaTime.get(5);
        Elements allTemperature = allTemperatureInTable.select(cssQuery);
        Elements temperatureInDate = allTemperature.select(centigradeTemperature);
        //sunrise and sunset
        Element allSunriseAndSunsetInTable = areaTime.get(12);
        Elements allSunriseAndSunset = allSunriseAndSunsetInTable.select(cssQuery);
        if (index == 0) {
            iterator = checkingWhatTimeIsIt(time , iterator);
        }
        methodWithCyclesForAddedUsefulInformationFromTable(time , temperatureInDate , allSunriseAndSunset , iterator , index);
        return iterator;
    }

    public static void main(String[] args) throws IOException {
        IzhevskWeather izhevskWeather = new IzhevskWeather();
        Document page = izhevskWeather.getPage("https://rp5.ru/????????????_??_??????????????");
        Element forecastTable = page.select(allInformationFromTable).first();
        Elements days = forecastTable.select(informationAboutDays);
        Elements areaTime = forecastTable.select("tr");
        int index = 0;
        for (Element weekDay : days) {//???? ???????? ?????????????? ?????????????? ???????????? ???????????? ???????????? ?? ?????????????? ??????????????
            Elements day = weekDay.select("td");
            for (Element day1 : day) {//?????????????????? ???????? ???????????? ???? ??????????????. (???????? ????????)
                if (day1 == day.last()) {
                    break;
                }
                System.out.print(day1.text());
                System.out.println();
                int iteratorCount = printHowCountOursInTodayDay(areaTime , index);
                index = index + iteratorCount;
                System.out.println();

            }
        }
    }


}