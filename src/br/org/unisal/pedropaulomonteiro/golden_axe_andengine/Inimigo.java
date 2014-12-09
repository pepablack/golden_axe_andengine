package br.org.unisal.pedropaulomonteiro.golden_axe_andengine;
import java.io.IOException;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseSineInOut;

import android.content.res.AssetManager;
import android.util.Log;

public class Inimigo {

	private static final String imagem = "gfx/golem-walk.png";
	private ITexture textura;
	private TiledTextureRegion texturaTiled;
	private static final int colunas_textura_tiled = 7;
	private static final int linhas_textura_tiled = 4;
	private AnimatedSprite player;
	private Camera camera;
	
	private enum Direcao {
        DIREITA, ESQUERDA, CIMA, BAIXO, 
        DIREITA_CIMA, DIREITA_BAIXO, ESQUERDA_CIMA,ESQUERDA_BAIXO,
        NEHHUMA
	}
	
	private Direcao direcaoInimigo;

	public Inimigo(TextureManager tm, AssetManager am, VertexBufferObjectManager vbom, Camera camera){
		carregarTexturas(tm, am);
		criarSprite(vbom);
		Random a = new Random();
		player.setPosition(a.nextInt((int) camera.getWidth()), a.nextInt((int) camera.getHeight()));
		direcaoInimigo = Direcao.NEHHUMA;
	}
	private void carregarTexturas(TextureManager tm, AssetManager am){
		try {
			textura = new AssetBitmapTexture(tm, am, imagem);
		} catch (IOException e) {
			e.printStackTrace();
		}
		texturaTiled = TextureRegionFactory.extractTiledFromTexture(textura, colunas_textura_tiled, linhas_textura_tiled);
		textura.load();
	}
	
	private void criarSprite(VertexBufferObjectManager vbom) {
		player = new AnimatedSprite(10, 10,texturaTiled, vbom);	
	}
	
	public AnimatedSprite obtemSprite(){
		return player;
	}

	public void andar(float x, float y) {
		
		
		if(x>=0.5 && x<=1 && y >=0 && y<=0.5) {
				Log.d("andar","DIREITA" + y);
					if(direcaoInimigo != Direcao.DIREITA){
						player.animate(new long[]{200, 200, 200,200,200,200,200}, 12, 18, true);
						direcaoInimigo = Direcao.DIREITA;
					}
					player.setPosition(player.getX()+x*5,player.getY()+y*5);
					return;
		}
			
		if(x>=0.5 && x<=1 && y >=0 && y<=0.5) {
				Log.d("andar","ESQUERDA");
					if(direcaoInimigo != Direcao.ESQUERDA){
						player.animate(new long[]{200, 200, 200,200,200,200,200}, 34, 40, true);
						direcaoInimigo = Direcao.ESQUERDA;
					}
					player.setPosition(player.getX()+x*5,player.getY()+y*5);
					return;
			}
			
		}	
}


