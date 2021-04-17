package ru.Korotaev.Jsoupproject.Moscowweather;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MoscowWeather {

    private static Document getPage() throws IOException {
        String url = "https://weather.com";
                return Jsoup.parse(new URL(url), 3000);
    }

    private static final Pattern pattern = Pattern.compile("\\w{2}\\s\\d{2}");

    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string");
    }

    public static void main(String[] args) throws IOException {
        Document page = getPage();
        Element tableWth = page.select("div[id=WxuDailyWeatherCard-main-bb1a17e7-dc20-421a-b1b8-c117308c6626]").first();
        Elements weatherToday = tableWth.select("li[class=Column--column--2bRa6Column--active--FeXwd]");
        Elements names = tableWth.select("li[class=Column--column--2bRa6]");
        System.out.println(tableWth.text());
        for(Element weathToday : weatherToday){
            System.out.println(weathToday.text());
        }
        for(Element name : names){
            System.out.println(name.text());
        }
    }
}
