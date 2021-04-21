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
        Elements weatherToday = tableWth.select("a[class=Column--innerWrapper--3K14X  Button--default--2yeqQ]");
//        Elements names = tableWth.select("li[class=Column--column--2bRa6]");
        Elements temperatureAndHumidity = weatherToday.select("div");
        //
        for(Element weathToday : weatherToday){
            String weathTodayString = weathToday.select("span[class=Ellipsis--ellipsis--lfjoB]").text();
            System.out.println(weathTodayString);
            System.out.println(temperatureAndHumidity.text());
        }
    }
}
