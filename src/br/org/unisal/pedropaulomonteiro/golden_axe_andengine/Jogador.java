package br.org.unisal.pedropaulomonteiro.golden_axe_andengine;
import java.io.IOException;

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

public class Jogador {

	private static final String imagem = "gfx/goblin.png";
	private ITexture textura;
	private TiledTextureRegion texturaTiled;
	private static final int colunas_textura_tiled = 11;
	private static final int linhas_textura_tiled = 5;
	private AnimatedSprite player;
	private Camera camera;
	
	private enum Direcao {
        DIREITA, ESQUERDA, CIMA, BAIXO, 
        DIREITA_CIMA, DIREITA_BAIXO, ESQUERDA_CIMA,ESQUERDA_BAIXO,
        NEHHUMA
	}
	
	private Direcao direcaoJogador;

	public Jogador(TextureManager tm, AssetManager am, VertexBufferObjectManager vbom, Camera camera){
		carregarTexturas(tm, am);
		criarSprite(vbom);
		player.setPosition(camera.getWidth()/2, camera.getHeight()/2);
		direcaoJogador = Direcao.NEHHUMA;
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
		
		if(x==1) {
				if(direcaoJogador != Direcao.DIREITA){
						player.animate(new long[]{200,200,200,200,200,200,200,200}, 11, 18, true);
						direcaoJogador = Direcao.DIREITA;
					}
					player.setPosition(player.getX()+x*5,player.getY()+y*5);
					return;
		}
			
		else if(x==-1) {
				Log.d("andar","ESQUERDA");
					if(direcaoJogador != Direcao.ESQUERDA){
						player.animate(new long[]{200,200,200,200,200,200,200,200}, 33, 40, true);
						direcaoJogador = Direcao.ESQUERDA;
					}
					player.setPosition(player.getX()+x*5,player.getY()+y*5);
					return;
			}
		
		else if(y==-1) {
			Log.d("andar","BAIXO");
				if(direcaoJogador != Direcao.BAIXO){
					player.animate(new long[]{200,200,200,200,200,200,200,200}, 0, 7, true);
					direcaoJogador = Direcao.BAIXO;
				}
				player.setPosition(player.getX()+x*5,player.getY()+y*5);
				return;
		}
		
		
		else if(y==1) {
			Log.d("andar","CIMA");
				if(direcaoJogador != Direcao.CIMA){
					player.animate(new long[]{200,200,200,200,200,200,200,200}, 22, 29, true);
					direcaoJogador = Direcao.CIMA;
				}
				player.setPosition(player.getX()+x*5,player.getY()+y*5);
				return;
		}
		
		else{
            if (player.isAnimationRunning()){
                    player.stopAnimation();
                    direcaoJogador = Direcao.NEHHUMA;
            }
		}
	}	
}


