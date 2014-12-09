package br.org.unisal.pedropaulomonteiro.golden_axe_andengine;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

public class GoldenAxe extends SimpleBaseGameActivity {
	
	private static final int LARGURA_CAMERA = 800;
	private static final int ALTURA_CAMERA = 480;
	private ITexture texturaBackground;
	private RepeatingSpriteBackground gramaBackground;
	private DigitalOnScreenControl mDigitalOnScreenControl;
	private AssetBitmapTexture textura;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private Jogador player;
	private Inimigo inimigo;
	private Camera camera;
	private AssetBitmapTexture mOnScreenControlBaseTexture;
	private AssetBitmapTexture mOnScreenControlKnobTexture;
	private TextureRegion mOnScreenControlKnobTextureRegion;

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, LARGURA_CAMERA, ALTURA_CAMERA);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(LARGURA_CAMERA, ALTURA_CAMERA),
				camera);
	}

	@Override
	public void onCreateResources() throws IOException {
		this.texturaBackground = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/background_grass.png",BitmapTextureFormat.RGB_565,TextureOptions.REPEATING_BILINEAR);
		this.texturaBackground.load();

		final ITextureRegion backgroundTextureRegion = TextureRegionFactory.extractFromTexture(this.texturaBackground);
		this.gramaBackground = new RepeatingSpriteBackground(LARGURA_CAMERA, ALTURA_CAMERA, backgroundTextureRegion,1,this.getVertexBufferObjectManager());

		player = new Jogador(this.getTextureManager(), this.getAssets(),
				this.getVertexBufferObjectManager(), camera);
		
		inimigo = new Inimigo(this.getTextureManager(), this.getAssets(),
				this.getVertexBufferObjectManager(), camera);
		
		
		this.mOnScreenControlBaseTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/onscreen_control_base.png", TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory.extractFromTexture(this.mOnScreenControlBaseTexture);
		this.mOnScreenControlBaseTexture.load();

		this.mOnScreenControlKnobTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/onscreen_control_knob.png", TextureOptions.BILINEAR);
		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory.extractFromTexture(this.mOnScreenControlKnobTexture);
		this.mOnScreenControlKnobTexture.load();

	}

	@Override
	public Scene onCreateScene() {
		//this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		scene.setBackground(this.gramaBackground);
		scene.setBackgroundEnabled(true);
		scene.attachChild(player.obtemSprite());
		scene.attachChild(inimigo.obtemSprite());
		
		final DigitalOnScreenControl analogOnScreenControl = new DigitalOnScreenControl(0, 0, camera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, this.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				player.andar(pValueX, pValueY);
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
			}
		});

		final Sprite controlBase = analogOnScreenControl.getControlBase();
		controlBase.setAlpha(1.0f);
		controlBase.setOffsetCenter(0, 0);
		analogOnScreenControl.getControlKnob().setScale(1.25f);
		scene.setChildScene(analogOnScreenControl);
		return scene;
	}
}
