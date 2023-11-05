package Word;


import java.sql.SQLException;

public class WordExample
{
    private String prefixSymbol = "➥ ";
    private String example = "";
    private String translation = "";

    public WordExample(){}
    public WordExample(String example, String translation) {
        if (example != null ) {
            this.example = example;
        }
        if (translation != null) {
            this.translation = translation;
        }
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }


    public String GetInfo(String prefixSpace)   {
        String exampleText= "<span class = \"exampleText\"> " + prefixSpace + prefixSymbol + example + ": </span>";
        exampleText += "<span class=\"translation\">" + translation  + "</span> <br>";
        return exampleText;
    }

}
