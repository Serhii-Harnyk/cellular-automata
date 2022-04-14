package sharnyk.testca.mapgen.screens;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GameConfig {

  private Integer scale = 1;
  private int density = 50;
  private int colors = 4;
  private int neighSize = 1;
  private String topology = "Plain";
  private String neigh = "Moore";

}
