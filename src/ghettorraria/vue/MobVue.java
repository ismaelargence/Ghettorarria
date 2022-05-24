package ghettorraria.vue;

import ghettorraria.modele.Mob;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class MobVue {
    
    private BorderPane border;
    private Mob mob;

    public MobVue(BorderPane border, Mob mob){
        super();
        this.border = border;
        this.mob = mob;
    }

    public void placerMob(){
        Image imageMob = new Image("ressources/singe.png");
        ImageView mobMap = new ImageView(imageMob);
        mobMap.translateXProperty().bind(mob.xProperty());
        mobMap.translateYProperty().bind(mob.yProperty());
        mob.xProperty().setValue(320);
        border.getChildren().add(mobMap);
    }
}
