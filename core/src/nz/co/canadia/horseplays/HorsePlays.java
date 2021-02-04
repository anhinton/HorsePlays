package nz.co.canadia.horseplays;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class HorsePlays extends Game {
	public SpriteBatch batch;
	private float musicVolume;
	public AssetManager manager;

	@Override
	public void create () {
		// catch Back key on Android
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			Gdx.input.setCatchKey(Input.Keys.BACK, true);
		}

		manager = new AssetManager();
		manager.load("ui/greyBubble.png", Texture.class);
		manager.load("ui/redBubble.png", Texture.class);
		manager.load("ui/menu-icon.png", Texture.class);
		manager.load("fonts/Podkova24.fnt", BitmapFont.class);
		manager.load("fonts/Inconsolata64.fnt", BitmapFont.class);
		manager.finishLoading();

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
		manager.dispose();
		batch.dispose();
	}

	public float getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(float musicVolume) {
		this.musicVolume = MathUtils.clamp(musicVolume, 0, 1);
	}
}
