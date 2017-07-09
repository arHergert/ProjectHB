package com.game.leveldesign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Zusammenfuassung aller Definitionen für die Erstellung der Font, die im
 * Spiel erstellt wird. Schrift ist "Code New Roman" und die Farbe #43435d
 *
 * Created by Artur on 03.07.2017.
 */
public class TryAndCatchFont extends BitmapFont{

    /** Erstellte Font mit der man Schrift zeichnen kann */
    private BitmapFont font;

    /**
     * Zeichnet mithilfe der Font "Code New Roman" und der Farbe #43435d
     * einen Text auf den Bildschirm
     *
     * @param size Schriftgröße
     */
    public TryAndCatchFont(int size){

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.color = Color.valueOf("43435d");
        fontParameter.size = size;
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("CodeNewRoman.otf"));
        fontGenerator.scaleForPixelHeight(9);
        fontParameter.minFilter = Texture.TextureFilter.Nearest;
        fontParameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();

    }


    public TryAndCatchFont(int size, String hexColor){

        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.color = Color.valueOf(hexColor);
        fontParameter.size = size;
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("CodeNewRoman.otf"));
        fontGenerator.scaleForPixelHeight(9);
        fontParameter.minFilter = Texture.TextureFilter.Nearest;
        fontParameter.magFilter = Texture.TextureFilter.MipMapLinearNearest;
        font = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();

    }



    @Override
    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y) {
        return font.draw(batch, str, x, y);
    }


}//end class TryAndCatchFont
