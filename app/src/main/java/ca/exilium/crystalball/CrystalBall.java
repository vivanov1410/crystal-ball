package ca.exilium.crystalball;

import java.util.Random;

/**
 * Created by vivanov on 7/29/2014.
 */
public class CrystalBall {

  private String[] _answers = {
      "Ну конечно же ДА",
      "Так было решено",
      "Все указывает на то, что ДА",
      "Мой ответ НЕТ",
      "Сомневаюсь",
      "Я лучше тебе не скажу",
      "Сконцентрируйся и спроси снова",
      "Не могу сейчас ответить",
      "Помоему я уже ответил",
      "Ну не надо так"
  };

  public String getAnAnswer() {
    Random random = new Random();
    int answerIndex = random.nextInt(_answers.length);
    String answer = _answers[answerIndex];

    return answer;
  }
}
