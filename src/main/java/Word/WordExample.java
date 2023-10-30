package Word;


public class WordExample 
{
    private String prefixSymbol = "➥ ";
    private String example;
    private String exampleDefinition;

    public void setExampleDefinition(String exampleDefinition) {
        this.exampleDefinition = exampleDefinition;
    }

    public WordExample(String example, String exampleDefinition)
    {
        this.example = example;
        this.exampleDefinition = exampleDefinition;
    }

    public String GetInfo(String prefixSpace)
    {
        String temp = "<h3>" + prefixSpace + example + "\n" + "</h3>";
        temp += "<h4>" + prefixSpace + prefixSymbol + exampleDefinition + "<h4>";
        return temp;
    }
}
