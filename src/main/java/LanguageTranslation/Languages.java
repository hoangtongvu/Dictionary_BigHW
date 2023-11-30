package LanguageTranslation;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Languages
{

    private final List<Pair<String, String>> langs;
    private final LangLoader langLoader;

    public List<Pair<String, String>> getLangs() {
        return langs;
    }

    public Languages()
    {
        this.langs = new ArrayList<>();
        this.langLoader = new LangLoader(this);
        this.langLoader.LoadLanguages();
    }

    public static void main(String[] args) {
        Languages languages = new Languages();
        System.out.println(languages.langs);
    }

}
