package application.baseball;

import application.constant.GameStatus;
import application.constant.Message;
import application.model.Computer;
import application.model.Player;
import application.view.Input;
import application.view.Output;

public class Game {
    private final Computer computer;
    private final Player player;
    private final Input input;
    private final Judgement judgement;
    private final Output output;
    private Integer gameStatus;
    public Game() {
        this.computer = new Computer();
        this.player = new Player();
        this.input = new Input();
        this.judgement = new Judgement();
        this.output = new Output();
    }

    private void init() {
        computer.createAnswerWithRandom();
        this.gameStatus = GameStatus.PREPARING.getCode();
    }
    //플레이
    public void play() {
        init();
        while(!gameStatus.equals(GameStatus.END.getCode())) {
            output.printNewLine("컴퓨터: " + computer.getAnswer().toString());

            output.printNowLine(Message.getInputNumberToPlay());
            String[] inputs = input.getNumber();

            player.generateNumbers(inputs);
            output.printNewLine("유저: " + player.getNumbers().toString());

            int strike = judgement.getStrikeCount(computer.getAnswer(), player.getNumbers());
            int ball = judgement.getBallCount(computer.getAnswer(), player.getNumbers());

            String status = Message.getStatus(ball, strike);
            output.printNewLine(status);

            if(status.equals(Message.getThreeStrike())) {
                if(retry()) {
                    init();
                } else {
                    gameStatus = GameStatus.END.getCode();
                }
            }
        }
    }

    private boolean retry() {
        output.printNewLine(Message.getRetry());
        return input.getRetry() == GameStatus.PLAYING.getCode();
    }
}
