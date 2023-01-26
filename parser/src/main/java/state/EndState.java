package state;

public class EndState extends State {

    EndState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void nextState() {
        exit();
    }
}
