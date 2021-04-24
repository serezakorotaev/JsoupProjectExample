package ru.Korotaev.Jsoupproject.Moscowweather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.Korotaev.Jsoupproject.Interfaces.GetPage;

import java.io.IOException;
import java.net.URL;

public class MoscowWeather implements GetPage {
    private static final String linkForInformationAboutAllStringInTable = "div[id=WxuDailyWeatherCard-main-bb1a17e7-dc20-421a-b1b8-c117308c6626]";
    private static final String informationAboutWeatherOnSeveralDays = "a[class=Column--innerWrapper--3K14X  Button--default--2yeqQ]";
    private static final String informationAboutWeatherOnToday = "span[class=Ellipsis--ellipsis--lfjoB]";
    private static final String informationAboutAfternoonTemperature = "div[data-testid=SegmentHighTemp]";
    private static final String informationAboutEveningTemperature = "div[data-testid=SegmentLowTemp]";

    @Override
    public Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url) , 5000);
    }

    public static void main(String[] args) throws Exception {
        //create object because need create method from interface
        MoscowWeather moscowWeather = new MoscowWeather();
        Document page = moscowWeather.getPage("https://weather.com");
        Element tableWth = page.select(linkForInformationAboutAllStringInTable).first();

        Elements allWeatherTodayWithAllInformation = tableWth.select("li");
        for (Element element : allWeatherTodayWithAllInformation) {
            //find necessary link in different links with temperature and get all information from it is link
            Elements weatherToday = element.select(informationAboutWeatherOnSeveralDays);
            Elements temperatureAndHumidity = weatherToday.select("div");
            // Break it into smaller parts with week's day and day's temperature
            for (Element weathToday : weatherToday) {

                String weathTodayString = weathToday.select(informationAboutWeatherOnToday).text();
                System.out.println(weathTodayString);
                for (Element temperatureInDay : temperatureAndHumidity) {
                    String temperatureHigh = temperatureInDay.select(informationAboutAfternoonTemperature).text();
                    String temperatureLow = temperatureInDay.select(informationAboutEveningTemperature).text();
                    System.out.println(temperatureHigh + temperatureLow);
                }
            }
        }
    }
}
