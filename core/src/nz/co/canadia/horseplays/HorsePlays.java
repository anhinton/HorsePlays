package nz.co.canadia.horseplays;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

import nz.co.canadia.horseplays.screens.TheatreScreen;
import nz.co.canadia.horseplays.util.Constants;
import nz.co.canadia.horseplays.util.FontLoader;

public class HorsePlays extends Game {
	private float musicVolume;
	private float soundVolume;
	private int uiWidth;
	private int uiHeight;
	public FontLoader fontLoader;
	public AssetManager manager;
	public SpriteBatch batch;

	public HorsePlays(FontLoader fontLoader) {
		this.fontLoader = fontLoader;
	}

	@Override
	public void create () {
		// catch Back key on Android
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			Gdx.input.setCatchKey(Input.Keys.BACK, true);
		}

		float aspectRatio = (float) Gdx.graphics.getBackBufferWidth() / Gdx.graphics.getBackBufferHeight();
		float gameRatio = (float) Constants.APP_WIDTH / Constants.APP_HEIGHT;
		if (aspectRatio >= gameRatio) {
			uiWidth = MathUtils.round(Gdx.graphics.getBackBufferHeight() * 16f / 9);
			uiHeight = Gdx.graphics.getBackBufferHeight();
		} else {
			uiWidth = Gdx.graphics.getBackBufferWidth();
			uiHeight = MathUtils.round(Gdx.graphics.getBackBufferWidth() * 9f / 16);
		}

		manager = new AssetManager();
		fontLoader.loadBigFont(manager, uiHeight);
		fontLoader.loadSmallFont(manager, uiHeight);
		manager.load("graphics/graphics.atlas", TextureAtlas.class);
		manager.load("audio/cough.mp3", Sound.class);
		manager.load("audio/ooh.mp3", Sound.class);
		manager.load("audio/music.mp3", Music.class);
		manager.finishLoading();

		batch = new SpriteBatch();
		musicVolume = 0;
		soundVolume = 0;

		// show the intro screen
		this.setScreen(new nz.co.canadia.horseplays.screens.TitleScreen(this));
		// TODO: Go back to title screen after play debugging is finished
//		this.setScreen(new TheatreScreen(this, Gdx.files.internal("playscripts/longfingers.xml"), false));
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

	public int getUiWidth() {
		return uiWidth;
	}

	public int getUiHeight() {
		return uiHeight;
	}
}
