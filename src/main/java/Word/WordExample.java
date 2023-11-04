package Word;


public class WordExample 
{
    private String prefixSymbol = "➥ ";
    private String example;
    private String translation;

    public WordExample(){}
    public WordExample(String example, String translation) {
        this.example = example;
        this.translation = translation;
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


    public String GetInfo(String prefixSpace)
    {
        String temp = "<h3>" + prefixSpace + example + "\n" + "</h3>";
        temp += "<h4>" + prefixSpace + prefixSymbol + translation + "<h4>";
        return temp;
    }
}
