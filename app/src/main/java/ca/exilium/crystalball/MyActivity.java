package ca.exilium.crystalball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MyActivity extends Activity {

  private CrystalBall _crystalBall = new CrystalBall();
  private TextView _answerLabel;
  private ImageView _crystalBallImage;
  private SensorManager _sensorManager;
  private Sensor _accelerometer;
  private ShakeDetector _shakeDetector;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my);

    _answerLabel = (TextView) findViewById(R.id.textView);
    _crystalBallImage = (ImageView) findViewById(R.id.imageView);

    _sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    _accelerometer = _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    _shakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
      @Override
      public void onShake() {
        handleNewAnswer();
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    _sensorManager.registerListener(_shakeDetector, _accelerometer, _sensorManager.SENSOR_DELAY_UI);
  }

  @Override
  public void onPause() {
    super.onPause();
    _sensorManager.unregisterListener(_shakeDetector);
  }

  private void handleNewAnswer() {
    String answer = _crystalBall.getAnAnswer();
    _answerLabel.setText(answer);
    animateCrystalBall();
    fadeInAnswer();
    playSound();
    fadeOutAnswer();
  }

  private void animateCrystalBall() {
    _crystalBallImage.setImageResource(R.drawable.ball_animation);
    AnimationDrawable ballAnimation = (AnimationDrawable) _crystalBallImage.getDrawable();

    if (ballAnimation.isRunning()) {
      ballAnimation.stop();
    }

    ballAnimation.start();
  }

  private void fadeInAnswer() {
    AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
    fadeInAnimation.setDuration(1500);
    fadeInAnimation.setFillAfter(true);

    _answerLabel.setAnimation(fadeInAnimation);
  }

  private void fadeOutAnswer() {
    AlphaAnimation fadeOutAnimation = new AlphaAnimation(1, 0);
    fadeOutAnimation.setDuration(3000);
    fadeOutAnimation.setStartOffset(2000);
    fadeOutAnimation.setFillAfter(true);

    _answerLabel.setAnimation(fadeOutAnimation);
  }

  private void playSound() {
    MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
    player.start();
    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
      @Override
      public void onCompletion(MediaPlayer mp) {
        mp.release();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.my, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
