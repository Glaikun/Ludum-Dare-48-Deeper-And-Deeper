package com.glaikunt.framework.splash;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.glaikunt.framework.Display;
import com.glaikunt.framework.application.Actor;
import com.glaikunt.framework.application.ApplicationResources;
import com.glaikunt.framework.application.Rectangle;
import com.glaikunt.framework.application.cache.FontCache;
import com.glaikunt.framework.application.cache.TextureCache;
import com.glaikunt.framework.esc.component.animation.AnimationComponent;
import com.glaikunt.framework.esc.component.common.SizeComponent;
import com.glaikunt.framework.esc.component.game.LevelComponent;
import com.glaikunt.framework.esc.component.misc.BloatingComponent;
import com.glaikunt.framework.game.GameScreen;
import com.glaikunt.framework.game.weapon.WeaponType;

public class ChooseWeaponActor extends Actor {

    private AnimationComponent animation;

    private Texture attackIcon, magicIcon, supportIcon;
    private Rectangle attackRect, magicRect, supportRect;

    private BitmapFont chooseYourWeaponFont;
    private GlyphLayout layout;

    private BloatingComponent bloating;

    public ChooseWeaponActor(ApplicationResources applicationResources) {
        super(applicationResources);

        this.animation = new AnimationComponent(getApplicationResources().getCacheRetriever().geTextureCache(TextureCache.NEXT_STAGE), 25, 1);
        this.animation.setPlayMode(Animation.PlayMode.NORMAL);
        this.animation.setFramerate(.2f);

        this.attackIcon = applicationResources.getTexture(TextureCache.ATTACK_ICON);
        this.magicIcon = applicationResources.getTexture(TextureCache.MAGIC_ICON);
        this.supportIcon = applicationResources.getTexture(TextureCache.SUPPORT_ICON);

        this.size = new SizeComponent(attackIcon.getWidth()*10, attackIcon.getHeight()*10);

        this.attackRect = new Rectangle();
        this.attackRect.set((Display.WORLD_WIDTH/2) - (size.x/2f), (Display.WORLD_HEIGHT/2) - (size.y/2f), size.x, size.y);

        this.magicRect = new Rectangle();
        this.magicRect.set((Display.WORLD_WIDTH/2) - (size.x/2f) - (size.x * 1.4f), (Display.WORLD_HEIGHT/2) - (size.y/2f), size.x, size.y);

        this.supportRect = new Rectangle();
        this.supportRect.set((Display.WORLD_WIDTH/2) - (size.x/2f) + (size.x * 1.4f), (Display.WORLD_HEIGHT/2) - (size.y/2f), size.x, size.y);

        this.chooseYourWeaponFont = applicationResources.getCacheRetriever().getFontCache(FontCache.BIG_FONT);
        this.layout = new GlyphLayout(chooseYourWeaponFont, "Choose Your Weapon", Color.WHITE, 1, Align.left, false);

        this.bloating = new BloatingComponent();
        this.bloating.setMaxBloating(5f);
        this.bloating.setSpeed(20f);

        Entity entity = new Entity();
        entity.add(animation);
        entity.add(bloating);
        getApplicationResources().getEngine().addEntity(entity);
    }

    @Override
    public void act(float delta) {

        if (animation.isAnimationFinished() && Gdx.input.isTouched()) {

            if (attackRect.contains(getApplicationResources().getFrontStageMousePosition().x,getApplicationResources().getFrontStageMousePosition().y )) {

                getApplicationResources().getGlobalEntity().getComponent(LevelComponent.class).setWeaponType(WeaponType.MELEE);
                getApplicationResources().getGlobalEntity().getComponent(LevelComponent.class).increateStage();
                getApplicationResources().getDisplay().setScreen(new GameScreen(getApplicationResources()));
            } else if (magicRect.contains(getApplicationResources().getFrontStageMousePosition().x,getApplicationResources().getFrontStageMousePosition().y )) {

                getApplicationResources().getGlobalEntity().getComponent(LevelComponent.class).setWeaponType(WeaponType.RANGED);
                getApplicationResources().getGlobalEntity().getComponent(LevelComponent.class).increateStage();
                getApplicationResources().getDisplay().setScreen(new GameScreen(getApplicationResources()));
            } else if (supportRect.contains(getApplicationResources().getFrontStageMousePosition().x,getApplicationResources().getFrontStageMousePosition().y )) {

                getApplicationResources().getGlobalEntity().getComponent(LevelComponent.class).setWeaponType(WeaponType.SUPPORT);
                getApplicationResources().getGlobalEntity().getComponent(LevelComponent.class).increateStage();
                getApplicationResources().getDisplay().setScreen(new GameScreen(getApplicationResources()));
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(animation.getCurrentFrame(), 0, 0);

        if (animation.isAnimationFinished()) {

            chooseYourWeaponFont.draw(batch, layout, (Display.WORLD_WIDTH/2) - (layout.width/2), (Display.WORLD_HEIGHT) - (layout.height) - 10);

            if (attackRect.contains(getApplicationResources().getFrontStageMousePosition().x,getApplicationResources().getFrontStageMousePosition().y )) {
                batch.draw(attackIcon, attackRect.x - (bloating.getBloating()/2), attackRect.y - (bloating.getBloating()/2), attackRect.width + bloating.getBloating(), attackRect.height + bloating.getBloating());
            } else {
                batch.draw(attackIcon, attackRect.x, attackRect.y, attackRect.width, attackRect.height);
            }
            if (magicRect.contains(getApplicationResources().getFrontStageMousePosition().x,getApplicationResources().getFrontStageMousePosition().y )) {
                batch.draw(magicIcon, magicRect.x - (bloating.getBloating()/2), magicRect.y - (bloating.getBloating()/2), magicRect.width + bloating.getBloating(), magicRect.height + bloating.getBloating());
            } else {
                batch.draw(magicIcon, magicRect.x, magicRect.y, magicRect.width, magicRect.height);
            }
            if (supportRect.contains(getApplicationResources().getFrontStageMousePosition().x,getApplicationResources().getFrontStageMousePosition().y )) {
                batch.draw(supportIcon, supportRect.x - (bloating.getBloating()/2), supportRect.y - (bloating.getBloating()/2), supportRect.width + bloating.getBloating(), supportRect.height + bloating.getBloating());
            } else {
                batch.draw(supportIcon, supportRect.x, supportRect.y, supportRect.width, supportRect.height);
            }
        }
    }
}
