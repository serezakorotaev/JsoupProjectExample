package ru.Korotaev.Jsoupproject.Moscowweather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Korotaev.Jsoupproject.Interfaces.GetPage;

import java.io.IOException;
import java.net.URL;

public class MoscowWeather implements GetPage {
    @Override
    public Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url) , 5000);
    }
    public static void main(String[] args) throws Exception {
        MoscowWeather moscowWeather = new MoscowWeather();
        Document page = moscowWeather.getPage("https://weather.com");
        Element tableWth = page.select("div[id=WxuDailyWeatherCard-main-bb1a17e7-dc20-421a-b1b8-c117308c6626]").first();

        Elements allWeatherTodayWithAllInformation = tableWth.select("li");
        for (Element element : allWeatherTodayWithAllInformation) {
            //find necessary link in different links with temperature and get all information from it is link
            Elements weatherToday = element.select("a[class=Column--innerWrapper--3K14X  Button--default--2yeqQ]");
            Elements temperatureAndHumidity = weatherToday.select("div");
            // Break it into smaller parts with week's day and day's temperature
            for (Element weathToday : weatherToday) {

                String weathTodayString = weathToday.select("span[class=Ellipsis--ellipsis--lfjoB]").text();
                System.out.println(weathTodayString);
                for (Element temperatureInDay : temperatureAndHumidity) {
                    String temperatureHigh = temperatureInDay.select("div[data-testid=SegmentHighTemp]").text();
                    String temperatureLow = temperatureInDay.select("div[data-testid=SegmentLowTemp]").text();
                    System.out.println(temperatureHigh + temperatureLow);
                }
            }
        }
    }
}
