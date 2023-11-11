package WordEditing;

import Word.WordExample;

public class ExampleNode extends WordSceneNode {
    private WordExample content;

    public WordExample getContent() {
        return content;
    }

    public void setContent(WordExample content) {
        this.content = content;
    }

    public ExampleNode() {
        super("Example");
        setOptions();
    }

    @Override
    protected void setOptions() {
        options.getOptions().getItems().addAll(options.getConnect(),options.getDelete());
    }

    @Override
    public void addChild(WordSceneNode wordSceneNode) {

    }
}
