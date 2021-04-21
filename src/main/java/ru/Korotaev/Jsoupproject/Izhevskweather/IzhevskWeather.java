package ru.Korotaev.Jsoupproject.Izhevskweather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Korotaev.Jsoupproject.Interfaces.GetPage;

import java.io.IOException;
import java.net.URL;

public class IzhevskWeather implements GetPage {

@Override
public Document getPage(String url) throws IOException {
    return Jsoup.parse(new URL(url) , 5000);
}
    private static int printHowCountOursInTodayDay(Elements areaTime ,int index){
        int iterator = 4;
        String cssQuery = "td[colspan=2]";
        Element areaTimeOneDay = areaTime.get(1);
        Elements time = areaTimeOneDay.select(cssQuery);
        //Temperature
        Element allTemperatureInTable = areaTime.get(5);
        Elements allTemperature = allTemperatureInTable.select(cssQuery);
        Elements temperatureInDate = allTemperature.select("div[class=t_0]");
        //sunrise and sunset
        Element allSunriseAndSunsetInTable = areaTime.get(12);
        Elements allSunriseAndSunset = allSunriseAndSunsetInTable.select(cssQuery);
        if (index ==0){
            Element time1 = time.get(0);

            if (time1.text().contains("10")) {
                iterator = 3;
            } else if (time1.text().contains("16")) {
                iterator = 2;
            } else if (time1.text().contains("22")) {
                iterator = 1;
            }
        }
            for (int i = 0; i<iterator;i++){
                Element time2 = time.get(index + i);
                System.out.print(time2.text() + " ");
            }
        System.out.println();
            for (int i = 0; i<iterator; i++){
                Element temperature = temperatureInDate.get(index +i);
                System.out.print(temperature.text() + " ");
            }
        System.out.println();
            for(int i = 0; i<iterator; i++){
                Element sunriseAndSunset = allSunriseAndSunset.get(index+i);
                System.out.print(sunriseAndSunset.text()+ " ");
            }
            return iterator;
    }
    public static void main(String[] args) throws IOException {
    IzhevskWeather izhevskWeather = new IzhevskWeather();
        Document page = izhevskWeather.getPage("https://rp5.ru/Погода_в_Ижевске");
        Element forecastTable = page.select("table[class=forecastTable]").first();
        Elements days = forecastTable.select("tr[class=forecastDate]");
        Elements areaTime = forecastTable.select("tr");
        int index = 0;
        for (Element weekDay : days) {//на один элемент сделать методы разных циклов с разными данными
            Elements day = weekDay.select("td");
            for (Element day1 : day) {//получение дней недели по очереди. (цикл дней)
                if(day1 == day.last()) break;
                System.out.print(day1.text());
                System.out.println();
                int iteratorCount = printHowCountOursInTodayDay(areaTime,index);
                index = index + iteratorCount;
                System.out.println();

            }
        }
    }


}