package ru.Korotaev.Jsoupproject.Interfaces;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface GetPage {
     Document getPage(String url) throws IOException;
}
