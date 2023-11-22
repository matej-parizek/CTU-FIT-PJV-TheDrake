package thedrake.animation;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import thedrake.action.PlayingSide;
import thedrake.action.Troop;
import thedrake.action.TroopFace;
import thedrake.action.TroopTile;

import java.awt.*;

public class TroopView {
    private final Troop troop;
    private final PlayingSide side;

    public TroopView(PlayingSide side, Troop troop){
        this.side=side;
        this.troop=troop;
    }


    public ImageView getStackView(){
        Image image= this.selectAVERS();
        ImageView imageViev=new ImageView(image);
        imageViev.setFitHeight(100);
        imageViev.setFitWidth(100);
        return imageViev;
    }
    public Image placeOnBoard(TroopTile tile){
        if(tile.face()== TroopFace.AVERS)
            return this.selectAVERS();
        else
            return this.selectREVERS();
    }


    public Image selectAVERS(){
        if(troop.name() == "Archer" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontArcherB.png");
        else if(troop.name() == "Archer" && side==PlayingSide.ORANGE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontArcherO.png");
        else if(troop.name() == "Clubman" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontClubmanB.png");
        else if(troop.name() == "Clubman" && side==PlayingSide.ORANGE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontClubmanO.png");
        else if(troop.name() == "Drake" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontDrakeB.png");
        else if(troop.name() == "Drake" && side==PlayingSide.ORANGE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontDrakeO.png");
        else if(troop.name() == "Spearman" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontSpearmanB.png");
        else if(troop.name() == "Spearman" && side==PlayingSide.ORANGE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontSpearmanO.png");
        else if(troop.name() == "Swordsman" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontSwordsmanB.png");
        else if(troop.name() == "Swordsman" && side==PlayingSide.ORANGE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontSwordsmanO.png");
        else if(troop.name() == "Monk" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontMonkB.png");
        return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\frontMonkO.png");
    }
    public Image selectREVERS(){
        if(troop.name() == "Archer" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backArcherB.png");
        else if(troop.name() == "Archer" && side==PlayingSide.ORANGE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backArcherO.png");
        else if(troop.name() == "Clubman" && side==PlayingSide.BLUE) {
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backClubmanB.png");
        }
        else if(troop.name() == "Clubman" && side==PlayingSide.ORANGE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backClubmanO.png");
        else if(troop.name() == "Drake" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backDrakeB.png");
        else if(troop.name() == "Drake" && side==PlayingSide.ORANGE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backDrakeO.png");
        else if(troop.name() == "Spearman" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backSpearmanB.png");
        else if(troop.name() == "Spearman" && side==PlayingSide.ORANGE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backSpearmanO.png");
        else if(troop.name() == "Swordsman" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backSwordsmanB.png");
        else if(troop.name() == "Swordsman" && side==PlayingSide.ORANGE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backSwordsmanO.png");
        else if(troop.name() == "Monk" && side==PlayingSide.BLUE)
            return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backMonkB.png");
        return new Image("C:\\Users\\matpa\\OneDrive\\Plocha\\THEDRAKE\\src\\main\\resources\\Images\\backMonkO.png");
    }
}
