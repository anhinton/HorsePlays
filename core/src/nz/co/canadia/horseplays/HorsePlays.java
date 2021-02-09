package nz.co.canadia.horseplays;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import nz.co.canadia.horseplays.util.FontLoader;

public class HorsePlays extends Game {
	private final FontLoader fontLoader;
	public SpriteBatch batch;
	private float musicVolume;
	private float soundVolume;
	public AssetManager manager;

	public HorsePlays(FontLoader fontLoader) {
		this.fontLoader = fontLoader;
	}

	@Override
	public void create () {
		// catch Back key on Android
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			Gdx.input.setCatchKey(Input.Keys.BACK, true);
		}

		manager = new AssetManager();
		fontLoader.loadBigFont(manager);
		fontLoader.loadSmallFont(manager);
		manager.load("ui/greyBubble.png", Texture.class);
		manager.load("ui/redBubble.png", Texture.class);
		manager.load("ui/menu-icon.png", Texture.class);
		manager.finishLoading();

		batch = new SpriteBatch();
		musicVolume = 0;
		soundVolume = 0;

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

	public float getSoundVolume() {
		return soundVolume;
	}

	public void setSoundVolume(float soundVolume) {
		this.soundVolume = MathUtils.clamp(soundVolume, 0, 1);
	}
}
