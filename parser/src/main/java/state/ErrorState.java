package state;

public class ErrorState extends State {

    ErrorState(Tokenizer tokenizer) {
        super(tokenizer);
    }

    @Override
    public void nextState() {
        exit();
    }
}
