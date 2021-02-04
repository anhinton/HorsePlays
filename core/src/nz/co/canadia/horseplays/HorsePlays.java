package nz.co.canadia.horseplays;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class HorsePlays extends Game {
	public SpriteBatch batch;
	private float musicVolume;

	@Override
	public void create () {
		// catch Back key on Android
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			Gdx.input.setCatchKey(Input.Keys.BACK, true);
		}

		batch = new SpriteBatch();
		musicVolume = 0;

		// show the intro screen
		this.setScreen(new nz.co.canadia.horseplays.screens.TitleScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public float getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(float musicVolume) {
		this.musicVolume = MathUtils.clamp(musicVolume, 0, 1);
	}
}
